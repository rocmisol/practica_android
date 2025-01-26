package es.cm.dam2.pmdm.eventos_culturales.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favoritoUsuario")
public class FavoritoUsuarioEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "idUsuario")
    public int idUsuario;

    @ColumnInfo(name = "idEvento")
    public int idEvento;

    @ColumnInfo(name = "favorito")
    public boolean favorito;

    public FavoritoUsuarioEntity() {

    }

    public FavoritoUsuarioEntity(int id, int idUsuario, int idEvento, boolean favorito) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.favorito = favorito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    @Override
    public String toString() {
        return "FavoritoUsuarioEntity{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idEvento=" + idEvento +
                ", favorito=" + favorito +
                '}';
    }
}
