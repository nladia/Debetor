package com.example.proj_beb;/**
 * Created by Влад on 30.06.2016.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class dbShow extends AppCompatActivity implements View.OnClickListener
{

    SQLdb sqldb;
    EditText etName, etMoney, etPrim;
    RadioButton rbMe, rbToMe, rbBYR, rbUSD, rbEUR;
    TextView tvAfter, tvToday;
    Button btnEdit, btnDelete, btnShow;
    RadioGroup rgOwe, rgCurrancy;


    @Override
    public void onClick(View v)
    {
        String name = etName.getText().toString();
        ContentValues cv = new ContentValues();

        SQLiteDatabase db = sqldb.getWritableDatabase();

        switch (v.getId())
        {
            case R.id.btnShow:

                Cursor c = db.query("mytable2", null, null, null, null, null, null);

                int idColIndex = c.getColumnIndex("id");
                int nameColIndex = c.getColumnIndex("name");
                int oweColIndex = c.getColumnIndex("owe");
                int valueColIndex = c.getColumnIndex("value");
                int currancyColIndex = c.getColumnIndex("currancy");
                int dateOweColIndex = c.getColumnIndex("dateOwe");
                int dateBackColIndex = c.getColumnIndex("dateBack");
                int specialBackColIndex = c.getColumnIndex("special");

                if (c.moveToFirst())
                {
                    do
                    {


                    }while (c.moveToNext());

                    // определяем номера столбцов по имени в выборке



                    etName.setText(c.getString(nameColIndex).toString());
                    etMoney.setText(c.getString(valueColIndex));
                    tvToday.setText(c.getString(dateOweColIndex));
                    tvAfter.setText(c.getString(dateBackColIndex));
                    etPrim.setText(c.getString(specialBackColIndex));


                    c.moveToNext();

                    while (c.moveToNext())
                    {
                        c.getInt(idColIndex);
                        c.getString(nameColIndex);
                        c.getString(oweColIndex);
                        c.getString(valueColIndex);
                        c.getString(currancyColIndex);
                        c.getString(dateOweColIndex);
                        c.getString(dateBackColIndex);
                        c.getString(specialBackColIndex);
                    }
                }
                c.close();

                break;


            case R.id.btnEdit:
                if (name.equalsIgnoreCase("")) {
                    break;
                }

                cv.put("name", etName.getText().toString());

                switch (rgOwe.getCheckedRadioButtonId()) {
                    case R.id.rbMe:
                        cv.put("owe", "Мне должны");
                        break;
                    case R.id.rbToMe:
                        cv.put("owe", "Я должен");
                        break;
                }

                cv.put("value", etMoney.getText().toString());

                switch (rgCurrancy.getCheckedRadioButtonId())
                {
                    case R.id.rbBYR:
                        cv.put("currancy", "BYR");
                        break;
                    case R.id.rbUSD:
                        cv.put("currancy", "USD");
                        break;
                    case R.id.rbEUR:
                        cv.put("currancy", "EUR");
                        break;
                }

                cv.put("dateOwe", tvToday.getText().toString());
                cv.put("dateBack", tvAfter.getText().toString());
                cv.put("special", etPrim.getText().toString());
                db.update("mytable2", cv, "id = ?",new String[] { "1" });


                break;
            case R.id.btnDelete:
                if (name.equalsIgnoreCase("")) {
                    break;
                }
                Log.d("LOG_TAG", "deleted rows count = ");
                int i = db.delete("mytable2",  "id = " + "1", null);
                Log.d("LOG_TAG", "deleted rows count = " + i);
                break;
        }
        sqldb.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbshow);

        btnShow = (Button) findViewById(R.id.btnShow);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        btnShow.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        etName = (EditText)findViewById(R.id.etName);
        etMoney = (EditText)findViewById(R.id.etMoney);
        etPrim = (EditText)findViewById(R.id.etPrim);

        tvToday = (TextView)findViewById(R.id.tvAfter);
        tvAfter = (TextView)findViewById(R.id.tvAfter);

        rgOwe = (RadioGroup)findViewById(R.id.rgOwe);
        rgCurrancy = (RadioGroup)findViewById(R.id.rgCurrancy);

        rbMe = (RadioButton)findViewById(R.id.rbMe);
        rbToMe = (RadioButton)findViewById(R.id.rbToMe);
        rbBYR = (RadioButton)findViewById(R.id.rbBYR);
        rbUSD = (RadioButton)findViewById(R.id.rbUSD);
        rbEUR = (RadioButton)findViewById(R.id.rbEUR);

        sqldb = new SQLdb(this);
    }



}