package com.example.yash.codehunt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CodehuntActivity extends AppCompatActivity {
    private TextView TeamNameET;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences(Constants.SP, MODE_PRIVATE);
        String TN = pref.getString(Constants.TeamName, Constants.TeamName);
        if(!TN.equals(Constants.TeamName)) {
            Intent intent = new Intent(CodehuntActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_codehunt);
        TeamNameET = findViewById(R.id.TeamNameET);
    }

    public void onClickStart(View v) {
        final String teamName = TeamNameET.getText().toString().trim();
        if(pref.getInt(Constants.CurrentQuestion,0) >= 6) {
            Intent i = new Intent(this, Finish.class);
            startActivity(i);
            finish();
        }
        if (!teamName.equals("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Ready?");
            alertDialogBuilder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    init(teamName);
                    Intent intent = new Intent(CodehuntActivity.this, MainActivity.class);
                    Toast.makeText(CodehuntActivity.this, "All the Best!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else
            Toast.makeText(this, "Please Enter Your Team Name!", Toast.LENGTH_SHORT).show();
    }

    void init(String teamName) {
        String tN = pref.getString(Constants.TeamName, Constants.TeamName);
        if (tN.equals(Constants.TeamName)) { // This is the first time
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(Constants.TeamName, teamName);

            long startTime = Math.round(System.currentTimeMillis() / 1000);
            editor.putLong("Q0Time", startTime);

            DatabaseReference teams = FirebaseDatabase.getInstance().getReference().child("teams");
            String key = teams.push().getKey();
            editor.putString(Constants.Key, key);
            TeamData team = new TeamData(teamName, 1, startTime, 0, -1, -1, -1, -1, -1, -1);
            teams.child(key).setValue(team);
            editor.commit();
        }
    }
}
