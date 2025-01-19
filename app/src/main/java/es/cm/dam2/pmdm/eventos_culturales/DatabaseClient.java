package es.cm.dam2.pmdm.eventos_culturales;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private static AppDatabase database;

    public static AppDatabase getInstance(Context context){
        //Se crea la base de datos si no existe
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "eventos")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
