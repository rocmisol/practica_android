package es.cm.dam2.pmdm.eventos_culturales.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.cm.dam2.pmdm.eventos_culturales.R;
import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.UsuarioViewHolder> {
    private List<Usuario> usuarios;
    private Context context;
    private int posicionSeleccionada;
    private OnUsuarioClickListener listener;

    //Interfaz para manejar la eliminación de ususarios
    public interface OnUsuarioClickListener {
        void onEliminarClick (Usuario usuario);
    }

    //Constructor
    public AdaptadorUsuario(List<Usuario> usuarios, Context context, OnUsuarioClickListener listener) {
        this.usuarios = usuarios;
        this.context = context;
        this.listener = listener;
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewItemNombreUsuario;
        public TextView textViewItemEmailUsuario;
        public ImageButton imagebuttonEliminarUsuario;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemNombreUsuario = itemView.findViewById(R.id.textViewItemNombreUsuario);
            textViewItemEmailUsuario = itemView.findViewById(R.id.textViewItemEmailUsuario);
            imagebuttonEliminarUsuario = itemView.findViewById(R.id.imageButtonEliminarUsuario);
        }
    }

    @NonNull
    @Override
    public AdaptadorUsuario.UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario_eliminar, parent, false);
        return new UsuarioViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorUsuario.UsuarioViewHolder holder, int position) {
        if (position % 2 == 0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.violet1));
        }
        else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.violet2));
        }

        Usuario usuario = this.usuarios.get(position);
        holder.textViewItemNombreUsuario.setText(usuario.getNombre());
        holder.textViewItemEmailUsuario.setText(usuario.getEmail());

        holder.imagebuttonEliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onEliminarClick(usuario);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


    //Método para devolver la posición seleccionada al activity para que esta sepa que elemento
    // del recycler está seleccionado
    public int getPosicionSeleccionada(){
        return posicionSeleccionada;
    }
}
