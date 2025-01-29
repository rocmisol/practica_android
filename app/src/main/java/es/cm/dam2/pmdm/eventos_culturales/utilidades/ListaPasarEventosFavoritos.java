package es.cm.dam2.pmdm.eventos_culturales.utilidades;

import java.io.Serializable;
import java.util.ArrayList;

import es.cm.dam2.pmdm.eventos_culturales.ui.Evento;


//Lista serializable para poder pasarla con putExtra en la intent
public class ListaPasarEventosFavoritos implements Serializable {
    ArrayList<Evento> listaEventosFavoritos;

    public ListaPasarEventosFavoritos(ArrayList<Evento> listaEventosFavoritos) {
        this.listaEventosFavoritos = listaEventosFavoritos;
    }

    public ArrayList<Evento> getListaEventosFavoritos() {
        return listaEventosFavoritos;
    }

    public void setListaEventosFavoritos(ArrayList<Evento> listaEventosFavoritos) {
        this.listaEventosFavoritos = listaEventosFavoritos;
    }

    @Override
    public String toString() {
        return "ListaEventosFavoritos{" +
                "listaEventosFavoritos=" + listaEventosFavoritos +
                '}';
    }
}
