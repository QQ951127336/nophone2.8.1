package com.ForestAnimals.nophone.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.ForestAnimals.nophone.R;
import com.ForestAnimals.nophone.Service.FileService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by MyWorld on 2016/6/12.
 */
public class user_fragment extends Fragment {

    FileService service = new FileService();

    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// ����
    public static final int PHOTOZOOM = 2; // ����
    public static final int PHOTORESOULT = 3;// ���
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private ImageView imageView_user_head;
    private Button button_user_edit;
    private Button button_user_save;
    private Button button_user_cancel;
    private EditText editText_user_nickname;
    private ImageView imageView_user_sex;
    private EditText editText_user_birthday;
    private EditText editText_user_constellation;
    private Spinner spinner_user_constellation;
    private ArrayAdapter adapter_constellation;
    private EditText editText_user_hobby;
    private EditText editText_user_email;
    private EditText editText_user_motto;
    private TextView textView_user_level;
    private TextView textView_user_money;
    private TextView textView_user_experience;

    String pro_constellation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_layout, container,
                false);

        //����ID����
        imageView_user_head = (ImageView) view.findViewById(R.id.imageView_user_head);
        imageView_user_sex = (ImageView) view.findViewById(R.id.imageView_user_sex);
        button_user_edit = (Button) view.findViewById(R.id.button_user_edit);
        button_user_save = (Button) view.findViewById(R.id.button_user_save);
        button_user_cancel = (Button) view.findViewById(R.id.button_user_cancel);
        editText_user_nickname = (EditText) view.findViewById(R.id.editText_user_nickname);
        editText_user_motto = (EditText) view.findViewById(R.id.editText_user_motto);
        editText_user_birthday = (EditText) view.findViewById(R.id.editText_user_birthday);
        editText_user_constellation = (EditText) view.findViewById(R.id.editText_user_constellation);
        spinner_user_constellation = (Spinner) view.findViewById(R.id.spinner_user_constellation);
        editText_user_hobby = (EditText) view.findViewById(R.id.editText_user_hobby);
        editText_user_email = (EditText) view.findViewById(R.id.editText_user_email);

        read();//��ȡ�ļ�����������
        enabled_false();//�������ı����޷��༭
        setAction();//�����¼�
        connection();//���������ѡ����������
        setText();//�ж����Ϊ�գ����ó�ʼ�ǳƺ�ǩ��

        button_user_save.setVisibility(View.INVISIBLE);
        button_user_cancel.setVisibility(View.INVISIBLE);
        //���á����桱�͡�ȡ���༭����ť���ɼ�

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            switch (button.getId()) {
                case R.id.button_user_edit:
                    //����ɱ༭״̬
                    enabled_true();
                    editText_user_constellation.setVisibility(View.INVISIBLE);
                    spinner_user_constellation.setVisibility(View.VISIBLE);
                    button_user_save.setVisibility(View.VISIBLE);
                    button_user_cancel.setVisibility(View.VISIBLE);
                    break;
                case R.id.button_user_cancel:
                    //���ز��ɱ༭״̬
                    enabled_false();
                    read();
                    editText_user_constellation.setVisibility(View.VISIBLE);
                    spinner_user_constellation.setVisibility(View.INVISIBLE);
                    button_user_save.setVisibility(View.INVISIBLE);
                    button_user_cancel.setVisibility(View.INVISIBLE);
                    break;
                case R.id.button_user_save:
                    //������Ϣ
                    try {
                        OutputStream outStream_user_nickname = getActivity().openFileOutput("editText_user_nickname", Context.MODE_PRIVATE);
                        OutputStream outStream_user_motto = getActivity().openFileOutput("editText_user_motto", Context.MODE_PRIVATE);
                        OutputStream outStream_user_birthday = getActivity().openFileOutput("editText_user_birthday", Context.MODE_PRIVATE);
                        OutputStream outStream_user_constellation = getActivity().openFileOutput("editText_user_constellation", Context.MODE_PRIVATE);
                        OutputStream outStream_user_hobby = getActivity().openFileOutput("editText_user_hobby", Context.MODE_PRIVATE);
                        OutputStream outStream_user_email = getActivity().openFileOutput("editText_user_email", Context.MODE_PRIVATE);
                        service.save(outStream_user_nickname, editText_user_nickname.getText().toString());
                        service.save(outStream_user_motto, editText_user_motto.getText().toString());
                        service.save(outStream_user_birthday, editText_user_birthday.getText().toString());
                        service.save(outStream_user_constellation, pro_constellation);
                        service.save(outStream_user_hobby, editText_user_hobby.getText().toString());
                        service.save(outStream_user_email, editText_user_email.getText().toString());
                        //������Ϣ
                        Toast.makeText(getActivity(), getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    enabled_false();
                    break;
            }
        }
    };


    class SpinnerXMLSelectedListener_constellation implements AdapterView.OnItemSelectedListener
    //�����������ֵ������б�
    {
        public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
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
                imageView_user_head.setImageBitmap(photo);
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

    public void enabled_false()
    //�����ı��򲻿ɱ༭
    {
        editText_user_nickname.setEnabled(false);
        editText_user_motto.setEnabled(false);
        editText_user_birthday.setEnabled(false);
        editText_user_constellation.setEnabled(false);
        spinner_user_constellation.setEnabled(false);
        editText_user_hobby.setEnabled(false);
        editText_user_email.setEnabled(false);
        imageView_user_head.setEnabled(false);
    }

    public void enabled_true()
    //�����ı���ɱ༭
    {
        editText_user_nickname.setEnabled(true);
        editText_user_motto.setEnabled(true);
        editText_user_birthday.setEnabled(true);
        editText_user_constellation.setEnabled(true);
        spinner_user_constellation.setEnabled(true);
        editText_user_hobby.setEnabled(true);
        editText_user_email.setEnabled(true);
        imageView_user_head.setEnabled(true);
    }


    public void read()
    //��ȡ��Ϣ
    {
        try {
            InputStream inStream_user_nickname = getActivity().openFileInput("editText_user_nickname");
            InputStream inStream_user_motto = getActivity().openFileInput("editText_user_motto");
            InputStream inStream_user_sex = getActivity().openFileInput("editText_user_sex");
            InputStream inStream_user_birhday = getActivity().openFileInput("editText_user_birthday");
            InputStream inStream_user_constellation = getActivity().openFileInput("editText_user_constellation");
            InputStream inStream_user_hobby = getActivity().openFileInput("editText_user_hobby");
            InputStream inStream_user_email = getActivity().openFileInput("editText_user_email");
            editText_user_nickname.setText(service.read(inStream_user_nickname));
            editText_user_motto.setText(service.read(inStream_user_motto));
            imageView_user_sex.setImageResource(R.drawable.user_boy);
            editText_user_birthday.setText(service.read(inStream_user_birhday));
            editText_user_constellation.setText(service.read(inStream_user_constellation));
            editText_user_hobby.setText(service.read(inStream_user_hobby));
            editText_user_email.setText(service.read(inStream_user_email));
            //��ȡ��Ϣ
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setText()
    //���ó�ʼ�ǳƺ�ǩ��
    {
        if (editText_user_nickname.getText().toString().length() == 0) {
            editText_user_nickname.setText(getString(R.string.my_nickname));
        }
        if (editText_user_motto.getText().toString().length() == 0) {
            editText_user_motto.setText(getString(R.string.no_motto));
        }
    }


    public void connection() {
        adapter_constellation = ArrayAdapter.createFromResource(getActivity(), R.array.constellation, android.R.layout.simple_spinner_item);
        //����ѡ������ArrayAdapter��������
        spinner_user_constellation.setAdapter(adapter_constellation);
        //��adapter��ӵ�spinner��
    }


    public void setAction()
    //�����¼�����
    {
        button_user_edit.setOnClickListener(listener);
        button_user_save.setOnClickListener(listener);
        button_user_cancel.setOnClickListener(listener);
        spinner_user_constellation.setOnItemSelectedListener(new SpinnerXMLSelectedListener_constellation());


        imageView_user_head.setOnClickListener(new View.OnClickListener() {
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

}
