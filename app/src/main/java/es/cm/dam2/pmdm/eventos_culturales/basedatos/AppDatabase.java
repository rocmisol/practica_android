package es.cm.dam2.pmdm.eventos_culturales.basedatos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.cm.dam2.pmdm.eventos_culturales.models.EventoEntity;
import es.cm.dam2.pmdm.eventos_culturales.models.FavoritoUsuarioEntity;
import es.cm.dam2.pmdm.eventos_culturales.models.UsuarioEntity;

@Database(entities = {UsuarioEntity.class, EventoEntity.class, FavoritoUsuarioEntity.class},  version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
    public abstract EventoDao eventoDao();
    public abstract FavoritoUsuarioDao favoritoUsuarioDao();
}
