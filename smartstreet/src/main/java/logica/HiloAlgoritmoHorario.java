package logica;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import basedatos.Calle;
import basedatos.Ciudad;
import basedatos.HoraPunta;
import basedatos.Registro;
import basedatos.Zona;

public class HiloAlgoritmoHorario extends Thread{
    
    private Zona _zona;
    private Ciudad _ciudad;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH");

    public HiloAlgoritmoHorario(Ciudad p_ciudad, Zona p_zona) {
        super();
        this._zona=p_zona;
        this._ciudad = p_ciudad;
    }

    @Override
    public void run() {
        ArrayList<Calle> callesZ = Logica.getCallesZonaBD(_zona.getCodigoCiudad(), _zona.getId());

        ArrayList<HoraPunta> horariosConflictivosZona = new ArrayList<>();

        for (Calle calle : callesZ) {
            ArrayList<Registro> sensoresC = Logica.getRegistrosUltimaSemana(calle);
            ArrayList<HoraPunta> horarioConflictivo = Logica.getHorasPuntaNoFija(calle);
            ArrayList<HoraPunta> horarioFijo = Logica.getHorasPuntaFija(calle);
            ArrayList<Integer> horasInicioFijo = new ArrayList<>();
            for (HoraPunta h: horarioFijo) {
                horasInicioFijo.add(Integer.parseInt(sdf.format(h.getHoraInicio())));
            }
            ArrayList<Integer> horasInicioConflitivo = new ArrayList<>();
            for (HoraPunta h: horarioConflictivo) {
                horasInicioConflitivo.add(Integer.parseInt(sdf.format(h.getHoraInicio())));
            }
            Logica.eliminarHoraPuntaNoFija(calle);

            ArrayList<ArrayList<Time>> horarioConflictivoNuevo = calcularHorarioConflictivo(sensoresC, horasInicioConflitivo, horasInicioFijo);
            
            for (ArrayList<Time> hora : horarioConflictivoNuevo) {
                HoraPunta horaPunta = new HoraPunta(calle.getCodigoCiudad(), calle.getCodigoZona(), calle.getNombre(), hora.get(0), hora.get(1), false);
                horariosConflictivosZona.add(horaPunta);
                Logica.insertarHoraPuntaNoFija(calle.getCodigoCiudad(), calle.getCodigoZona(), calle.getNombre(), hora.get(0), hora.get(1), false);
            }
        }

        HiloAlertaHorarioConf hiloAlerta = new HiloAlertaHorarioConf(_ciudad, _zona, horariosConflictivosZona);
        hiloAlerta.start();

    }

    public ArrayList<ArrayList<Time>> calcularHorarioConflictivo(ArrayList<Registro> sensoresC, ArrayList<Integer> horasInicioConflitivo, ArrayList<Integer> horasInicioFijo) {  
        
        ArrayList<ArrayList<Time>> horarioConflictivo = new ArrayList<>();
        ArrayList<Integer> aparicionesPorHora = new ArrayList<>();
        for (int i = 0; i<24; i++) {
            if (horasInicioConflitivo.contains(i)) {
                aparicionesPorHora.add(8000);
            }
            else {
                aparicionesPorHora.add(0);
            }
        }

        for (Registro registro : sensoresC) {
            if (registro.getValor()==1) {
                int hora = Integer.parseInt(sdf.format(registro.getMarcaTemporal()));
                aparicionesPorHora.set(hora, aparicionesPorHora.get(hora)+1);
            }            
        }

        for (int i = 0; i < aparicionesPorHora.size(); i++) {
            if (!horasInicioFijo.contains(i)) {
                if (aparicionesPorHora.get(i)>30000) {
                    ArrayList<Time> horario = new ArrayList<>();
                    horario.add(new Time(3600*1000*i));
                    horario.add(new Time(3600*1000*i+1));
                    horarioConflictivo.add(horario);
                }
            }
        }

        return horarioConflictivo;
    }

}
