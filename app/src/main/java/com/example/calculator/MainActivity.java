package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Basic.OnExpressionPass {
    private TextView mainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        int width = getWindowManager().getDefaultDisplay().getWidth() / 7;
        int height = getWindowManager().getDefaultDisplay().getHeight() / 4;

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        height = (int) ((height/displayMetrics.density)+0.5);
        width = (int) ((width/displayMetrics.density)+0.5);

        System.out.println("kek");
        System.out.println(width);
        System.out.println(height);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainTextView = findViewById(R.id.mainTextView);
    }

    @Override
    public void onExpressionPass(String expression) {
        mainTextView.setText(expression);

        System.out.println(mainTextView.getText());
    }




}
