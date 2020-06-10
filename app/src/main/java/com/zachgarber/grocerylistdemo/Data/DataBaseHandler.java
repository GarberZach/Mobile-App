package com.zachgarber.grocerylistdemo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import com.zachgarber.grocerylistdemo.ListActivity;
import com.zachgarber.grocerylistdemo.Model.Grocery;
import com.zachgarber.grocerylistdemo.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    private Context ctx;

//Constructor
    public DataBaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE= "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_GROCERY_ITEM +" TEXT,"
                +Constants.KEY_QTY_NUMBER + " TEXT,"
                +Constants.KEY_DATE_ADDED + " LONG);";

        db.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }

    //add grocery
    public void addGrocery (Grocery grocery){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(Constants.KEY_GROCERY_ITEM, grocery.getGroceryItem());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_ADDED, java.lang.System.currentTimeMillis());

        //inserts row into table
        db.insert(Constants.TABLE_NAME,null, values);

    }

    //get a grocery
    public Grocery getGrocery(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
        Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER,Constants.KEY_DATE_ADDED},
        Constants.KEY_ID+ "=?", new String[]{String.valueOf(id)}, null, null,null,null);

        if (cursor !=null)
            cursor.moveToFirst();
            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            grocery.setGroceryItem(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

            //convert timestamp to something readable
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)))
                    .getTime());

            grocery.setDateAdded(formattedDate);

            return grocery;
        }

    //get all groceries
    public List<Grocery> getAllGroceries(){
        SQLiteDatabase db=this.getWritableDatabase();

        List<Grocery> groceryList=new ArrayList<>();

        Cursor cursor =db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
        Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER,Constants.KEY_DATE_ADDED
        }, null, null,null, null,
                Constants.KEY_DATE_ADDED+ " DESC");

        if (cursor.moveToFirst()){
            do {
                Grocery grocery =new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setGroceryItem(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)))
                        .getTime());

                grocery.setDateAdded(formattedDate);

                groceryList.add(grocery);

            }while (cursor.moveToNext());
        }


        return groceryList;

    }
    //Update Grocery
    public int updateGrocery(Grocery grocery){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Constants.KEY_GROCERY_ITEM, grocery.getGroceryItem());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_ADDED, java.lang.System.currentTimeMillis());

//RETURN UPDATED ROW
        return db.update(Constants.TABLE_NAME,values, Constants.KEY_ID +"=?",
                new String[]{ String.valueOf(grocery.getId())});

    }

    //delete Grocery
    public void deleteGrocery(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();


    }
    //get count of database
    public int getGroceriesCount(){
        String countQuery= "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor=db.rawQuery(countQuery, null);

        return cursor.getCount();

    }
}
