package es.cm.dam2.pmdm.eventos_culturales;

import java.util.ArrayList;

public class FuncionRelleno {

    static ArrayList<Evento> rellenaEventos() {
        String GRATUITO = "gratuito";
        ArrayList<Evento> listaEventos;

        listaEventos = new ArrayList<>();




        listaEventos.add(new Evento("La senda de los animales de hielo", "29/11/2024", "Exposicion",
                R.drawable.senda_animales_hielo,"Huerto de Calixto y Melibea", "Experiencia inmersiva", GRATUITO, false,
                4.8F, "18:30"));
        listaEventos.add(new Evento("Esto no es un show", "01/12/2024", "Escena",
                R.drawable.galder,"CAEM", "Galder Varas", "20€", false, 4.5F, "17:30"));
        listaEventos.add(new Evento("La reina de la belleza de Leeane", "13/12/2024", "Escena",
                R.drawable.reina_belleza,"Teatro Liceo", "Intérpretes: María Galiana, Lucía Quintana, Javier Mora y Alberto Fraga",
                "15€", false, 4.1F, "21:00"));
        listaEventos.add(new Evento("Navidad Polifónica", "14/12/2024", "Música",
                R.drawable.navidad_polifonica,"Iglesia de San Sebastián", "Quinteto de Metales del COSCYL", GRATUITO, false,
                4.1F, "20:30"));
        listaEventos.add(new Evento("Belén navideño", "02/12/2024", "Exposición", R.drawable.belen_navidenio,"Torre de los Anaya",
                "Belén bíblico", GRATUITO, false, 4.0F, "12:00"));
        listaEventos.add(new Evento("Animales de compañía", "23/11/2024", "Escena",
                R.drawable.animales_compania,"Teatro Liceo", "Intérpretes: Mónica Regueiro, Iñaki Ardanaz, Laura Galán, Silvia Marty y Jorge Suquet/Nacho López",
                "15€", false, 4.5F, "21:30"));
        listaEventos.add(new Evento("El lago de los cisnes", "04/01/2025", "Escena",
                R.drawable.laco_cisnes,"CAEM", "Cía. Ballet de la Ópera Nacional de Rumania Cluj-Napoca", "32€", false,
                4.2F, "19:00"));
        listaEventos.add(new Evento("Gran café teatro", "07/12/2024", "Escena",
                R.drawable.gran_cafe,"CAEM", "Espectáculo al más puro estilo del Cabaret", "18€", false,
                4.3F, "22:00"));

        return listaEventos;
    }
}
