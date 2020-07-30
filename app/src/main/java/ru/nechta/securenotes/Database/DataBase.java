package ru.nechta.securenotes.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static ru.nechta.securenotes.Interface.MainActivity.AES;

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
        c= db.rawQuery("Select * from mytable",null);
        Records.clear();

        int ColIndexId,ColIndexIco,ColIndexCaption,ColIndexMessage;

        if (c.moveToFirst()) { //переходим на первый элемент если он есть
            ColIndexId      = c.getColumnIndex("id");
            ColIndexIco     = c.getColumnIndex("ico");
            ColIndexCaption = c.getColumnIndex("caption");
            ColIndexMessage = c.getColumnIndex("message");
            do{
                Records.add(new MessageRecord(
                                    c.getInt(ColIndexId),
                                    c.getInt(ColIndexIco),
                                    AES.decrypt( c.getString(ColIndexCaption)),
                                    AES.decrypt(c.getString(ColIndexMessage))));
            }while(c.moveToNext()); //переходим к следующему элементу
        }
    }

    public void AddRecord(int i,int ico,String Caption,String Message){
        if (i==-1){ //Is it new record?
            db.execSQL("INSERT INTO mytable ( 'ico','caption','message') VALUES ('"+ico+"','"+AES.encrypt( Caption) +"','"+AES.encrypt(Message)+"');");
        }else{
            db.execSQL("UPDATE mytable SET  ico='"+ico+"',caption='"+AES.encrypt( Caption)+"',message='"+AES.encrypt(Message)+"' where id="+i+";");
        }
    }

    public void DeleteRecord(int i){
        db.execSQL("DELETE from mytable where id='"+i+"';");
    }

    public void Clear(){
        db.execSQL("DELETE from mytable;");
    }

    public void SaveDB(){
        for (MessageRecord m:Records) {
            AddRecord(-1,m.Icon,m.Caption,m.Message);
        }
    }





}
