package com.example.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //项目提交第二次
    //广告条的图片资源
    private int[]    imageRes     = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
    //广告条的描述信息数组
    private String[] descriptions = {"巩俐不低俗，我就不低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            " 揭秘北京电影如何升级",
            "乐视网TV版大派送",
            " 热血屌丝的反杀",};
    private int prevousPosition = 0; //用来记录前一个圆点的位置


    private ViewPager    viewpager;
    private TextView     mTextView;
    private LinearLayout mLinearLayout;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //获取当前viewpager的界面索引值
            int currentItem = viewpager.getCurrentItem();
            //跳转到下一个页面
            viewpager.setCurrentItem(currentItem + 1);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        mTextView = (TextView) findViewById(R.id.tv_description);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_point_group);
        viewpager.setAdapter(new MyAdapter());
        //直接设置viewpager的显示位置
        viewpager.setCurrentItem(imageRes.length * 10000);

        //设置监听
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //当页面滑动的时候调用，实时调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //当页面被选中时，调用该方法
            @Override
            public void onPageSelected(int position) {
                int pos = position % imageRes.length;
                //当对应界面选中时，设置描述信息
                mTextView.setText(descriptions[pos]);
                //先让前一个点设置为不可用，未选中状态
                View at = mLinearLayout.getChildAt(prevousPosition);
                at.setEnabled(false);
                //把当前点记录下来,为下一次滑动做准备
                prevousPosition = pos;
                //获取容器中对应角标的小圆点，设置为可用，选中状态
                View childAt = mLinearLayout.getChildAt(pos);
                childAt.setEnabled(true);
            }

            //当页面滚动状态改变时，调用该方法
            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        //第一次显示界面，设置第一个广告的描述信息
        mTextView.setText(descriptions[0]);

        //动态添加小圆点
        for (int i = 0; i < imageRes.length; i++) {
            //设置小圆点的宽和高,6个像素
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(6, 6);
            ImageView view = new ImageView(MainActivity.this);
            if (i != 0) {
                //等价于xml中的marginleft
                params.leftMargin = 6;
                //让除了第一个点以外的点设置为白色
                view.setEnabled(false);
            }
            view.setLayoutParams(params);
            view.setImageResource(R.drawable.point_bg);
            mLinearLayout.addView(view);
        }

        //2秒之后发送一个消息，来跳转到下一个页面
        mHandler.sendEmptyMessageDelayed(0,2000);

        //给viewpager设置触摸监听，按下则停止轮播，抬起则继续轮播
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(0,2000);
                        break;
                }
                return false;
            }
        });
    }

    private class MyAdapter extends PagerAdapter {
        //与baseadapter的getcount一致，决定viewpager的长度
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        //判断将要显示到界面上的view视图与初始化方法返回的对象是否一致，如果一致的话就显示，true就显示，false就不显示
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化条目方法，类似于getview方法，来设置条目的界面

        /**
         * @param container 容器，其实就是viewpager自身
         * @param position  将要显示的界面的索引值
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % imageRes.length;
            ImageView iv = new ImageView(MainActivity.this);
            //如果用iv.setImageResource(imageRes[position]); 会有白色的标题栏//xml中的src属性
            iv.setBackgroundResource(imageRes[pos]);
            //如果要显示条目必须要添加到容器中
            container.addView(iv);
            return iv;
        }

        /**
         * 销毁方法
         *
         * @param container 容器，其实就是viewpager自身
         * @param position  将要销毁的对应界面的索引
         * @param object    将要销毁的对象
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
