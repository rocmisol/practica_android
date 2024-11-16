package es.cm.dam2.pmdm.eventos_culturales;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTitulo, textViewGratuito, textViewFecha;
    private Button buttonCalendario;
    private Spinner spinnerCategoria;
    private CheckBox checkBoxGratuito;
    private RecyclerView recyclerViewMain;

    private ArrayList<Evento> listaEventos;
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

        listaEventos = FuncionRelleno.rellenaEventos();

        //Registro el listViewMenu para que pueda utilizar el menú contextual
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

        int id = item.getItemId();
        if (id == R.id.verDetalles){
            //LLeva a la activity DetalleActivity para ver los detalles del evento.
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
            intent.putExtra("");
            //////////////////////////////////////////////////////////////////////////////////////Launcher



        }

        if(id == R.id.compartir){
            ///////////////////////intent implicita
        }

        if(id == R.id.modificarFavorito){

        }

        return super.onContextItemSelected(item);



    }
}