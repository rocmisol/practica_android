package es.cm.dam2.pmdm.eventos_culturales;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.Locale;

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

    }

    private void cambiarTema(boolean modoOscuro){
        AppCompatDelegate.setDefaultNightMode(
                modoOscuro? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void cambiarColorLetra(String colorKey){
        String colorLetra ="AppTheme.Color" + colorKey;

        int colorID = getResources().getIdentifier(colorLetra, "style", getContext().getPackageName());
        if (colorID != 0){
            getActivity().setTheme(colorID);
        }
        // getActivity().setTheme(R.style.AppTheme_ColorRed);
    }

    private void configurarSonido (boolean activarSonido){
        // Se envia un broadcast para notificar a LoginActivity
        Intent intent = new Intent("ACTUALIZAR_SONIDO");
        intent.putExtra("activarSonido", activarSonido);
        requireContext().sendBroadcast(intent);
    }


}
