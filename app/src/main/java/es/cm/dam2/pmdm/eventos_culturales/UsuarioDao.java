package es.cm.dam2.pmdm.eventos_culturales;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsuarioDao {
    @Insert
    void insertar(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE id = :id")
    Usuario obtenerUsuario (int id);

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password")
    Usuario comprobarUsuarioRegistrado (String email, String password);

}
