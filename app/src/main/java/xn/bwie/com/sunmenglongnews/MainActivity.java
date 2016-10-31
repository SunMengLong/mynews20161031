package xn.bwie.com.sunmenglongnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import xn.bwie.com.sunmenglongnews.base.BaseData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseData baseData = new BaseData() {

            @Override
            public void setResultData(String data) {
                Toast.makeText(MainActivity.this, data + "---", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void setFailResult(int code) {
                Toast.makeText(MainActivity.this, "fail" + code, Toast.LENGTH_SHORT).show();
            }
        };

        baseData.getData("https://www.baidu.com", 1000 * 60);
    }
}
