package io.github.mczeyrox.footballmanager.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.mczeyrox.footballmanager.R;
import io.github.mczeyrox.footballmanager.client.player.SoccerClub;
import io.github.mczeyrox.footballmanager.core.utility.Utility;

public class CreateClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

        findViewById(R.id.create_club_button).setOnClickListener(v -> {
            CreateClub(v);
        });
    }

    public void CreateClub(View view) {
        SoccerClub club = new SoccerClub();

        TextView name = findViewById(R.id.create_club_name);

        club.setClubName(name.getText().toString());
        Utility.LaunchAcitvity(this, MainActivity.class);
        finish();
    }
}