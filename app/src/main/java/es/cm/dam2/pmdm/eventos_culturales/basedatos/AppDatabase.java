package es.cm.dam2.pmdm.eventos_culturales.basedatos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.cm.dam2.pmdm.eventos_culturales.models.Usuario;

@Database(entities = {Usuario.class},  version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();

}
