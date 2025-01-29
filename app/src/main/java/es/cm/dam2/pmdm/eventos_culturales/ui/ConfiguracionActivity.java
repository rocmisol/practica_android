package es.cm.dam2.pmdm.eventos_culturales.ui;


import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.cm.dam2.pmdm.eventos_culturales.Adaptadores.AdaptadorUsuario;
import es.cm.dam2.pmdm.eventos_culturales.R;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.AppDatabase;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.DatabaseClient;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.UsuarioDao;
import es.cm.dam2.pmdm.eventos_culturales.models.UsuarioEntity;

public class ConfiguracionActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsuarios;
    private AdaptadorUsuario adaptadorUsuario;
    private List<UsuarioEntity> listaUsuarios;
    private UsuarioDao usuarioDao;
    private AppDatabase database;
    private Button buttonVolverConfiguracion;
    private TextView textViewUsuariosAplicacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activityConfiguracion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Se vincula la vista
        textViewUsuariosAplicacion = findViewById(R.id.textViewUsuariosAplicacion);

        //Se inicialia la base de datos y se obtiene la lista de usuarios.
        database = DatabaseClient.getInstance(this);
        usuarioDao = database.usuarioDao();
        listaUsuarios = usuarioDao.obtenerTodosUsuariosUser();

        //Configuración del RecyclerView
        recyclerViewUsuarios = findViewById(R.id.recyclerViewConfiguracion);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewUsuarios.setLayoutManager(layoutManager);

        //Configuración del adaptador
        adaptadorUsuario = new AdaptadorUsuario(listaUsuarios, this, new AdaptadorUsuario.OnUsuarioClickListener() {
            @Override
            public void onEliminarClick(UsuarioEntity usuario) {
                eliminarUsuario(usuario);
            }
        });
        recyclerViewUsuarios.setAdapter(adaptadorUsuario);

        //Se configura el botón volver para regresar a la actividad principal
        buttonVolverConfiguracion = findViewById(R.id.buttonVolverConfiguracion);

        buttonVolverConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Método para cargar los usuario en el recycler

    //Método para eliminar un usuario
    private void eliminarUsuario (UsuarioEntity usuario) {
        usuarioDao.eliminarUsuario(usuario);
        listaUsuarios.remove(usuario);
        adaptadorUsuario.notifyDataSetChanged();
        mostrarNotificacionBajaUsuario(usuario.getNombre());
        Toast.makeText(ConfiguracionActivity.this, R.string.usuario_eliminado,
                Toast.LENGTH_SHORT).show();
    }

    //Método que muestra una notificación al borrar un usuario
    public void mostrarNotificacionBajaUsuario (String nombreUsuario){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_baja");
        builder.setSmallIcon(R.drawable.warning);
        builder.setContentTitle("Baja");
        builder.setContentText(getString(R.string.se_ha_dadjo_de_baja_al_susuario) + " " + nombreUsuario);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}