package es.cm.dam2.pmdm.eventos_culturales.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import es.cm.dam2.pmdm.eventos_culturales.LoginActivity;
import es.cm.dam2.pmdm.eventos_culturales.MainActivity;
import es.cm.dam2.pmdm.eventos_culturales.R;
import es.cm.dam2.pmdm.eventos_culturales.RegistroActivity;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.AppDatabase;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.DatabaseClient;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.UsuarioDao;
import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;


public class LoginFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private ImageButton imageButtonSound;
    private TextView textViewRegistro, textViewVienvenidoLogin;
    private UsuarioDao usuarioDao;
    private AppDatabase database;
    private SharedPreferences userSharedPreferences, defaultSharedPreferences;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Se vinculan las vistas
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        textViewRegistro = view.findViewById(R.id.textViewRegistrate);
        textViewVienvenidoLogin = view.findViewById(R.id.textViewBienvenidoLogin);
        imageButtonSound = view.findViewById(R.id.imageButtonSoundLogin);

        //Se obtiene la base de datos
        database = DatabaseClient.getInstance(getActivity());

        //Se obtiene el usuarioDao
        usuarioDao = database.usuarioDao();

        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userSharedPreferences = getActivity().getSharedPreferences("UserPrefereces", MODE_PRIVATE);



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

                        if (rol != null){
                            //Se guarda el rol en SharedPreferences
                            SharedPreferences.Editor editor = userSharedPreferences.edit();
                            editor.putString("tipoRol", rol); // Puede ser admin o user
                            editor.apply();

                            // Se obtiene el mensaje de bienvenida y se muestra al usuario
                            String mensaejBienvenida = defaultSharedPreferences.getString("pref_bienvenida", "Bienvenido");
                            textViewVienvenidoLogin.setText(mensaejBienvenida);

                            // Se abre la aplicación con un retardo de un segundo
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }, 1000);
                        }
                        else{
                            Toast.makeText(getActivity(), "El usuario no está bien " +
                                    "creado. Consulta con el administrador", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Introduce tu email y la contraseña " +
                            "para acceder a la paliación", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Textview para redirigir a fragment registro
        textViewRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ir a fragment registro
                ((LoginActivity) getActivity()).mostrarRegistroFragment();
            }
        });
    }
}