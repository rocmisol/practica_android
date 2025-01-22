package es.cm.dam2.pmdm.eventos_culturales;

import android.content.Intent;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import es.cm.dam2.pmdm.eventos_culturales.basedatos.AppDatabase;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.DatabaseClient;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.UsuarioDao;
import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private ImageButton imageButtonSound;
    private TextView textViewRegistro;
    private UsuarioDao usuarioDao;
    private AppDatabase database;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        boolean modoOscuro = preferencias.getBoolean("pref_tema", false);
        if (modoOscuro){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Se vinculan las vistas
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegistro = findViewById(R.id.textViewRegistrate);
        imageButtonSound = findViewById(R.id.imageButtonSoundLogin);

        //Se obtiene la base de datos
        database = DatabaseClient.getInstance(this);

        //Se obtiene el usuarioDao
        usuarioDao = database.usuarioDao();

        //Se insertan usuarios iniciales en la base de datos (si está vacía)
        insertarUsuariosPredeterminados();

        //Se iniciliza el MediaPlayer y se configura el audio en bucle
        mediaPlayer = MediaPlayer.create(this, R.raw.relaxing_guitar);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();




        //Botón Login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //Se validan los campos de entrada
                if (!email.isEmpty() && !password.isEmpty()){
                    //Se comprueba si las credenciales con correctas
                    Usuario usuario = usuarioDao.comprobarUsuarioRegistrado(email, password);
                    if (usuario != null){
                        //Se obtiene el rol de usuario
                        String rol = usuario.getRol();

                        //Se comprueba si el usuario es admin
                        if (rol != null){
                            if (rol.equals("admin")){
                                //
                            }
                            else if (rol.equals("user")){
                                // Se abre la aplicación
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "El usuario no está bien creado. Consulta con el administrador", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Introduce tu email y la contraseña para acceder a la paliación", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LLeva a la activity RegistroActivity para que el usuario se registre
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

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
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null){
            mediaPlayer.start();
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
    }

    //Método que inserta dos usuarios predeterminados enla base de datos (la 1ª vez)
    private void insertarUsuariosPredeterminados(){
        //Se comprueba que la base de datos está vacía
        if (usuarioDao.obtenerTodosUsuarios().isEmpty()){
            // Se crea el administrador y un usuario por defecto
            Usuario admin = new Usuario("admin", "666111222", "admin@eventosculturales.com", "admin", "admin");
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





}