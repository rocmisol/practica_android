package es.cm.dam2.pmdm.eventos_culturales.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import es.cm.dam2.pmdm.eventos_culturales.R;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.DatabaseClient;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.UsuarioDao;
import es.cm.dam2.pmdm.eventos_culturales.models.UsuarioEntity;


public class RegistroFragment extends Fragment {

    public RegistroFragment() {

    }

    private EditText editTextNombre, editTextTelefono, editTextEmail, editTextPassword;
    private Button buttonGuardar, buttonCancelar;
    private ImageButton imageButtonAgenda;


    // En un fragment onRequestPermissionsResult está deprecado. Se crea una variable para recibir el result
    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean isGranted) {
            if (isGranted) {
                // Si se concede el permiso, se accede a la agenda para seleccionar el contacto
                seleccionarContacto();
            } else {
                // Si no se concede el permiso, se muestra un mensaje
                Toast.makeText(getContext(), R.string.permiso_para_acceder_a_la_agenda_denegado, Toast.LENGTH_SHORT).show();
            }
        }
    });


    // Variable para recibir el resultado de seleccionar un contacto despues de lanzar la intent de agenda.
    private final ActivityResultLauncher<Intent> contactoResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Se obtiene el Intent con el contacto obtenido
                        Intent contacto = result.getData();
                        if (contacto != null) {
                            //Se extrae la URI del contacto seleccionado
                            Uri uri = contacto.getData();
                            if (uri != null) {
                                // Se obtiene los datos del contacto (nombre y tlfno) con un cursor
                                obtenerDatosContacto(uri);
                            }
                        }
                    }
                }
            }
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        imageButtonAgenda = view.findViewById(R.id.imageButtonAgenda);

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
                    UsuarioEntity usuarioExistente = usuarioDao.obtenerUsuarioPorEmail(email);

                    if (usuarioExistente != null){
                        Toast.makeText(getActivity(), R.string.el_usuario_ya_registrado, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        UsuarioEntity nuevoUsuario = new UsuarioEntity(nombre, telefono, email, password, "user");
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

        imageButtonAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitarPermisos();
            }
        });
    }

    //Método que comprueba si se tiene permisos para leer los contactos
    private void solicitarPermisos() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            // Si el permiso ya está concedido, permite seleccionar el contacto
            seleccionarContacto();
        } else {
            // Si el permiso no está concedido se solicita al usuario
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS);
        }
    }

    // Método para abrir la agenda de contacos y permitir seleccionar un contacto
    private void seleccionarContacto() {
        // Se crea un intent para abrir la agenda y elegir un contacto
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        contactoResult.launch(intent); // el contacto elegido se recibe en contactoResult
    }

    //Método para obtener los datos del contacto seleccionado
    //Recibe la URI del conectado y extrae el nombre y el teléfono
    private void obtenerDatosContacto(Uri uriContacto) {
        String nombre = "";
        String telefono = "";

        // Con un cursor se consulta el contenido de la URI del contacto
        Cursor cursor = requireActivity().getContentResolver().query(uriContacto, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            //Se obtiene el nombre del contacto con el cursor
            int indiceNombre = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            nombre = cursor.getString(indiceNombre);

            //Se comprueba si el contacto tiene número de teléfono
            int tieneNumero = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int indiceIdContacto = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            if (tieneNumero >= 0 && cursor.getInt(tieneNumero) > 0) {
                //Se obtiene el ID del contacto
                String contactId = cursor.getString(indiceIdContacto);
                //Se consultan los númerod de teléfono asociados al contacto
                Cursor cursorTelefono = requireActivity().getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{contactId},
                        null
                );
                // Se obtiene el número de teléfono mediante el cursor
                int indiceNumerocontacto = cursorTelefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                if (cursorTelefono != null && cursorTelefono.moveToFirst()) {
                    telefono = cursorTelefono.getString(indiceNumerocontacto);
                    cursorTelefono.close();
                }
            }
            cursor.close();
        }

        //Se muestra el nombre del contacto en el editText
        if (!nombre.isEmpty() ) {
            editTextNombre.setText(nombre);
        }
        //Si el contacto tiene teléfono, se muestra en el ediText.
        if (!telefono.isEmpty()) {
            editTextTelefono.setText(telefono);
        }
    }
}