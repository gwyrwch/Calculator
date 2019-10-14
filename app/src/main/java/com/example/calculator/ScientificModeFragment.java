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

import java.util.ArrayList;


public class ScientificModeFragment extends Fragment implements View.OnClickListener {
    private View v;

    private OnExpressionPass dataPasser;

    private static final ArrayList<String> buttonIds = new ArrayList<String>() {
        {
            add("button_sin");
            add("button_cos");
            add("button_tan");
            add("button_pi");
            add("button_sinh");
            add("button_cosh");
            add("button_tanh");
            add("button_openbr");
            add("button_closebr");
            add("button_fact");
            add("button_inverse");
            add("button_lg");
            add("button_ln");
            add("button_log2");
            add("button_sqrt");
            add("button_cbrt");
            add("button_n_root");
            add("button_power");
            add("button_exp");
            add("button_rand");
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
        v = inflater.inflate(R.layout.fragment_scientific_mode, container, false);

        for (int i = 0; i < buttonIds.size(); i++) {
            int id = this.getResources().getIdentifier(buttonIds.get(i), "id", getActivity().getPackageName());
            Button b = v.findViewById(id);
            b.setOnClickListener(this);
        }
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N) //todo: remove this by changing minApi
    @Override
    public void onClick(View v) {
        Calculator calculator = dataPasser.getCalculator();
        switch (v.getId()) {
            case R.id.button_sin:
                calculator.onUnOp(
                    x -> Math.sin(Math.toRadians(x))
                );
                break;
            case R.id.button_cos:
                calculator.onUnOp(
                    x -> Math.cos(Math.toRadians(x))
                );
                break;
            case R.id.button_tan:
                calculator.onUnOp(
                    x -> Math.tan(Math.toRadians(x))
                );
                break;
            case R.id.button_sinh:
                calculator.onUnOp(
                    x -> Math.sinh(Math.toRadians(x))
                );
                break;
            case R.id.button_cosh:
                calculator.onUnOp(
                    x -> Math.cosh(Math.toRadians(x))
                );
                break;
            case R.id.button_tanh:
                calculator.onUnOp(
                    x -> Math.tanh(Math.toRadians(x))
                );
                break;
            case R.id.button_pi:
                calculator.onMathConstantPass(Math.PI);
                break;
            case R.id.button_openbr:
                calculator.onOpenBracket();
                break;
            case R.id.button_closebr:
                calculator.onClosingBracket();
                break;
            case R.id.button_fact:
                calculator.onUnOp(
                    x -> {
                        long y = (long)(x + 0.5);
                        if (Math.abs(x - y) < 1e-12) {
                            if (y > 103) {
                                return Double.POSITIVE_INFINITY;
                            }
                            double res = 1;
                            for (int i = 1; i <= y; i++)
                                res *= i;
                            return res;
                        }
                        return Double.NaN;
                    }
                );
                break;
            case R.id.button_inverse:
                calculator.onUnOp(
                    x -> 1. / x
                );
                break;
            case R.id.button_lg:
                calculator.onUnOp(
                    Math::log10
                );
                break;
            case R.id.button_ln:
                calculator.onUnOp(
                    x -> Math.log10(x) / Math.log10(Math.E)
                );
                break;
            case R.id.button_log2:
                calculator.onUnOp(
                    x -> Math.log10(x) / Math.log10(2)
                );
                break;
            case R.id.button_exp:
                calculator.onMathConstantPass(Math.E);
                break;
            case R.id.button_rand:
                calculator.onMathConstantPass(Math.random());
                break;
            case R.id.button_sqrt:
                calculator.onUnOp(
                    Math::sqrt
                );
                break;
            case R.id.button_cbrt:
                calculator.onUnOp(
                    Math::cbrt
                );
                break;
            case R.id.button_n_root:
                calculator.onBinOpPass(BinOperation.NROOT);
                break;
            case R.id.button_power:
                calculator.onBinOpPass(BinOperation.NPOWER);
                break;
        }

        dataPasser.onExpressionPass();

    }
}
