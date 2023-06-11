package ru.mirea.komaristyi.mireaproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import ru.mirea.komaristyi.mireaproject.databinding.ActivityAudioRecordBinding;
import ru.mirea.komaristyi.mireaproject.databinding.ActivityMainBinding;

public class AudioRecord extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 200;
    private final String TAG = getClass().getSimpleName();
    private boolean isWork = false;
    private Button recordButton;
    private Button playButton;
    private String recordFilePath;

    private boolean isStartRecording = true;
    private boolean isStartPlaying = true;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);

        recordButton = findViewById(R.id.recordButton);
        playButton = findViewById(R.id.playButton);
        playButton.setEnabled(false);
        recordFilePath = (new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "/audioRecordTest.3gp")).getAbsolutePath();
        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);


        if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION);
        }

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartRecording) {
                    recordButton.setText("ОСТАНОВИТЬ ЗАПИСЬ");
                    playButton.setEnabled(false);
                    startRecording();
                } else {
                    recordButton.setText("НАЧАТЬ ЗАПИСЬ");
                    playButton.setEnabled(true);
                    stopRecording();
                }
                isStartRecording = !isStartRecording;
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartPlaying) {
                    playButton.setText("ОСТАНОВИТЬ");
                    recordButton.setEnabled(false);
                    startPlaying();
                } else {
                    playButton.setText("ВОСПРОИЗВЕСТИ");
                    recordButton.setEnabled(true);
                    stopPlaying();
                }
                isStartPlaying = !isStartPlaying;
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!isWork) finish();
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() Recording failed");
        }
        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();


        } catch (IOException e) {
            Log.e(TAG, "Произведение не удалось");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }
}