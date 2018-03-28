package com.example.bhutani.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    Button controllerButton;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void set(){
        controllerButton.setText("Go");
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        timerSeekBar.setProgress(30);
        timerTextView.setText("0:30");
        counterIsActive = false;
    }

    public void update(int secondsLeft){

        int minutes = (int)secondsLeft/60;
        int seconds = secondsLeft - (minutes*60);

        String secondString = Integer.toString(seconds);

        if(seconds<=9){
            secondString = "0"+Integer.toString(seconds);
        }

        timerTextView.setText(Integer.toString(minutes)+":"+secondString);

    }

    public void controller(View view){

        if(counterIsActive == false) {
            controllerButton.setText("Stop");
            timerSeekBar.setEnabled(false);

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                public void onTick(long millisecondsUntilDone) {

                    update((int) millisecondsUntilDone / 1000);
                }

                public void onFinish() {

                    set();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mplayer.start();
                }

            }.start();
             counterIsActive = true;
        }else{
            set();
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.controllerButton);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                update(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
