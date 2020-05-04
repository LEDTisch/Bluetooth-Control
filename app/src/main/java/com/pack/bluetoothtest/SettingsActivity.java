package com.pack.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {
    SeekBar seekBar;
    Button zuruck_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        zuruck_btn=(Button) findViewById(R.id.zuruck);

        zuruck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,MainActivity.class));
                finish();

            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                ConnectionManager.send("h");
                if(progress < 100){
                    ConnectionManager.send("0"+progress);
                }else{
                    ConnectionManager.send(""+progress);
                }

                ConnectionManager.send("e");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
