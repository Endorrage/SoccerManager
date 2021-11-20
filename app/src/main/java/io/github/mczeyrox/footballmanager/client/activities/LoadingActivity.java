package io.github.mczeyrox.footballmanager.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import io.github.mczeyrox.footballmanager.R;
import io.github.mczeyrox.footballmanager.client.player.SoccerClub;
import io.github.mczeyrox.footballmanager.core.utility.DataContainer;
import io.github.mczeyrox.footballmanager.core.utility.Utility;
import io.github.mczeyrox.footballmanager.core.utility.text.TextUtility;
import io.github.mczeyrox.footballmanager.core.utility.threads.AsyncCore;

public class LoadingActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private String[] dotStrings = new String[] { ".", "..", "..." };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading);
        SetTask("Initializing");
        LoadApp();
    }

    private void SetTask(String taskText) {
        TextView task = findViewById(R.id.taskDisplay);
        task.setText(taskText + "...");

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        final int[] index = {0};
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 450) {
            public void onTick(long millisUntilFinished) {
                task.setText(taskText + dotStrings[index[0]++]);
                if (index[0] >= dotStrings.length) {
                    index[0] = 0;
                }
            }
            public void onFinish() {
                start();
            }
        }.start();
    }

    private void LoadApp() {
        SetTask("Downloading newest player data");
        AsyncCore.Run(() -> DataContainer.init()).await();

        SetTask("Loading club");
        try {
            SoccerClub.localClub = (SoccerClub) TextUtility.read("club.soccer", this, SoccerClub.class);
            if (SoccerClub.localClub == null) {
                Utility.LaunchAcitvity(this, CreateClubActivity.class);
                finish();
                return;
            }
        } catch (IOException e) {
            Toast.makeText(this, "Can't load club", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        SetTask("Please wait while finalizing everything");
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Utility.LaunchAcitvity(this, MainActivity.class);
            finish();
        }, 1000);
    }
}