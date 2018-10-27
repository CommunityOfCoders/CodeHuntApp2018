package com.coc.codehunt;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

public class Finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        TextView statsTV = findViewById(R.id.statsTV);
        SharedPreferences pref = getSharedPreferences(Constants.SP, MODE_PRIVATE);
        StringBuilder stats = new StringBuilder("<h2>Your Stats</h2><br>");
        stats.append("<table>");
        stats.append("<tr> <th>Question No.</th> <th>Time</th> <th>Hints</th> <th>Effective Time</th> </tr>");
        for (int i = 1; i <= 6; i++) {
            long time = (pref.getLong("Q" + i + "Time", 0) -
                    pref.getLong("Q" + (i - 1) + "Time", 0));
            int hints = pref.getInt("Q" + i + "Hints", 0);
            long effTime = time + TeamData.calc_hint_time(hints);
            stats.append("<tr> <td>").append(i).
                    append("</td> <td>").append(time).
                    append("</td> <td>").append(hints).
                    append("</td> <td>").append(effTime).append("</td> </tr>");
        }
        stats.append("</table>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            statsTV.setText(Html.fromHtml(stats.toString(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            statsTV.setText(Html.fromHtml(stats.toString()));
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You've Completed the Hunt!", Toast.LENGTH_LONG).show();
    }
}
