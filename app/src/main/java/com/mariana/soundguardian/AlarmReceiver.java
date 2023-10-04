package com.mariana.soundguardian;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String action = extras.getString("action");

            if (action != null && action.equals("ACTION_START_ALARM")) {
                // Se recibe la acción para iniciar la alarma de sonido
                reproducirAlarma(context);
            }
        }
    }

    private void reproducirAlarma(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.alarmita); // Reemplaza con tu sonido de alarma
            mediaPlayer.setLooping(true);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
}
