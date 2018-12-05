package com.winnie.utils.notification;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * @author winnie
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void systemNotify(View view) {
        Notification notification;
    }

    public void progressNotify(View view) {
    }

    public void customNotify(View view) {
    }
}
