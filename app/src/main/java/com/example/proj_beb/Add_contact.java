package com.example.proj_beb;
/**
 * Created by Влад on 29.06.2016.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class Add_contact extends AppCompatActivity implements View.OnClickListener{


    private static final int DIALOG_DATE_AFTER = 12;
    private static final int DIALOG_EXIT = 4;

    EditText etName, etMoney, etPrim;
    TextView tvToday, tvAfter;
    int myYear, myMonth, myDay;
    SQLdb sqldb;
    RadioGroup rgOwe, rgCurrancy;
    private ListView lv;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        etMoney = (EditText) findViewById(R.id.etMoney);
        etName = (EditText) findViewById(R.id.etName);
        tvToday = (TextView) findViewById(R.id.tvToday);
        tvAfter = (TextView) findViewById(R.id.tvAfter);
        etPrim = (EditText) findViewById(R.id.etPrim);
        lv = (ListView) findViewById(R.id.list_view);

        Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null,null, null);

        if (contacts.getCount() < 0)
        {
            final String products[] = new String[contacts.getCount()];

            StrChg(products, contacts);
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, products);
        } else
        {
            final String products[] = new String[1];
            products[0] = "";
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, products);
        }



        lv.setAdapter(adapter);

        Add_contact.this.adapter.getFilter().filter("saih@dgfakj");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView <? > parent, View view, int position, long id)
            {

                String  itemValue    = (String) lv.getItemAtPosition(position);
                etName.setText(itemValue);
                lv.setVisibility(View.INVISIBLE);

            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

                if ((etName.getText().toString().length() > 0) && (etMoney.getText().toString().length() > 0))
                {
                    SQLadd();
                    Toast.makeText(Add_contact.this, "Создано", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    showDialog(DIALOG_EXIT);
                }

                return false;
            }
        });

        etName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
                if (cs.length() >= 1)
                {
                    lv.setVisibility(View.VISIBLE);
                    Add_contact.this.adapter.getFilter().filter(cs);

                    if (Add_contact.this.adapter.getCount() <= 0)
                        lv.setVisibility(View.INVISIBLE);
                    else;
                }
                else
                {
                    Add_contact.this.adapter.getFilter().filter("saih@dgfa!@kj");
                    lv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    lv.setVisibility(View.INVISIBLE);
                }
            }
        });

        Calendar c = Calendar.getInstance();
        myDay = c.get(Calendar.DAY_OF_MONTH);
        myMonth = c.get(Calendar.MONTH);
        myYear = c.get(Calendar.YEAR);

        rgOwe = (RadioGroup) findViewById(R.id.rgOwe);
        rgCurrancy = (RadioGroup) findViewById(R.id.rgCurrancy);


        tvAfter.setText(myDay + "/" + myMonth + "/" + myYear);
        tvToday.setText(myDay + "/" + myMonth + "/" + myYear);

        tvAfter.setOnClickListener(this);

        sqldb = new SQLdb(this);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public String[] StrChg(String prod[], Cursor contacts) {
        int nameColIndex = contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

        if (contacts.moveToFirst()) {
            int i = 0;
            do {
                if(contacts.getString(nameColIndex).length() > 0)
                    prod[i++] = contacts.getString(nameColIndex).toString();

            } while (contacts.moveToNext());
        }
        return prod;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvAfter:
                showDialog(DIALOG_DATE_AFTER);
                break;
        }
    }

    protected void SQLadd()
    {

            ContentValues cv = new ContentValues();
            SQLiteDatabase db = sqldb.getWritableDatabase();

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
            db.insert("mytable2", null, cv);
            sqldb.close();

    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE_AFTER)
        {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);

            return tpd;
        }
        if (id == DIALOG_EXIT)
        {
            if (id == DIALOG_EXIT) {
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("Ошибка сохранения");
                adb.setMessage("Введены не все данные");
                adb.setIcon(android.R.drawable.ic_dialog_info);
                adb.setPositiveButton("OK", null);
                return adb.create();
            }
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            tvAfter.setText(myDay + "/" + myMonth + "/" + myYear);
        }
    };
}