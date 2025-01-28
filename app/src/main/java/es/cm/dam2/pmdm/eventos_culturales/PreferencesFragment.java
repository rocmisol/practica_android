package es.cm.dam2.pmdm.eventos_culturales;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


public class PreferencesFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences sharedPreferences;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);

        //Se obtienene las preferencias
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Se registra el listener de cambios en preferencias
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        if (key.equals("pref_tema")){
            boolean modoOscuro = sharedPreferences.getBoolean("pref_tema", false);
            cambiarTema(modoOscuro);
        }

        if (key.equals("pref_seleccion_sonido")){
            String sonidoInicio =sharedPreferences.getString("pref_seleccion_sonido", "magia");
            actualizarSonidoIncio(sonidoInicio);
        }

        if (key.equals("pref_sonido")){
            boolean activarSonido = sharedPreferences.getBoolean("pref_sonido", true);
            configurarSonido(activarSonido);
        }

        if (key.equals("pref_bienvenida")){
            String mensajeBienvenida = sharedPreferences.getString("pref_bienvenida", "Bienvenido");
            actualizarMensajeBienvenida(mensajeBienvenida);
        }

    }

    private void cambiarTema(boolean modoOscuro){
        AppCompatDelegate.setDefaultNightMode(
                modoOscuro? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void actualizarSonidoIncio(String sonido){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pref_seleccion_sonido", sonido);
        editor.apply();
    }

    private void configurarSonido (boolean activarSonido){
        // Se envia un broadcast para notificar a LoginActivity
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("pref_sonido", activarSonido);
        editor.apply();
    }

    private void actualizarMensajeBienvenida(String mensaje){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pref_bienvenida", mensaje);
        editor.apply();
    }


}
