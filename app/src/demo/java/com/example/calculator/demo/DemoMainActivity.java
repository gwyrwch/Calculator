package com.example.calculator.demo;

import androidx.appcompat.app.AppCompatActivity;

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

        calculator = new Calculator();

        int width = getWindowManager().getDefaultDisplay().getWidth() / 7;
        int height = getWindowManager().getDefaultDisplay().getHeight() / 4;

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        height = (int) ((height/displayMetrics.density)+0.5);
        width = (int) ((width/displayMetrics.density)+0.5);
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
}
