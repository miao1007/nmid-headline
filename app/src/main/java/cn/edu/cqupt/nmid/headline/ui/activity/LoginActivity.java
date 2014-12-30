package cn.edu.cqupt.nmid.headline.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.ThemeUtils;
import cn.edu.cqupt.nmid.headline.utils.UIutils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;

/**
 * Created by leon on 14/10/1.
 * Function : 基于ShareSDK的授权回调Activity
 */
public class LoginActivity extends BaseActivity {

    /**
     * ShareSDK status
     */
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;

    private MyPlatformListener mPlatformListener;
    private ActionBarActivity mActivity = LoginActivity.this;

    /**
     * ShareSDK author
     */
    @OnClick(R.id.activity_login_by_qzone)
    void activity_login_by_qzone() {
//        Platform qzone = ShareSDK.getPlatform(this, QZone.NAME);
//        qzone.setPlatformActionListener(mPlatformListener);
//        qzone.authorize();
        Platform qzone = ShareSDK.getPlatform(this, QZone.NAME);
        if (qzone.isValid ()) {
            qzone.removeAccount();
        }
        qzone.setPlatformActionListener(mPlatformListener);
        qzone.authorize();
    }

    @OnClick(R.id.activity_login_by_sinaweibo)
    void activity_login_by_sinaweibo() {
        Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        weibo.setPlatformActionListener(mPlatformListener);
        weibo.authorize();

    }

    @OnClick(R.id.activity_login_exit)
    void activity_login_exit() {
        Platform qzone = ShareSDK.getPlatform(this, QZone.NAME);
        if (qzone.isValid()) {
            qzone.removeAccount();
        }

        //isValid和removeAccount不开启线程，会直接返回。
    }

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
        ThemeUtils.setThemeFromDb(this);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mPlatformListener = new MyPlatformListener();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }




    private void authorize(Platform plat) {
        if (plat instanceof QZone){
            plat.authorize();
        }else if (plat instanceof SinaWeibo){
            plat.authorize();
        }


//
//        if (plat == null) {
//            Log.e("authorize", "plat is Empty");
//        } else if (!plat.isValid()) {
//            Log.e("authorize", "trying to log in");
//            plat.showUser(null);
//            plat.setPlatformActionListener(mPlatformListener);
//        } else {
//            String userId = plat.getDb().getUserId();
//            //如果id不为空，就视为用户已经登录
//            String avatar = plat.getDb().getUserIcon();
//            String name = plat.getDb().getUserName();
//            if (!TextUtils.isEmpty(userId)) {
//                Log.e("authorize", "You have already logined in");
//                setResult(name, avatar);
//                UIutils.disMsg(this,"You have already logined in");
//            } else {
//                Log.e("authorize", "userId isEmpty");
//            }
//        }
    }



    class MyPlatformListener implements PlatformActionListener {
        //这个只有第一次登陆能使用，登陆成功后就没用了
        @Override
        public void onComplete(Platform plat, int action, HashMap<String, Object> stringObjectHashMap) {
            String name;
            String avatar;
            UIutils.disMsg(mActivity,"您已经成功登陆");
            if (plat instanceof SinaWeibo) {
                name = String.valueOf(stringObjectHashMap.get("name"));
                avatar = String.valueOf(stringObjectHashMap.get("avatar_large"));
                plat.getDb().put("avatar",avatar);
                plat.getDb().put("name",name);

            } else if (plat instanceof QZone) {
                System.out.println("stringObjectHashMap = " + stringObjectHashMap);
                name = String.valueOf(stringObjectHashMap.get("nickname"));
                avatar = String.valueOf(stringObjectHashMap.get("figureurl_qq_2"));
                plat.getDb().put("avatar",avatar);
                plat.getDb().put("name",name);

            } else {
                Log.e("plat cast failed", plat.getName());
            }

        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            if (i == Platform.ACTION_USER_INFOR) {
                UIutils.disMsg(mActivity,"授权错误");
            }
            throwable.printStackTrace();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            if (i == Platform.ACTION_USER_INFOR) {
                UIutils.disMsg(mActivity,"授权取消");
            }
        }
    }


//    //Call OnSharedPreferencesChanged On HomeActivity
//    private void setResult(String name, String avatar) {
//        Log.e("setResult", name + "+" + avatar);
//        //shared performance
//        SharedPreferences preferences = mActivity.getSharedPreferences("sharesdk", 0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("name", name);
//        editor.putString("avatar", avatar);
//        editor.commit();
//
//    }

}
