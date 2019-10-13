package com.example.calculator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;

public class Calculator {
    private ArrayList<Double> expression, inBracketsExp;
    private ArrayList<BinOperation> operators, inBracketsOprs;
    private String firstOperand, secondOperand, firstBrOperand, secondBrOperand;
    private BinOperation currentOperator, inBracketsOperator;
    FSM currentState;


//    int priority(int operator) {
////        switch (operator) {
////
////        }
//    }

    public Calculator() {
        expression = new ArrayList<>();
        inBracketsExp = new ArrayList<>();
        operators = new ArrayList<>();
        inBracketsOprs = new ArrayList<>();

        currentOperator = BinOperation.NOTHING; inBracketsOperator = BinOperation.NOTHING;
        currentState = FSM.FIRST_OPERAND;
        firstOperand = "0";
    }

    private boolean lowPriority(BinOperation op) {
        return op == BinOperation.ADD || op == BinOperation.SUB;
    }

    private double parse(String s) {
        return Double.parseDouble(s);
    }

    public void onDigitPass(String digit) {
        switch (currentState) {
            case FIRST_OPERAND:
                firstOperand += digit;
                break;
            case FIRST_OPERATION:
                if (lowPriority(currentOperator)) {
                    operators.add(currentOperator);
                    expression.add(parse(firstOperand));
                    currentOperator = BinOperation.NOTHING;

                    firstOperand = digit;
                    currentState = FSM.FIRST_OPERAND;
                } else {
                    secondOperand = digit;
                    currentState = FSM.SECOND_OPERAND;
                }
                break;
            case FIRST_IN_BRACKET_OPERAND:
                firstBrOperand += digit;
                break;
            case IN_BRACKET_OPERATION:
                if (lowPriority(inBracketsOperator)) {
                    inBracketsOprs.add(inBracketsOperator);
                    inBracketsExp.add(parse(firstBrOperand));
                    inBracketsOperator = BinOperation.NOTHING;

                    firstBrOperand = digit;
                    currentState = FSM.FIRST_IN_BRACKET_OPERAND;
                } else {
                    secondBrOperand = digit;
                    currentState = FSM.SECOND_IN_BRACKET_OPERAND;
                }
                break;
            case SECOND_IN_BRACKET_OPERAND:
                secondBrOperand += digit;
                break;
            case SECOND_OPERAND:
                secondOperand += digit;
                break;
        }
    }

    public void onBinOpPass(BinOperation operation) {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onUnOp(DoubleUnaryOperator operator) {
        switch (currentState) {
            case FIRST_OPERAND:
                firstOperand = ((Double)operator.applyAsDouble(parse(firstOperand))).toString(); // todo change minsdk
                break;
            case FIRST_OPERATION:
                break;
            case FIRST_IN_BRACKET_OPERAND:
                firstBrOperand = ((Double)operator.applyAsDouble(parse(firstBrOperand))).toString();
                break;
            case IN_BRACKET_OPERATION:
                break;
            case SECOND_IN_BRACKET_OPERAND:
                secondBrOperand = ((Double)operator.applyAsDouble(parse(secondBrOperand))).toString();
                break;
            case SECOND_OPERAND:
                secondOperand = ((Double)operator.applyAsDouble(parse(secondOperand))).toString();
                break;
        }
    }

    public void onOpenBracket(){}

    public void onClosingBracket(){}

    public void onAllClear(){}

    public void onClear() {}

    public void onGetResult() {}
}
