package es.cm.dam2.pmdm.eventos_culturales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegistro;
    private UsuarioDao usuarioDao;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        //Se obtiene la base de datos
        database = DatabaseClient.getInstance(this);

        //Se obtiene el usuarioDao
        usuarioDao = database.usuarioDao();

        //e insertan usuarios iniciales en la base de datos (si está vacía)
        insertarUsuariosPredeterminados();

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

    }

    private void insertarUsuariosPredeterminados(){
        //Se comprueba que la base de datos está vacía
        if (usuarioDao.obtenerTodosUsuarios().isEmpty()){
            // Se crea el administrador y un usuario por defecto
            Usuario admin = new Usuario(1, "admin", "admin@eventosculturales.com", "admin", "admin");
            Usuario user1 = new Usuario(2, "usuario", "usuario@eventosculturales.com", "usuario", "user");

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