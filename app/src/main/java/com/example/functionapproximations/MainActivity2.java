package com.example.functionapproximations;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity {
    ArrayList<File> notes;
    ArrayAdapter<File> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button back = findViewById(R.id.button4);
        File folder = new File(getFilesDir(), "lists");
        if (!folder.exists()) {
            // The folder might not exist if it was not created before
            folder.mkdirs();
        }
        ListView lists = findViewById(R.id.listView1);
        notes = new ArrayList<File>();
        int i = 1;
        File search = new File(folder , "list 1");
        while(search.exists()) {
            i++;
            search.renameTo(new File(folder, "list " + i));
            adapter.add(search);
        }
    }


    private void onClickBack(){
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }
}