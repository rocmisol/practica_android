package es.cm.dam2.pmdm.eventos_culturales;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorEvento extends RecyclerView.Adapter<AdaptadorEvento.MiviewHolder> {
    private ArrayList<Evento> eventos;
    private Context context;

    public AdaptadorEvento(ArrayList<Evento> eventos, Context context) {
        this.eventos = eventos;
        this.context = context;
    }

    public class MiviewHolder extends RecyclerView.ViewHolder{
        public TextView itemNombre;
        public TextView itemFecha;
        public ImageView itemFotoMini;
        public ImageView itemFotoFavorito;


        public MiviewHolder(@NonNull View itemView) {
            super(itemView);
            itemNombre = itemView.findViewById(R.id.textViewItemNombre);
            itemFecha = itemView.findViewById(R.id.textViewItemFecha);
            itemFotoMini = itemView.findViewById(R.id.imageViewItemFotomini);
            itemFotoFavorito = itemView.findViewById(R.id.imageViewItemFavorito);
        }
    }


    @NonNull
    @Override
    public AdaptadorEvento.MiviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new MiviewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEvento.MiviewHolder holder, int position) {
        if (position % 2 ==0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.violet1));

        } else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.violet2));
        }
        Evento evento = this.eventos.get(position);
        holder.itemNombre.setText(evento.getNombre());
        holder.itemFecha.setText(evento.getFecha());
        holder.itemFotoMini.setImageResource(evento.getImagen());
        holder.itemFotoFavorito.setImageResource(
                evento.isFavorito()? R.drawable.maspequelleno : R.drawable.maspequevacio);
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
}
