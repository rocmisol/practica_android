package es.cm.dam2.pmdm.eventos_culturales;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ProgressBar;
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
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView textViewFecha;
    private Button buttonFecha;
    private Spinner spinnerCategoria;
    private CheckBox checkBoxGratuito;
    private RecyclerView recyclerViewEventos;
    private ProgressBar progressBarMain;

    private ArrayList<Evento> listaEventos;
    private ArrayList<Evento> listaEventosFiltrados;
    private ArrayList<Evento> listaEventosFavoritos;
    private AdaptadorEvento adaptadorEvento;
    private ActivityResultLauncher<Intent> launcher;

    private String[] categorias = {"Todos", "Escena", "Exposición", "Música"};
    private String categoriaSelecionada = "Todos";
    private String fechaSeleccionada = "Todos";
    private boolean gratuito;

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

        // listaEventos = database.dao.geteventos();????

        //en lugar de coger la lista de eventos general de la FuncionRelleno, cogerlo desde base de datos???


        //Vinculación del textView
        textViewFecha = findViewById(R.id.textViewFechaMain);

        //Se asocia la vista al objeto
        progressBarMain = findViewById(R.id.progressBarMain);


        //Configuración del Spinner la selección de categoría
        spinnerCategoria = findViewById(R.id.spinnerCategoriaMain);
        ArrayAdapter<String> adaptadorCategorias = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, categorias);
        spinnerCategoria.setAdapter(adaptadorCategorias);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoriaSelecionada = categorias[i];
                filtrarEventos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Configuración del checkBox
        checkBoxGratuito = findViewById(R.id.checkBoxGratuitoMain);
        checkBoxGratuito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filtrarEventos();
            }
        });


        //Configuración del RecyclerView
        recyclerViewEventos = findViewById(R.id.recyclerViewMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewEventos.setLayoutManager(linearLayoutManager);


        //Configuración del adaptador
        adaptadorEvento = new AdaptadorEvento(listaEventos, this);
        recyclerViewEventos.setAdapter((adaptadorEvento));


        //Configuración del launcher para gestionar el flujo de datos entre la MainActivity y la DetalleActivity
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null){
                            //El evento se duplica, asíque no vale. Hay que modificarlo
                            // Intent datos = result.getData();
                            //Evento evento = (Evento)datos.getSerializableExtra("eventoModificado");
                            //listaEventos.add(evento);
                            //filtrarEventos();

                            Evento eventoModificado = (Evento) result.getData().getSerializableExtra("eventoModificado");
                            if (eventoModificado != null){
                                //Bucle para buscar la posición del evento modificado
                                for (int i = 0; i< listaEventos.size(); i++){
                                    Evento eventoBuscado = listaEventos.get(i);
                                    //Si encuentra el evento lo sustituye en la lista original
                                    if (eventoBuscado.getNombre().equalsIgnoreCase(eventoModificado.getNombre())){
                                        listaEventos.set(i, eventoModificado);
                                        filtrarEventos();
                                    }
                                }
                            }
                        }
                    }
                }
        );


        //Configuración del botón que abrirá un DatePickerDialog para seleccionar la fecha
        buttonFecha = findViewById(R.id.buttonCalendarioMain);
        buttonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, (month+1), year);
                                textViewFecha.setText(fechaSeleccionada);
                                filtrarEventos();
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        // Como es un recyclerView ya no se puede utilizar
        // registerForContextMenu(recyclerViewEventos);

    }

    //Método para filtrar los eventos según su categoría, fecha seleccionada y si son gratuitos o no
    // y actualizar la lista que se pasa al recycler
    public void filtrarEventos(){
        categoriaSelecionada = spinnerCategoria.getSelectedItem().toString();
        fechaSeleccionada = textViewFecha.getText().toString();
        gratuito = checkBoxGratuito.isChecked();

        //Lista temporal para almacenar los eventos que cumplen con los filtros seleccionados
        listaEventosFiltrados = new ArrayList<>();

        //Se recorre la lista de Eventos y se aplican los filtros
        for (Evento evento : listaEventos){
            //Si la categoría es todos, todos los eventos pasarán el filtro
            //Si no, comprueba si la categoría coincide con la categoría seleccionada
            boolean coincideCategoria = categoriaSelecionada.equals("Todos") ||
                    evento.getCategoria().equalsIgnoreCase(categoriaSelecionada);
            //Si no se establece fecha, todos los eventos pasarán el filtro.
            //Si se establece fecha, comprueba si la fecha coincide con la fecha selecionada
            boolean coincideFecha = fechaSeleccionada.equalsIgnoreCase("") ||
                    evento.getFecha().equals(fechaSeleccionada);
            //Si el checkBox no está marcado todos los eventos pasaran el filtro gratuito
            //Si el checkbox está marcado solo pasarán el filtro los que tengan el precio "GRATUITO"
            boolean coincideGratuito = !gratuito || evento.getPrecio().equalsIgnoreCase("GRATUITO");

            //Si el evento cumple con todos los filtros activos, se añade a la lista temporal
            if (coincideCategoria && coincideFecha && coincideGratuito){
                listaEventosFiltrados.add(evento);
            }
        }
        //Si al filtrar la lista no contiene eventos, se muestra un DialogFragment y se vuelven a mostrar todos los eventos
        if (listaEventosFiltrados.isEmpty()){
            DialogoListaVacia dialogoListaVacia = new DialogoListaVacia();
            dialogoListaVacia.show(getSupportFragmentManager(), "DialogoListaVacia");
            fechaSeleccionada = "";
            textViewFecha.setText("");
            filtrarEventos(); // Se vuelve a actualizar la lista filtrada
        }

        //Se actualiza el adaptador del Recycler con los eventos filtrados
        //Se simula una carga con retraso de 2 segundos
        //https://developer.android.com/reference/android/os/Handler

        mostrarProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adaptadorEvento.actualizarListaFiltradoEventos(listaEventosFiltrados);
                ocultarProgressBar();
            }
        }, 2000);

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
            //Se llama al método obtenerEventosFavoritos para actualizar la lista de eventos favoritos
            obtenerEventosFavoritos();
            //Se crea objeto serializable con la lista de favoritos para pasalo por el intent
            ListaPasarEventosFavoritos listaPasarEventosFavoritos = new ListaPasarEventosFavoritos(listaEventosFavoritos);
            Intent intent = new Intent (MainActivity.this, FavoritosActivity.class);
            intent.putExtra("listaFavoritos", listaPasarEventosFavoritos);
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
        //Ya no sirve: AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        //La posición que se necesita es la de la lista filtrada, no la de la completa
        int position = adaptadorEvento.getPosicionSeleccionada();
        Evento eventoSeleccionado = listaEventosFiltrados.get(position);

        int id = item.getItemId();
        if (id == R.id.verDetalles){
            //LLeva a la activity DetalleActivity para ver los detalles del evento.
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
            intent.putExtra("evento", eventoSeleccionado);
            launcher.launch(intent);
            return true;
        }

        if(id == R.id.compartir){
            //Se utiliza una aplicación de nuestro dispositivo para enviar información del evento.
            String nombreEvento = eventoSeleccionado.nombre;
            String fechaEvento = eventoSeleccionado.fecha;
            String lugarEvento = eventoSeleccionado.lugar;
            String precioEvento = eventoSeleccionado.precio;
            String descripcionEvento = eventoSeleccionado.descripcion;

            //Mensaje que aparecerá al enviar la información.
            String mensaje =getString(R.string.mira_este_evento) + "\n" + nombreEvento + "\n" +
                    getString(R.string.fecha) + fechaEvento + "\n" + getString(R.string.lugar) +
                    lugarEvento + "\n" + getString(R.string.precio) + precioEvento + "\n" +
                    getString(R.string.descripcion) +"\n" + descripcionEvento;

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
                //Si no hay ninguna aplicación que pueda manjear el intent se muestra un mensaje
                Toast.makeText(this, R.string.no_hay_aplicacion_instalada, Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if(id == R.id.modificarFavorito){
        //Se cambia el estado de favorito del evento
            boolean estadoFavorito = !eventoSeleccionado.isFavorito();//Se invierte el estado actual
            eventoSeleccionado.setFavorito(estadoFavorito);
            adaptadorEvento.notifyItemChanged(position);//Se notifica al adaptador que el evento ha cambiado
            return true;
        }
        return super.onContextItemSelected(item);

    }

    //Método para obtener los eventos favoritos de la lista de eventos principal
    private void obtenerEventosFavoritos(){
        listaEventosFavoritos = new ArrayList<>();
        for (Evento evento : listaEventos){
            if (evento.isFavorito()){
                listaEventosFavoritos.add(evento);
            }
        }
    }

    //Método para mostrar el ProgressBar
    private void mostrarProgressBar(){
        progressBarMain.setVisibility(View.VISIBLE);
    }

    //Método para ocultar el ProgressBar
    private void ocultarProgressBar(){
        progressBarMain.setVisibility(View.GONE);
    }
}