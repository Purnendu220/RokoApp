package com.rokoapp.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.rokoapp.adapter.ContactAdapter;
import com.rokoapp.model.ContactList;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RokoDbAdapter {

    private HomeDbHelper homeDbHelper;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "roko_db";
    private static final String CONTACT_NEW = "contact_new";

    private static final String ID = "id";
    private static final String  USER_NAME = "user";
    private static final String  PHONE_NUMBER = "mobile";

    public RokoDbAdapter(Context context) {
        homeDbHelper = new HomeDbHelper(context);
    }


    public long insertNewHomeData(List<ContactList> favItems) {
        SQLiteDatabase db = homeDbHelper.getWritableDatabase();
//        db.execSQL("delete from "+ CONTACT_NEW);
        ContentValues contentValues = new ContentValues();
        long value = (long) -1;
        for (ContactList catModel: favItems) {
            contentValues.put(USER_NAME, catModel.getContactName());
            contentValues.put(PHONE_NUMBER, catModel.getContactNumber());


            String uId = catModel.getContactNumber();
            String [] column = {PHONE_NUMBER};
            Cursor cursor = db.query(CONTACT_NEW, column, PHONE_NUMBER+" = '"+uId+"'", null, null, null, null);
//            String Query = "Select * from " + CONTACT_NEW + " where " + PHONE_NUMBER + " = " + catModel.getContactNumber();
//            Cursor cursor = db.rawQuery(Query, null);


            /*if(cursor.getCount() <= 0){
                cursor.close();
                value = db.insert(CONTACT_NEW, null, contentValues);
            }else {
                cursor.close();
                value = Long.valueOf(db.update(CONTACT_NEW, contentValues, PHONE_NUMBER+" = '"+catModel.getContactNumber()+"'", null));
            }
        }
        return value;*/

            if(cursor.getCount() <= 0){
                cursor.close();
//                return false;
                value = db.insert(CONTACT_NEW, null, contentValues);
            }else {
                cursor.close();
                value = (db.update(CONTACT_NEW, contentValues, PHONE_NUMBER+" = '"+catModel.getContactNumber()+"'", null));
            }


           /* String uId = chatModel.getId();
            String dateId = chatModel.getDateText();
            String [] column = {ParamDb.ID};
            Cursor cursor = db.query(HomeDbAdapter.CHAT_MAIN_TAB, column, ParamDb.ID+" = '"+uId+"'", null, null, null, null);
            Log.d(TAG, "insertDataInChatMainTab: ===================================== "+cursor.getCount()+" and date is: "+dateId);
            if(cursor.getCount()>0){

                Log.d(TAG, "insertDataInChatMainTab: ===================================== DATE: "+dateId);
//                value = db.update(HomeDbAdapter.CHAT_MAIN_TAB, contentValues, ParamDb.ID+" = '"+chatModel.getId()+"'", null);
            }else {
                value = db.insert(HomeDbAdapter.CHAT_MAIN_TAB, null, contentValues);
            }
            cursor.close();*/
        }
        return value;
    }

    public boolean deleteContact(String contactNumber) {
        SQLiteDatabase db = homeDbHelper.getWritableDatabase();
         boolean data = db.delete(CONTACT_NEW, PHONE_NUMBER +" = '"+contactNumber+"'", null) > 0;
         return data;
    }

    public void readLocalNewHomeDataOne(ContactAdapter adapter, List<ContactList> favItems, LinearLayout layoutAdd){
        SQLiteDatabase db = homeDbHelper.getWritableDatabase();
        String[] column = {ID, USER_NAME, PHONE_NUMBER};
        Cursor cursor = db.query(CONTACT_NEW, column, null, null, null, null, null);
        try {
            while (cursor.moveToNext()){

                int index2 = cursor.getColumnIndex(USER_NAME);
                int index3 = cursor.getColumnIndex(PHONE_NUMBER);


                String userName = cursor.getString(index2);
                String phoneNumber = cursor.getString(index3);


                favItems.add(new ContactList(userName, phoneNumber));
            }
            cursor.close();
//            adapter.notifyDataSetChanged();
        }finally {
            if (favItems.size()>=5){
                layoutAdd.setVisibility(View.GONE);
            }else
                layoutAdd.setVisibility(View.VISIBLE);
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
    }




    class HomeDbHelper extends SQLiteOpenHelper {
        Context context;

        private static final String CREATE_NEW_HOME_TABLE = "create table "+CONTACT_NEW +"("+ ID+"  INTEGER primary key autoincrement,"+ USER_NAME+" text,"+ PHONE_NUMBER+" text"+");";
        private static final String DROP_NEW_HOME_TABLE = "DROP TABLE IF EXISTS "+CONTACT_NEW;



        private HomeDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
//            Message.message(context, "Constructor called.");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.d(TAG, "onCreate: onCreateCalled.");
//                Message.message(context, "onCreate: onCreateCalled.");
                db.execSQL(CREATE_NEW_HOME_TABLE);

            } catch (SQLException e) {
//                Message.message(context, "onCreate: error=== " + e);
                Log.d(TAG, "onCreate: error Occurred === " + e);
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Log.d(TAG, "onUpgrade: " + "onUpgradeCalled.");
//                Message.message(context, "onUpgradeCalled.");

                db.execSQL(DROP_NEW_HOME_TABLE);


                onCreate(db);
            } catch (SQLException e) {
//                Message.message(context,"onUpgrade: error=== " + e);
                Log.d(TAG, "onUpgrade: error Occurred === " + e);
                e.printStackTrace();
            }
        }
    }
}
