package logica;

import java.time.LocalTime;
import java.util.ArrayList;

import basedatos.Ciudad;
import basedatos.HoraPunta;
import basedatos.Zona;
import mqtt.MQTTBroker;
import mqtt.MQTTPublisher;

public class HiloAlertaHorarioConf extends Thread {
    
    private Zona _zona;
    private Ciudad _ciudad;
    private ArrayList<HoraPunta> _horariosConflictivos;

    public HiloAlertaHorarioConf(Ciudad p_ciudad, Zona p_zona, ArrayList<HoraPunta> p_horariosConflictivos) {
        super();
        this._zona = p_zona;
        this._horariosConflictivos = p_horariosConflictivos;
    }

    @Override
    public void run() {
        ArrayList<HoraPunta> horariosConflictivosCopia = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (HoraPunta h: _horariosConflictivos) {horariosConflictivosCopia.add(h);}

            while (!horariosConflictivosCopia.isEmpty()) {
                for (HoraPunta h: horariosConflictivosCopia) {
                    if (h.getHoraInicio().toLocalTime().isBefore(LocalTime.now())) {
                        String topico = _ciudad.getNombre() + "/" + _zona.getNombre() + "/" + h.getNombreCalle() + "/sensores/horarioConflictivo";
                        MQTTPublisher.publish(new MQTTBroker(), topico, "6-1-0");
                    }
                    else if (h.getHoraFin().toLocalTime().isBefore(LocalTime.now())) {
                        String topico = _ciudad.getNombre() + "/" + _zona.getNombre() + "/" + h.getNombreCalle() + "/sensores/horarioConflictivo";
                        MQTTPublisher.publish(new MQTTBroker(), topico, "6-0-0");
                        horariosConflictivosCopia.remove(h);
                    }
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
