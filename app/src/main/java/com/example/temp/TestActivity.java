package com.example.temp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {
private TextView path;
private Button open;
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.more_info);
        path = findViewById(R.id.location);
        open = findViewById(R.id.open);

        Intent intent = getIntent();
        String pathReceived = intent.getStringExtra("path");

        path.setText("The file is named : \n"+pathReceived);

    }
}
