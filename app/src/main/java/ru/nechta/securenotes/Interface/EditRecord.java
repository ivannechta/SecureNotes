package ru.nechta.securenotes.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import ru.nechta.securenotes.R;

public class EditRecord extends AppCompatActivity {
    private int id;
    //private int ico;
    private EditText EditCaption;
    private EditText EditMessage;
    private final int []Ico={R.drawable.bell,R.drawable.info,R.drawable.error};
    int CurrentIco=0;
    public ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        EditCaption=findViewById(R.id.EditCaption);
        EditMessage=findViewById(R.id.EditMessage);

        img=findViewById(R.id.imageView);


        Intent intent= getIntent();
        id=intent.getIntExtra("id",-1);
        CurrentIco=intent.getIntExtra("ico",0);
        if (id!=-1) {
            EditCaption.setText(intent.getStringExtra("caption"));
            EditMessage.setText(intent.getStringExtra("message"));
            img.setImageResource(Ico[CurrentIco]);
        }
    }

    public void SaveRecord(View v){
        String caption=EditCaption.getText().toString();
        String message=EditMessage.getText().toString();

        Intent i=new Intent();
        i.putExtra("id",id);
        i.putExtra("ico",CurrentIco);
        i.putExtra("caption",caption);
        i.putExtra("message",message);

        setResult(RESULT_OK, i);
        finish();
    }

    public void CancelRecord(View v){
        setResult(RESULT_CANCELED, null);
        finish();
    }

    public void ClickIco(View v){
        CurrentIco=(CurrentIco==2)?0:CurrentIco+1;
        img.setImageResource(Ico[CurrentIco]);
    }

}
