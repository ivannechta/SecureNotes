package ru.nechta.securenotes.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import javax.xml.transform.Result;

import ru.nechta.securenotes.DataBase;
import ru.nechta.securenotes.R;

public class MainActivity extends Activity  {
    ListView lst;
    public DataBase DB;
    public final int EditActivity=1;

    public final int DeleteStateBegin=1;
    public final int DeleteStateStop=2;
    public int DeleteState=DeleteStateStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB=new DataBase(this);
        DB.ReadDB();
        lst=findViewById(R.id.list);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (DeleteState==DeleteStateBegin){
                    DB.DeleteRecord(DB.Records.get(position).id);
                    DB.ReadDB();
                    UpdateListView();
                }
            }
        });
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditRecord(position);
                DB.ReadDB();
                UpdateListView();
            }
        });

        UpdateListView();
    }
    public void EditRecord(int id){
        Intent i=new Intent(this,EditRecord.class);
        i.putExtra("id",        DB.Records.get(id).id);
        i.putExtra("caption",   DB.Records.get(id).Caption);
        i.putExtra("message",   DB.Records.get(id).Message);
        startActivityForResult(i,EditActivity);
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
        Button b=findViewById(R.id.DeleteButton);

        if (DeleteState==DeleteStateStop){
            DeleteState=DeleteStateBegin;
            b.setText("Закончить удаление");
        }else{
            DeleteState=DeleteStateStop;
            b.setText("Удаление");
        }
    }
}
