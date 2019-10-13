package com.example.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class ScientificModeFragment extends Fragment implements View.OnClickListener {
    private View v;

    OnExpressionPass dataPasser;

    private static final ArrayList<String> buttonIds = new ArrayList<String>() {
        {
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

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button_sin:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_cos:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_tan:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_sinh:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_cosh:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_tanh:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_pi:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_openbr:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_closebr:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_fact:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_inverse:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_lg:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_ln:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_log2:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_exp:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_rand:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_sqrt:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_cubic:
//                dataPasser.onExpressionPass("1");
//                break;
//            case R.id.button_n_root:
//                dataPasser.onExpressionPass("2");
//                break;
//            case R.id.button_power:
//                dataPasser.onExpressionPass("1");
//                break;
//        }
    }

}
