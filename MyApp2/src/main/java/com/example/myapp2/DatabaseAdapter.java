package com.example.myapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseAdapter {
    DatabaseHelper helper;
    Context context;

    public DatabaseAdapter(Context context){
        helper = new DatabaseHelper(context);
        this.context = context;
    }


    /**
     * Takes parameters internal storage(is)
     * and external storage(es) and inserts them in the database
     * @param is
     * @param es
     */
    public void insertData(long is,long es){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");// HH:mm:ss
        Date date = new Date();
        if(!lastDayExists(dateFormat.format(date).toString())){
            SQLiteDatabase db=helper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(DatabaseHelper.DATE,dateFormat.format(date));
            values.put(DatabaseHelper.IS,is);
            values.put(DatabaseHelper.ES,es);
            long id=db.insert(DatabaseHelper.TABLE_NAME,null,values);
            db.close();
        }
    }


    /**
     * Returns all the data from the database
     * in order to be displayed
     * @return
     */
    public String getAllData(){
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {DatabaseHelper.UID, DatabaseHelper.DATE, DatabaseHelper.IS, DatabaseHelper.ES};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME,columns,null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            int cid=cursor.getInt(0);
            String date=cursor.getString(1);
            int is=cursor.getInt(2);
            int es=cursor.getInt(3);
            buffer.append(cid+"  "+date+"  "+is+"MB  "+es+"MB\n");
        }
        db.close();
        return buffer.toString();
    }


    /**
     * Checks if the database has data
     * and if the last entry is the current date
     * @param currentDate
     * @return
     */
    public boolean lastDayExists(String currentDate){
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns={DatabaseHelper.UID, DatabaseHelper.DATE, DatabaseHelper.IS, DatabaseHelper.ES};
        Cursor cursor=db.query(DatabaseHelper.TABLE_NAME,columns,null, null, null, null, null);
        cursor.moveToLast();
        db.close();
        String lastEntry ="null";
        if(cursor.getCount()>0){
            lastEntry = cursor.getString(1);
        }
        if(lastEntry.equals("null")){
            return false;
        }else if(lastEntry.equals(currentDate)){
            return true;
        }else{
            return false;
        }
    }


    /**
     * This class creates the database
     */
    static class DatabaseHelper extends SQLiteOpenHelper{
        private static final String DATABASE_NAME="mydatabase";
        private static final String TABLE_NAME="MYTABLE";
        private static final int DATABASE_VERSION=1;
        private static final String UID="_id";
        private static final String IS="InternalStorage";
        private static final String ES="ExternalStorage";
        private static final String DATE="Date";
        private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE+" VARCHAR(255), "+IS+" INTEGER, "+ES+" INTEGER);";
        private Context context;

        public DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context=context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
