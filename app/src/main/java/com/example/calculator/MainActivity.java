package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnExpressionPass {
    private TextView mainTextView;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();

        int width = getWindowManager().getDefaultDisplay().getWidth() / 7;
        int height = getWindowManager().getDefaultDisplay().getHeight() / 4;

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        height = (int) ((height/displayMetrics.density)+0.5);
        width = (int) ((width/displayMetrics.density)+0.5);

        System.out.println("kek");
        System.out.println(width);
        System.out.println(height);

//        App.Current.Resources[""] = new SolidColorBrush(Colors.Red); // Red for example
    


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
