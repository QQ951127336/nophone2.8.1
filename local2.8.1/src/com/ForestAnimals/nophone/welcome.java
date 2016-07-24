package com.ForestAnimals.nophone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.ForestAnimals.nophone.login_and_register.login;

import java.util.ArrayList;
/*	@descript ʵ�ֻ����л�ͼƬ�����
 *  @Date 2014-8-4
 *  @come��http://www.cnblogs.com/tinyphp/p/3892936.html
 */

public class welcome extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> pageview;

    private Button button_welcome;
    private ImageView imageView;
    private ImageView[] imageViews;
    //��������LinearLayout
    private ViewGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //�����ޱ�����
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.welcome);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //���Ҳ����ļ���LayoutInflater.inflate
        LayoutInflater inflater =getLayoutInflater();
        View view1 = inflater.inflate(R.layout.item01, null);
        View view2 = inflater.inflate(R.layout.item02, null);
        View view3 = inflater.inflate(R.layout.item03, null);
        View view4 = inflater.inflate(R.layout.item04, null);

        //��viewװ������
        pageview =new ArrayList<View>();
        pageview.add(view1);
        pageview.add(view2);
        pageview.add(view3);
        pageview.add(view4);


        button_welcome=(Button)view4.findViewById(R.id.button_welcome);
        //��������ť�ڵ��ĸ�������
        button_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(welcome.this, login.class);
                startActivity(intent);
            }
        });

        group = (ViewGroup)findViewById(R.id.viewGroup);

        //�ж�����ͼ���ж��ٸ����
        imageViews = new ImageView[pageview.size()];
        for(int i =0;i<pageview.size();i++){
            imageView = new ImageView(welcome.this);
            imageView.setLayoutParams(new LayoutParams(20,20));
            imageView.setPadding(20, 0, 20, 0);
            imageViews[i] = imageView;

            //Ĭ�ϵ�һ��ͼ��ʾΪѡ��״̬
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            group.addView(imageViews[i]);
        }

        //��������
        viewPager.setAdapter(mPagerAdapter);
        //�󶨼����¼�
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
    }

    //����������
    PagerAdapter mPagerAdapter = new PagerAdapter(){
        @Override
        //��ȡ��ǰ���������
        public int getCount() {
            // TODO Auto-generated method stub
            return pageview.size();
        }

        @Override
        //���Ƿ��ɶ������ɽ���
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0==arg1;
        }
        //�Ǵ�ViewGroup���Ƴ���ǰView
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(pageview.get(arg1));
        }

        //����һ������������������PagerAdapter������ѡ���ĸ�������ڵ�ǰ��ViewPager��
        public Object instantiateItem(View arg0, int arg1){
            ((ViewPager)arg0).addView(pageview.get(arg1));
            return pageview.get(arg1);
        }


    };



    //pageView������
    class GuidePageChangeListener implements OnPageChangeListener{

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        //����л��ˣ��Ͱѵ�ǰ�ĵ������Ϊѡ�б�������������δѡ�б���
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            for(int i=0;i<imageViews.length;i++){
                imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
                if (arg0 != i) {
                    imageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                }
            }

        }
    }


    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //˫�����˻��˳�����
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(welcome.this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
