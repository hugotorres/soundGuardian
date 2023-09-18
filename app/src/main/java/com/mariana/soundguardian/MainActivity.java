package com.mariana.soundguardian;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final int RECORD_AUDIO_PERMISSION_CODE = 1;
    private Button startStopButton;
    private TextView soundLevelTextView;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;

    private static final int SOUND_THRESHOLD = 70; // Establece tu umbral de ruido deseado aquí

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startStopButton = findViewById(R.id.startStopButton);
        soundLevelTextView = findViewById(R.id.soundLevelTextView);

        // Comprueba y solicita permisos de grabación de audio si es necesario.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
        }

        startStopButton.setOnClickListener(v -> {
            if (!isRecording) {
                startRecording();
            } else {
                stopRecording();
            }
        });
    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile("/dev/null");

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            startStopButton.setText("Detener");
            monitorSoundLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            startStopButton.setText("Iniciar");
        }
    }

    private void monitorSoundLevel() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int amplitude = getAmplitude();
                        soundLevelTextView.setText("Nivel de ruido: " + amplitude + " dB");

                        if (amplitude > SOUND_THRESHOLD) {
                            // El nivel de ruido supera el umbral, puedes mostrar una alerta aquí.
                            // Por ejemplo, puedes mostrar un diálogo o enviar una notificación.
                            // ¡No olvides solicitar permisos para mostrar notificaciones si es necesario!
                        }
                    }
                });
            }
        }, 0, 1000); // Actualiza cada segundo
    }

    private int getAmplitude() {
        if (mediaRecorder != null) {
            return mediaRecorder.getMaxAmplitude() / 2700;
        } else {
            return 0;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de grabación de audio concedido.
                // Puedes habilitar la funcionalidad de grabación aquí si lo deseas.
            }
        }
    }
}
