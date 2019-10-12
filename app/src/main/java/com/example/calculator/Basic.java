package com.example.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Basic extends Fragment implements View.OnClickListener {
    private TextView mainTextView;
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
        v.findViewById(R.id.button_add);

        for (int i = 0; i <  10; i++) {
            System.out.println(buttonIds.size());
        }
        for (int i = 0; i < buttonIds.size(); i++) {
            int id = this.getResources().getIdentifier(buttonIds.get(i), "id", getActivity().getPackageName());
            Button b = v.findViewById(id);
            System.out.println(b  == null);
            System.out.println(b);
            b.setOnClickListener(this);
        }


//        mainTextView = getActivity().findViewById(R.id.mainTextView);

        return v;
    }


    public interface OnExpressionPass {
        public void onExpressionPass(String Expression);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_one:
                dataPasser.onExpressionPass("1");
                break;
            case R.id.button_two:
                dataPasser.onExpressionPass("2");
                break;
            case R.id.button_three:
                dataPasser.onExpressionPass("3");
                break;
            case R.id.button_four:
                dataPasser.onExpressionPass("4");
                break;
            case R.id.button_five:
                dataPasser.onExpressionPass("5");
                break;
            case R.id.button_six:
                dataPasser.onExpressionPass("6");
                break;
            case R.id.button_seven:
                dataPasser.onExpressionPass("7");
                break;
            case R.id.button_eight:
                dataPasser.onExpressionPass("8");
                break;
            case R.id.button_nine:
                dataPasser.onExpressionPass("9");
                break;
            case R.id.button_zero:
                dataPasser.onExpressionPass("0");
                break;
            case R.id.button_div:
                dataPasser.onExpressionPass("/");
                break;
            case R.id.button_mult:
                dataPasser.onExpressionPass("*");
                break;
            case R.id.button_add:
                dataPasser.onExpressionPass("+");
                break;
            case R.id.button_sub:
                dataPasser.onExpressionPass("-");
                break;
            case R.id.button_period:
                dataPasser.onExpressionPass(".");
                break;

        }
    }

}
