package com.example.temp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class NewPage extends AppCompatActivity {
    private int i = 0;


    @Override
    public void onCreate(Bundle b){

        super.onCreate(b);
        setContentView(R.layout.new_page);

        TextView msg = null ,counter ;
        Button back = null ,inc = null ,dec = null;

//        back = findViewById(R.id.back);
        counter = findViewById(R.id.counter);
        inc = findViewById(R.id.inc);
        dec = findViewById(R.id.dec);

        dec.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                i--;
                counter.setText(""+i);
            }
        });

        inc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    i++;
                counter.setText(""+i);
            }
        });

    }
}
