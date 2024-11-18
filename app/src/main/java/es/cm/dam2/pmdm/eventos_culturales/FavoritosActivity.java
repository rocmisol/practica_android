package es.cm.dam2.pmdm.eventos_culturales;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity {

    private TextView textViewTituloFavoritos, textViewNotasFavoritos;

    private RecyclerView recyclerViewFavoritos;
    EditText editTextNotasFavoritos;
    Button buttonGuardarFavoritos, buttonVolverFavoritos;

    private AdaptadorEvento adaptadorEvento;
    private ListaPasarEventosFavoritos listaPasarEventosFavoritos;

    //https://developer.android.com/training/data-storage/shared-preferences?hl=es-419
    //Objeto que permite el guardado de datos de manera persistente
    private SharedPreferences sharedPreferences;
    private static final String NOTAS_KEY = "notas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favoritos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Vinculación con las vistas de los textView
        textViewTituloFavoritos = findViewById(R.id.textViewTituloFavoritos);
        textViewNotasFavoritos = findViewById(R.id.textViewNotasFavoritos);

        recyclerViewFavoritos = findViewById(R.id.recyclerViewFavoritos);
        editTextNotasFavoritos = findViewById(R.id.editTextNotasFavoritos);

        //Se obtiene el evento desde el Intent
        listaPasarEventosFavoritos = (ListaPasarEventosFavoritos) getIntent().getSerializableExtra("listaFavoritos");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewFavoritos.setLayoutManager(linearLayoutManager);

        //Creación del adaptador del recycler con la lista de eventos (serializable) recibidos por Intent
        adaptadorEvento = new AdaptadorEvento(listaPasarEventosFavoritos.getListaEventosFavoritos(), this);
        recyclerViewFavoritos.setAdapter((adaptadorEvento));

        //Se recuperan los eventos favoritos

        //Se cargan las anotaciones guardadas
        sharedPreferences = getSharedPreferences("NotasFavoritos", MODE_PRIVATE);
        String notasGuardadas = sharedPreferences.getString(NOTAS_KEY, ""); //"" es el valor por defecto
        editTextNotasFavoritos.setText(notasGuardadas); //Se muestran las notas guardadas en el editText



        //Se configura el botón para guardar las notas
        buttonGuardarFavoritos = findViewById(R.id.buttonGuardarFavoritos);
        buttonGuardarFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notas = editTextNotasFavoritos.getText().toString();
                guardarNotas (notas);
            }
        });

        //Se configura el botón para volver al MainActivity
        buttonVolverFavoritos = findViewById(R.id.buttonVolverFavoritos);
        buttonVolverFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

    //Método para guardar las notas utilizando SharedPreferences (de manera persistene)
    private void guardarNotas (String notas){
        SharedPreferences.Editor editor = sharedPreferences.edit(); //Se obtiene el editor de SharedPreferences
        editor.putString(NOTAS_KEY, notas); //Guarda las notas con una clave
        editor.apply(); //Se confirman los cambios
    }
}