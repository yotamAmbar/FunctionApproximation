package com.example.functionapproximations;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Boolean isCancel = new Boolean(false);
        Button button1 = findViewById(R.id.button);
        Button cancel = findViewById(R.id.button2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button save = findViewById(R.id.button5);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button list = findViewById(R.id.button3);
        list.setOnClickListener(v -> onClickList());
        Switch pouse = findViewById(R.id.switch1);
        button1.setOnClickListener(v -> onClickButton1(pouse, isCancel));
        cancel.setOnClickListener(v -> onClickCancel(isCancel));
        File folder = new File(getFilesDir(), "lists");
        if (!folder.exists()) {
            folder.mkdirs();
        cancel.setOnClickListener(v -> onClickSave(folder));

        }



    }
    private void onClickSave(File folder){
        EditText myEditText = findViewById(R.id.Notifications);
        String content = myEditText.getText().toString();
        createFileAndWriteContent(content , folder);
    }
    private void onClickList(){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    private void onClickCancel(Boolean isCancel) {
        isCancel.value = true;
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                isCancel.value = false;
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(r1 , 10000);
    }

    private void onClickButton1(Switch pouse, Boolean isCancel) {
        Log.d("GARGAMEL", "BUTTON CLICKED");
        EditText myEditText = findViewById(R.id.Notifications);
        String[] notifications = (myEditText.getText().toString()).split("\n\n\n");
        callNotificate(notifications, this, 0, pouse, isCancel);
    }

    private void notificate(String[] notifications, Context context, int i, int j) {
        createNotificationChannel();
        NotificationHelper notification = new NotificationHelper();
        notification.sendNotification(context, "messege number " + j, notifications[i]);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Your Channel Name";
            String description = "Your Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(new String("channel 1"), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public class NotificationHelper {

        private static final String CHANNEL_ID = "channel 1";

        public void sendNotification(Context context, String title, String content) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_icon_background)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManager.notify(1, builder.build());
        }
    }

    private void callNotificate(String[] notifications, Context context, int i, Switch pause, Boolean isCancel) {
        if (i == 3 * notifications.length)
            return;
        notificate(notifications, context, i / 3, i);
        /* call the method callNotificate in 10 seconds. */
        Handler handler = new Handler();
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                callNotificate(notifications , context , i+1 , pause , isCancel);
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                callNotificate(notifications , context , i , pause , isCancel);
            }
        };
        if (!pause.isChecked()) {
            handler.postDelayed(r1, 10000);
        } else {
            handler.postDelayed(r2, 10000);
        }
        if(isCancel.value){
            handler.removeCallbacks(r1);
        }
    }
    private void createFileAndWriteContent(String content , File folder) {
        File listsDir = new File(folder, "lists");
        if (!listsDir.exists()) {
            listsDir.mkdirs(); // Create the directory if it does not exist
        }

        int i = 1;
        String fileName = "list" + i;
        File file = new File(listsDir, fileName);
        while(file.exists()) {
            i++;
            fileName = "list" + i;
            file = new File(listsDir, fileName);
        }
        try {
            PrintWriter out = new PrintWriter(file);
            out.println(content);
            out.close();
        }
        catch(IOException e) {
            Log.e("GARGAMEL", e.getMessage());
        }
    }
    private void createFolderInExternalStorage(String folderName) {
        File folder = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (success) {
                Log.d(TAG, "Folder created successfully");
            } else {
                Log.d(TAG, "Failed to create folder");
            }
        }
    }
}