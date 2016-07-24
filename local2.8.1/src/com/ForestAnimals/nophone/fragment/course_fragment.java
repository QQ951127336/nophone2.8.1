package com.ForestAnimals.nophone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ForestAnimals.nophone.R;

/**
 * Created by MyWorld on 2016/6/12.
 * É­ÁÖÊ÷µÄÒ³Ãæ
 */
public class course_fragment extends Fragment {

    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_layout, container,
                false);
        return view;
    }

}
