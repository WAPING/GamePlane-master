package com.ispring.gameplane;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements Button.OnClickListener {
    public final static String ACTION_EXIT_APP = "cn.edu.zafu.leakcanary.exit";
    private static LocalBroadcastManager mLocalBroadcatManager;
    private BroadcastReceiver mExitReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_EXIT_APP)) {
                Log.d("TAG", "exit from broadcast");
                finish();
            }
        }
    };

    private void init() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_EXIT_APP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        getLocalBroadcastManager().registerReceiver(mExitReceiver, filter);
    }

    private LocalBroadcastManager getLocalBroadcastManager() {
        if (mLocalBroadcatManager == null) {
            mLocalBroadcatManager = LocalBroadcastManager.getInstance(this);
        }
        return mLocalBroadcatManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.btnGame){
            startGame();
        }
    }

    public void startGame(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getRefWatcher().watch(this);
    }
}