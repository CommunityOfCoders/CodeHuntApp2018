package com.example.yash.codehunt;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class TeamData {
    public String name;
    public long start_time;
    public int current_ques, total_time, q1, q2, q3, q4, q5, q6;
    public TeamData(String name, int current_ques, long start_time, int total_time,
                    int q1, int q2, int q3, int q4, int q5, int q6) {
        this.name = name;
        this.current_ques = current_ques;
        this.start_time = start_time;
        this.total_time = total_time;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
    }

    public TeamData() {
        // Default constructor required for calls to DataSnapshot.getValue(TeamData.class)
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.FB_Name, name);
        result.put(Constants.FB_CurrentQues, current_ques);
        result.put(Constants.FB_StartTime, start_time);
        result.put(Constants.FB_TotalTime, total_time);
        result.put(Constants.FB_Q1, q1);
        result.put(Constants.FB_Q2, q2);
        result.put(Constants.FB_Q3, q3);
        result.put(Constants.FB_Q4, q4);
        result.put(Constants.FB_Q5, q5);
        result.put(Constants.FB_Q6, q6);
        return result;
    }
}