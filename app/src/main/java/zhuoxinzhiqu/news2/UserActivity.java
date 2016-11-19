package zhuoxinzhiqu.news2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.app.biz.FragmentDenglu;
import com.android.app.biz.FragmentUsers;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class UserActivity extends AppCompatActivity {
    private FragmentUsers fragmentUsers;
    public static SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentUsers = new FragmentUsers();
        setContentView(R.layout.activity_user);
        slidingMenu = new SlidingMenu(this);
        showUsers();
    }

    public void showUsers() {
        slidingMenu.showContent();
        //先判断新闻列表界面是否需要创建
        if (fragmentUsers == null)
            fragmentUsers = new FragmentUsers();
        //将当前主界面替换为新闻列表界面
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_users, fragmentUsers).commit();


    }
}
