package com.example.yash.codehunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionFragment extends Fragment {
    private static final String TAG = "QuestionFragment";

    private TextView questionNumber;
    private EditText passCode;
    private Button nextButton;
    private Button hintsButton;

    // lab3 -> NetLab -> VLSILab -> NCC -> Lab3Q1 -> Lab3Q2 -> END
    private int[] passcodes = { 124067, 834592, 348215, 783152, 672153, 681354 };
    private String[] questions = {
            "GET CODIN!",          // Lab3 -> NetLab           // 124067
            "SCRAM - UNSCRAM",      // NetLab -> VLSILab        // 834592
            "RIDDLE",               // VLSILab -> NCC           // 348215
            "CIPHER",               // NCC -> Lab3Q1            // 783152
            "IS SYMM?",             // Lab3Q1 -> Lab3Q2         // 672153
            "INV COUNT"              // Lab3Q2 -> END            // 681354
    };
    private int curr_question = 0;
    private int hints = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container, false);

        questionNumber = view.findViewById(R.id.question_number);
        passCode = view.findViewById(R.id.passcode);
        nextButton = view.findViewById(R.id.next);
        hintsButton = view.findViewById(R.id.hints);

        questionNumber.setText(questions[curr_question]);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passCode.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "ENTER PASSCODE", Toast.LENGTH_SHORT).show();
                    return;
                }

                int code = Integer.parseInt(passCode.getText().toString());
                passCode.setText("");
                passCode.setHint("PASSCODE");

                if (curr_question < 6 && code == passcodes[curr_question]) {

                    // update the database
                    // update hints, ques_solved, time


                    // reseting hints to 0
                    hints = 0;

                    // updating the app
                    curr_question++;

                    // if all questions solved
                    if (curr_question == 6) {
                        Toast.makeText(getActivity().getApplicationContext(), "WOAH YOU DID IT", Toast.LENGTH_SHORT).show();

                        // For now returning to start of App TODO go to Finish activity
                        Intent intent = new Intent(getActivity(), CodehuntActivity.class);
                        getActivity().startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "SOLVE NEXT!", Toast.LENGTH_SHORT).show();
                        questionNumber.setText(questions[curr_question]);
                    }
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "INCORRECT", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hints < 3) {
                    Toast.makeText(getActivity().getApplicationContext(), "GIVE HINT", Toast.LENGTH_SHORT).show();
                    hints++;
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "NO HINTS LEFT", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }
}
