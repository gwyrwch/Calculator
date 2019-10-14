package com.example.calculator;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;
import java.util.logging.Logger;

public class Calculator {
    private ArrayList<Double> expression, inBracketsExp;
    private ArrayList<BinOperation> operators, inBracketsOprs;
    private String firstOperand, secondOperand, firstBrOperand, secondBrOperand;
    private BinOperation currentOperation, inBracketsOperation;
    FSM currentState;
    private boolean wasConstant;

    private static final String TAG = "Calculator";

    void Reset() {
        expression = new ArrayList<>();
        inBracketsExp = new ArrayList<>();
        operators = new ArrayList<>();
        inBracketsOprs = new ArrayList<>();

        currentOperation = BinOperation.NOTHING; inBracketsOperation = BinOperation.NOTHING;
        currentState = FSM.FINISHED;
        firstOperand = "0";
        wasConstant = false;
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
        Log.d(TAG, "passed digit " + digit + " state = " + currentState.toString());
        switch (currentState) {
            case FINISHED:
                firstOperand = digit;
                currentState = FSM.FIRST_OPERAND;
                break;
            case FIRST_OPERAND:
                if (wasConstant) {
                    firstOperand = "";
                }
                firstOperand += digit;
                break;
            case FIRST_OPERATION:
                if (lowPriority(currentOperation)) {
                    operators.add(currentOperation);
                    expression.add(parse(firstOperand));
                    currentOperation = BinOperation.NOTHING;

                    firstOperand = digit;
                    currentState = FSM.FIRST_OPERAND;
                } else {
                    secondOperand = digit;
                    currentState = FSM.SECOND_OPERAND;
                }
                break;
            case FIRST_IN_BRACKET_OPERAND:
                if (wasConstant) {
                    firstBrOperand = "";
                }
                firstBrOperand += digit;
                break;
            case IN_BRACKET_OPERATION:
                if (lowPriority(inBracketsOperation)) {
                    inBracketsOprs.add(inBracketsOperation);
                    inBracketsExp.add(parse(firstBrOperand));
                    inBracketsOperation = BinOperation.NOTHING;

                    firstBrOperand = digit;
                    currentState = FSM.FIRST_IN_BRACKET_OPERAND;
                } else {
                    secondBrOperand = digit;
                    currentState = FSM.SECOND_IN_BRACKET_OPERAND;
                }
                break;
            case SECOND_IN_BRACKET_OPERAND:
                if (wasConstant) {
                    secondBrOperand = "";
                }
                secondBrOperand += digit;
                break;
            case SECOND_OPERAND:
                if (wasConstant) {
                    secondOperand = "";
                }
                secondOperand += digit;
                break;
        }
        wasConstant = false;
    }

    public void onBinOpPass(BinOperation operation) {
        Log.d(TAG, "bin operation " + operation.toString() + " state = " + currentState.toString());
        switch (currentState) {
            case FINISHED:
            case FIRST_OPERAND:
                currentOperation = operation;
                currentState = FSM.FIRST_OPERATION;
                break;
            case FIRST_OPERATION:
                currentOperation = operation;
                break;
            case FIRST_IN_BRACKET_OPERAND:
                inBracketsOperation = operation;
                currentState = FSM.IN_BRACKET_OPERATION;
                break;
            case IN_BRACKET_OPERATION:
                inBracketsOperation = operation;
                break;
            case SECOND_IN_BRACKET_OPERAND:
                break;
            case SECOND_OPERAND:
                if (lowPriority(currentOperation)) {
                    operators.add(currentOperation);
                    expression.add(parse(firstOperand));

                    firstOperand = secondOperand;
                    currentOperation = operation;
                    secondOperand = "";
                    currentState = FSM.FIRST_OPERATION;
                } else {
                    firstOperand =
                        Double.toString(applyBinOp(currentOperation, parse(firstOperand), parse(secondOperand)));
                    secondOperand = "";
                    currentOperation = operation;

                    currentState = FSM.FIRST_OPERATION;
                }
                break;
        }
    }

