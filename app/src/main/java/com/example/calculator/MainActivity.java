package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.TextView;

import java.io.Console;

public class MainActivity extends AppCompatActivity implements Basic.OnExpressionPass {
    private TextView mainTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        int width = getWindowManager().getDefaultDisplay().getWidth() ;
        int height = getWindowManager().getDefaultDisplay().getHeight() ;

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
