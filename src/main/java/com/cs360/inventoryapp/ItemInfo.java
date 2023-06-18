package com.cs360.inventoryapp;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



public class ItemInfo extends AppCompatActivity {

    TextView itemName, itemDescription, itemQuantity;
    Button minusButton, plusButton, deleteButton;
    String id, name, description, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);

        // ID's for buttons and text input
        itemName = findViewById(R.id.item);
        itemDescription = findViewById(R.id.itemDescription);
        itemQuantity = findViewById(R.id.addItemQuantity);
        minusButton = findViewById(R.id.subtractQuantity);
        plusButton = findViewById(R.id.addQuantity);
        deleteButton = findViewById(R.id.deleteItemButton);

        // Get data necessary to fill in text inputs / buttons for editing
        getAndSetIntentData();


        plusButton.setOnClickListener(view -> addToQuantity());
        minusButton.setOnClickListener(view -> subtractFromQuantity());

        deleteButton.setOnClickListener(view -> confirmDialog());

    }

    private void subtractFromQuantity() {
        Main_DBHelper myDB = new Main_DBHelper(ItemInfo.this);
        myDB.updateQuantity(id, Integer.parseInt(quantity) ,0);
        finish();
    }

    private void addToQuantity() {
        Main_DBHelper myDB = new Main_DBHelper(ItemInfo.this);
        myDB.updateQuantity(id, Integer.parseInt(quantity),1);
        finish();
    }

    void getAndSetIntentData() {
            id = getIntent().getStringExtra("itemId");
            name = getIntent().getStringExtra("itemName");
            description = getIntent().getStringExtra("itemDescription");
            quantity = getIntent().getStringExtra("itemQuantity");

            itemName.setText(name);
            itemDescription.setText(description);
            itemQuantity.setText(quantity);

    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + "?");
        builder.setMessage("Are you sure you want to delete " + name + "?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            Main_DBHelper myDB = new Main_DBHelper(ItemInfo.this);
            myDB.deleteItem(id);
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

        });
        builder.create().show();

    }

}