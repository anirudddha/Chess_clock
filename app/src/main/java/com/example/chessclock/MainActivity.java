package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView textViewTimer1;
    private TextView textViewTimer2;
    private Button reset;
    private Button pause;

    private CountDownTimer countDownTimer1;
    private CountDownTimer countDownTimer2;
    private long timeLeftInMillis1;
    private long timeLeftInMillis2;
    private boolean isTimerRunning1;
    private boolean isTimerRunning2;

    private boolean isBothTimersRunning = true;

    private static final long START_TIME_IN_MILLIS1 = 60000*10; // 1 minute
    private static final long START_TIME_IN_MILLIS2 = 60000*10; // 2 minutes


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer1 = findViewById(R.id.textViewTimer1);
        textViewTimer2 = findViewById(R.id.textViewTimer2);
        reset = findViewById(R.id.reset);
        pause = findViewById(R.id.pause);

        textViewTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTimerRunning1) {
//                    pauseTimer1();
//                    startTimer2();
                } else {
                    playClickSound();
                    startTimer1();
                    if(isTimerRunning2) pauseTimer2();
                }
            }
        });

        textViewTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning2) {
//                    pauseTimer2();
//                    startTimer1();
                } else {
                    playClickSound();
                    startTimer2();
                    if(isTimerRunning1) pauseTimer1();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playresetsound();
                resetTimer();
                isBothTimersRunning = true;
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playresetsound();
                if (isBothTimersRunning) {
                    pauseBothTimers();
                } else {
                    startBothTimers();
                }
            }
        });


    }


    private void startTimer1() {
        countDownTimer1 = new CountDownTimer(timeLeftInMillis1, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis1 = millisUntilFinished;
                updateTimer1();
            }

            @Override
            public void onFinish() {
                isTimerRunning1 = false;
            }
        }.start();

        isTimerRunning1 = true;
    }

    private void startTimer2() {
        countDownTimer2 = new CountDownTimer(timeLeftInMillis2, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis2 = millisUntilFinished;
                updateTimer2();
            }

            @Override
            public void onFinish() {
                isTimerRunning2 = false;
            }
        }.start();

        isTimerRunning2 = true;
    }

    private void pauseTimer1() {
        countDownTimer1.cancel();
        isTimerRunning1 = false;
    }

    private void pauseTimer2() {
        countDownTimer2.cancel();
        isTimerRunning2 = false;
    }

    private void resetTimer() {
        timeLeftInMillis1 = START_TIME_IN_MILLIS1;
        timeLeftInMillis2 = START_TIME_IN_MILLIS2;
        updateTimer1();
        updateTimer2();
        pauseBothTimers();
        isTimerRunning1 = false;
        isTimerRunning2 = false;
    }

    private void updateTimer1() {
        int minutes = (int) (timeLeftInMillis1 / 1000) / 60;
        int seconds = (int) (timeLeftInMillis1 / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        textViewTimer1.setText(timeLeftFormatted);
    }

    private void updateTimer2() {
        int minutes = (int) (timeLeftInMillis2 / 1000) / 60;
        int seconds = (int) (timeLeftInMillis2 / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        textViewTimer2.setText(timeLeftFormatted);
    }

    private void pauseBothTimers() {
        if(isTimerRunning1) {
            countDownTimer1.cancel();
        }
        if(isTimerRunning2) {
            countDownTimer2.cancel();
        }
        isBothTimersRunning = false;
        pause.setText("Resume");
    }

    private void startBothTimers() {
        if(isTimerRunning1) {
            startTimer1();
        }
        if(isTimerRunning2) {
            startTimer2();
        }
        isBothTimersRunning = true;
        pause.setText("Pause");
    }

    private void playClickSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.click_sound);
        mediaPlayer.start();
    }
    private void playresetsound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.reset_sound);
        mediaPlayer.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}