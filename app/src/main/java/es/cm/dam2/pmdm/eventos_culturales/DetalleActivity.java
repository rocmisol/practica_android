package es.cm.dam2.pmdm.eventos_culturales;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import es.cm.dam2.pmdm.eventos_culturales.models.Evento;

public class DetalleActivity extends AppCompatActivity {

    private TextView textViewTituloDetalle, textViewFechaDetalle, textViewLugarDetalle,
            textViewPrecioDetalle, textViewDescripcionDetalle;
    private ImageView imageViewFotoDetalle;
    private RatingBar ratingBarDetalle;
    private Switch switchFavoritoDetalle;
    private Button buttonGuardarDetalle, buttonVolverDetalle, buttonAniadirCalendarioDetalle;

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
            textViewFechaDetalle.setText(getString(R.string.fecha) + evento.getFecha());
            textViewLugarDetalle.setText(getString(R.string.lugar) + evento.getLugar());
            textViewPrecioDetalle.setText(getString(R.string.precio) + evento.getPrecio());
            textViewDescripcionDetalle.setText(evento.getDescripcion());
            imageViewFotoDetalle.setImageResource(evento.getImagen());
            ratingBarDetalle.setRating(evento.getValoracion());
            switchFavoritoDetalle.setChecked(evento.isFavorito());

        }

        //Configuración del botón Aniadir a Calendario
        buttonAniadirCalendarioDetalle = findViewById(R.id.buttonAniadirCalendarioDetalle);
        buttonAniadirCalendarioDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Se verifica si se tiene permiso para escribir en el calendario.
                    if (ContextCompat.checkSelfPermission(DetalleActivity.this,
                            android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                        //Si no se tiene, se solicita en onRequestPermissionsResult
                        ActivityCompat.requestPermissions(DetalleActivity.this, new String[]{android.Manifest.permission.WRITE_CALENDAR}, 1);
                    } else {
                        // Si ya tiene el permiso, se añade el evento al calendario sin pasar por onRequestPermissionsResult
                        addEventToCalendar(evento);
                    }
                } catch (ParseException e) {
                    Toast.makeText(DetalleActivity.this,
                            getResources().getString(R.string.no_aniadido_evento),
                            Toast.LENGTH_LONG).show();

                }
            }
        });

        //Configuración del botón Guardar. Se guardan los cambios.
        buttonGuardarDetalle = findViewById(R.id.buttonGuardarDetalle);
        buttonGuardarDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evento.setFavorito(switchFavoritoDetalle.isChecked());
                evento.setValoracion(ratingBarDetalle.getRating());

                //Se devuelve el evento modificado
                Intent intent = new Intent();
                intent.putExtra("eventoModificado", evento);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //Configuración del botón Volver. Se vuelve al ActivityMain
        buttonVolverDetalle = findViewById(R.id.buttonVolverDetalle);
        buttonVolverDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //Método para manejar la respuesta del ususario cuando se solicita un permiso en tiempo de ejecución
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Se verifica que la respuesta corresponde al permiso solicitado (código 1)
        if (requestCode == 1) {
            //Comprueba si tiene permiso. Si tiene, agrega el evento al calendario
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    addEventToCalendar(evento);
                } catch (ParseException e) {
                    Toast.makeText(DetalleActivity.this,
                            getResources().getString(R.string.no_aniadido_evento),
                            Toast.LENGTH_LONG).show();
                }
            } else {
                // Permiso denegado, muestra un mensaje al usuario
                Toast.makeText(this, R.string.permiso_denegado_al_calendario, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Método para añadir un evento al calendario de manera directa
    private void addEventToCalendar(Evento evento) throws ParseException {
        // Formato de fecha y hora
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        // Se combina la fecha y la hora en un solo string
        String fechaHora = evento.getFecha() + " " + evento.getHora();

        // Se convierte fecha al formato de milisegundos
        Date date = dateFormat.parse(fechaHora);
        if (date != null) {
            long beginTime = date.getTime();
            long endTime = beginTime + (60 * 60 * 1000); // Se establece una duración de 1 hora por defecto

            // Se verifica si se tiene el permiso para escribir en el calendario
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED) {

                // Se crea un objeto ContentValues para definir los detalles del evento
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.CALENDAR_ID, 1); // Se usa el primer calendario disponible
                values.put(CalendarContract.Events.TITLE, evento.getNombre());
                values.put(CalendarContract.Events.EVENT_LOCATION, evento.getLugar());
                values.put(CalendarContract.Events.DTSTART, beginTime);
                values.put(CalendarContract.Events.DTEND, endTime);
                values.put(CalendarContract.Events.ALL_DAY, false);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID()); // Zona horaria actual

                // Se inserta el evento en el calendario
                Uri uri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
                if (uri != null) {
                    Toast.makeText(this, R.string.evento_aniadido_al_calendario, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.no_aniadido_evento, Toast.LENGTH_SHORT).show();
                }
            } else {
                // Si el permiso no ha sido concedido, se pide el permiso
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_CALENDAR}, 1);
            }
        }
    }
}