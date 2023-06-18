package com.cs360.inventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button sign_in_button, register_button;

    Login_DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        sign_in_button = (Button) findViewById(R.id.logInButton);
        register_button = (Button) findViewById(R.id.createUserButton);
        DB = new Login_DBHelper(this);

        // create the channel to handle notifications in the app
        createNotificationChannel();

        // Log in
        sign_in_button.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();

            // check that fields have data
            if(user.equals("") || pass.equals("")){
                Toast.makeText(MainActivity.this, "Please check all fields", Toast.LENGTH_SHORT).show();
            }
            // check if the user and password exist and match in the database
            else{
                Boolean checkUserPass = DB.checkUsernamePassword(user, pass);
                if(checkUserPass){
                    Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();

                    // Sending Name to ItemsListActivity using intent
                    Bundle bundle = new Bundle();
                    bundle.putString("user_name", user);

                    // Going to ItemsListActivity after login success message
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // allow user to register and store login info in database
        register_button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateUser.class);
            startActivity(intent);
        });

    }
    private void createNotificationChannel(){
        CharSequence name = "ReminderChannel";
        String description = "Channel for Reminder";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notifyText", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}