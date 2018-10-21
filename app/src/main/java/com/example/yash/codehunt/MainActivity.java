package com.example.yash.codehunt;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CodeHunt";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // setup the viewpager with the section adaapter
        mViewPager = findViewById(R.id.container);
        setupviewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupviewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new QuestionFragment(), "QUESTION");
        adapter.addFragment(new LeaderboardFragment(), "LEADERBOARD");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "There's no looking back ;)", Toast.LENGTH_SHORT).show();
    }
}