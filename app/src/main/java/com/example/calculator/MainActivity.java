package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnClear, btnBracket, btnPercent, btnDivision, btnMultiply, btnMinus, btnPlus, btnDot, btnEqual;
    TextView tvInput, tvOutput;
    String process;
    boolean checkBracket = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDivision = findViewById(R.id.btnDivision);
        btnMultiply = findViewById(R.id.btnMultiply);

        btnEqual = findViewById(R.id.btnEqual);

        btnClear = findViewById(R.id.btnClear);
        btnDot = findViewById(R.id.btnDot);
        btnPercent = findViewById(R.id.btnPercent);
        btnBracket = findViewById(R.id.btnBracket);

        tvInput = findViewById(R.id.tvInput);
        tvOutput = findViewById(R.id.tvOutput);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnDivision.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);

        btnEqual.setOnClickListener(this);

        btnClear.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnPercent.setOnClickListener(this);
        btnBracket.setOnClickListener(this);
    }

    private void setTvInput(String ch) {
        process = tvInput.getText().toString();
        tvInput.setText(process + ch);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                setTvInput("0");
                break;
            case R.id.btn1:
                setTvInput("1");
                break;
            case R.id.btn2:
                setTvInput("2");
                break;
            case R.id.btn3:
                setTvInput("3");
                break;
            case R.id.btn4:
                setTvInput("4");
                break;
            case R.id.btn5:
                setTvInput("5");
                break;
            case R.id.btn6:
                setTvInput("6");
                break;
            case R.id.btn7:
                setTvInput("7");
                break;
            case R.id.btn8:
                setTvInput("8");
                break;
            case R.id.btn9:
                setTvInput("9");
                break;
            case R.id.btnClear:
                tvInput.setText("");
                tvOutput.setText("");
                break;
            case R.id.btnPlus:
                setTvInput("+");
                break;
            case R.id.btnMinus:
                setTvInput("-");
                break;
            case R.id.btnMultiply:
                setTvInput("×");
                break;
            case R.id.btnDivision:
                setTvInput("÷");
                break;
            case R.id.btnDot:
                setTvInput(".");
                break;
            case R.id.btnPercent:
                setTvInput("%");
                break;
            case R.id.btnBracket:
                if (checkBracket) {
                    setTvInput(")");
                    checkBracket = false;
                }else {
                    setTvInput("(");
                    checkBracket = true;
                }
                break;
            case R.id.btnEqual:
                process = tvInput.getText().toString();

                process = process.replaceAll("×", "*");
                process = process.replaceAll("%", "/100");
                process = process.replaceAll("÷", "/");

                Context rhino = Context.enter();
                rhino.setOptimizationLevel(-1);

                String finalResult = "";

                try {
                    Scriptable scriptable = rhino.initStandardObjects();
                    finalResult = rhino.evaluateString(scriptable, process, "javascript", 1, null).toString();
                }catch (Exception e) {
                    finalResult = "Ошибка!";
                }

                if (finalResult.length() > 10) tvOutput.setTextSize(26);
                if (finalResult.length() < 10) tvOutput.setTextSize(60);

                tvOutput.setText(finalResult);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putString("Save_tvInput", tvInput.getText().toString());
        outState.putString("Save_tvOutput", tvOutput.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        tvInput.setText(savedInstanceState.getString("Save_tvInput"));
        tvOutput.setText(savedInstanceState.getString("Save_tvOutput"));

    }

}
