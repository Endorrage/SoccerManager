package io.github.mczeyrox.footballmanager.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.github.mczeyrox.footballmanager.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void startMatch(View view) {
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
    }
    public void verein(View view) {
        Intent intent = new Intent(this, VereinActivity.class);
        startActivity(intent);
    }
}