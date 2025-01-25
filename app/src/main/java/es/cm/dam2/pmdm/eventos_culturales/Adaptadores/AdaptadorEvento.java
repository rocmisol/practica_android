package es.cm.dam2.pmdm.eventos_culturales.Adaptadores;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.cm.dam2.pmdm.eventos_culturales.models.Evento;
import es.cm.dam2.pmdm.eventos_culturales.MainActivity;
import es.cm.dam2.pmdm.eventos_culturales.R;

public class AdaptadorEvento extends RecyclerView.Adapter<AdaptadorEvento.MiviewHolder> {
    private ArrayList<Evento> eventos;
    private Context context;
    private int posicionSeleccionada;

    public AdaptadorEvento(ArrayList<Evento> eventos, Context context) {
        this.eventos = eventos;
        this.context = context;
    }

    public class MiviewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView itemNombre;
        public TextView itemFecha;
        public ImageView itemFotoMini;
        public ImageView itemFotoFavorito;

        public MiviewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            // Se vinculan las vistas del item
            itemNombre = itemView.findViewById(R.id.textViewItemNombre);
            itemFecha = itemView.findViewById(R.id.textViewItemFecha);
            itemFotoMini = itemView.findViewById(R.id.imageViewItemFotomini);
            itemFotoFavorito = itemView.findViewById(R.id.imageViewItemFavorito);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            // Se infla el menú contextual
            MenuInflater inflater = ((MainActivity) context).getMenuInflater();
            inflater.inflate(R.menu.menu_contextual, contextMenu);

            // Se asocian las acciones al elemento seleccionado
            contextMenu.setHeaderTitle(R.string.selecciona_opcion);
        }
    }


    @NonNull
    @Override
    public AdaptadorEvento.MiviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Se infla la vista del item
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new MiviewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEvento.MiviewHolder holder, int position) {
        //Se modifica la vista del recycler con los valores que se quieren para cada posición
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
                evento.isFavorito()? R.drawable.corazon_lleno : R.drawable.corazon_vacio);

        //Se configura un clic largo para abrir el menú contextual
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                posicionSeleccionada = position; //Guarda la posició seleccionada
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    //Método para manejar la lista de eventos filtrada
    public void actualizarListaFiltradoEventos (ArrayList<Evento> eventosFiltrados ){
        this.eventos = eventosFiltrados; //Se actualiza la lista interna de datos del adaptador
        notifyDataSetChanged();//Notifica al Recyclerview para refrescar la vista
    }

    //Método para devolver la posición seleccionada al activity para que esta sepa que elemento
    // del recycler está seleccionado
    public int getPosicionSeleccionada(){
        return posicionSeleccionada;
    }


}
