package com.pranay.contactroom.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.pranay.contactroom.Util.ContactRoomDatabase;
import com.pranay.contactroom.model.Contact;

import java.util.List;

public class ContactRepository {

    private final ContactDao contactDao;
    private final LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDataBase(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContact();




    }
    public LiveData<List<Contact>> getAllData(){return  allContacts;}
    public void insert(Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.insert(contact);
        });
    }

    public LiveData<Contact> get(int id) {
        return  contactDao.get(id);
    }
    public void update(Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.update(contact));
    }
    public void delete(Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.delete(contact));

    }

}
