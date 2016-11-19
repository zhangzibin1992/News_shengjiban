package zhuoxinzhiqu.news2;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.app.adapter.CommentsAdapter;
import com.android.app.base.MyBaseActivity;
import com.android.app.entity.Comment;
import com.android.app.parser.ParserComments;
import com.android.app.tools.CommentsManager;
import com.android.app.tools.CommonUtil;
import com.android.app.tools.MyActionBar;
import com.android.app.tools.NewsManager;
import com.android.app.tools.SharedPreferenceUtils;
import com.android.app.tools.SystemUtils;
import com.android.app.xlistxiews.XListView;

import java.util.List;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.VolleyError;

public class ThirdActivity extends MyBaseActivity {
    private MyActionBar myActionBar;
    private XListView listview;
    private EditText etv_pinglun;
    private ImageButton ibt_pinglun;
    private CommentsAdapter adapter;
    private int mode,nid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        nid = getIntent().getIntExtra("nid",-1);
        findId();
        setAction();
        loadNextComment();
    }

    private void findId() {
        myActionBar = (MyActionBar) findViewById(R.id.actionbar2);
        listview = (XListView) findViewById(R.id.xlv_pinglun);
        etv_pinglun = (EditText) findViewById(R.id.etv_pinglun);
        ibt_pinglun = (ImageButton) findViewById(R.id.ibt_pinglun);
    }

    private void setAction() {
        myActionBar.setActionBar("评论栏",R.drawable.back,-1,listener);
        ibt_pinglun.setOnClickListener(listener);
        adapter = new CommentsAdapter(this,listview);
        listview.setAdapter(adapter);
        listview.setXListViewListener(xlvListener);
        listview.setPullRefreshEnable(true);
        listview.setPullLoadEnable(true);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imv_left:
                    finish();
                    break;

                case R.id.ibt_pinglun:
                    String context = etv_pinglun.getText().toString();
                    if (context==null||context.equals("")){
                        Toast.makeText(ThirdActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //从sp中获取token（还没有存储）
                    final String token = SharedPreferenceUtils.getToken(ThirdActivity.this);
                    if(TextUtils.isEmpty(token)){
                        Toast.makeText(ThirdActivity.this, "not yet login", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //当点击了发送按钮之后，暂时让此按钮失去点击效果，当联网结束后恢复
                    ibt_pinglun.setEnabled(false);

                    String imei = SystemUtils.getIMEI(ThirdActivity.this);

                    //联网，获取数据
                    CommentsManager.sendComment(ThirdActivity.this, nid, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int status = ParserComments.parserSendComment(response);
                            if(status==0){
                                Toast.makeText(ThirdActivity.this, "comment success!", Toast.LENGTH_SHORT).show();
                                ibt_pinglun.setEnabled(true);
                                etv_pinglun.setText(null);
                                etv_pinglun.clearFocus();
                            }else{
                                Toast.makeText(ThirdActivity.this, "comment failed!", Toast.LENGTH_SHORT).show();
                                ibt_pinglun.setEnabled(true);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ThirdActivity.this, "check your network!", Toast.LENGTH_SHORT).show();
                            ibt_pinglun.setEnabled(true);
                        }
                    },CommonUtil.VERSION_CODE+"",token,imei,context);


                    break;
            }
        }
    };

   protected void loadPreComment() {
        Comment comment= (Comment) adapter.getItem(listview.getLastVisiblePosition()-2);
        mode= NewsManager.MODE_PREVIOUS;
        if(SystemUtils.getInstance(this).isNetConn()){
            CommentsManager.loadComments(this, CommonUtil.VERSION_CODE + "",listener2, errorListener, nid, 1, comment.getCid());
        }
    }

    private void loadNextComment(){
        Comment comment = (Comment) adapter.getItem(0);
        int curId = adapter.getAdapterData().size()<=0?0:comment.getCid();
        mode = NewsManager.MODE_NEXT;
        if(SystemUtils.getInstance(this).isNetConn()){
            CommentsManager.loadComments(this,CommonUtil.VERSION_CODE+"",listener2,errorListener,nid,mode,curId);
        }
    }

    Response.Listener<String> listener2 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<Comment> comments = ParserComments.parserComments(response);
            if (comments == null || comments.size() < 1) {
                return;
            }
            boolean flag = mode == NewsManager.MODE_NEXT ? true : false; adapter.appendData(comments, flag);
            adapter.update();
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(ThirdActivity.this, "服务器连接错误！", Toast.LENGTH_SHORT).show();
        }

    };

        private XListView.IXListViewListener xlvListener = new XListView.IXListViewListener() {
             @Override
             public void onRefresh() {
                 loadNextComment();
                 listview.stopLoadMore();
                 listview.stopRefresh();
                 listview.setRefreshTime(CommonUtil.getSystime());
    }

             @Override
             public void onLoadMore() {
                 int count = adapter.getCount();
                 if (count > 1) {
                     loadPreComment();
                 }
                 listview.stopLoadMore();
                 listview.stopRefresh();
    }
};




}
