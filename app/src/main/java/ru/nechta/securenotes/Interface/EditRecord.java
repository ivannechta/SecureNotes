package ru.nechta.securenotes.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import ru.nechta.securenotes.R;

public class EditRecord extends AppCompatActivity {
    private int id;
    private EditText EditCaption;
    private EditText EditMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        EditCaption=findViewById(R.id.EditCaption);
        EditMessage=findViewById(R.id.EditMessage);

        Intent intent= getIntent();
        id=intent.getIntExtra("id",-1);
        if (id!=-1) {
            EditCaption.setText(intent.getStringExtra("caption"));
            EditMessage.setText(intent.getStringExtra("message"));
        }
    }

    public void SaveRecord(View v){


        String caption=EditCaption.getText().toString();
        String message=EditMessage.getText().toString();

        Intent i=new Intent();
        i.putExtra("id",id);
        i.putExtra("caption",caption);
        i.putExtra("message",message);

        setResult(RESULT_OK, i);
        finish();
    }

    public void CancelRecord(View v){
        setResult(RESULT_CANCELED, null);
        finish();
    }

}
