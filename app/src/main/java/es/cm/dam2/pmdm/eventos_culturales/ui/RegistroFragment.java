package es.cm.dam2.pmdm.eventos_culturales.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.cm.dam2.pmdm.eventos_culturales.LoginActivity;
import es.cm.dam2.pmdm.eventos_culturales.R;
import es.cm.dam2.pmdm.eventos_culturales.RegistroActivity;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.DatabaseClient;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.UsuarioDao;
import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;


public class RegistroFragment extends Fragment {

    public RegistroFragment() {
        // Required empty public constructor
    }

    private EditText editTextNombre, editTextTelefono, editTextEmail, editTextPassword;
    private Button buttonGuardar, buttonCancelar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Se vinculan las vistas
        editTextNombre = view.findViewById(R.id.editTextNombreRegistro);
        editTextTelefono = view.findViewById(R.id.editTextTelefonoRegistro);
        editTextEmail = view.findViewById(R.id.editTextEmailRegistro);
        editTextPassword = view.findViewById(R.id.editTextPasswordRegistro);
        buttonGuardar = view.findViewById(R.id.buttonGuardarRegistro);
        buttonCancelar = view.findViewById(R.id.buttonCancelarRegistro);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se obtienen los datos de los editText
                String nombre = editTextNombre.getText().toString().trim();
                String telefono = editTextTelefono.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //Se validan los campos
                if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(), R.string.se_deben_completar_todos_los_campos, Toast.LENGTH_SHORT).show();
                }
                else{
                    UsuarioDao usuarioDao = DatabaseClient.getInstance(getActivity().getApplicationContext()).usuarioDao();

                    //Se comprueba si el usuario ya está registrado
                    Usuario usuarioExistente = usuarioDao.obtenerUsuarioPorEmail(email);

                    if (usuarioExistente != null){
                        Toast.makeText(getActivity(), R.string.el_usuario_ya_registrado, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Usuario nuevoUsuario = new Usuario(nombre, telefono, email, password, "user");
                        usuarioDao.insertar(nuevoUsuario);

                        //Si el usuario se inserta se muestra un mensaje y se envia una notificación
                        Toast.makeText(getActivity(), R.string.usuario_registrado, Toast.LENGTH_SHORT).show();
                        String nombreUsuario = usuarioDao.obtenerUltimoUsuario();
                        ((LoginActivity) getActivity()).mostrarNotificacionAltaUsuario(nombreUsuario);

                        //Regresa a fragment anterior (login)
                        ((LoginActivity) getActivity()).mostrarLoginFragment();
                    }
                }
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Regresa a fragment anterior (login)
                ((LoginActivity) getActivity()).mostrarLoginFragment();
            }
        });
    }
}