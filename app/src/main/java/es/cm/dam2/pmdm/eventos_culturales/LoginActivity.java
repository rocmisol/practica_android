package es.cm.dam2.pmdm.eventos_culturales;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;

import es.cm.dam2.pmdm.eventos_culturales.basedatos.AppDatabase;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.DatabaseClient;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.EventoDao;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.UsuarioDao;
import es.cm.dam2.pmdm.eventos_culturales.models.Evento;
import es.cm.dam2.pmdm.eventos_culturales.models.EventoEntity;
import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;
import es.cm.dam2.pmdm.eventos_culturales.ui.LoginFragment;
import es.cm.dam2.pmdm.eventos_culturales.ui.RegistroFragment;

public class LoginActivity extends AppCompatActivity {
    private UsuarioDao usuarioDao;
    private EventoDao eventoDao;
    private AppDatabase database;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = true;
    private SharedPreferences sharedPreferences;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACTUALIZAR_SONIDO")){
                boolean activarSonido = intent.getBooleanExtra("activarSonido", true);
                configurarSonido(activarSonido);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frameLayoutLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Se carga el fragmento que se va a mostrar (Login)
        mostrarLoginFragment();

        //Se obtienen las preferencias
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuración del sonido según preferencias
        boolean activarSonido = preferencias.getBoolean("pref_sonido", true);
        //Configuración de modo claro/oscuro según preferencias
        boolean modoOscuro = preferencias.getBoolean("pref_tema", false);
        if (modoOscuro){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        database = DatabaseClient.getInstance(this);
        usuarioDao = database.usuarioDao();
        eventoDao = database.eventoDao();
        //Se insertan usuarios iniciales en la base de datos (si está vacía)
        insertarUsuariosPredeterminados();
        insertarEventosPredeterminados();

        //Se configura el sonido inicial
        configurarSonido(activarSonido);

        //Se registra el BroadcastReciver
        registerReceiver(broadcastReceiver, new IntentFilter("ACTUALIZAR_SONIDO"));

        /* Lo voy a utilizar para la agenda (Pendiente modificar)
        imageButtonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    mediaPlayer.pause();
                    imageButtonSound.setImageResource(R.drawable.icon_sound_off);
                }
                else{
                    mediaPlayer.start();
                    imageButtonSound.setImageResource(R.drawable.icon_sound_on);
                }
                isPlaying = !isPlaying;
            }
        });*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean activarSonido = preferences.getBoolean("pref_sonido", true);
        configurarSonido(activarSonido);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release(); // Se liberan recursos al cerrar la actividad
            mediaPlayer = null;
        }
        unregisterReceiver(broadcastReceiver);
    }


    //Método que inserta dos usuarios predeterminados enla base de datos (la 1ª vez)
    private void insertarUsuariosPredeterminados(){
        //Se comprueba que la base de datos está vacía
        if (usuarioDao.obtenerTodosUsuarios().isEmpty()){
            // Se crea el administrador y un usuario por defecto
            Usuario admin = new Usuario("admin", "666111222", "admin", "admin", "admin");
            Usuario user1 = new Usuario("usuario", "666111333", "usuario@eventosculturales.com", "usuario", "user");

            //Se insertan los usuarios en la base de datos
            new Thread((new Runnable() {
                @Override
                public void run() {
                    usuarioDao.insertar(admin);
                    usuarioDao.insertar(user1);
                }
            })).start();
        }
    }

    //Método que inserta eventos predeterminados enla base de datos (la 1ª vez)
    private void insertarEventosPredeterminados() {
        //Se comprueba que la base de datos está vacía
        if (eventoDao.obtenerTodosEventos().isEmpty()){
            // Se cargan los eventos en la base de datos

            ArrayList<Evento> eventos = FuncionRelleno.rellenaEventos();

            for (Evento evento : eventos)  {
                EventoEntity eventoEntity = new EventoEntity(evento.getNombre(),evento.getFecha(), evento.getCategoria(),
                        evento.getImagen(), evento.getLugar(), evento.getDescripcion(),
                        evento.getPrecio(), evento.getHora(), evento.getComentario()
                );
                //Se inserta el evento
                new Thread((new Runnable() {
                    @Override
                    public void run() {
                        eventoDao.insertar(eventoEntity);
                    }
                })).start();
            }
        }
    }

    //Método para configurar el sonido
    private void configurarSonido(boolean activarSonido){
        if (activarSonido){
            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this, R.raw.relaxing_guitar);
                mediaPlayer.setLooping(true);
            }
            mediaPlayer.start();
        }
        else{
            if (mediaPlayer != null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }
    }

    public void mostrarLoginFragment () {
        // Se obtiene una instancia de FragmentManager()
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Se crea una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Se crea una instancia del fragmento a cargar
        LoginFragment loginFragment= new LoginFragment();
        // Se añade el fragmento a la transacción indicando el contenedor en el que se va a cargar
        fragmentTransaction.replace(R.id.frameLayoutLogin, loginFragment);
        //Se ejecuta la transacción
        fragmentTransaction.commit();
    }

    public void mostrarRegistroFragment () {
        // Se obtiene una instancia de FragmentManager()
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Se crea una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Se crea una instancia del fragmento a cargar
        RegistroFragment registroFragment = new RegistroFragment();
        // Se añade el fragmento a la transacción indicando el contenedor en el que se va a cargar
        fragmentTransaction.replace(R.id.frameLayoutLogin, registroFragment);
        //Se ejecuta la transacción
        fragmentTransaction.commit();
    }

    //Método que muestra una notificación al dar de alta un usuario
    public void mostrarNotificacionAltaUsuario (String nombreUsuario){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_alta");
        builder.setSmallIcon(R.drawable.warning);
        builder.setContentTitle("Alta");
        builder.setContentText(getString(R.string.se_ha_dado_de_alta_al_usuario) + nombreUsuario);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}