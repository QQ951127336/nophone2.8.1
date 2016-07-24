package com.ForestAnimals.nophone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import com.ForestAnimals.nophone.R;

/**
 * Created by MyWorld on 2016/6/12.
 */
public class tree_fragment extends Fragment{

    private ScrollView scrollView_tree;
    private ImageView imageView_tree;
    private ImageView imageView_tree_leaf1;
    private ImageView imageView_tree_leaf2;
    private ImageView imageView_tree_leaf3;
    private ImageView imageView_tree_leaf4;
    private Animation leaf1_anim;
    private Animation leaf2_anim;
    private Animation leaf3_anim;
    private Animation leaf4_anim;

    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tree_layout, container,
                false);

        //声明ID集合
        scrollView_tree=(ScrollView)view.findViewById(R.id.scrollView_tree);
        imageView_tree=(ImageView)view.findViewById(R.id.imageView_tree);
        imageView_tree_leaf1=(ImageView)view.findViewById(R.id.imageView_tree_leaf1);
        imageView_tree_leaf2=(ImageView)view.findViewById(R.id.imageView_tree_leaf2);
        imageView_tree_leaf3=(ImageView)view.findViewById(R.id.imageView_tree_leaf3);
        imageView_tree_leaf4=(ImageView)view.findViewById(R.id.imageView_tree_leaf4);

        leaf_anim();
        //树叶会飘动


        return view;
    }





    public void leaf_anim()
    //让树叶随风摆动吧
    {
        leaf1_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.leaf1_anim);
        leaf2_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.leaf2_anim);
        leaf3_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.leaf3_anim);
        leaf4_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.leaf4_anim);
        imageView_tree_leaf1.startAnimation(leaf1_anim);
        imageView_tree_leaf2.startAnimation(leaf2_anim);
        imageView_tree_leaf3.startAnimation(leaf3_anim);
        imageView_tree_leaf4.startAnimation(leaf4_anim);
        leaf1_anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                leaf_anim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}