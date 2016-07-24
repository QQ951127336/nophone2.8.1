package com.ForestAnimals.nophone.login_and_register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.ForestAnimals.nophone.R;
import com.ForestAnimals.nophone.Service.FileService;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * Created by dell on 2016/7/11.
 */
public class register_information extends Activity {

    FileService service = new FileService();

    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// ����
    public static final int PHOTOZOOM = 2; // ����
    public static final int PHOTORESOULT = 3;// ���
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private EditText editText_register_nickname;
    private RadioGroup radioGroup_register_sex;
    private RadioButton radioButton_register_boy;
    private RadioButton radioButton_register_girl;
    private EditText editText_register_birthday;
    private Spinner spinner_register_constellation;
    private ArrayAdapter adapter_constellation;
    private EditText editText_register_hobby;
    private EditText editText_register_email;
    private EditText editText_register_motto;
    private Button button_register_finish;
    private ImageView imageView_register_head;

    String pro_constellation;//������������
    String pro_sex;//�����Ա����

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_information);

        initUI();
        connection();
    }

    /**
     * ��ʼ��UI
     */
    private void initUI() {
        editText_register_nickname = (EditText) findViewById(R.id.editText_register_nickname);
        radioGroup_register_sex = (RadioGroup) findViewById(R.id.radioGroup_register_sex);
        radioButton_register_boy = (RadioButton) findViewById(R.id.radioButton_register_boy);
        radioButton_register_girl = (RadioButton) findViewById(R.id.radioButton_register_girl);
        editText_register_birthday = (EditText) findViewById(R.id.editText_register_birthday);
        spinner_register_constellation = (Spinner) findViewById(R.id.spinner_register_constellation);
        editText_register_hobby = (EditText) findViewById(R.id.editText_register_hobby);
        editText_register_email = (EditText) findViewById(R.id.editText_register_email);
        editText_register_motto = (EditText) findViewById(R.id.editText_register_motto);
        button_register_finish = (Button) findViewById(R.id.button_register_finish);
        imageView_register_head = (ImageView) findViewById(R.id.imageView_register_head);

        button_register_finish.setOnClickListener(listener);
        radioGroup_register_sex.setOnCheckedChangeListener(listener_radioGroup);
        spinner_register_constellation.setOnItemSelectedListener(new SpinnerXMLSelectedListener_constellation());

        imageView_register_head.setOnClickListener(new View.OnClickListener() {
            //����ͼƬ�ĵ����¼�
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_UNSPECIFIED);
                startActivityForResult(intent, PHOTOZOOM);
            }
        });

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //���ְ�ť�¼�
            Button button = (Button) view;
            Intent intent = new Intent();
            switch (button.getId()) {
                case R.id.button_register_finish:
                    //��ת����½���棬�����������Ϣ
                    try {
                        OutputStream outStream_user_nickname = register_information.this.openFileOutput("editText_user_nickname", MODE_PRIVATE);
                        OutputStream outStream_user_sex = register_information.this.openFileOutput("editText_user_sex", MODE_PRIVATE);
                        OutputStream outStream_user_birthday = register_information.this.openFileOutput("editText_user_birthday", MODE_PRIVATE);
                        OutputStream outStream_user_constellation = register_information.this.openFileOutput("editText_user_constellation", MODE_PRIVATE);
                        OutputStream outStream_user_hobby = register_information.this.openFileOutput("editText_user_hobby", MODE_PRIVATE);
                        OutputStream outStream_user_email = register_information.this.openFileOutput("editText_user_email", MODE_PRIVATE);
                        OutputStream outStream_user_motto = register_information.this.openFileOutput("editText_user_motto", MODE_PRIVATE);
                        service.save(outStream_user_nickname, editText_register_nickname.getText().toString());
                        service.save(outStream_user_sex, pro_sex);
                        service.save(outStream_user_birthday, editText_register_birthday.getText().toString());
                        service.save(outStream_user_constellation, pro_constellation);
                        service.save(outStream_user_hobby, editText_register_hobby.getText().toString());
                        service.save(outStream_user_email, editText_register_email.getText().toString());
                        service.save(outStream_user_motto, editText_register_motto.getText().toString());
                        //������Ϣ

                        Toast.makeText(register_information.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    intent.setClass(register_information.this, login.class);
                    startActivity(intent);
                    break;
            }

        }
    };


    private RadioGroup.OnCheckedChangeListener listener_radioGroup = new RadioGroup.OnCheckedChangeListener() {
        //�Ա�ĵ�ѡ���¼�
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            switch (checkedId) {
                case R.id.radioButton_register_boy:
                //������Ӻ���¼�
                    pro_sex=radioButton_register_boy.getText().toString();
                case R.id.radioButton_register_girl:
                //������Ӻ���¼�
                    pro_sex=radioButton_register_girl.getText().toString();
            }
        }
    };


    class SpinnerXMLSelectedListener_constellation implements AdapterView.OnItemSelectedListener
    //�����������ֵ������б�
    {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        //�����б���ѡ����Ӧ��Ľ��
        {
            Spinner spinner = (Spinner) parent;
            pro_constellation = (String) spinner.getItemAtPosition(position);
        }

        public void onNothingSelected(AdapterView<?> arg0)
        //�����б���ȡ��ѡ����Ӧ��Ľ��
        {

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;

        if (data == null)
            return;

        // ��ȡ�������ͼƬ
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // ������
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
                // 100)ѹ���ļ�
                imageView_register_head.setImageBitmap(photo);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY �ǿ�ߵı���
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY �ǲü�ͼƬ���
        intent.putExtra("outputX", 240);
        intent.putExtra("outputY", 240);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }

    public void connection() {
        adapter_constellation = ArrayAdapter.createFromResource(register_information.this, R.array.constellation, android.R.layout.simple_spinner_item);
        //����ѡ������ArrayAdapter��������
        spinner_register_constellation.setAdapter(adapter_constellation);
        //��adapter��ӵ�spinner��
    }


    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //˫�����˻��˳�����
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(register_information.this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
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
