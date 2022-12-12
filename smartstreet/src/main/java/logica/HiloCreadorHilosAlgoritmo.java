package logica;

import java.time.LocalDateTime;
import java.util.ArrayList;

import basedatos.Ciudad;
import basedatos.Zona;

public class HiloCreadorHilosAlgoritmo extends Thread{
    
    public HiloCreadorHilosAlgoritmo(){

    }

    @Override
    public void run() {
        while (LocalDateTime.now().getHour()!=0)
        {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            crearHilosAlgoritmo();
            
            try {
                sleep(1000*3600*24*7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void crearHilosAlgoritmo() {
        ArrayList<Ciudad> ciudades = Logica.getCiudadesBD();

        for (Ciudad c : ciudades) {
            ArrayList<Zona> zonas = Logica.getZonasCiudadBD(c.getCodigo());
            for (Zona z: zonas) {
                HiloAlgoritmoHorario hilo = new HiloAlgoritmoHorario(c, z);
                hilo.start();
            }
        }
    }

}
