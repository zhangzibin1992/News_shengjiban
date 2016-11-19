package zhuoxinzhiqu.news2;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.app.adapter.MyPagerAdapter;
import com.android.app.base.MyBaseActivity;

public class YindaoActivity extends MyBaseActivity{
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private boolean isRunning;
    private ImageView[] imageViews = new ImageView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yindao);

        SharedPreferences sharep = getSharedPreferences("key_p",MODE_PRIVATE);
        isRunning = sharep.getBoolean("key_isFirstRun",true);
        if (!isRunning){
            isRunning=false;
            jumpActivity(MainActivity.class);
            finish();
        }else {

            getAndSetView();
            setlayout();
        }
    }

    private void getAndSetView(){
        imageViews[0] = (ImageView) findViewById(R.id.igv_dian1);
        imageViews[0].setAlpha(100);
        imageViews[1] = (ImageView) findViewById(R.id.igv_dian2);
        imageViews[2] = (ImageView) findViewById(R.id.igv_dian3);
        imageViews[3] = (ImageView) findViewById(R.id.igv_dian4);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new MyPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(pageChangeListener);

    }

    private void setlayout(){
        ImageView imageView = null;
        imageView = (ImageView) getLayoutInflater().inflate(R.layout.add_image,null);
        imageView.setImageResource(R.drawable.welcome);
        adapter.addViewToAdapter(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.add_image,null);
        imageView.setImageResource(R.drawable.wy);
        adapter.addViewToAdapter(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.add_image,null);
        imageView.setImageResource(R.drawable.bd);
        adapter.addViewToAdapter(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.add_image,null);
        imageView.setImageResource(R.drawable.small);
        adapter.addViewToAdapter(imageView);

        adapter.notifyDataSetChanged();
    }


    private void setPoint(int index){

        for (int i = 0; i <imageViews.length ; i++) {
            if (i==index){
                imageViews[i].setAlpha(300);
            }else{
                imageViews[i].setAlpha(100);
            }
        }
    }

    private void biaoJi(){
        SharedPreferences preference = getSharedPreferences("key_p",MODE_PRIVATE);
        SharedPreferences.Editor  editor= preference.edit();
        editor.putBoolean("key_isFirstRun",false);
        editor.commit();

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setPoint(position);
            if (position==3){
                jumpActivityWithAnim(MainActivity.class,R.anim.danru,R.anim.danchu);
                biaoJi();
                finish();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
