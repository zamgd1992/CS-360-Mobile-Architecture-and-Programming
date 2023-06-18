package com.cs360.inventoryapp;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItem extends AppCompatActivity {

    EditText nameInput, descriptionInput, quantityInput;
    Button addButton;

    long notificationCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        // ID's for buttons and text input
        nameInput = findViewById(R.id.itemName);
        descriptionInput = findViewById(R.id.itemDescription);
        quantityInput = findViewById(R.id.addItemQuantity);
        addButton = findViewById(R.id.addItemButton);

        nameInput.addTextChangedListener(textWatcher);
        descriptionInput.addTextChangedListener(textWatcher);
        quantityInput.addTextChangedListener(textWatcher);

        addButton.setOnClickListener(view -> {

            Main_DBHelper myDB = new Main_DBHelper(AddItem.this);

            notificationCounter = myDB.addItem(nameInput.getText().toString().trim(),
                    descriptionInput.getText().toString().trim(),
                    quantityInput.getText().toString().trim());

            finish();

        });

    }

    // TEXT WATCHER to check that all text has been entered
    // Disable 'ADD' button if false
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = nameInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();
            String date = quantityInput.getText().toString().trim();

            addButton.setEnabled(!title.isEmpty() && !description.isEmpty() &&
                    !date.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

}

