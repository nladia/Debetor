package com.example.proj_beb;/**
 * Created by Влад on 29.06.2016.
 */

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Pwd_change extends AppCompatActivity implements View.OnClickListener
{

    Button btnOK, btnCancel;
    EditText etPwd;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pwd_change);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        etPwd = (EditText) findViewById(R.id.etPwd);


    }

    @Override
    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.btnOK:
                savePwd();
                finish();
                break;

            case R.id.btnCancel:
                finish();
                break;
        }
    }

    void savePwd()
    {
        sPref = getSharedPreferences("Saved", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Saved", etPwd.getText().toString());
        ed.apply();
    }
}