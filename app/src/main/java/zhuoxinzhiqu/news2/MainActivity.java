package zhuoxinzhiqu.news2;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.app.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logo = (ImageView) findViewById(R.id.imv_logo);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.logo);
        animation.setFillAfter(true);

        /**动画监听*/
        animation.setAnimationListener(new Animation.AnimationListener() {

            /**动画启动时调用*/
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**动画结束时调用*/
            @Override
            public void onAnimationEnd(Animation animation) {
                jumpActivity(HomeActivity.class);
                finish();

            }

            /**动画重复时调用*/
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logo.setAnimation(animation);

    }



}
