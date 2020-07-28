package ru.nechta.securenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB=new DataBase(this);
        //DB.ClearDB();
        //DB.AddRecord(-1,"First","11111");
        //DB.AddRecord(-1,"Second","22222");
        DB.ReadDB();
        ListView l=findViewById(R.id.list);
        l.setAdapter(new BoxAdapter(this,DB.Records));
    }
}
