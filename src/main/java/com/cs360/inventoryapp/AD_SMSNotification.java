package com.cs360.inventoryapp;

import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


/**
 * SMS Notification Alert Dialog java code.
 * <p>
 * The AD_SMSNotification class include the functionality to build the
 * alert dialog to enable or disable the SMS notifications when an item
 * quantity id zero.
 * <p>
 * This is class is called from the ItemsListActivity class.
 *
 * @author	Arturo Santiago-Rivera <i>asantiago@arsari.com</i>
 * @course	CS-360-X6386 Mobile Architect & Programming 21EW6
 * @college	Southern New Hampshire University
 */
public class AD_SMSNotification {

    public static AlertDialog doubleButton(final HomeActivity context){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SMS Permission Needed")
                .setCancelable(false)
                .setMessage("The app SMS feature requires permission to notified for items with zero inventory. You can enable or disable this feature at any time. However, you should allow the device permission request to use this app feature.")
                .setPositiveButton("Enable", (dialog, arg1) -> {
                    Toast.makeText(context, "SMS Alerts Enable", Toast.LENGTH_LONG).show();
                    HomeActivity.AllowSendSMS();
                    dialog.cancel();
                })
                .setNegativeButton("Disable", (dialog, arg1) -> {
                    Toast.makeText(context, "SMS Alerts Disable", Toast.LENGTH_LONG).show();
                    HomeActivity.DenySendSMS();
                    dialog.cancel();
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}

