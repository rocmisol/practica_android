package es.cm.dam2.pmdm.eventos_culturales;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTitulo, textViewGratuito, textViewFecha;
    private Button buttonCalendario;
    private Spinner spinnerCategoria;
    private CheckBox checkBoxGratuito;
    private RecyclerView recyclerViewEventos;

    private ArrayList<Evento> listaEventos;
    private ActivityResultLauncher<Intent> launcher;
    private ListView listViewMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Se rellena el ArrayList listaEventos con todos los eventos.
        listaEventos = FuncionRelleno.rellenaEventos();

        //Configuración de los textView y el checkBox
        textViewTitulo = findViewById(R.id.textViewTituloMain);
        textViewGratuito = findViewById(R.id.textViewGratuitosMain);
        textViewFecha = findViewById(R.id.textViewFechaMain);

        //Configuración del Spinner
        spinnerCategoria = findViewById(R.id.spinnerCategoriaMain);
        ///////////////////////////////////////////////////////////////////////////////falta

        //Configuración del checkBox
        checkBoxGratuito = findViewById(R.id.checkBoxGratuitoMain);
        ///////////////////////////////////////////////////////////////////////////////falta

        //Configuración del RecyclerView
        recyclerViewEventos = findViewById(R.id.recyclerViewMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewEventos.setLayoutManager(linearLayoutManager);

        //Configuración del adaptador
        AdaptadorEvento adaptadorEvento = new AdaptadorEvento(listaEventos, this);
        recyclerViewEventos.setAdapter((adaptadorEvento));

        //Configuración del launcher para gestionar el flujo de datos entre la MainActivity y la DetalleActivity
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null){
                            Intent datos = result.getData();
                            Evento evento = (Evento)datos.getSerializableExtra("evento");
                            listaEventos.add(evento);
                        }
                    }
                }
        );



        //Configuración del botón para seleccionar la fecha
        buttonCalendario = findViewById(R.id.buttonCalendarioMain);
        buttonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ////////////////////////////////////////////////////////////////////falta
            }
        });



        //Se registra el listViewMenu para que pueda utilizar el menú contextual
        registerForContextMenu(listViewMenu);



    }

    //Se agrega el menú de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones, menu);
        return true;
    }

    //Selección de la acción del menú de opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favoritos){
            Intent intent = new Intent (MainActivity.this, Favoritos.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Se agrega el menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu_contextual, menu);
    }

    //Selección de la acción del menú contextual (ver detalles, compartir y modificar favorito)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Uso del adaptador creado para acceder a los datos del objeto evento.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        Evento eventoSeleccionado = listaEventos.get(position);

        int id = item.getItemId();
        if (id == R.id.verDetalles){
            //LLeva a la activity DetalleActivity para ver los detalles del evento.
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
            intent.putExtra("evento", eventoSeleccionado);
            launcher.launch(intent);
            return true;
        }

        if(id == R.id.compartir){
            //Utiliza una aplicación de nuestro dispositivo para enviar información del evento.
            String nombreEvento = textViewTitulo.getText().toString();
            String fechaEvento = textViewFecha.getText().toString();

            //Mensaje que aparecerá al enviar la información.
            String mensaje ="¡Mira este evento!\n" + nombreEvento + "\n" + fechaEvento;

            //Creación de un intent implicito y un selector de apliaciones disponibles
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, mensaje);
            intent.setType("text/plain");
            Intent chooser = Intent.createChooser(intent, getResources().getString(R.string.elige_aplicacion));

            //Se comprueba si hay aplicaciones disponibles e inicia la actividad correspondiente al selector de aplicaciones
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(chooser);
            }
            else{
                //Si no hay ningún aplicación que pueda manjear el intent se muestra un mensaje
                Toast.makeText(this, "No hay ninguna aplicación instalada para enviar este mensaje", Toast.LENGTH_LONG).show();
            }
        }

        if(id == R.id.modificarFavorito){
        ////////////////////////////////////////////////////////////////////////////////////////////////Falta
        }

        return super.onContextItemSelected(item);



    }
}