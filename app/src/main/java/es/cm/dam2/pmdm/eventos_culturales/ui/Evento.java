package es.cm.dam2.pmdm.eventos_culturales.ui;

import java.io.Serializable;

public class Evento implements Serializable {
    int id;
    String nombre;
    String fecha;
    String categoria;
    int imagen;
    String lugar;
    String descripcion;
    String precio;
    boolean favorito;
    float valoracion;
    String hora;
    String comentario = "";

    public Evento(int id, String nombre, String fecha, String clase, int imagen, String lugar, String descripcion, String precio, boolean favorito, float valoracion, String hora) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.categoria = clase;
        this.imagen = imagen;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.precio = precio;
        this.favorito = favorito;
        this.valoracion = valoracion;
        this.hora = hora;
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

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
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
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", categoria='" + categoria + '\'' +
                ", imagen=" + imagen +
                ", lugar='" + lugar + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio='" + precio + '\'' +
                ", favorito=" + favorito +
                ", valoracion=" + valoracion +
                ", hora='" + hora + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
