package es.cm.dam2.pmdm.eventos_culturales.basedatos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.cm.dam2.pmdm.eventos_culturales.models.EventoEntity;
import es.cm.dam2.pmdm.eventos_culturales.models.FavoritoUsuarioEntity;

@Dao
public interface FavoritoUsuarioDao {

    @Insert
    void insertar(FavoritoUsuarioEntity favoritoUsuario);

    @Query("SELECT * FROM favoritoUsuario WHERE idUsuario = :idUsuario")
    List<FavoritoUsuarioEntity> obtenerTodosFavoritos(int idUsuario);

    @Query("UPDATE favoritoUsuario SET favorito = :favorito WHERE idUsuario = :idUsuario AND idEvento = :idEvento")
    void actualizarFavorito(int idUsuario, int idEvento, boolean favorito);

}
