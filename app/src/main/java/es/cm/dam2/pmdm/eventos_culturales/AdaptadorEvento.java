package es.cm.dam2.pmdm.eventos_culturales;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorEvento extends RecyclerView.Adapter<AdaptadorEvento.MiviewHolder> {
    private ArrayList<Evento> eventos;

    public AdaptadorEvento(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    public class MiviewHolder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public TextView fecha;
        public ImageView fotoMini;
        public ImageView fotoFavorito;


        public MiviewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textViewNombre);
            fecha = itemView.findViewById(R.id.textViewFecha);
            fotoMini = itemView.findViewById(R.id.imageViewFotomini);
            fotoFavorito = itemView.findViewById(R.id.imageViewFavorito);

        }
    }



    @NonNull
    @Override
    public AdaptadorEvento.MiviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEvento.MiviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
