package edu.scu.aaron.chatty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.scu.aaron.chatty.test.TestLogin;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * @Deprecated
         * 下面两行仅供测试，进行正式开发时需要注释掉
         */
        Intent intent=new Intent(this, TestLogin.class);
        startActivity(intent);
    }
}
