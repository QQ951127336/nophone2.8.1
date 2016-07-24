package com.ForestAnimals.nophone.fragment;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.ForestAnimals.nophone.R;
import com.ForestAnimals.nophone.phone_lock.AdminReceiver;
import com.ForestAnimals.nophone.phone_lock.AlarmReceiver_normal;
import com.ForestAnimals.nophone.phone_lock.AlarmReceiver_silent;
import com.ForestAnimals.nophone.shape.DoubleTimePickerDialog;
import com.ForestAnimals.nophone.shape.TimePickerDialog_setting;

import java.util.Calendar;


/**
 * Created by MyWorld on 2016/6/12.
 */
public class setting_fragment extends Fragment {

    //���廮������������Ӳ������
    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    private Button button_setting_locking;
    private TextView textView_setting_start;
    private TextView textView_setting_finish;
    private Calendar calendar_start;
    private Calendar calendar_finish;
    private ToggleButton toggleButton_setting_selfstudy;
    private RadioGroup radioGroup_setting;
    private RadioButton radioButton_setting_immediate;
    private RadioButton radioButton_setting_timing;
    //�Զ���ѧϰģʽ���ÿ���
    private LinearLayout linearLayout_setting_selfstudy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container,
                false);

        //����ID����
        button_setting_locking = (Button) view.findViewById(R.id.button_setting_locking);
