package com.ForestAnimals.nophone.login_and_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.ForestAnimals.nophone.MainActivity;
import com.ForestAnimals.nophone.R;
import com.ForestAnimals.nophone.Service.FileService;
import com.ForestAnimals.nophone.help;

import java.io.InputStream;

/**
 * Created by dell on 2016/7/9.
 */
public class login extends Activity {

    FileService service = new FileService();

    private EditText editText_login_identification;
    private EditText editText_login_password;
    private Button button_login;
    private Button button_login_register;
    private Button button_login_help;
    private ImageView tree_button_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initUI();
        read_login();

//        Intent intentmusic = new Intent(getBaseContext(),MusicService.class);
//        intentmusic.putExtra("isPlaying", true);
//        startService(intentmusic);
//        //���ű�������

    }


    /**
     * ��ʼ��UI
     */
    private void initUI() {
        editText_login_identification = (EditText) findViewById(R.id.editText_login_identification);
        editText_login_password = (EditText) findViewById(R.id.editText_login_password);
        button_login = (Button) findViewById(R.id.button_login);
        button_login_register = (Button) findViewById(R.id.button_login_register);
        button_login_help = (Button) findViewById(R.id.button_login_help);

        button_login.setOnClickListener(listener);
        button_login_register.setOnClickListener(listener);
        button_login_help.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            Intent intent = new Intent();
            switch (button.getId()) {
                case R.id.button_login:
                    //��ת��������
                    Toast.makeText(login.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                    intent.setClass(login.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_login_register:
                    //��ת��ע�����
                    intent.setClass(login.this, register.class);
                    startActivity(intent);
                    break;
                case R.id.button_login_help:
                    //��ת����������
                    intent.setClass(login.this, help.class);
                    startActivity(intent);
                    break;
            }

        }
    };

    public void read_login()
    //��ȡ��Ϣ
    {
        try {
            InputStream inStream_login_identification = login.this.openFileInput("editText_user_identification");
            InputStream inStream_login_password = login.this.openFileInput("editText_user_password");
            editText_login_identification.setText(service.read(inStream_login_identification));
            editText_login_password.setText(service.read(inStream_login_password));
            //��ȡ��Ϣ
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //˫�����˻��˳�����
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(login.this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
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