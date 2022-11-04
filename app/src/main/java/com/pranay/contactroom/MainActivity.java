package com.pranay.contactroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pranay.contactroom.databinding.ActivityMainBinding;
import com.pranay.contactroom.model.Contact;
import com.pranay.contactroom.model.ContactViewModel;

public class MainActivity extends AppCompatActivity {
    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private ActivityMainBinding binding;


    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(MainActivity.this, contacts -> {
            StringBuilder builder = new StringBuilder();
            for (Contact contact : contacts) {

                builder.append(" - ").append(contact.getName()).append(" Occupation: ").append(contact.getOccupation());
                Log.d("TAG", "onCreate: " + contact.getName());
            }


        });

        FloatingActionButton fab = findViewById(R.id.add_contact_fab);

        fab.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, NewContact.class);
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("TAG", "onActivityResult: " + data.getStringExtra(NewContact.NAME_REPLY));
        }
    }
}
