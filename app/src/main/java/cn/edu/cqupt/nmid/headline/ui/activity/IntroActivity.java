package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import cn.edu.cqupt.nmid.headline.R;
import cn.jpush.android.api.JPushInterface;

public class IntroActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intro);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
      }
    }, 1000);
  }

  @Override protected void onResume() {
    super.onResume();
    JPushInterface.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    JPushInterface.onPause(this);
  }
}
