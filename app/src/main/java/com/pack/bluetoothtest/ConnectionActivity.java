package com.pack.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final Button connect_btn;

        connect_btn=(Button)  findViewById(R.id.bluetooth_connect_btn);


        connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(ConnectionManager.BTinit())
                        {
                            ConnectionManager.BTconnect();

                        }
                        startActivity(new Intent(ConnectionActivity.this,MainActivity.class));
                        finish();
                    }
                });


            }
        });
    }
}
