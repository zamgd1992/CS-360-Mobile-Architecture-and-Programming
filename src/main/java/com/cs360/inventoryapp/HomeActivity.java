package com.cs360.inventoryapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    Button SmsButton;
    static String NameHolder;
    AboutItem aboutItem;
    AlertDialog AlertDialog = null;
    Main_DBHelper my_library;
    ArrayList<String> itemId, itemName, itemDescription, itemQuantity;
    private static final int USER_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static boolean smsAuthorized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // build a recycler view to display events from db
        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.addNewItemButton);
        SmsButton = findViewById(R.id.smsNotification);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            NameHolder = bundle.getString("user_name");
        }

        // action button to add event
        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddItem.class);
            startActivityForResult(intent, USER_PERMISSIONS_REQUEST_SEND_SMS);       // deprecated use here

        });

        // create arrays to store db data
        my_library = new Main_DBHelper(HomeActivity.this);
        itemId = new ArrayList<>();
        itemName = new ArrayList<>();
        itemDescription = new ArrayList<>();
        itemQuantity = new ArrayList<>();

        // access db and get data to fill arrays
        storeDataInArrays();


        aboutItem = new AboutItem(HomeActivity.this, this, itemId, itemName, itemDescription, itemQuantity);
        recyclerView.setAdapter(aboutItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));



        // Adding click listener to smsNotification
        SmsButton.setOnClickListener(view -> {
            // Request sms permission for the device
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.SEND_SMS)) {
                    Toast.makeText(this,"Device SMS Permission is Needed", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[] {Manifest.permission.SEND_SMS},
                            USER_PERMISSIONS_REQUEST_SEND_SMS);
                }
            } else {
                Toast.makeText(this,"Device SMS Permission is Allowed", Toast.LENGTH_LONG).show();
            }
            // Open SMS Alert Dialog
            AlertDialog = com.cs360.inventoryapp.AD_SMSNotification.doubleButton(this);
            AlertDialog.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    // get data from db and create rows in recycler view for scroll
    void storeDataInArrays(){
        Cursor cursor = my_library.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();

        }
        else{
            while (cursor.moveToNext()){
                itemId.add(cursor.getString(0));
                itemName.add(cursor.getString(1));
                itemDescription.add(cursor.getString(2));
                itemQuantity.add(cursor.getString(3));

            }
        }
    }

    // Receive and evaluate user response from AlertDialog to send SMS
    public static void AllowSendSMS() {
        smsAuthorized = true;
    }

    public static void DenySendSMS() {
        smsAuthorized = false;
    }

    public static void SendSMSMessage(Context context) {
        String holder = NameHolder;
        String smsMsg = "Please, you have items with zero value in your Inventory App.";

        if (smsAuthorized) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(holder, null, smsMsg, null, null);
                Toast.makeText(context, "SMS Sent", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(context, "Device Permission Denied", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(context, "App SMS Alert Disable", Toast.LENGTH_LONG).show();
        }
    }
}