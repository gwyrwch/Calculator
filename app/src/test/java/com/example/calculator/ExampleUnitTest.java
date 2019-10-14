package com.example.calculator;

import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void division() {
        Calculator calculator = new Calculator();
        calculator.onDigitPass("2");
        calculator.onBinOpPass(BinOperation.DIVISION);
        calculator.onDigitPass("6");
        calculator.onGetResult();
        assertEquals(Double.parseDouble(calculator.display()), 1.0/3.0, 1e-9);
    }
}