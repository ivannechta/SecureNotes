package ru.nechta.securenotes.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import javax.xml.transform.Result;

import ru.nechta.securenotes.DataBase;
import ru.nechta.securenotes.R;

public class MainActivity extends Activity  {
    ListView lst;
    public DataBase DB;
    public final int EditActivity=1;
    //public final int DeleteActivity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DB=new DataBase(this);
        //DB.ClearDB();
        //DB.AddRecord(-1,"First","11111");
        //DB.AddRecord(-1,"Second","22222");
        DB.ReadDB();
        lst=findViewById(R.id.list);
        UpdateListView();
    }

    public void UpdateListView() {
        lst.setAdapter(new BoxAdapter(this, DB.Records));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        if (resultCode==RESULT_CANCELED){return;}
        if (requestCode==EditActivity){
            int id          =data.getIntExtra("id",-1);
            String caption  =data.getStringExtra("caption");
            String message  =data.getStringExtra("message");

            DB.AddRecord(id,caption,message);
            DB.ReadDB();
            UpdateListView();
        }
    }

    public void CreateButton(View view) {
        Intent i=new Intent(this,EditRecord.class);
        startActivityForResult(i,EditActivity);
    }

    public void DeleteButton(View view) {
    }
}
