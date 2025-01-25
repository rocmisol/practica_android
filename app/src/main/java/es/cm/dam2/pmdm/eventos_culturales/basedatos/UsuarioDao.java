package es.cm.dam2.pmdm.eventos_culturales.basedatos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;

@Dao
public interface UsuarioDao {
    @Insert
    void insertar(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE id = :id")
    Usuario obtenerUsuario (int id);

    //Método para comprobar si un usario está en la base de datos
    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password")
    Usuario comprobarUsuarioRegistrado (String email, String password);

    //Método para obtener todos los usuarios
    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodosUsuarios();

    //Método para buscar un usuario por email
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario obtenerUsuarioPorEmail(String email);

    //Método para obtener el número de teléfono del administrador
    @Query("SELECT telefono FROM usuarios WHERE rol = 'admin'")
    String obtenerTelefonoAdministrador();

    //Método para obtener el último usuario
    @Query("SELECT nombre FROM usuarios ORDER BY id DESC LIMIT 1")
    String obtenerUltimoUsuario();

    //Método para obtener todos los usuarios
    @Query("SELECT * FROM usuarios WHERE rol = 'user'")
    List<Usuario> obtenerTodosUsuariosUser();

    @Delete
    void eliminarUsuario(Usuario usuario);

}
