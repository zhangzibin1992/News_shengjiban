package zhuoxinzhiqu.news2;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.base.MyBaseActivity;
import com.android.app.entity.News;
import com.android.app.tools.DBManager;
import com.android.app.tools.SystemUtils;

public class ShowActivity extends MyBaseActivity {
    private ProgressBar progressbar;
    private WebView webView;
    private News news;
    private ImageView img_back,img_gengduo;
    private TextView tv_gentie;
    private PopupWindow popupWindow;
    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SystemUtils.getInstance(getApplicationContext()).isNetConn()){
            setContentView(R.layout.oh_no);
        }else{
            setContentView(R.layout.activity_show);
            dbManager = DBManager.getInstance(this);
            findId();
            setAction();
        }
    }

    private WebViewClient viewClinent = new WebViewClient(){
        public boolean shouldOverrideUrlLoading(WebView webView, String url){
            webView.loadUrl(url);
            return true;
        }
    };

    private WebChromeClient wcclient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressbar.setProgress(newProgress);
            if (progressbar.getProgress()==100){
                progressbar.setVisibility(View.GONE);
            }
        }
    };

    private void findId(){
        progressbar = (ProgressBar) findViewById(R.id.pgb_loading);
        webView = (WebView) findViewById(R.id.webView);
        img_back= (ImageView) findViewById(R.id.img_back);
        img_gengduo = (ImageView) findViewById(R.id.img_gengduo);
        tv_gentie = (TextView) findViewById(R.id.tv_gentie);
    }

    private void setAction(){
        news = (News) getIntent().getSerializableExtra("newsitem");
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(viewClinent);
        webView.setWebChromeClient(wcclient);
        webView.loadUrl(news.getLink());
        img_back.setOnClickListener(listener);
        img_gengduo.setOnClickListener(listener);
        tv_gentie.setOnClickListener(listener);
        addPopupWindow();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_back:
                    finish();
                    break;

                case R.id.img_gengduo:
                    if (popupWindow!=null&&popupWindow.isShowing()){
                        popupWindow.dismiss();
                    }else {
                        popupWindow.showAsDropDown(img_gengduo,0,14);
                    }
                    break;

                case R.id.tv_gentie:
                    Bundle bundle = new Bundle();
                    bundle.putInt("nid",news.getNid());
                    jumpActivityWithData(ThirdActivity.class,bundle);

                    break;
            }
        }
    };

    private void addPopupWindow(){
        View view = getLayoutInflater().inflate(R.layout.popwindow_layout,null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        TextView tv_addshoucang = (TextView) view.findViewById(R.id.tv_addshoucang);
        tv_addshoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                boolean isSuccessful = dbManager.saveLoveNews(news);
                if (isSuccessful){
                    Toast.makeText(ShowActivity.this,"收藏成功\n在主界面侧滑菜单中查看",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShowActivity.this,"已经收藏过这条新闻了!\n在主界面侧滑菜单中查看",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
