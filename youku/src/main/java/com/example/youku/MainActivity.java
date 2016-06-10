package com.example.youku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlLeve3;
    private ImageView      ivChannel1;
    private ImageView      ivChannel2;
    private ImageView      ivChannel3;
    private ImageView      ivChannel4;
    private ImageView      ivChannel7;
    private ImageView      ivChannel6;
    private ImageView      ivChannel5;
    private RelativeLayout rlLeve2;
    private ImageView      ivMenu;
    private ImageView      ivHome;
    //记录二级菜单的显示状态，true为显示
    private boolean isMenu2Display = true;
    //记录三级菜单的显示状态，true为显示
    private boolean isMenu3Display = true;

    private void init() {
        rlLeve3 = (RelativeLayout) findViewById(R.id.rl_leve3);
        ivChannel1 = (ImageView) findViewById(R.id.iv_channel1);
        ivChannel2 = (ImageView) findViewById(R.id.iv_channel2);
        ivChannel3 = (ImageView) findViewById(R.id.iv_channel3);
        ivChannel4 = (ImageView) findViewById(R.id.iv_channel4);
        ivChannel7 = (ImageView) findViewById(R.id.iv_channel7);
        ivChannel6 = (ImageView) findViewById(R.id.iv_channel6);
        ivChannel5 = (ImageView) findViewById(R.id.iv_channel5);
        rlLeve2 = (RelativeLayout) findViewById(R.id.rl_leve2);
        ivMenu = (ImageView) findViewById(R.id.iv_menu);
        ivHome = (ImageView) findViewById(R.id.iv_home);

        ivHome.setOnClickListener(this);
        ivMenu.setOnClickListener(this);
        rlLeve3.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                //大于0 的话，当前有动画正在执行，不应该执行新的动画
                if (AnimTools.animationNum > 0) {
                    break;
                }
                if (isMenu3Display) {
                    AnimTools.hideView(rlLeve3);
                } else {
                    AnimTools.showView(rlLeve3);
                }
                isMenu3Display = !isMenu3Display;
                break;
            case R.id.iv_home:
                if (AnimTools.animationNum > 0) {
                    break;
                }
                long startOffset = 0;
                //特殊情况如果三级菜单显示则隐藏
                if (isMenu3Display) {
                    AnimTools.hideView(rlLeve3);
                    isMenu3Display = false;
                    startOffset += 200;
                }
                if (isMenu2Display) {
                    AnimTools.hideView(rlLeve2, startOffset);
                } else {
                    AnimTools.showView(rlLeve2);
                }
                isMenu2Display = !isMenu2Display;
                break;
            case R.id.rl_leve3:
                Log.d("tag", "三级菜单被点击了");
                break;
        }
    }
}
