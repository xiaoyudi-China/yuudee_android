package com.easychange.admin.smallrain.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.login.LoginActivity;
import com.easychange.admin.smallrain.login.RegisterActivity;
import com.qlzx.mylibrary.util.EventBusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import bean.FinishHomeActivityBean;
import bean.FinishLoginBean;
import bean.RegisterDestroyOtherBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mingci.MingciIdeaOneActivity;
import mingci.MingciOneActivity;
import mingci.MingciTestOneActivity;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.layout_more)
    LinearLayout layoutMore;
    private List<Activity> activityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initStatusBar();
        ButterKnife.bind(this);

        EventBusUtil.register(this);

        boolean isCloseOtherActivity = getIntent().getBooleanExtra("isCloseOtherActivity", false);
        if (isCloseOtherActivity) {
            BaseActivity.removeAllActivitys();
        }

        activityList = com.easychange.admin.smallrain.base.BaseActivity.getActivityList();
        if (activityList != null) {
            activityList.add(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FinishHomeActivityBean(FinishHomeActivityBean event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RegisterDestroyOtherBean(RegisterDestroyOtherBean event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBusUtil.unregister(this);
        if (activityList != null) {
            activityList.remove(this);
        }
        super.onDestroy();

    }

    private void initStatusBar() {
        //最终方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 全透明实现
            // getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    @OnClick({R.id.btn_register, R.id.btn_login, R.id.layout_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:

//                startActivity(new Intent(this, BalloonActivity.class));
//                startActivity(new Intent(this, JuZiExerciseActivity.class));
//                startActivity(new Intent(this, JuZiExerciseActivityFourClick.class));
//                startActivity(new Intent(this, JuZiDecomposeActivityFourClick.class));
//                startActivity(new Intent(this, MingciOneActivity.class));
//                startActivity(new Intent(this, MingciIdeaOneActivity.class));
//                startActivity(new Intent(this, MingciIdeaOneActivity.class));


//                startActivity(new Intent(this, MingciTestOneActivity.class));
//                startActivity(new Intent(this, MingciOneActivity.class));

//                startActivity(new Intent(this, MingciOneActivity.class));

//                startActivity(new Intent(this, DongciTestActivity.class));

//                startActivity(new Intent(this, ChoosePhoneHomeLocationActivity.class));
//                startActivity(new Intent(this, PerfectionChildrenInfoActivity.class));
//                startActivity(new Intent(this, MainActivity.class));
//                startActivity(new Intent(this, ModifyingChildNicknamesActivity.class));
//                Intent intent = new Intent(HomeActivity.this, EditDataActivity.class);
//                intent.putExtra("TYPE", 2);
//                startActivity(intent);

                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
//                startActivity(new Intent(this, LoginActivity.class));
                startActivity(new Intent(this, LoginActivity.class));
//                Intent intent = new Intent(this, MingciTestOneActivity.class);
//                intent.putExtra("position",9);
//                startActivity(intent);


                break;
            case R.id.layout_more:
                startActivity(new Intent(this, ProdectDetailActivity.class));
                break;
        }
    }
}