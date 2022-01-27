package com.example.metodo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyAdapter {
  //TODO:write database name
  public static final String DATABASE_NAME ="METODO";
  //TODO: write table name
  public static final String TABLE_NAME ="metodotable";
  //TODO: write db version name
  public static final int DB_VERSION= 1;
  //TODO: write column names
  public static final String COL_ROW ="SerialNo";
//  public static final String  COL_PHOTO="photo";
  public static final String COL_TITLE="title";
  public static final String COL_CONTENT ="content";
//  public static final String COL_DATE="date";//***


//"CREATE TABLE trainee(col1 dataType, col1 dataType, col1 dataType, col1 dataType, col1 dataType)"

  String createDB ="CREATE TABLE "+TABLE_NAME+" ("+COL_ROW+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_TITLE+" text, "+COL_CONTENT+" text "+")";/* "+COL_DATE+"text *** */

  private MyDbOpenHelper myDbOpenHelper;
  private SQLiteDatabase database;

  public MyAdapter(Context context) {
    myDbOpenHelper = new MyDbOpenHelper(context);
  }
  public MyAdapter openDataBase(){
    database = myDbOpenHelper.getWritableDatabase();
    return this;
  }
  //TODO:to upgrade record
public void updateRecord(Context context,String rowId ,String title,String content){/*,String date *** */
   String localmsgg;
    ContentValues contentValues = new ContentValues();
    contentValues.put(COL_TITLE,title);
    contentValues.put(COL_CONTENT,content);
//    contentValues.put(COL_DATE,date); //***
 int upgradeRecord =database.update(TABLE_NAME,contentValues,COL_ROW+"="+rowId,null);
      if (upgradeRecord==-1){
        localmsgg="Update failed";
      }else {
       localmsgg="Updated Successfully";
      }
  Toast.makeText(context, localmsgg, Toast.LENGTH_SHORT).show();
}
//TODO:to insert record

  public void insertRecord(Context context,String title,String content) {/*,String date *** */
    ContentValues contentValues = new ContentValues();
    String localmsg;
    contentValues.put(COL_TITLE,title);
    contentValues.put(COL_CONTENT,content);
//    contentValues.put(COL_DATE,date);//***
    long insertionValue = database.insert(TABLE_NAME,null,contentValues);
    if (insertionValue==-1){
     localmsg ="Insertion failed";
    }else {
      localmsg="inserted Successfully";
    }
    Toast.makeText(context, localmsg, Toast.LENGTH_SHORT).show();
  }
  //TODO:to delete one record
  public void deleteRecord(String colRow,Context context){

    int id = database.delete(TABLE_NAME, COL_ROW+" = "+colRow, null);
    if (id>0){
      Toast.makeText(context, id+"deleted", Toast.LENGTH_SHORT).show();
    }else {
      Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
    }
  }
  //TODO: to delete all record
  public void deleteAllRecords(Context context){
    int id =database.delete(TABLE_NAME,null,null);
     if (id>0){
       Toast.makeText(context, id+"Records deleted", Toast.LENGTH_SHORT).show();
     }else {
       Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show();
     }
  }



//TODO:to set cursor all records
  public Cursor getAllRecords(){
    String[] COLUMNS = {COL_ROW, COL_TITLE, COL_CONTENT};/*,COL_DATE *** */
    return database.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
  }
  public class MyDbOpenHelper extends SQLiteOpenHelper {
    public MyDbOpenHelper( Context context) {
      super(context, MyAdapter.DATABASE_NAME, null, MyAdapter.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
           sqLiteDatabase.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion) {
//sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
//     onCreate(sqLiteDatabase);
    }
  }
}

