//https://github.com/MartinxMax
package com.example.raider;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final String DEVICE_ADDRESS = "98:D3:31:F6:69:26"; // Martin-HID
    private static final UUID UUID_SERIAL_PORT_PROFILE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // 蓝牙串口服务的UUID

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private OutputStream mOutputStream;

    private Button button1_payload;
    private Button button2_payload;
    private Button button3_Check;
    private Button button4_Connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1_payload = findViewById(R.id.button_payload1);
        button2_payload = findViewById(R.id.button_payload2);
        button3_Check = findViewById(R.id.Check);
        button4_Connect = findViewById(R.id.Connect);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        button1_payload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothSocket != null && mOutputStream != null) {
                    try {
                        mOutputStream.write('1');
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        button2_payload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothSocket != null && mOutputStream != null) {
                    try {
                        mOutputStream.write('2');
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        button3_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    enableBluetooth();
                }
            }
        });

        button4_Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    enableBluetooth();
                } else {
                    connectDevice();
                }
            }
        });
    }

    private void connectDevice() {
        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS);
        try {
            Toast.makeText(this, "Attempting to connect to hacker devices...", Toast.LENGTH_SHORT).show();
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(UUID_SERIAL_PORT_PROFILE);
            mBluetoothSocket.connect();
            mOutputStream = mBluetoothSocket.getOutputStream();
            Toast.makeText(this, "Successfully established connection!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Connection establishment failed:" + DEVICE_ADDRESS, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error connecting to device", e);
        }
    }

    private void enableBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "This device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bluetooth on", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bluetooth off", Toast.LENGTH_SHORT).show();
            }
        }
    }
}