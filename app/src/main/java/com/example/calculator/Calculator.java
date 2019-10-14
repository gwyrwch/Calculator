package com.example.calculator;

import android.icu.text.SymbolTable;
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

    void Reset() {
        expression = new ArrayList<>();
        inBracketsExp = new ArrayList<>();
        operators = new ArrayList<>();
        inBracketsOprs = new ArrayList<>();

        currentOperator = BinOperation.NOTHING; inBracketsOperator = BinOperation.NOTHING;
        currentState = FSM.FIRST_OPERAND;
        firstOperand = "0";
    }

    public Calculator() {
        Reset();
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
        switch (currentState) {
            case FIRST_OPERAND:
                currentOperator = operation;
                currentState = FSM.FIRST_OPERATION;
                break;
            case FIRST_OPERATION:
                currentOperator = operation;
                break;
            case FIRST_IN_BRACKET_OPERAND:
                inBracketsOperator = operation;
                currentState = FSM.IN_BRACKET_OPERATION;
                break;
            case IN_BRACKET_OPERATION:
                inBracketsOperator = operation;
                break;
            case SECOND_IN_BRACKET_OPERAND:
                break;
            case SECOND_OPERAND:
                if (lowPriority(currentOperator)) {
                    operators.add(currentOperator);
                    expression.add(parse(firstOperand));

                    firstOperand = secondOperand;
                    currentOperator = operation;
                    secondOperand = "";
                    currentState = FSM.FIRST_OPERATION;
                } else {
                    firstOperand =
                            Double.toString(applyBinOp(currentOperator, parse(firstBrOperand), parse(secondOperand)));
                    secondOperand = "";
                    currentOperator = operation;
                }
                break;
        }
    }

    public void onMathConstantPass(double constant) {

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

    public void onAllClear() {
        Reset();
    }

    public void onClear() {}

    public void onGetResult() {
        switch (currentState) {
            case FIRST_OPERAND:
                expression.add(parse(firstOperand));
                break;
            case FIRST_OPERATION:
                currentOperator = BinOperation.NOTHING;
                expression.add(parse(firstOperand));
                break;
            case FIRST_IN_BRACKET_OPERAND:
            case IN_BRACKET_OPERATION:
            case SECOND_IN_BRACKET_OPERAND:
                onClosingBracket();
                onGetResult();
                return;
            case SECOND_OPERAND:
                if (lowPriority(currentOperator)) {
                    expression.add(parse(firstOperand));
                    operators.add(currentOperator);
                    expression.add(parse(secondOperand));
                } else {
                    expression.add(applyBinOp(currentOperator, parse(firstBrOperand), parse(secondOperand)));
                }
                break;
        }

        double result = eval(expression, operators);
        Reset();
        firstOperand = Double.toString(result);
    }

    private double applyBinOp(BinOperation operation, double a, double b) {
        switch (operation) {
            case MULT:
                return a * b;
            case DIVISION:
                return a / b;
            case MOD:
                return a % b;
            case ADD:
                return a + b;
            case SUB:
                return a - b;
            case NROOT:
                return Math.pow(a, 1 / b);
            case NPOWER:
                return Math.pow(a, b);
        }

        return -1;
    }

    private double eval(ArrayList<Double> expression, ArrayList<BinOperation> operations) {
        if (expression.size() == 0) {
            return 0;
        }

        double result = expression.get(0);
        for (int i = 1; i < expression.size(); i++) {
            result = applyBinOp(operations.get(i - 1), result, expression.get(i));
        }
        return result;
    }

    public String display() {
        String result = "";
        switch (currentState) {
            case FIRST_OPERAND:
            case FIRST_OPERATION:
                result = firstOperand;
                break;
            case FIRST_IN_BRACKET_OPERAND:
            case IN_BRACKET_OPERATION:
                result = firstBrOperand;
                break;
            case SECOND_IN_BRACKET_OPERAND:
                result = secondBrOperand;
                break;
            case SECOND_OPERAND:
                result = secondOperand;
                break;
        }
        return Double.toString(parse(result));
    }
}
