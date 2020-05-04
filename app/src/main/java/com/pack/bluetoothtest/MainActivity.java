package com.pack.bluetoothtest;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {
    public static Activity fb;


    void steuerung(){

    links_btn.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // PRESSED
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ConnectionManager.send("s0");
                    break;
            }
            return false;
        }
    });


    rechts_btn.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // PRESSED
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // RELEASED
                    ConnectionManager.send("s2");
                    break;
            }
            return false;
        }
    });


    drehen_btn.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // PRESSED
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // RELEASED
                    ConnectionManager.send("s4");
                    break;
            }
            return false;
        }
    });


    runter_btn.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ConnectionManager.send("s7");
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ConnectionManager.send("s9");
                    break;
            }
            return false;
        }
    });



    neuesspiel_btn.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // PRESSED
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ConnectionManager.send("s1");
                    break;
            }
            return false;
        }
    });





    steuerungwechsel_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("timeraserf");
            str++;
            if(str>2){
                str=0;
            }
            switch (str){
                case 0:
                    setContentView(R.layout.activity_main);
                    break;
                case 1:
                    setContentView(R.layout.steuerung);
                    break;
                case 2:
                    setContentView(R.layout.steuerung2);
                    break;
            }



            links_btn = (Button) findViewById(R.id.links);
            rechts_btn = (Button) findViewById(R.id.rechts);
            drehen_btn = (Button) findViewById(R.id.drehen);
            runter_btn = (Button) findViewById(R.id.runter);
            neuesspiel_btn = (Button) findViewById(R.id.neuesspiel);
            steuerungwechsel_btn = (Button) findViewById(R.id.steuerungwechsel);
            settings_btn = (ImageButton) findViewById(R.id.settings);


        }
    });





    settings_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
finish();

        }
    });



}

    boolean c=true;
static int str=0;






    Button links_btn, rechts_btn,drehen_btn,runter_btn, bluetooth_connect_btn, neuesspiel_btn,steuerungwechsel_btn;
    ImageButton settings_btn;

    //String command; //string variable that will store value to be transmitted to the bluetooth module

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fb = this;
        Timer a;
        a= new Timer();

        a.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                if(c==true){
                try {
                    steuerung();
                }catch (Exception e) {

                }
                }else{

                }


            }
        },0, 100);


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final  MediaPlayer tetrismelodie = MediaPlayer.create(this, R.raw.tetris);
        tetrismelodie.start();
        tetrismelodie.setLooping(true);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //declaration of button variables


        links_btn = (Button) findViewById(R.id.links);
        rechts_btn = (Button) findViewById(R.id.rechts);
        drehen_btn = (Button) findViewById(R.id.drehen);
        runter_btn = (Button) findViewById(R.id.runter);
        neuesspiel_btn = (Button) findViewById(R.id.neuesspiel);
        steuerungwechsel_btn = (Button) findViewById(R.id.steuerungwechsel);
        settings_btn = (ImageButton) findViewById(R.id.settings);


        //OnTouchListener code for the forward button (button long press)



        //OnTouchListener code for the reverse button (button long press)


        //Button that connects the device to the bluetooth module when pressed




    }

    //Initializes bluetooth module


    @Override
    protected void onStart()
    {
        super.onStart();
    }



}
