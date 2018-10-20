package com.example.yash.codehunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView play;
    EditText teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        play = findViewById(R.id.play);
        teamName = findViewById(R.id.team_name);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = teamName.getText().toString();

                // if name exits in the data base

                    // some way to create a link between this team and database
                    // so every time hint is taken or answer is solved
                    // time of those events are recorded
                    // and database as well as leaderboard is updated
                    // for all the players

                    //starting the game
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                // else print Toast
                    // Error toast
            }
        });
    }
}
