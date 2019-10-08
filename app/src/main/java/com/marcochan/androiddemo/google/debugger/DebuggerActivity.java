package com.marcochan.androiddemo.google.debugger;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.marcochan.androiddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DebuggerActivity extends AppCompatActivity {

    @BindView(R.id.num_1_et)
    EditText num_1_et;

    @BindView(R.id.num_2_et)
    EditText num_2_et;

    @BindView(R.id.result_tv)
    TextView result_tv;

    @BindView(R.id.add_btn)
    Button add_btn;

    @BindView(R.id.sub_btn)
    Button sub_btn;

    @BindView(R.id.div_btn)
    Button div_btn;

    @BindView(R.id.mul_btn)
    Button mul_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debugger);
        ButterKnife.bind(this);
    }

    private double[] getNum() {
        double[] num = new double[2];
        num[0] = Double.valueOf(num_1_et.getText().toString());
        num[1] = Double.valueOf(num_2_et.getText().toString());
        return num;
    }

    @OnClick(R.id.add_btn)
    public void addBtnClick() {
        double[] num = getNum();
        double result = num[0] + num[1];
        result_tv.setText(Double.toString(result));
    }

    @OnClick(R.id.sub_btn)
    public void subBtnClick() {
        double[] num = getNum();
        double result = num[0] - num[1];
        result_tv.setText(Double.toString(result));
    }

    @OnClick(R.id.div_btn)
    public void divBtnClick() {
        double[] num = getNum();
        double result = num[0] / num[1];
        result_tv.setText(Double.toString(result));
    }

    @OnClick(R.id.mul_btn)
    public void mulBtnClick() {
        double[] num = getNum();
        double result = num[0] * num[1];
        result_tv.setText(Double.toString(result));
    }


}
