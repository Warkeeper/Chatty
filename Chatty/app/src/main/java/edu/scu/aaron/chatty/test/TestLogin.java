package edu.scu.aaron.chatty.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import edu.scu.aaron.chatty.R;
import edu.scu.aaron.chatty.beans.UserBean;
import edu.scu.aaron.chatty.bmobutils.BmobUtils;

public class TestLogin extends AppCompatActivity {

    private EditText et_name;
    private EditText et_password;
    private UserBean userBean;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);
        et_name=findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        tv=findViewById(R.id.tv);

    }

    public void login(View view) {
        String name=et_name.getText().toString().trim();
        String password=et_password.getText().toString().trim();
        BmobUtils.getBmobUtilsInstance().userLogin(name, password, new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    Toast.makeText(TestLogin.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestLogin.this, "登陆失败！" + "\nReason: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void register(View view) {
        String name=et_name.getText().toString().trim();
        String password=et_password.getText().toString().trim();
        BmobUtils.getBmobUtilsInstance().userRegister(name, password, password, new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    Toast.makeText(TestLogin.this, "注册成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestLogin.this, "注册失败！" + "\n原因: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
