package com.pack.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ConnectionActivity extends AppCompatActivity {
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private ArrayList<String> mDeviceListinMAC = new ArrayList<String>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final Button connect_btn;
        final EditText editText;


        BluetoothAdapter mBluetoothAdapter;

        listView = (ListView) findViewById(R.id.listView);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);


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

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            editText.setText(mDeviceListinMAC.get(position));
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

}
