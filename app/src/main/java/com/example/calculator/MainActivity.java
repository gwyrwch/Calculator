package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnExpressionPass {
    private TextView mainTextView;
    private Calculator calculator;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();

        // todo: make if with current activity orientation
        // todo: when changing orientation Activity recreates
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //fixme: refactor this code and method setButtonMode

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Basic fragment = new Basic();
            fragmentTransaction.add(R.id.fragment_frame, fragment);
            fragmentTransaction.commit();
            currentFragment = fragment;
            setButtonMode();

        }


    }

    private void setButtonMode() {
        final Button btn = findViewById(R.id.button_mode);
        btn.setOnClickListener(v -> {
            Fragment newFragment;
            int id;
            if (currentFragment.getClass() == Basic.class) {
                System.out.println("mda");
                newFragment = new ScientificModeFragment();
                id = R.id.fragment_frame;
            } else {
                System.out.println("lol");
                newFragment = new Basic();
                id = R.id.fragment_frame;
            }
            currentFragment = newFragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(id, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainTextView = findViewById(R.id.mainTextView);
    }

    @Override
    public void onExpressionPass() {
        mainTextView.setText(calculator.display());
    }

    @Override
    public Calculator getCalculator() {
        return calculator;
    }

    private Fragment getCurrentFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();

        return getSupportFragmentManager()
                .findFragmentByTag(fragmentTag);
    }
}
