package es.cm.dam2.pmdm.eventos_culturales.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "eventos")
public class EventoEntity {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "nombre")
    String nombre;

    @ColumnInfo(name = "fecha")
    String fecha;

    @ColumnInfo(name = "categoria")
    String categoria;

    @ColumnInfo(name = "imagen")
    int imagen;

    @ColumnInfo(name = "lugar")
    String lugar;

    @ColumnInfo(name = "descripcion")
    String descripcion;

    @ColumnInfo(name = "precio")
    String precio;

    @ColumnInfo(name = "hora")
    String hora;

    @ColumnInfo(name = "comentario")
    String comentario = "";

    public EventoEntity() {

    }

    public EventoEntity( String nombre, String fecha, String categoria, int imagen, String lugar, String descripcion, String precio, String hora, String comentario) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.categoria = categoria;
        this.imagen = imagen;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.precio = precio;
        this.hora = hora;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "EventoEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", categoria='" + categoria + '\'' +
                ", imagen=" + imagen +
                ", lugar='" + lugar + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio='" + precio + '\'' +
                ", hora='" + hora + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
