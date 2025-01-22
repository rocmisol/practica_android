package es.cm.dam2.pmdm.eventos_culturales;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import es.cm.dam2.pmdm.eventos_culturales.basedatos.DatabaseClient;
import es.cm.dam2.pmdm.eventos_culturales.basedatos.UsuarioDao;

public class BatteryService extends Service {
    private BroadcastReceiver batteryReceiver;

    //Método que se llama cuando se crea el servicio
    @Override
    public void onCreate() {
        super.onCreate();

        //Se crea el receptor y se registra para escuchar los cambios
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals((intent.getAction()))){
                    enviarEstadoBateria(context, intent);
                }
            }
        };

        //Se registra el receptor con un filtro para el nivel de batería
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // El servicio debe seguir corriendo hasta que lo detengamos explícitamente
        return START_STICKY;// El servicio se reinicia si es destruido por el sistema
    }

    // Como es un servicio no vinculado, se devuelve null
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        // Se desregistra el receptor cuando el servicio se destruye
        if (batteryReceiver != null){
            unregisterReceiver(batteryReceiver);
        }
    }

    //Método para comprobar el nivel de batería. Si es inferior al 15% se llama a enviarSmsAlertaBateriaBaja
    private void enviarEstadoBateria(Context context, Intent intent){
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryPercent = (int) ((level / (float) scale) * 100);

        // Si el nivel de batería es inferior al 15%
        if (batteryPercent < 5 & batteryPercent >= 4){
            try{
                enviarSmsAlertaBateriaBaja(context);
            }
            catch (Exception e){
                Toast.makeText(context, R.string.no_tienes_permisos_para_enviar_sms,Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Método para enviar un mensaje cuando el nivel de batería es bajo (<5%)
    private void enviarSmsAlertaBateriaBaja(Context context){
        String numeroTelefono = obtenerNumeroTelefonoAdmin(context);
        String mensaje = getString(R.string.el_nivel_de_bateria_est_por_debajo_del_5);

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numeroTelefono, null, mensaje, null, null);
            Toast.makeText(context, R.string.mensaje_enviado, Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            Toast.makeText(context, R.string.se_ha_producido_un_error_al_enviar_el_mensaje, Toast.LENGTH_SHORT).show();
        }
    }
    private String obtenerNumeroTelefonoAdmin (Context context){
        UsuarioDao usuarioDao = DatabaseClient.getInstance(context).usuarioDao();
        return usuarioDao.obtenerTelefonoAdministrador();
    }
}
