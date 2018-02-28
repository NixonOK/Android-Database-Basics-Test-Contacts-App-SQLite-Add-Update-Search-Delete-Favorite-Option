package com.w3e.nixonok.assignmentondbcontactsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    EditText firstNameField;
    EditText lastNameField;
    EditText emailField;
    EditText phoneField;
    Spinner numbertType;
    CheckBox favouriteField;
    Contact toBeUpdatedContact;

    int contactCount;
    boolean update;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactCount = getIntent().getIntExtra("contact_count", 0);
        update = getIntent().getBooleanExtra("update", false);
        position = getIntent().getIntExtra("position", 0);

        firstNameField = findViewById(R.id.contact_first_name);
        lastNameField = findViewById(R.id.contact_last_name);
        emailField = findViewById(R.id.contact_email);
        numbertType = findViewById(R.id.phone_no_type_spinner);
        phoneField = findViewById(R.id.contact_phone);
        favouriteField = findViewById(R.id.contact_favourite);

        if(update)
        {

            toBeUpdatedContact = (Contact) getIntent().getSerializableExtra("contact_to_be_updated");

                    firstNameField.setText(toBeUpdatedContact.get_first_name());
                    lastNameField.setText(toBeUpdatedContact.get_last_name());
                    emailField.setText(toBeUpdatedContact.get_email());
                    phoneField.setText(toBeUpdatedContact.get_phone_number());

                    favouriteField.setChecked(toBeUpdatedContact._favourite == "true" ? true : false);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(update){

                    DatabaseHelper db = new DatabaseHelper(view.getContext());
                    String favourite;
                    if(favouriteField.isChecked()){
                        favourite = "true";
                    }else{
                        favourite = "false";
                    }

                    Contact contact = new Contact( toBeUpdatedContact.get_id(),
                            firstNameField.getText().toString(),
                            lastNameField.getText().toString(),
                            emailField.getText().toString(),
                            phoneField.getText().toString(),
                            numbertType.getSelectedItem().toString(),
                            favourite);

                    boolean isInserted = db.updateContact(contact);

                    if(isInserted)
                        Toast.makeText(view.getContext(),"Contact Updated "+favourite, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(view.getContext(),"Contact Not Updated", Toast.LENGTH_SHORT).show();

                    db.close();
                    finish();

                }else{
                    DatabaseHelper db = new DatabaseHelper(view.getContext());
                    String favourite;
                    if(favouriteField.isChecked()){
                        favourite = "true";
                    }else{
                        favourite = "false";
                    }

                    Contact contact = new Contact( contactCount+1,
                            firstNameField.getText().toString(),
                            lastNameField.getText().toString(),
                            emailField.getText().toString(),
                            phoneField.getText().toString(),
                            numbertType.getSelectedItem().toString(),
                            favourite);

                    boolean isInserted = db.addContact(contact);

                    if(isInserted)
                        Toast.makeText(view.getContext(),"Contact Created. "+favourite, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(view.getContext(),"Can not create new contact", Toast.LENGTH_SHORT).show();

                    db.close();
                    finish();
                }
            }
        });

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phone_no_type_menu_items, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        numbertType.setAdapter(adapter);
    }

}
