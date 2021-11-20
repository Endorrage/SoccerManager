package io.github.mczeyrox.footballmanager.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.github.mczeyrox.footballmanager.R;

public class VereinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verein);
    }
    public void endVerein(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}