package com.ForestAnimals.nophone.goods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ForestAnimals.nophone.R;

/**
 * Created by MyWorld on 2016/6/21.
 */
public class goodsFragment extends Fragment {
    private goods the_goods;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        the_goods=new goods();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,
                             Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.goods_meterial_layout,parent,false);
        return v;
    }

}