    public void onMathConstantPass(double constant) {
        Log.d(TAG, "passed const" + Double.toString(constant) + " state = " + currentState.toString());
        String strConstant = Double.toString(constant);
        wasConstant = true;
        switch (currentState) {
            case FINISHED:
            case FIRST_OPERAND:
                firstOperand = strConstant;
                break;
            case FIRST_OPERATION:
                if (lowPriority(currentOperation)) {
                    operators.add(currentOperation);
                    expression.add(parse(firstOperand));
                    currentOperation = BinOperation.NOTHING;

                    firstOperand = strConstant;
                    currentState = FSM.FIRST_OPERAND;
                } else {
                    secondOperand = strConstant;
                    currentState = FSM.SECOND_OPERAND;
                }
            case FIRST_IN_BRACKET_OPERAND:
                firstBrOperand = strConstant;
                break;
            case IN_BRACKET_OPERATION:
                if (lowPriority(inBracketsOperation)) {
                    inBracketsOprs.add(inBracketsOperation);
                    inBracketsExp.add(parse(firstBrOperand));
                    inBracketsOperation = BinOperation.NOTHING;

                    firstBrOperand = strConstant;
                    currentState = FSM.FIRST_IN_BRACKET_OPERAND;
                } else {
                    secondBrOperand = strConstant;
                    currentState = FSM.SECOND_IN_BRACKET_OPERAND;
                }
                break;
            case SECOND_IN_BRACKET_OPERAND:
                secondBrOperand = strConstant;
                break;
            case SECOND_OPERAND:
                secondOperand = strConstant;
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onUnOp(DoubleUnaryOperator operator) {
        Log.d(TAG, "passed unary operation " + " state = " + currentState.toString());
        switch (currentState) {
            case FINISHED:
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

    public void onOpenBracket() {
        switch (currentState) {
            case FINISHED:
            case FIRST_OPERAND:
                if (firstOperand.equals("0")) {
                    currentOperation = BinOperation.ADD;
                    currentState = FSM.FIRST_IN_BRACKET_OPERAND;
                }
                break;
            case FIRST_OPERATION:
                firstBrOperand = "0";
                currentState = FSM.FIRST_IN_BRACKET_OPERAND;
                break;
            case FIRST_IN_BRACKET_OPERAND:
            case SECOND_OPERAND:
            case SECOND_IN_BRACKET_OPERAND:
            case IN_BRACKET_OPERATION:
                break;
        }
    }

    public void onClosingBracket() {
        double result;
        switch (currentState) {
            case FINISHED:
            case FIRST_OPERAND:
            case SECOND_OPERAND:
            case FIRST_OPERATION:
                break;
            case IN_BRACKET_OPERATION:
            case FIRST_IN_BRACKET_OPERAND:
            case SECOND_IN_BRACKET_OPERAND:
                if (currentState == FSM.SECOND_IN_BRACKET_OPERAND) {
                    inBracketsExp.add(applyBinOp(inBracketsOperation, parse(firstBrOperand), parse(secondBrOperand)));
                } else {
                    inBracketsExp.add(parse(firstBrOperand));
                }

                result = eval(inBracketsExp, inBracketsOprs);

                if (lowPriority(currentOperation)) {
                    operators.add(currentOperation);
                    expression.add(parse(firstOperand));

                    firstOperand = Double.toString(result);
                } else {
                    firstOperand =
                            Double.toString(applyBinOp(currentOperation, parse(firstOperand), result));
                }
                currentState = FSM.FINISHED;
                break;
        }

        inBracketsOperation = BinOperation.NOTHING;
        firstBrOperand = "";
        secondBrOperand = "";
        inBracketsExp.clear();
        inBracketsOprs.clear();
    }

    public void onAllClear() {
        Reset();
    }

    public void onClear() {
        // todo: refactor (you have 1 + 2 tap c and get 1 + and can again tap c and get 1
        switch (currentState) {
            case FINISHED:
            case FIRST_OPERAND:
                firstOperand = "0";
                break;
            case FIRST_OPERATION:
                currentOperation = BinOperation.NOTHING;
                currentState = FSM.FIRST_OPERAND;
                break;
            case FIRST_IN_BRACKET_OPERAND:
                firstBrOperand = "";
                break;
            case IN_BRACKET_OPERATION:
                inBracketsOperation = BinOperation.NOTHING;
                currentState = FSM.SECOND_IN_BRACKET_OPERAND;
                break;
            case SECOND_IN_BRACKET_OPERAND:
                secondBrOperand = "";
                break;
            case SECOND_OPERAND:
                secondOperand = "";
                break;
        }
    }

    public void onGetResult() {
        Log.d(TAG, "GET result " + " state = " + currentState.toString());
        switch (currentState) {
            case FINISHED:
            case FIRST_OPERAND:
                expression.add(parse(firstOperand));
                break;
            case FIRST_OPERATION:
                currentOperation = BinOperation.NOTHING;
                expression.add(parse(firstOperand));
                break;
            case FIRST_IN_BRACKET_OPERAND:
            case IN_BRACKET_OPERATION:
            case SECOND_IN_BRACKET_OPERAND:
                onClosingBracket();
                onGetResult();
                return;
            case SECOND_OPERAND:
                if (lowPriority(currentOperation)) {
                    expression.add(parse(firstOperand));
                    operators.add(currentOperation);
                    expression.add(parse(secondOperand));
                } else {
                    expression.add(applyBinOp(currentOperation, parse(firstOperand), parse(secondOperand)));
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
            case FINISHED:
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
        return result;
    }
}
