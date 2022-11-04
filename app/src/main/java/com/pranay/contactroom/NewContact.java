package com.pranay.contactroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.pranay.contactroom.databinding.ActivityNewContactBinding;
import com.pranay.contactroom.model.ContactViewModel;

public class NewContact extends AppCompatActivity {

    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION = "occupation";
    private ActivityNewContactBinding binding;
    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this
                .getApplication())
                .create(ContactViewModel.class);
        binding = DataBindingUtil.setContentView(NewContact.this,R.layout.activity_new_contact);


        binding.saveButton.setOnClickListener(view ->{
            Intent replyIntent = new Intent();
            if(!TextUtils.isEmpty(binding.enterName.getText()) && !TextUtils.isEmpty(binding.enterOccupation.getText())){
                String name = binding.enterName.getText().toString();
                String occuation = binding.enterOccupation.getText().toString();

                replyIntent.putExtra(NAME_REPLY,name);
                replyIntent.putExtra(OCCUPATION,occuation);
                setResult(RESULT_OK,replyIntent);
//                ContactViewModel.insert(contact);
            }else {
                setResult(RESULT_CANCELED,replyIntent);
            }
            finish();

        });



    }
}