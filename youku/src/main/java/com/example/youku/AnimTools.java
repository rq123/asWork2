package com.example.youku;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * 工具类
 * Created by 乱世一柒宗罪 on 2016/6/10.
 */
public class AnimTools {
    //用来记录当前有多少个动画正在执行
    public static int animationNum = 0;
    private static MyAnimationListener mListener = new MyAnimationListener();

    /**
     * 隐藏动画
     *
     * @param view        将要执行动画的视图
     * @param startOffset 设置动画延迟时间
     */
    public static void hideView(RelativeLayout view, long startOffset) {
        //让菜单里的子控件设置为不可点击
        for (int i = 0; i < view.getChildCount(); i++) {
            View childAt = view.getChildAt(i);
            //设置每一个子控件为不可用，禁止掉点击事件
            childAt.setEnabled(false);
        }
        //相对于自己relative_to_self
        RotateAnimation ra = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f
                , RotateAnimation.RELATIVE_TO_SELF, 1);
        ra.setDuration(500);
        ra.setFillAfter(true);//让视图执行完动画，停留在结束状态
        ra.setStartOffset(startOffset);//延迟多少ms后执行动画
        ra.setAnimationListener(mListener);
        view.startAnimation(ra);
    }

    public static void hideView(RelativeLayout view) {
        hideView(view, 0);
    }

    /**
     * 显示动画
     *
     * @param view 将要执行动画的视图
     */
    public static void showView(RelativeLayout view) {
        //让菜单里的子控件设置为可点击
        for (int i = 0; i < view.getChildCount(); i++) {
            View childAt = view.getChildAt(i);
            childAt.setEnabled(true);
        }
        RotateAnimation ra = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1);
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setAnimationListener(mListener);
        view.startAnimation(ra);

    }

    private static class MyAnimationListener implements Animation.AnimationListener {
        //当动画开始之前
        @Override
        public void onAnimationStart(Animation animation) {
            animationNum++;
        }

        //当动画结束的时候
        @Override
        public void onAnimationEnd(Animation animation) {
            animationNum--;
        }

        //当动画重复的时候
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
