package com.pack.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class ConnectionManager {
    public static BluetoothDevice device;
    public static BluetoothSocket socket;
    public static OutputStream outputStream;
    public InputStream inputStream;
    public static BluetoothDevice device2;
    private static String DEVICE_ADDRESS = "00:21:13:01:86:61"; //Die Mac adresse vom arduino bluetooth modul muss angepasst werden
    private static final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    public static void send(String command){
        try {
            ConnectionManager.outputStream.write(command.getBytes());
        } catch (Exception e) {

            Toast.makeText(MainActivity.fb,"Bitte das Gerät erst Pairen!",Toast.LENGTH_LONG);



        }
    }


    public static boolean BTinit(String s)
    {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
        }

        if(!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            MainActivity.fb.startActivityForResult(enableAdapter,0);

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if(bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
        }
        else
        {
            for(BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(s))
                {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }

        return found;
    }



    public static boolean BTconnect(String s)
    {


        boolean connected = true;

        try
        {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID); //Creates a socket to handle the outgoing connection
            socket.connect();

        }
        catch(IOException e)
        {
            e.printStackTrace();
            connected = false;
        }

        if(connected)
        {
            try
            {
                outputStream = socket.getOutputStream(); //gets the output stream of the socket
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return connected;
    }

    private static void pairDevice(BluetoothDevice device) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {



                socket.getRemoteDevice().createBond();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
