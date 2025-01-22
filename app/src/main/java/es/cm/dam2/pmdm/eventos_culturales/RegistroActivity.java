package es.cm.dam2.pmdm.eventos_culturales;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.cm.dam2.pmdm.eventos_culturales.basedatos.DatabaseClient;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.UsuarioDao;
import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;

public class RegistroActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextTelefono, editTextEmail, editTextPassword;
    private Button buttonGuardar, buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Se vinculan las vistas
        editTextNombre = findViewById(R.id.editTextNombreRegistro);
        editTextTelefono = findViewById(R.id.editTextTelefonoRegistro);
        editTextEmail = findViewById(R.id.editTextEmailRegistro);
        editTextPassword = findViewById(R.id.editTextPasswordRegistro);
        buttonGuardar = findViewById(R.id.buttonGuardarRegistro);
        buttonCancelar = findViewById(R.id.buttonCancelarRegistro);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se obtienen los datos de los editText
                String nombre = editTextNombre.getText().toString().trim();
                String telefono = editTextTelefono.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //Se validan los campos
                if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegistroActivity.this, R.string.se_deben_completar_todos_los_campos, Toast.LENGTH_SHORT).show();
                }
                else{
                    UsuarioDao usuarioDao = DatabaseClient.getInstance(getApplicationContext()).usuarioDao();

                    //Se comprueba si el usuario ya está registrado
                    Usuario usuarioExistente = usuarioDao.obtenerUsuarioPorEmail(email);

                    if (usuarioExistente != null){
                        Toast.makeText(RegistroActivity.this, R.string.el_usuario_ya_registrado, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Usuario nuevoUsuario = new Usuario(nombre, telefono, email, password, "user");
                        usuarioDao.insertar(nuevoUsuario);

                        //Si el usuario se inserta se muestra un mensaje y se envia una notificación
                        Toast.makeText(RegistroActivity.this, R.string.usuario_registrado, Toast.LENGTH_SHORT).show();
                        String nombreUsuario = usuarioDao.obtenerUltimoUsuario();
                        mostrarNotificacionAltaUsuario(nombreUsuario);

                        //Regresa a la activity LoginActivity
                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //Método que muestra una notificación al dar de alta un usuario
    private void mostrarNotificacionAltaUsuario (String nombreUsuario){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_alta");
        builder.setSmallIcon(R.drawable.warning);
        builder.setContentTitle("Alta");
        builder.setContentText(getString(R.string.se_ha_dado_de_alta_al_usuario) + nombreUsuario);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }


}