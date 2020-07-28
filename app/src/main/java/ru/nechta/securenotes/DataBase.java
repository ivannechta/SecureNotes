package ru.nechta.securenotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DataBase {
    private Context context;
    public ArrayList<MessageRecord>Records;
    private SQLiteDatabase db;
    private DBHelper dbHelper;


    //--------------------------
    public DataBase(Context cnt){
        context=cnt;
        Records=new ArrayList<>();
        dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public void ReadDB(){
        Cursor c;
        c= db.rawQuery("Select * from mytable",null); //Выполняем запрос из базы
        Records.clear();

        int ColIndexId,ColIndexCaption,ColIndexMessage;

        if (c.moveToFirst()) { //переходим на первый элемент если он есть
            ColIndexId      = c.getColumnIndex("id");
            ColIndexCaption = c.getColumnIndex("caption");
            ColIndexMessage = c.getColumnIndex("message");
            do{
                Records.add(new MessageRecord(
                                    c.getInt(ColIndexId),
                                    c.getString(ColIndexCaption),
                                    c.getString(ColIndexMessage)));
            }while(c.moveToNext()); //переходим к следующему элементу
        }
    }
    public void AddRecord(int i,String Caption,String Message){
        if (i==-1){ //Is it new record?
            db.execSQL("INSERT INTO mytable ( 'caption','message') VALUES ('"+Caption +"','"+Message+"');");
            Log.v("Ivandows","INSERT INTO mytable ( 'caption','message') VALUES ('"+Caption +"','"+Message+"');");
        }else{
            db.execSQL("UPDATE mytable SET caption='"+Caption+"',message='"+Message+"' where id="+i+";");
            Log.v("Ivandows","UPDATE mytable SET caption='"+Caption+"',message='"+Message+"' where id="+i+";");
        }
    }

    public void ClearDB(){
        db.execSQL("DELETE from mytable;");
    }


}