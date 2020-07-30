package ru.nechta.securenotes.Interface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Toast;

import ru.nechta.securenotes.Cypher;
import ru.nechta.securenotes.Database.DataBase;
import ru.nechta.securenotes.R;

public class MainActivity extends Activity  {
    ListView lst;
    public DataBase DB;
    public final int EditActivity=1;
    private String hash;

    public final int DeleteStateBegin=1;
    public final int DeleteStateStop=2;
    public int DeleteState=DeleteStateStop;
    public static Cypher AES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AES=new Cypher();
        PasswordRequest();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences P = getPreferences(0);
        SharedPreferences.Editor editor = P.edit();
        editor.putString("Hash", hash);
        editor.apply();//.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences P = getPreferences(0);
        hash =P.getString("Hash", "");
    }


    public void EditRecord(int id){
        Intent i=new Intent(this,EditRecord.class);
        i.putExtra("id",        DB.Records.get(id).id);
        i.putExtra("ico",       DB.Records.get(id).Icon);
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
            int ico         =data.getIntExtra("ico",0);
            String caption  =data.getStringExtra("caption");
            String message  =data.getStringExtra("message");

            DB.AddRecord(id,ico,caption,message);
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

    public void PasswordRequest(){

        final EditText Password1,NewPassword1;
        final Context cnt=this;

        final View view =getLayoutInflater().inflate(R.layout.password, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Security check");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Enter password");

        Password1=view.findViewById(R.id.password);
        NewPassword1=view.findViewById(R.id.newpassword);


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String Password,NewPassword;
                Password=Password1.getText().toString();
                NewPassword=NewPassword1.getText().toString();

                if (hash.equals("")){
                    if (Password.equals("")){
                        if(NewPassword.equals("")){
                            Show(Password);
                        }else{
                            SetNewPassword(Password,NewPassword);
                            Show(NewPassword);
                        }
                    }else{
                        Toast.makeText(cnt,"Current password is wrong",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    if (AES.md5(Password).equals(hash)){
                        if(NewPassword.equals("")){
                            Show(Password);
                        }else{
                            SetNewPassword(Password,NewPassword);
                            Show(NewPassword);
                        }
                    }else{
                        Toast.makeText(cnt,"Current password is wrong",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }

    public void Show(String password){

        AES.secret=password;

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
        lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (DeleteState==DeleteStateStop) {
                    EditRecord(position);
                    DB.ReadDB();
                    UpdateListView();
                }
                return false;
            }
        });
        UpdateListView();
    }

    public void SetNewPassword(String OldPassword,String password){
        AES.secret=OldPassword;
        DB=new DataBase(this);
        DB.ReadDB();
        AES.secret=password;
        DB.Clear();
        DB.SaveDB();
        hash=AES.md5(password);
    }

}
