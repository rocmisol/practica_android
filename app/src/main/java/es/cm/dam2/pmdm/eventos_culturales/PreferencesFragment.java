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

        if (key.equals("pref_color_letra")){
            String colorKey =sharedPreferences.getString("pref_color_letra", "negro");
            cambiarColorLetra(colorKey);
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

    private void cambiarColorLetra(String colorKey){
        if (getActivity() == null){
            return;
        }

        int cambiarTema;

        //Si se selecciona el color negro, se aplica el tema predeterminado de Android
        if (colorKey.equals("negro")){
            // cambiarTema = android.R.style.Theme_DeviceDefault_Light;
            cambiarTema = android.R.style.Theme_DeviceDefault_Light;

        }
        //Si se selecciona el color rojo, se aplica el tema con estilo rojo
        else if (colorKey.equals("rojo")){
            cambiarTema = R.style.AppTheme_ColorRed;
        }
        //Si se selecciona el color azul, se aplica el tema con estilo azul
        else if (colorKey.equals("azul")){
            cambiarTema = R.style.AppTheme_ColorDarkBlue;
        }
        else{
            return;
        }

        getActivity().setTheme(cambiarTema);
        getActivity().recreate();

    }

    private void configurarSonido (boolean activarSonido){
        // Se envia un broadcast para notificar a LoginActivity
        Intent intent = new Intent("ACTUALIZAR_SONIDO");
        intent.putExtra("activarSonido", activarSonido);
        requireContext().sendBroadcast(intent);
    }

    private void actualizarMensajeBienvenida(String mensaje){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pref_bienvenida", mensaje);
        editor.apply();
    }


}
