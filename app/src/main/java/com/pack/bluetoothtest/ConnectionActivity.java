package com.pack.bluetoothtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ConnectionActivity extends AppCompatActivity {
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private ArrayList<String> mDeviceListinMAC = new ArrayList<String>();
    private ListView listView;
    private ProgressBar progressBar;
    private Button recent;
    int CODE_REQUEST = 45;
    String recent_mac;
    BluetoothAdapter mBluetoothAdapter;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);






        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},CODE_REQUEST );
        }

        progressBar = findViewById(R.id.progressBar2);

        listView = (ListView) findViewById(R.id.listView);

        recent = findViewById(R.id.button);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.getString("recent", "")=="") {
            recent.setEnabled(false);
        }else{
            recent.setEnabled(true);
            recent_mac =sharedPref.getString("recent", "");
        }





recent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(recent.getText().toString().contains("Schneller Verbinden")) {
            thread.interrupt();
            finish();
        }else{


        }

        recent.setEnabled(true);
        listView.setVisibility(View.INVISIBLE);
        recent.setText("Schneller Verbinden");
        progressBar.setVisibility(View.VISIBLE);

        thread = new Thread() {
            @Override
            public void run() {

                if(ConnectionManager.BTinit(recent_mac))
                {
                    ConnectionManager.BTconnect(recent_mac);

                    SharedPreferences sharedPref = ConnectionActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("recent", recent_mac);
                    editor.commit();

                }

                startActivity(new Intent(ConnectionActivity.this,MainActivity.class));
                finish();
            }
        };

        thread.start();
    }
});




    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            recent.setEnabled(true);
            listView.setVisibility(View.INVISIBLE);
            recent.setText("Schneller Verbinden");
progressBar.setVisibility(View.VISIBLE);

            Thread thread = new Thread() {
                @Override
                public void run() {

                    if(ConnectionManager.BTinit(mDeviceListinMAC.get(position)))
                    {
                        ConnectionManager.BTconnect(mDeviceListinMAC.get(position));

                        SharedPreferences sharedPref = ConnectionActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("recent", mDeviceListinMAC.get(position));
                        editor.commit();

                    }

                    startActivity(new Intent(ConnectionActivity.this,MainActivity.class));
                    finish();
                }
            };

            thread.start();


        }
    });

    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
                mDeviceListinMAC.add(device.getAddress());
                Log.i("BT", device.getName() + "\n" + device.getAddress());
                listView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mDeviceList));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CODE_REQUEST) {


            if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                mBluetoothAdapter.cancelDiscovery();
                mBluetoothAdapter.startDiscovery();
            }else{
                this.finish();
            }
        }
    }
}
