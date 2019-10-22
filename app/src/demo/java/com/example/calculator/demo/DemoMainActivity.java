package com.example.calculator.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.calculator.Basic;
import com.example.calculator.Calculator;
import com.example.calculator.OnExpressionPass;
import com.example.calculator.R;


public class DemoMainActivity extends AppCompatActivity implements OnExpressionPass {
    private TextView mainTextView;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);

        calculator = ViewModelProviders.of(this).get(Calculator.class);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mainTextView = findViewById(R.id.mainTextView);

        calculator.currentDisplay.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mainTextView.setText(s);
            }
        });
    }

    @Override
    public void onExpressionPass() {
        calculator.display();
    }

    @Override
    public Calculator getCalculator() {
        return calculator;
    }
}
