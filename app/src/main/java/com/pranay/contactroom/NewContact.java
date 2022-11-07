package com.pranay.contactroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.pranay.contactroom.databinding.ActivityNewContactBinding;
import com.pranay.contactroom.model.Contact;
import com.pranay.contactroom.model.ContactViewModel;

public class NewContact extends AppCompatActivity {

    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION = "occupation";
    private ActivityNewContactBinding binding;
    private int contactId = 0;
    private Boolean isEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        ContactViewModel contactViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this
                .getApplication())
                .create(ContactViewModel.class);
        binding = DataBindingUtil.setContentView(NewContact.this,R.layout.activity_new_contact);


        if(getIntent().hasExtra(MainActivity.CONTACT_ID)){
            contactId = getIntent().getIntExtra(MainActivity.CONTACT_ID,0);
            contactViewModel.get(contactId).observe(this, contact ->{
                if(contact!=null){
                    binding.enterName.setText(contact.getName());
                    binding.enterOccupation.setText(contact.getOccupation());
                }

            });
            isEdit = true;
        }

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
        //Delete Button
        binding.deleteButton.setOnClickListener(view -> edit(true));

        //Update Button
        binding.updateButton.setOnClickListener(view -> edit(false));
        if(isEdit){
            binding.saveButton.setVisibility(View.GONE);
        }else {
            binding.updateButton.setVisibility(View.GONE);
            binding.deleteButton.setVisibility(View.GONE);
        }



    }

    private void edit(Boolean isDelete) {
        String name = binding.enterName.getText().toString().trim();
        String occupation = binding.enterOccupation.getText().toString().trim();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)){
            Snackbar.make(binding.enterName,R.string.empty,Snackbar.LENGTH_SHORT)
                    .show();
        }else{
            Contact contact = new Contact();
            contact.setId(contactId);
            contact.setName(name);
            contact.setOccupation(occupation);
            if(isDelete){ ContactViewModel.delete(contact);
            } else{ ContactViewModel.update(contact);
            }
            finish();
        }
    }
}