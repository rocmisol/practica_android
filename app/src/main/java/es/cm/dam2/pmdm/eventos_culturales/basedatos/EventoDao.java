package es.cm.dam2.pmdm.eventos_culturales.basedatos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.cm.dam2.pmdm.eventos_culturales.models.EventoEntity;
import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;

@Dao
public interface EventoDao {

    @Insert
    void insertar(EventoEntity evento);

    @Query("SELECT * FROM eventos")
    List<EventoEntity> obtenerTodosEventos();

}
