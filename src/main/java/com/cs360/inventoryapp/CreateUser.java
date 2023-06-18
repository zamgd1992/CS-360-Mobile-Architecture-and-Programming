package com.cs360.inventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateUser extends AppCompatActivity {

    EditText username, password, rePassword;
    Button signUp, signIn;
    Login_DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        username = (EditText) findViewById(R.id.newUsername);
        password = (EditText) findViewById(R.id.newPassword);
        rePassword = (EditText) findViewById(R.id.reenterPassword);

        signUp = (Button)  findViewById(R.id.createNewUserButton);
        signIn = (Button) findViewById(R.id.alreadyExist);

        DB = new Login_DBHelper(this);

        // let user sign up based on username and password entered
        signUp.setOnClickListener(view -> {

            String user = username.getText().toString();
            String pass = password.getText().toString();
            String repass = rePassword.getText().toString();

            // check if all fields have data or not
            if(user.equals("") || (pass.equals("") || repass.equals("")))
                Toast.makeText(CreateUser.this, "Please check all fields", Toast.LENGTH_SHORT).show();
            else{
                // check if passwords are same
                if(pass.equals(repass)){
                    // check if username is in db already
                    Boolean checkUser = DB.checkUsername(user);
                    if(!checkUser){
                        // insert username and password into db
                        Boolean insert = DB.insertData(user, pass);
                        // start the home activity
                        if(insert){
                            Toast.makeText(CreateUser.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(CreateUser.this, "User already exists; please sign-in", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(CreateUser.this, "User already exists; please sign-in", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CreateUser.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Go back to log in screen
        signIn.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);


        });
    }
}