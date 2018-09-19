package com.c4po.eventbus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c4po.eventbus.R;
import com.c4po.eventbus.event.SpecificEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 订阅者，消息接收端
 *
 * @author Lisa
 */
public class SubscriberActivity extends AppCompatActivity {

    private final String TAG = "SubscriberActivity";

    TextView tvReceivedMsg;
    Button btnToPublisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_subscriber);
        tvReceivedMsg = findViewById(R.id.tv_received_msg);
        btnToPublisher = findViewById(R.id.btn_to_publisher);
        btnToPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPublish = new Intent(SubscriberActivity.this,PublisherActivity.class);
                SubscriberActivity.this.startActivity(toPublish);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isFinishing()&&EventBus.getDefault().isRegistered(this)){
            Log.d(TAG,"onStop");
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 消息接收器
     * @param specificEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(SpecificEvent specificEvent) {
        Toast.makeText(this, "收到消息", Toast.LENGTH_SHORT).show();
        tvReceivedMsg.setText("收到消息："+ specificEvent.userName+" "+specificEvent.userAvatar);
    }
}
