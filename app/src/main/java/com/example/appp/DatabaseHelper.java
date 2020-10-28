package com.example.appp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final int Version = 1;  // key안의 array 이름 느낌.

    private static final String DB_NAME = "dd";   // key 같은 느낌.
    private static final String TB_NAME = "tt";  // key안의 array 이름 느낌.

    private static final String ID = "id";  // key안의 array 이름 느낌.
    private static final String TITLE = "t";  // key안의 array 이름 느낌.
    private static final String DATE = "d";  // key안의 array 이름 느낌.


    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {   ///데이터베이스 만들기



        //테이블을 만들어 준다.
        String createTable = " CREATE TABLE " + TB_NAME +   //테이블 이름.
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT ," +
                DATE + " TEXT);";  ///// id:INTEGER == 인덱스 , txt Text 하나(value)넣는다.
        sqLiteDatabase.execSQL(createTable);

        Toast.makeText(context, "테이블 생성", Toast.LENGTH_SHORT).show();
        //명령

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //기존의 테이블을 가져온다.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(sqLiteDatabase);
        Toast.makeText(context, "테이블 가져옴", Toast.LENGTH_SHORT).show();
        //명령
    }

    void addText(String title, String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(); // Sql을 쓸 수 있게 한다 .

        // value를 만든다.
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(DATE, date);


        //데이터 베이스로 추가 .
        long result = sqLiteDatabase.insert(TB_NAME, null, contentValues);
        //명령
        if (result == -1) {
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();

        }


    }


    Cursor readdate() {
        String query = " SELECT * FROM " + TB_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null ;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }


        return cursor ;
    }

    void updatedata (String row_id , String updatetitle , String updatedate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE,updatetitle);
        cv.put(DATE,updatedate);

        long result = db.update(TB_NAME, cv , "id=?", new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();

        }

    }

    void delete (String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TB_NAME , "id=?", new String[] {row_id});

        if (result == -1) {
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();

        }
    }

    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
         //db.execSQL("drop table " + TB_NAME);
           db.execSQL("Delete from " +TB_NAME);
    }

}
//
//    public ArrayList getAllText(){
//
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        ArrayList<String> arrayList = new ArrayList<String>();
//
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from " +TABLE_NAME,null);
//        cursor.moveToFirst();
//
//        while(!cursor.isAfterLast()){
//            arrayList.add((cursor.getString(cursor.getColumnIndex("txt"))));
//            cursor.moveToNext();
//
//        }
//        return arrayList;
//    }
//}
