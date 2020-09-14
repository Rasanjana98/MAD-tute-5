package com.rasanjana.dbhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rasanjana.dbhandling.Database.DBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etUserName,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName=findViewById(R.id.etUserName);
        etPassword=findViewById(R.id.etPassword);
    }

    public void addData(View view){
        DBHelper dbHelper = new DBHelper(this);
        long val =dbHelper.addInfo(etUserName.getText().toString(),etPassword.getText().toString());
        if (val>0){
            Toast.makeText(this, "Data successfully inserted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Data not inserted", Toast.LENGTH_SHORT).show();
        }
    }
    public  void viewAll(View view){
        DBHelper dbHelper=new DBHelper(this);
        List unames = dbHelper.readAllInfo("user");
        Toast.makeText(this, unames.toString(), Toast.LENGTH_SHORT).show();
    }
    public  void deleteData(View view){
        DBHelper dbHelper =new DBHelper(this);
        dbHelper.deleteInfo(etUserName.getText().toString());
        Toast.makeText(this, etUserName.getText().toString()+ "deleted successfully", Toast.LENGTH_SHORT).show();
    }
    public  void  updateData (View view){
        DBHelper dbHelper=new DBHelper(this);
        int val =dbHelper.updateInfo(etUserName.getText().toString(),etPassword.getText().toString());

        if (val>0){
            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data not updated", Toast.LENGTH_SHORT).show();
        }
    }
    public  void signIn(View view){
        DBHelper dbHelper=new DBHelper(this);
        List usernames= dbHelper.readAllInfo("user");
        List passwords= dbHelper.readAllInfo("password");

        String user = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if (usernames.indexOf(user)>0){
            if (passwords.get(usernames.indexOf(user)).equals(password)){
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}