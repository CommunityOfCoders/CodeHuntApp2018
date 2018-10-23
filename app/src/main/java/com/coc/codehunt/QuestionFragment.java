package com.coc.codehunt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.content.Context.CONTEXT_IGNORE_SECURITY;
import static android.content.Context.MODE_PRIVATE;

public class QuestionFragment extends Fragment {
    private static final String TAG = "QuestionFragment";
    private TextView questionNumber;
    private EditText passCode;
    private Button nextButton;
    private Button hintsButton;
    SharedPreferences pref;
    private int[] passcodes = {124067, 834592, 348215, 783152, 672153, 681354};
    private String[] questions = {
            "GET CODIN!",          // Lab3 -> NetLab           // 124067
            "SCRAM - UNSCRAM",      // NetLab -> VLSILab        // 834592
            "RIDDLE",               // VLSILab -> NCC           // 348215
            "CIPHER",               // NCC -> Lab3Q1            // 783152
            "THE MATRIX",             // Lab3Q1 -> Lab3Q2         // 672153
            "INV COUNT"              // Lab3Q2 -> END            // 681354
    };
    private int curr_question; // Real_Q - 1
    private int curr_hints;
    private long curr_start_time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container, false);
        questionNumber = view.findViewById(R.id.question_number);
        passCode = view.findViewById(R.id.passcode);
        nextButton = view.findViewById(R.id.next);
        hintsButton = view.findViewById(R.id.hints);
        pref = Objects.requireNonNull(getContext()).getSharedPreferences(com.coc.codehunt.Constants.SP, MODE_PRIVATE);

        curr_question = pref.getInt(com.coc.codehunt.Constants.CurrentQuestion, 0); // Real_Q-1
        curr_hints = pref.getInt(com.coc.codehunt.Constants.Hints, 0);
        curr_start_time = pref.getLong("Q" + Integer.toString(curr_question) + "Time", 0);
        Log.e(TAG, String.format("onCreateView: %d, %d, %d", curr_question, curr_hints, curr_start_time));

        if (curr_question >= 6) {
            Intent i = new Intent(getContext(), com.coc.codehunt.Finish.class);
            startActivity(i);
//            return view;
        }

        questionNumber.setText(questions[curr_question]);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passCode.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Enter the Passcode", Toast.LENGTH_SHORT).show();
                    return;
                }
                int code = Integer.parseInt(passCode.getText().toString());
                passCode.setText("");
                passCode.setHint("Passcode");
                if (curr_question < 6 && code == passcodes[curr_question]) {
                    long time = Math.round(System.currentTimeMillis() / 1000);
                    curr_question++;
                    int tot_hints = pref.getInt(com.coc.codehunt.Constants.TotalHints, 0) + curr_hints;
                    Log.e(TAG, "onClick: tot_hints = "+tot_hints );

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt(com.coc.codehunt.Constants.CurrentQuestion, curr_question);
                    editor.putInt(com.coc.codehunt.Constants.Hints, 0);
                    editor.putLong("Q" + Integer.toString(curr_question) + "Time", time); // end time of ques no. curr_ques
                    editor.putInt(com.coc.codehunt.Constants.TotalHints, tot_hints);
                    editor.commit();
                    Log.e(TAG, String.format("onClick: %d, %d, %d, %d", curr_question, 0, time, tot_hints));

                    updateFBDB(curr_question, curr_hints);
                    curr_hints = 0;
                    curr_start_time = time;

                    if (curr_question == 6) {   // all questions solved
                        Toast.makeText(getContext(), "Congratulations!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), com.coc.codehunt.Finish.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Great Going!", Toast.LENGTH_SHORT).show();
                        questionNumber.setText(questions[curr_question]);
                    }
                } else {
                    Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curr_hints < 3) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setMessage("Are you sure you want to take a hint?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "Ask a volunteer to give you a hint", Toast.LENGTH_SHORT).show();
                            curr_hints++;
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(getContext(), "You don't have any hints left...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void updateFBDB(final int curr_question, final int current_hints) {
        final DatabaseReference teams = FirebaseDatabase.getInstance().getReference().child("teams");
        final String key = pref.getString(com.coc.codehunt.Constants.Key, com.coc.codehunt.Constants.Key);
        Log.e(TAG, "updateFBDB: key = "+key);
        teams.child(key).child(com.coc.codehunt.Constants.FB_TotalTime).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer tot_time = dataSnapshot.getValue(Integer.class);
                teams.child(key).child(com.coc.codehunt.Constants.FB_CurrentQues).setValue(curr_question + 1);
                Log.e(TAG, "onDataChange: curr_ques = " + (curr_question + 1));
                // did curr_ques
                int time = (int) (pref.getLong("Q" + curr_question + "Time", 0) -
                        pref.getLong("Q" + (curr_question - 1) + "Time", 0));
                time += calc_hint_time(current_hints);
                teams.child(key).child("q" + curr_question).setValue(time);
                Log.e(TAG, "onDataChange: q" + curr_question +" = "+ time);
                try {
                    tot_time += time;
                    teams.child(key).child(com.coc.codehunt.Constants.FB_TotalTime).setValue(tot_time);
                    Log.e(TAG, "onDataChange: tot_time = "+tot_time );
                } catch (NullPointerException e) {}

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private int calc_hint_time(int hints) {
        switch (hints) {
            case 1:
                return 180;
            case 2:
                return 180 * 2 + 120;
            case 3:
                return 180 * 3 + 120;
            default:
                return 0;
        }
    }
}
