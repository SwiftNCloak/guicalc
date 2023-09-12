package com.mj_bonifacio.guicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // Lines
    TextView firstLine, secondLine;

    private double operand1 = 0;
    private double operand2 = 0;
    private double res = 0;

    // Number Buttons
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;

    // Operator and Parenthesis Buttons
    Button buttonAdd, buttonMinus, buttonMultiply, buttonDivide, buttonAnswer, buttonParent1, buttonParent2;

    // Negative sign, Dot, and backspace
    Button btnDot, btnBSpace;

    // CE and C
    Button buttonCE, buttonC;

    boolean currActive;
    boolean opeActive = false;
    boolean decimalPointAdded = false; // Flag to track decimal point

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Input fields
        firstLine = (TextView) findViewById(R.id.tv_line1);
        secondLine = (TextView) findViewById(R.id.tv_line2);
        decimalPointAdded = false;
    }

    public void addNum (View add){
        if (currActive) {
            secondLine = (TextView) findViewById(R.id.tv_line2);
            Button btn = (Button) add;
            String num = secondLine.getText().toString() + btn.getText().toString();
            secondLine.setText(num);
            // Remove the leading "0" if it exists
            if (secondLine.getText().toString().equals("0")) {
                secondLine.setText(btn.getText().toString());
            }
        } else {
            // Remove the initial "0" when a user starts typing a number.
            secondLine = (TextView) findViewById(R.id.tv_line2);
            secondLine.setText("");
            Button btn = (Button) add;

            String lol = secondLine.getText().toString();

            String num = lol + btn.getText().toString();
            secondLine.setText(num);

            currActive = true;
        }
    }

    public void addOpe (View ope){
        currActive = false;
        decimalPointAdded = false;

        if (opeActive){
            String secondText = secondLine.getText().toString();
            String firstText = firstLine.getText().toString();
            double newVal = solve(firstText, secondText);
            Button btn = (Button) ope;
            String oper = btn.getText().toString();
            String eq = newVal + oper;
            firstLine.setText(eq);
        }
        else {
            Button btn = (Button) ope;
            String oper = btn.getText().toString();
            String eq = secondLine.getText().toString() + oper;
            firstLine.setText(eq);

            opeActive = true;
        }
    }

    public void equal (View solve){
        firstLine = (TextView) findViewById(R.id.tv_line1);
        secondLine = (TextView) findViewById(R.id.tv_line2);
        String secondText = secondLine.getText().toString();
        String firstText = firstLine.getText().toString();

        if (!firstText.isEmpty()){
            firstLine.setText(firstText + secondText);
            double res = solve(firstText, secondText);
            secondLine.setText(Double.toString(res));
        }
        else {
            Toast.makeText(this, "Invalid operation!", Toast.LENGTH_LONG).show();
        }
    }

    public double solve (String first, String second){
        first = first.replace("x", "*");
        double result = calculateResult(first, second);
        return result;
    }

    public double calculateResult (String operation, String secondOperand){
        operand1 = Double.parseDouble(operation.substring(0, operation.length() - 1));
        operand2 = Double.parseDouble(secondOperand);
        res = 0;

        switch (operation.charAt(operation.length() - 1)) {
            case '+':
                res = operand1 + operand2;
                break;
            case '-':
                res = operand1 - operand2;
                break;
            case '*':
                res = operand1 * operand2;
                break;
            case '/':
                if (operand2 != 0)
                    res = operand1 / operand2;
                else{
                    Toast.makeText(this, "UNDEFINED", Toast.LENGTH_LONG).show();
                }
                break;
            case '%':
                res = operand1 % operand2;
                break;
        }
        return res;
    }

    public void del (View del){
        String secondText = secondLine.getText().toString();

        if (!secondText.isEmpty()) {
            // Check if the last character is a decimal point.
            if (secondText.endsWith(".")) {
                decimalPointAdded = false;
            }

            String newCurr = secondText.substring(0, secondText.length() - 1);

            // Reset to "0" if all characters are removed.
            if (newCurr.equals("")) {
                newCurr = "";
            }

            secondLine.setText(newCurr);
        }

    }

    public void addDot(View dot) {
        // Check if a decimal point is already added.
        if (!decimalPointAdded) {
            String secondText = secondLine.getText().toString();
            // If no numbers are present, add "0" before the decimal point.
            if (secondText.isEmpty()) {
                secondText = "0";
            }
            // Add the decimal point and set the flag.
            secondText += ".";
            secondLine.setText(secondText);
            decimalPointAdded = true;
        }
    }

    public void clr (View clear){
        String secondText = "";
        secondLine.setText(secondText);
        decimalPointAdded = false;
    }

    public void clrAll (View clearAll){
        String secondText = "";
        secondLine.setText(secondText);
        firstLine.setText("");
        decimalPointAdded = false;

        // Reset the double variables to zero
        operand1 = 0;
        operand2 = 0;
        res = 0;
    }
}