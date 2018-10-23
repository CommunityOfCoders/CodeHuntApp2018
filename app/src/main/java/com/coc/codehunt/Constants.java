package com.coc.codehunt;

public class Constants {
    public static final String SP = "SP";
    public static final String CurrentQuestion = "CurrQues";  // int
    public static final String Key = "key"; // Team UID
    public static final String TeamName = "TeamName";
    public static final String Hints = "Hints";
    public static final String TotalHints = "TotalHints";
//    public static final String Q0Time = "Q0Time";  // Start Time      // End Time of Q0
//    public static final String Q1Time = "Q1Time";  // End Time of Q1
//    public static final String Q2Time = "Q2Time";
//    public static final String Q3Time = "Q3Time";
//    public static final String Q4Time = "Q4Time";
//    public static final String Q5Time = "Q5Time";
//    public static final String Q6Time = "Q6Time";
    public static final String FB_Name = "name";
    public static final String FB_CurrentQues = "current_ques";
    public static final String FB_StartTime = "start_time";
    public static final String FB_TotalTime = "total_time";
    public static final String FB_Q1 = "q1";
    public static final String FB_Q2 = "q2";
    public static final String FB_Q3 = "q3";
    public static final String FB_Q4 = "q4";
    public static final String FB_Q5 = "q5";
    public static final String FB_Q6 = "q6";
}


/*
{
  "teams": {
    "$id": {
      "name": "$name",
      "current_ques":2,
      "start_time": 1151,
      "total_time", 820,  // total time, updated after completion of each question
      "q1": 820,
      "q2": -1,
      "q3": -1,
      "q4": -1,
      "q5": -1,
      "q6": -1
    },
    "$id": { ... },
    "$id": { ... }
  }
}
 */