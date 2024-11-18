package es.cm.dam2.pmdm.eventos_culturales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoListaVacia extends DialogFragment implements DialogInterface.OnClickListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Se crea un objeto de la clase AlertDialog.Builder y se establece el contexto de la Actividad en la que se va a mostrar
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //Se establecen el título y el mensaje que va a tener
        builder.setTitle(getResources().getString(R.string.tituloDialog));
        builder.setMessage(getResources().getString(R.string.mensajeDialog));
        //Se establece el botón aceptar que va a tener
        builder.setPositiveButton(getResources().getString(R.string.botonAceptarDialog), this);
        return builder.create();//Se devuelve la creación del diálogo
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();//Se cierra el diálogo
    }
}
