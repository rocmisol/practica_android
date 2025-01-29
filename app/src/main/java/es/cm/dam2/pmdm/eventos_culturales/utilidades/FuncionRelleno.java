package es.cm.dam2.pmdm.eventos_culturales.utilidades;

import java.util.ArrayList;

import es.cm.dam2.pmdm.eventos_culturales.R;
import es.cm.dam2.pmdm.eventos_culturales.ui.Evento;

public class FuncionRelleno {

    //Método para rellenar el ArrayList Evento con todos los datos de cada uno de los eventos
    public static ArrayList<Evento> rellenaEventos() {
        String GRATUITO = "gratuito";
        ArrayList<Evento> listaEventos;

        listaEventos = new ArrayList<>();

        String descripcionReinoHielo ="Del 29 de noviembre al 5 de enero de 18.30 a 22.00 horas (los días 24 y 31 de diciembre el horario será de 18:30h a 20:00h.)\n" + "\n" +
                "“La senda de los animales del hielo” es una experiencia inmersiva al aire libre, especialmente diseñada para el huerto de Calixto y Melibea." +
                " Un paseo en el que los visitantes podrán reconocer distintos tipos de animales iluminados como osos, focas, zorros blancos, renos, morsas," +
                " búhos o leopardo de las nieves y disfrutar del momento haciendo fotos y conociendo un poco acerca de los seres que habitan una de las " +
                "regiones más frías del planeta.\n" + "\n" +
                "El hilo conductor es la recreación de las condiciones de iluminación de estas regiones, pudiendo distinguir dos zonas diferenciadas " +
                "a lo largo de la experiencia: la bóveda celeste, gracias a la recreación del paisaje de estrellas sin contaminación lumínica en la " +
                "parte inicial del recorrido (zona terrestre) y la aurora boreal a través de la recreación del juego de colores de semejante espectáculo" +
                " natural, mediante iluminación artificial, en la parte final (zona acuática).\n" + "\n" + "“La senda de los animales del hielo” se completa" +
                " con el diseño de un espacio sonoro que acompañará al visitante durante todo el recorrido. Una experiencia que se podrá apreciar en su" +
                " totalidad en la visita a este espacio y que está concebida para todos los públicos.";

        String descripcionGalder = "Si esto no es un show, se estará preguntando, ¿de qué se trata?\n" + "\n" +
                "Pues probablemente de lo mismo que otros muchos monólogos. Puede usted esperar un escenario, un micrófono y un cómico." +
                " Además, también puede esperar mucha improvisación, chistes y bloques diferentes a los que haya visto de Galder Varas en" +
                " redes (de hecho, si no ha visto nada aún, le recomiendo echar un vistazo antes de comprar una entrada, seguro que le sirve" +
                " de más ayuda que esta descripción).\n" + "\n" +
                "Y puede esperar, por último, la honesta intención de hacerle reír a carcajadas. Pero lo que no puede esperar es un show." +
                " Eso ya se lo hemos advertido.";

        String descripcionLeeane = "Una casa encaramada en una colina del extrarradio de Leenane, en la región de Connemara, al Noroeste de Irlanda," +
                " a mediados de la década de los 90. Mag Folan y su hija Maureen conviven solas desde hace veinte años.\n" + "\n" + "Sus otras dos hijas " +
                "casadas viven lejos de Leenane y Maureen, que permanece soltera con más de cuarenta años, tiene que encargarse de la alimentación y" +
                " demás cuidados de su madre, muy limitada de movimientos, incluso malherida en una mano. Sus caracteres respectivos, viciados por " +
                "silencios y mentiras, y marcados por las rutinas domésticas, por una relación maternofilial tensa y represiva, por actos traumáticos " +
                "del pasado cercano y por un historial de daños recíprocos que se irán desvelando a lo largo de la acción, se verán alterados por un " +
                "episodio local: el regreso eventual a Leenane de Pato Dooley, que trabaja de obrero en Londres, con motivo de una fiesta de despedida " +
                "familiar a un tío que retorna a América después de las vacaciones.\n" + "\n" +"Un texto de Martin Mcdonagh que nos habla de la soledad y " +
                "las relaciones familiares en las zonas más desfavorecidas y despobladas de nuestro mundo.\n" + "\n" +"Autor: Martin Mcdonagh. \n" +
                "Dirección: Juan Echanove. \n" + "Intérpretes: María Galiana, Lucía Quintana, Javier Mora y Alberto Fraga";

        String descripcionNavidadPolifQuinteto ="Programa: \n" + "  - Hallelujah\n" + "  - Joy to the world\n" + "  - Cantata 147 bach\n" +
                "  - White Christmas\n" + "  - Kamen\n" + "  - Adeste fideles\n" + " - Tamborilero\n" + "  - Santa Baby\n" + "  - Let it Snow\n" +
                "  - Jingle bells rock";

        String descricionBelen ="En colaboración con la Real Cofradía Penitencial del Santísimo Cristo Yacente de la Misericordia y de la" +
                " Agonía Redentora de Salamanca.\n" + "\n" + "Todos los días de 12’00 a 14’00 horas y de 17’00 a 21’00 horas.\n" + "\n" +
                "Cerrado los días 24 y 31 de diciembre por la tarde y 25 de diciembre y 1 de enero todo el día.\n" + "\n" +
                "Belén bíblico que recrea los paisajes, los personajes, enseres o costumbres de la época, y que representa los pasajes bíblicos" +
                " más importantes y representativos de las fechas navideñas.";

        String descripcionAnimales = "La obra transcurre en una misma noche, durante la cena.\n" + "\n" +
                "Es la mentira de un grupo de amigos que deciden simular una realidad inexistente para poder proteger a uno de los personajes." +
                " La necesidad de inventar historias para esconder las propias inseguridades y crear una ficción donde cada uno puede convertirse" +
                " en una persona mejor. Acaban saliendo los trapos sucios de todos los personajes y aquel grupo de amigos aparentemente perfectos" +
                " y agradables sacará lo peor de cada uno, como individuos y como colectivo. Se pondrá en duda el valor de la amistad y de la sinceridad." +
                " Los egos y las envidias colisionarán las unas con las otras. Animales de compañía es una obra coral de cuatro amigos que se conocen desde" +
                " hace tiempo y se reúnen para celebrar una cena de bienvenida." + "\n" + "Director: Fele Martínez.." + "\n" +"Autora: Estel Solé.\n" + "\n" +
                " Intérpretes: Mónica Regueiro, Iñaki Ardanaz, Laura Galán, Silvia Marty y Jorge Suquet/Nacho López";

        String descripcionLago ="Tchaikovsky compuso su primer ballet, El lago de los cisnes, durante 1875 – 1876, basado en el libreto de " +
                "Vladimir Beghicev y Vasili Geltzer, que transmite la idea del bien y el amor verdadero que vence al mal, centrado en la " +
                "“doncella cisne”, un símbolo de pureza y feminidad, y su amado Siegfried, cuyo amor honesto logra romper el hechizo que la " +
                "ha tenido cautiva. La actual producción dirigida por Vasile Solomon es fiel al personaje clásico original.\n" + "\n" +
                "El Teatro de Ópera y Ballet Nacional de Rumanía -epicentro de excelencia lírica y artística- se fundó el 18 de septiembre de" +
                " 1919 en Cluj, Napoca. El Ballet es un tesoro cultural que ha dejado una huella indeleble en Rumanía y más allá. Esta institución" +
                " se enorgullece de su legado que incluye más de 200 producciones de ópera, opereta y ballet, así como numerosos estrenos rumanos" +
                " significativos.\n" + "\n" +"Más allá de su compromiso con la excelencia artística, esta institución desempeña un papel vital en la " +
                "promoción de la ópera y el ballet en Rumanía, brindando una experiencia cultural rica y emocionante a su audiencia. Su misión de " +
                "apoyar y promover a artistas excepcionales continúa enriqueciendo la vida cultural de la región y más allá. El Ballet de la Ópera " +
                "Nacional de Rumania Cluj-Napoca es un testimonio de la duradera belleza del arte lírico y coreográfico.\n" + "\n" +
                "Música: Pyotr Tchaikovsky.\n" + "\n" +
                "Dirección y coreografía, adaptación después de Lev Ivanov: Vasile Solomon. \n" +
                "Asistente del coreógrafo: Anca Opris Popdan.\n" +
                "Primeros bailarines, solistas y cuerpo de baile del Ballet de la Ópera Nacional de Rumania Cluj-Napoca con más de 45 bailarines en escena.\n" +
                "Cía. Ballet de la Ópera Nacional de Rumania Cluj-Napoca";

        String descripcionCafeTeatro = "Nuevo espectáculo al más puro estilo del Cabaret que nos adentra en un mundo mágico y fascinante con sensaciones" +
                " y momentos irrepetibles.\n" + "\n" + "Un espectáculo único donde la música, el humor, la magia, la danza y el teatro se suceden vertiginosamente" +
                " a lo largo de la noche. Cita ineludible con la diversión, la risa, la emoción y la sorpresa de la mano de: Maestro Ruiz y Miguelón, " +
                "Sheila Blanco, Divinas Conmedias, Alberto Cabrillas, Jes Martin’s, Armando de Miguel y Javi & Jenny, todos ellos presentados por Don Carlos. ";


        listaEventos.add(new Evento(1,"La senda de los animales de hielo", "29/11/2024", "Exposición",
                R.drawable.senda_animales_hielo,"Huerto de Calixto y Melibea", descripcionReinoHielo, GRATUITO, false,
                4.8F, "18:30"));
        listaEventos.add(new Evento(2,"Esto no es un show", "01/12/2024", "Escena",
                R.drawable.galder,"CAEM", descripcionGalder, "20€", false, 4.5F, "17:30"));
        listaEventos.add(new Evento(3,"La reina de la belleza de Leeane", "13/12/2024", "Escena",
                R.drawable.reina_belleza,"Teatro Liceo", descripcionLeeane, "15€", false, 4.1F, "21:00"));
        listaEventos.add(new Evento(4,"Navidad Polifónica. Quinteto de Metales del COSCYL", "14/12/2024", "Música",
                R.drawable.navidad_polifonica,"Iglesia de San Sebastián", descripcionNavidadPolifQuinteto, GRATUITO, false,
                4.1F, "20:30"));
        listaEventos.add(new Evento(5, "Belén navideño", "02/12/2024", "Exposición", R.drawable.belen_navidenio,"Torre de los Anaya",
                descricionBelen, GRATUITO, false, 4.0F, "12:00"));
        listaEventos.add(new Evento(6,"Animales de compañía", "23/11/2024", "Escena",
                R.drawable.animales_compania,"Teatro Liceo", descripcionAnimales, "15€", false, 4.5F, "21:30"));
        listaEventos.add(new Evento(7,"El lago de los cisnes", "04/01/2025", "Escena",
                R.drawable.laco_cisnes,"CAEM", descripcionLago, "32€", false,4.2F, "19:00"));
        listaEventos.add(new Evento(8,"Gran café teatro", "07/12/2024", "Escena",
                R.drawable.gran_cafe,"CAEM", descripcionCafeTeatro, "18€", false,4.3F, "22:00"));

        return listaEventos;
    }
}
