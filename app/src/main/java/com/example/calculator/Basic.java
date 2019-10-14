package com.example.calculator;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;


public class Basic extends Fragment implements View.OnClickListener {
    private View v;

    OnExpressionPass dataPasser;
    private static final ArrayList<String> buttonIds = new ArrayList<String>() {
        {
            add("button_one");
            add("button_two");
            add("button_three");
            add("button_four");
            add("button_five");
            add("button_six");
            add("button_seven");
            add("button_eight");
            add("button_nine");
            add("button_zero");
            add("button_div");
            add("button_mult");
            add("button_add");
            add("button_sub");
            add("button_period");
            add("button_percent");
            add("button_ac");
            add("button_clear");
            add("button_sign");
            add("button_eq");
        }

    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnExpressionPass) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_basic, container, false);

        for (int i = 0; i < buttonIds.size(); i++) {
            int id = this.getResources().getIdentifier(buttonIds.get(i), "id", getActivity().getPackageName());
            Button b = v.findViewById(id);
            b.setOnClickListener(this);
        }
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        Calculator calculator = dataPasser.getCalculator();
        switch (v.getId()) {
            case R.id.button_one:
                calculator.onDigitPass("1");
                break;
            case R.id.button_two:
                calculator.onDigitPass("2");
                break;
            case R.id.button_three:
                calculator.onDigitPass("3");
                break;
            case R.id.button_four:
                calculator.onDigitPass("4");
                break;
            case R.id.button_five:
                calculator.onDigitPass("5");
                break;
            case R.id.button_six:
                calculator.onDigitPass("6");
                break;
            case R.id.button_seven:
                calculator.onDigitPass("7");
                break;
            case R.id.button_eight:
                calculator.onDigitPass("8");
                break;
            case R.id.button_nine:
                calculator.onDigitPass("9");
                break;
            case R.id.button_zero:
                calculator.onDigitPass("0");
                break;
            case R.id.button_div:
                calculator.onBinOpPass(BinOperation.DIVISION);
                break;
            case R.id.button_mult:
                calculator.onBinOpPass(BinOperation.MULT);
                break;
            case R.id.button_add:
                calculator.onBinOpPass(BinOperation.ADD);
                break;
            case R.id.button_sub:
                calculator.onBinOpPass(BinOperation.SUB);
                break;
            case R.id.button_period:
                calculator.onDigitPass(".");
                break;
            case R.id.button_eq:
                calculator.onGetResult();
                break;
            case R.id.button_ac:
                calculator.onAllClear();
                break;
            case R.id.button_percent:
                calculator.onBinOpPass(BinOperation.MOD);
                break;
            case R.id.button_clear:
                calculator.onClear();
                break;
            case R.id.button_sign:
                calculator.onUnOp(
                    x -> (-x)
                );
                break;

        }
        dataPasser.onExpressionPass();
    }
}
