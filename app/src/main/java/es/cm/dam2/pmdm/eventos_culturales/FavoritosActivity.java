package es.cm.dam2.pmdm.eventos_culturales;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FavoritosActivity extends AppCompatActivity {

    private TextView textViewTituloFavoritos, textViewNotasFavoritos;
    private ProgressBar progressBarFavoritos;
    private ListView listViewFavoritos;
    EditText editTextNotasFavoritos;
    Button buttonGuardarFavoritos, buttonVolverFavoritos;

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

        textViewTituloFavoritos = findViewById(R.id.textViewTituloFavoritos);
        textViewNotasFavoritos = findViewById(R.id.textViewNotasFavoritos);
        progressBarFavoritos = findViewById(R.id.progressBarFavoritos);
        listViewFavoritos = findViewById(R.id.listViewFavoritos);
        editTextNotasFavoritos = findViewById(R.id.editTextNotasFavoritos);
        buttonGuardarFavoritos = findViewById(R.id.buttonGuardarFavoritos);
        buttonVolverFavoritos = findViewById(R.id.buttonVolverFavoritos);

        //Se muestra el progressBar durante la carga de esta Activity


    }
}