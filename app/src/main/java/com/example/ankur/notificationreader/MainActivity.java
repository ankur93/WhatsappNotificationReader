package com.example.ankur.notificationreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.notificationreader.database.Notification;
import com.example.ankur.notificationreader.repository.NotificationRepository;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {
    TableLayout tab;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tab = findViewById(R.id.tab);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

        final Button clearAll = findViewById(R.id.clearAll);
        clearAll.setOnClickListener((view)->{
            new NotificationRepository(getApplicationContext()).deleteAll();
            Toast.makeText(this, "All notifications deleted", Toast.LENGTH_SHORT).show();
            this.displayAllNotifications(getApplicationContext());
        });

        this.displayAllNotifications(this);
    }

    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String appPackage = intent.getStringExtra("package");
            final String Msgtitle = intent.getStringExtra("title");
            final String rcvdMsg = intent.getStringExtra("text");

            //database entry
            new NotificationRepository(context, new Notification(appPackage, Msgtitle, rcvdMsg));

            displayAllNotifications(context);
        }
    };

    private void displayAllNotifications(final Context context) {
        final List<Notification> allNotifications = new NotificationRepository(context).getAllNotifications();
        tab.removeAllViews();
        this.addHeader();

        allNotifications.forEach(notification -> {
            final TableRow tr = new TableRow(getApplicationContext());
            tr.setLayoutParams(new LayoutParams( MATCH_PARENT, WRAP_CONTENT));
            tr.setBackgroundColor(getResources().getColor(R.color.black));

            final LayoutParams colLayoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1.0f);
            colLayoutParams.setMargins(5,5,5,0);

            final TextView pack = new TextView(getApplicationContext());
            pack.setBackgroundColor(getResources().getColor(R.color.grey));
            pack.setText(Html.fromHtml(notification.getAppPackage() + " | " + notification.getTitle()));
            tr.addView(pack, colLayoutParams);

            final TextView message = new TextView(getApplicationContext());
            message.setBackgroundColor(getResources().getColor(R.color.grey));
            message.setText(Html.fromHtml(notification.getMessage()));
            tr.addView(message, colLayoutParams);

            tab.addView(tr);
        });
    }

    private void addHeader() {
        final TableRow tr = new TableRow(getApplicationContext());
        tr.setLayoutParams(new LayoutParams( MATCH_PARENT, WRAP_CONTENT));
        tr.setBackgroundColor(getResources().getColor(R.color.black));

        final LayoutParams colLayoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1.0f);
        colLayoutParams.setMargins(5,5,5,0);

        final TextView packHeader = new TextView(getApplicationContext());
        packHeader.setBackgroundColor(getResources().getColor(R.color.grey));
        packHeader.setText(Html.escapeHtml("PACKAGE - TITLE"));
        packHeader.setTextSize(16);
        tr.addView(packHeader, colLayoutParams);

        final TextView messageHeader = new TextView(getApplicationContext());
        messageHeader.setBackgroundColor(getResources().getColor(R.color.grey));
        messageHeader.setText(Html.escapeHtml("MESSAGE"));
        messageHeader.setTextSize(16);
        tr.addView(messageHeader, colLayoutParams);

        tab.addView(tr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onNotice);
    }
}
