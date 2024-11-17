package es.cm.dam2.pmdm.eventos_culturales;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalleActivity extends AppCompatActivity {

    private TextView textViewTituloDetalle, textViewFechaDetalle, textViewLugarDetalle, textViewPrecioDetalle, textViewDescripcionDetalle;
    private ImageView imageViewFotoDetalle;
    private RatingBar ratingBarDetalle;
    private Switch switchFavoritoDetalle;
    private Button buttonGuardarDetalle, buttonVolverDetalle;

    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Vinculación con las vistas de los textView
        textViewTituloDetalle = findViewById(R.id.textViewTituloDetalle);
        textViewFechaDetalle = findViewById(R.id.textViewFechaDetalle);
        textViewLugarDetalle = findViewById(R.id.textViewLugarDetalle);
        textViewPrecioDetalle = findViewById(R.id.textViewPrecioDetalle);
        textViewDescripcionDetalle = findViewById(R.id.textViewDescripcionDetalle);

        //Vinculación con la vista de la imagen
        imageViewFotoDetalle = findViewById(R.id.imageViewFotoDetalle);

        //Vinculación con la vista del ratingBar
        ratingBarDetalle = findViewById(R.id.ratingBarDetalle);

        //Vinculación con la vista del Switch
        switchFavoritoDetalle = findViewById(R.id.switchFavoritoDetalle);

        //Se obtiene el evento desde el Intent
        evento = (Evento) getIntent().getSerializableExtra("evento");

        if (evento != null){
            //Se muestran los datos del evento
            textViewTituloDetalle.setText(evento.getNombre());
            textViewFechaDetalle.setText("Fecha: " + evento.getFecha());
            textViewLugarDetalle.setText("Lugar: " + evento.getLugar());
            textViewPrecioDetalle.setText("Precio: " + evento.getPrecio());
            textViewDescripcionDetalle.setText(evento.getDescripcion());
            imageViewFotoDetalle.setImageResource(evento.getImagen());
            ratingBarDetalle.setRating(evento.getValoracion());

        }

        //Configuración del botón Guardar
        buttonGuardarDetalle = findViewById(R.id.buttonGuardarDetalle);

        //Configuración del botón Volver
        buttonVolverDetalle = findViewById(R.id.buttonVolverDetalle);


    }
}