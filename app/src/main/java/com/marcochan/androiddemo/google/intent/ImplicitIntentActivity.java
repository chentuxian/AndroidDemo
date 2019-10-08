package com.marcochan.androiddemo.google.intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marcochan.androiddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 隐式Intent的使用
 */
public class ImplicitIntentActivity extends AppCompatActivity {

    @BindView(R.id.url_et)
    EditText url_et;
    @BindView(R.id.url_btn)
    Button url_btn;
    @BindView(R.id.location_et)
    EditText location_et;
    @BindView(R.id.location_btn)
    Button location_btn;
    @BindView(R.id.text_et)
    EditText text_et;
    @BindView(R.id.text_btn)
    Button text_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.url_btn)
    public void urlBtnClick(){
        String url = url_et.getText().toString();
        // 将字符串转换成URI(统一资源描述符类)
        Uri uri = Uri.parse(url);
        // 创建一个隐式Intent,指定Action和Data
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        // 判断该intent有没有可处理的activity
        if(intent.resolveActivity(getPackageManager())!=null) startActivity(intent);
        else Toast.makeText(this, "没有可以适配启动的Activity", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.location_btn)
    public void locationBtnClick(){
        String url = location_et.getText().toString();
        // 将字符串转成Uri
        Uri uri = Uri.parse("geo:0,0?q=" + url);
        // 创建一个隐式Intent,指定Action和Data
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        // 判断该intent有没有可处理的activity
        if(intent.resolveActivity(getPackageManager())!=null) startActivity(intent);
        else Toast.makeText(this, "没有可以适配启动的Activity", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.text_btn)
    public void textBtnClick(){
        String txt = text_et.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share this text with: ")
                .setText(txt)
                .startChooser();
    }


}
