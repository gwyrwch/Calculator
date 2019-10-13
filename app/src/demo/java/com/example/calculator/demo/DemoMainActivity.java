package com.example.calculator.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.calculator.Basic;
import com.example.calculator.R;

public class DemoMainActivity extends AppCompatActivity implements Basic.OnExpressionPass {
    private TextView mainTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainTextView = findViewById(R.id.mainTextView);
    }

    @Override
    public void onExpressionPass(String expression) {
        mainTextView.setText(expression);
    }
}
