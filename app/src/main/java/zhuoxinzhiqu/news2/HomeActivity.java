package zhuoxinzhiqu.news2;


import android.os.Bundle;
import android.view.View;

import com.android.app.base.MyBaseActivity;
import com.android.app.biz.FragmentDenglu;
import com.android.app.biz.FragmentMain;
import com.android.app.biz.FragmentMenu;
import com.android.app.biz.FragmentMenuRight;
import com.android.app.biz.FragmentShoucang;
import com.android.app.biz.FragmentZhaohui;
import com.android.app.biz.FragmentZhuCe;
import com.android.app.tools.MyActionBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends MyBaseActivity {
    private FragmentMenu leftActivity;
    private FragmentMenuRight rightActivity;
    private FragmentMain fragmentMain;
    private FragmentShoucang fragmentShoucang;
    private FragmentDenglu fragmentDenglu;
    private FragmentZhuCe fragmentZhuCe;
    private FragmentZhaohui fragmentZhaohui;
    public static SlidingMenu slidingMenu;
    private MyActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentShoucang = new FragmentShoucang();
        fragmentDenglu = new FragmentDenglu();
        fragmentZhuCe = new FragmentZhuCe();
        fragmentZhaohui = new FragmentZhaohui();
        findId();
        initSlidingMenu();
        showFragmentMain();

    }



    private void findId(){
        actionBar = (MyActionBar) findViewById(R.id.actionbar);

        actionBar.setActionBar("资讯",R.drawable.ic_title_home_default,R.drawable.ic_title_share_default,listener);
    }

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.imv_left:
                    if (slidingMenu==null&&slidingMenu.isMenuShowing()){
                        slidingMenu.showContent();
                    }
                    slidingMenu.showMenu();

                    break;

                case R.id.imv_right:
                    if (slidingMenu==null&&slidingMenu.isMenuShowing()){
                        slidingMenu.showContent();
                    }
                    slidingMenu.showSecondaryMenu();
                    break;
            }
        }
    };

    public void showDenglu() {
        slidingMenu.showContent();
        //先判断新闻列表界面是否需要创建
        if (fragmentDenglu == null)
            fragmentDenglu = new FragmentDenglu();
        //将当前主界面替换为新闻列表界面
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_home, fragmentDenglu).commit();
        actionBar.setActionBar("登陆",R.drawable.ic_title_home_default,R.drawable.ic_title_share_default,listener);

    }

    public void showZhaohui() {
        slidingMenu.showContent();
        //先判断新闻列表界面是否需要创建
        if (fragmentZhaohui == null)
            fragmentZhaohui = new FragmentZhaohui();
        //将当前主界面替换为新闻列表界面
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_home, fragmentZhaohui).commit();
        actionBar.setActionBar("忘记密码",R.drawable.ic_title_home_default,R.drawable.ic_title_share_default,listener);
    }


    public void showZhuCe() {
        slidingMenu.showContent();
        //先判断新闻列表界面是否需要创建
        if (fragmentZhuCe == null)
            fragmentZhuCe = new FragmentZhuCe();
        //将当前主界面替换为新闻列表界面
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_home, fragmentZhuCe).commit();
        actionBar.setActionBar("注册",R.drawable.ic_title_home_default,R.drawable.ic_title_share_default,listener);
    }

    public void showFragmentShoucang() {
        slidingMenu.showContent();
        //先判断新闻列表界面是否需要创建
        if (fragmentShoucang == null)
            fragmentShoucang = new FragmentShoucang();
        //将当前主界面替换为新闻列表界面
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_home, fragmentShoucang).commit();
        actionBar.setActionBar("收藏",R.drawable.ic_title_home_default,R.drawable.ic_title_share_default,listener);

    }

    public void showFragmentMain() {
        slidingMenu.showContent();
        //先判断新闻列表界面是否需要创建
        if (fragmentMain == null)
            fragmentMain = new FragmentMain();
        //将当前主界面替换为新闻列表界面
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_home, fragmentMain).commit();
        actionBar.setActionBar("新闻",R.drawable.ic_title_home_default,R.drawable.ic_title_share_default,listener);
    }




    /**初始化侧滑菜单**/
    public void initSlidingMenu(){
        slidingMenu = new SlidingMenu(this);

        //设置侧拉栏为双向测拉栏
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置菜单栏显示时偏移量，可定义在res/values/dimens文件中
        slidingMenu.setBehindOffsetRes(R.dimen.fragment_chicun);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        //设置左右菜单栏的布局
        slidingMenu.setMenu(R.layout.layout_menu);
        slidingMenu.setSecondaryMenu(R.layout.layout_menu_right);

        leftActivity = new FragmentMenu();
        rightActivity = new FragmentMenuRight();
        //设置左右菜单栏具体的样式
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_menu, leftActivity).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_menu_right, rightActivity).commit();


    }
}
