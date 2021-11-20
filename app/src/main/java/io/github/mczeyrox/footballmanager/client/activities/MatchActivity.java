package io.github.mczeyrox.footballmanager.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.github.mczeyrox.footballmanager.R;

public class MatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
    }
    public void MainMenu(View view) {
        Log.d("ggg","back");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void startMatch(View view) {
        Intent intent = new Intent(this, RunningMatchActivity.class);
        startActivity(intent);
    }

}