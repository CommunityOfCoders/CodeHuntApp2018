package com.example.yash.codehunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You've Completed the Hunt!", Toast.LENGTH_SHORT).show();
    }
}