//        textView_setting_start = (TextView) view.findViewById(R.id.textView_setting_start);
//        textView_setting_finish = (TextView) view.findViewById(R.id.textView_setting_finish);
        toggleButton_setting_selfstudy = (ToggleButton) view.findViewById(R.id.toggleButton_setting_selfstudy);
        linearLayout_setting_selfstudy = (LinearLayout) view.findViewById(R.id.linearLayout_setting_selfstudy);
        radioGroup_setting = (RadioGroup) view.findViewById(R.id.radioGroup_setting);
        radioButton_setting_immediate = (RadioButton) view.findViewById(R.id.radioButton_setting_immediate);
        radioButton_setting_timing = (RadioButton) view.findViewById(R.id.radioButton_setting_timing);

        setAction();
        calendar_start = Calendar.getInstance();
        calendar_finish = Calendar.getInstance();

        linearLayout_setting_selfstudy.setVisibility(View.INVISIBLE);
        //���ö�ʱѧϰģʽ�ؼ�Ϊ������

        policyManager = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        //��ȡ�豸������񣨼���ȡ������Ҫ��Ȩ�ޣ�

        componentName = new ComponentName(getActivity(), AdminReceiver.class);
        //AdminReceiver �̳��� DeviceAdminReceiver

        return view;
    }


    @TargetApi(Build.VERSION_CODES.FROYO)
    private void mylock() {
        boolean active = policyManager.isAdminActive(componentName);
        if (!active) {
            //����Ȩ��
            activeManage();//ȥ���Ȩ��
            policyManager.lockNow();//������
        }
        if (active) {
            policyManager.lockNow();//ֱ������
        }
    }


    private void activeManage()
    //�����豸������
    {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        //�����豸����(��ʽIntent) - ��AndroidManifest.xml���趨��Ӧ������
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        //Ȩ���б�
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "------ �������� ------");
        //����(additional explanation)
        startActivityForResult(intent, 0);
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            switch (button.getId()) {
                case R.id.toggleButton_setting_selfstudy:
                    //������������
                    if (toggleButton_setting_selfstudy.isChecked()) {
                        linearLayout_setting_selfstudy.setVisibility(View.VISIBLE);//�ɼ�
                    } else {
                        linearLayout_setting_selfstudy.setVisibility(View.INVISIBLE);//���ɼ�
                    }
                    break;
                case R.id.button_setting_locking: {
                    mylock();
                    break;
                }
            }
        }
    };


    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            switch (checkedId) {
                case R.id.radioButton_setting_immediate:
                    calendar_finish.setTimeInMillis(0 - 8 * 60 * 60 * 1000);
                    //����������ʼʱ��
                    int Hour = calendar_finish.get(Calendar.HOUR_OF_DAY);
                    //ʹ���������������Сʱ��
                    int Minute = calendar_finish.get(Calendar.MINUTE);
                    //ʹ��������������÷�����
                    TimePickerDialog_setting timePickerDialog = new TimePickerDialog_setting(getActivity(), new TimePickerDialog_setting.OnTimeSetListener() {
                        //���á�ѧϰģʽ������ʱ�䣬��������
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            // TODO Auto-generated method stub
                            calendar_finish.add(Calendar.HOUR_OF_DAY, hourOfDay);
                            //���ô���СʱΪ���õ�Сʱ��
                            calendar_finish.add(Calendar.MINUTE, minute);
                            //���ô�������Ϊ���õķ�����
                            calendar_finish.add(Calendar.SECOND, 0);
                            //��������Ӧ����0��ʱ����
                            calendar_finish.add(Calendar.MILLISECOND, 0);
                            //��������Ӧ����0����ʱ����
                            Intent intent_start = new Intent(getActivity(), AlarmReceiver_silent.class);
                            Intent intent_finish = new Intent(getActivity(), AlarmReceiver_normal.class);
                            //����ʱ������Ӧ�Ĺ㲥
                            PendingIntent pendingIntent_start = PendingIntent.getService(getActivity(), 0, intent_start, 0);
                            PendingIntent pendingIntent_finish = PendingIntent.getService(getActivity(), 0, intent_finish, 0);
                            //�����㲥
                            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                            //��ȡϵͳ����
                            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent_start);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + hourOfDay * 60 * 60 * 1000 + minute * 60 * 1000, pendingIntent_finish);
                            //�����õ�ʱ�̴����¼�
//                            String tmps = getString(R.string.finish_still) + format(hourOfDay) + ":" + format(minute);
//                            textView_setting_start.setText(tmps);
                            //��ʾѧϰģʽʣ��ʱ��
                        }
                    }, Hour, Minute, true);
                    timePickerDialog.show();
                    //��ʾ����
                    break;

                case R.id.radioButton_setting_timing:
                    calendar_start.setTimeInMillis(System.currentTimeMillis());
                    calendar_finish.setTimeInMillis(System.currentTimeMillis());
                    //��ȡϵͳ����ʱ��
                    int hour_start = calendar_start.get(Calendar.HOUR_OF_DAY);
                    int hour_finish = calendar_finish.get(Calendar.HOUR_OF_DAY);
                    //ʹ���������������Сʱ��
                    int minute_start = calendar_start.get(Calendar.MINUTE);
                    int minute_finish = calendar_finish.get(Calendar.MINUTE);
                    //ʹ��������������÷�����
                    DoubleTimePickerDialog timePickerDialog_finish = new DoubleTimePickerDialog(getActivity(), new DoubleTimePickerDialog.OnTimeSetListener() {
                        //���á�ѧϰģʽ������ʱ�䣬��������
                        @Override
                        public void onTimeSet(TimePicker view_start, TimePicker view_finish, int hourOfDay_start, int minute_start, int hourOfDay_finish, int minute_finish) {
                            // TODO Auto-generated method stub
                            calendar_start.set(Calendar.HOUR_OF_DAY, hourOfDay_start);
                            calendar_finish.set(Calendar.HOUR_OF_DAY, hourOfDay_finish);
                            //���ô���СʱΪ���õ�Сʱ��
                            calendar_start.set(Calendar.MINUTE, minute_start);
                            calendar_finish.set(Calendar.MINUTE, minute_finish);
                            //���ô�������Ϊ���õķ�����
                            calendar_start.set(Calendar.SECOND, 0);
                            calendar_finish.set(Calendar.SECOND, 0);
                            //��������Ӧ����0��ʱ����
                            calendar_start.set(Calendar.MILLISECOND, 0);
                            calendar_finish.set(Calendar.MILLISECOND, 0);
                            //��������Ӧ����0����ʱ����
                            Intent intent_start = new Intent(getActivity(), AlarmReceiver_silent.class);
                            Intent intent_finish = new Intent(getActivity(), AlarmReceiver_normal.class);
                            //����ʱ������Ӧ�Ĺ㲥
                            PendingIntent pendingIntent_start = PendingIntent.getService(getActivity(), 0, intent_start, 0);
                            PendingIntent pendingIntent_finish = PendingIntent.getService(getActivity(), 0, intent_finish, 0);
                            //�����㲥
                            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                            //��ȡϵͳ����
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar_start.getTimeInMillis(), pendingIntent_start);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar_finish.getTimeInMillis(), pendingIntent_finish);
                            //�����õ�ʱ�̴����¼�
//                            String tmps_start = getString(R.string.study_mode) + format(hourOfDay_start) + ":" + format(minute_start) + getString(R.string.start);
//                            String tmps_finish = getString(R.string.study_mode) + format(hourOfDay_finish) + ":" + format(minute_finish) + getString(R.string.finish);
                            //��ȡ��ʼ�ͽ�������ʱ��
//                            textView_setting_start.setText(tmps_start);
//                            textView_setting_finish.setText(tmps_finish);
                            //��ʾ��ʼ�ͽ���ʱ��
                        }
                    }, hour_start, minute_start, hour_finish, minute_finish, true);
                    timePickerDialog_finish.show();
                    //��ʾ����
                    break;
            }
        }
    };


    private String format(int time) {
        String str = "" + time;
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }


    public void setAction()
    //�����¼�����
    {
        button_setting_locking.setOnClickListener(listener);
        toggleButton_setting_selfstudy.setOnClickListener(listener);
        radioGroup_setting.setOnCheckedChangeListener(listener2);
    }
}
