package com.pranay.contactroom.Util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pranay.contactroom.Data.ContactDao;
import com.pranay.contactroom.model.Contact;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class},version =1,exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();
    public static final int NUMBER_OF_THREADS = 4;

    private static volatile ContactRoomDatabase INSTANCE;

    //Executor service helps us to run things in the back thread

    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ContactRoomDatabase getDataBase(final Context context){
        if(INSTANCE==null) {
            synchronized (ContactRoomDatabase.class){
                if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ContactRoomDatabase.class,"contact_database")
                        .addCallback(sRoomDatabaseCallback)
                        .build();
                }
            }
        }
        return  INSTANCE;
    }
    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        ContactDao contactDao = INSTANCE.contactDao();;
                        contactDao.deleteAll();
                        Contact contact = new Contact("pranay saini","Student");
                        contactDao.insert(contact);
                        contact = new Contact("Bittu saini","Student");
                        contactDao.insert(contact);
                        contact = new Contact("patrick bateman","Sigma");
                        contactDao.insert(contact);

                    });

                }
            };
}
