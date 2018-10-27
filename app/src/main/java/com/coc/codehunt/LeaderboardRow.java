package com.coc.codehunt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

public class LeaderboardRow extends ConstraintLayout {

    public LeaderboardRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("SetTextI18n")
    public LeaderboardRow(Context context, String teamName, String rank, String questionNo, String time, boolean bold, boolean header) {
        super(context);
        TextView TeamName, Time, Rank, QuestionNo;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.leaderboard_row, this);
        TeamName = findViewById(R.id.TeamName);
        Rank = findViewById(R.id.Rank);
        Time = findViewById(R.id.Time);
        QuestionNo = findViewById(R.id.QuestionNo);
        TeamName.setText(teamName);
        Rank.setText(rank);
        QuestionNo.setText(questionNo);
        Time.setText(time);
        if(bold) {
            TeamName.setTextColor(getResources().getColor(R.color.black));
            Rank.setTextColor(getResources().getColor(R.color.black));
            QuestionNo.setTextColor(getResources().getColor(R.color.black));
            Time.setTextColor(getResources().getColor(R.color.black));
        }
        if(header) {
            TeamName.setPaintFlags(TeamName.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            Rank.setPaintFlags(Rank.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            QuestionNo.setPaintFlags(QuestionNo.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            Time.setPaintFlags(Time.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    public LeaderboardRow(Context context, String teamName, String rank, String questionNo, String time) {
        this(context, teamName, rank, questionNo, time, false, false);
    }
    public LeaderboardRow(Context context, String teamName, String rank, String questionNo, String time, boolean bold) {
        this(context, teamName, rank, questionNo, time, bold, false);
    }
}
