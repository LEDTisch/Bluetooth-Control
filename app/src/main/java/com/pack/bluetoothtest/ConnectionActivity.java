package com.pack.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final Button connect_btn;
        final EditText editText;

        connect_btn=(Button)  findViewById(R.id.bluetooth_connect_btn);
        editText = findViewById(R.id.editText);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


    if(sharedPref.getString("mac", "")!="") {

        editText.setText(sharedPref.getString("mac", ""));

    }

        connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        editor.putString("mac", editText.getText().toString());
                        editor.commit();

                        if(ConnectionManager.BTinit(editText.getText().toString()))
                        {
                            ConnectionManager.BTconnect(editText.getText().toString());

                        }
                        startActivity(new Intent(ConnectionActivity.this,MainActivity.class));
                        finish();
                    }
                });


            }
        });
    }
}
