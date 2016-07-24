package com.ForestAnimals.nophone.login_and_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ForestAnimals.nophone.R;
import com.ForestAnimals.nophone.Service.FileService;

import java.io.OutputStream;

/**
 * Created by dell on 2016/7/11.
 */
public class register extends Activity {

    FileService service = new FileService();

    private EditText editText_register_identification;
    private EditText editText_register_password;
    private Button button_register_nextstep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        initUI();
        //
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        editText_register_identification = (EditText) findViewById(R.id.editText_register_identification);
        editText_register_password = (EditText) findViewById(R.id.editText_register_password);
        button_register_nextstep = (Button) findViewById(R.id.button_register_nextstep);

        button_register_nextstep.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            Intent intent = new Intent();
            switch (button.getId()) {
                case R.id.button_register_nextstep:
                    //跳转到登陆界面，并保存个人信息
                    try {
                        OutputStream outStream_user_identification = register.this.openFileOutput("editText_user_identification", MODE_PRIVATE);
                        OutputStream outStream_user_password = register.this.openFileOutput("editText_user_password", MODE_PRIVATE);
                        service.save(outStream_user_identification, editText_register_identification.getText().toString());
                        service.save(outStream_user_password, editText_register_password.getText().toString());
                        //保存信息
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    intent.setClass(register.this, register_information.class);
                    startActivity(intent);
                    break;
            }
        }
    };


    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击后退会退出程序
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(register.this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                setResult(RESULT_OK);
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
