package com.example.work722;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 12;
    Button btn_sms;
    Button btn_call;
    EditText number_text;
    EditText text_sms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_sms = findViewById(R.id.sms_btn);
        btn_call = findViewById(R.id.call_btn);
        number_text = findViewById(R.id.number_text);
        text_sms = findViewById(R.id.sms_text);
        btn_call.setOnClickListener(this);
        btn_sms.setOnClickListener(this);
    }


    private void callByNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number_text.getText().toString()));
            startActivity(dialIntent);
        }
    }

    private void sendSms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager smgr = SmsManager.getDefault();
            if (number_text.getText().toString().equals("") || text_sms.getText().toString().equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.no_nuber), Toast.LENGTH_LONG);
                toast.show();
            } else {
                smgr.sendTextMessage(number_text.getText().toString(), null, text_sms.getText().toString(), null, null);
                Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.send_message), Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callByNumber();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.no_access), Toast.LENGTH_LONG);
                    toast.show();
                }
                break;

            case MY_PERMISSIONS_REQUEST_SEND_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.no_access), Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_btn:
                callByNumber();
                break;
            case R.id.sms_btn:
                sendSms();
                break;
        }
    }
}
