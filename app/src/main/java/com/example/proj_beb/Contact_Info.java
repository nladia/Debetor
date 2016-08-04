package com.example.proj_beb;/**
 * Created by Влад on 12.07.2016.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Contact_Info extends AppCompatActivity
{
    SQLdb sqldb;
    TextView tvName,tvOwe, tvMoney, tvToday, tvAfter, tvPrim;
    int cursID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);

        tvName = (TextView)findViewById(R.id.tvName);
        tvMoney = (TextView)findViewById(R.id.tvMoney);
        tvToday = (TextView)findViewById(R.id.tvToday);
        tvAfter = (TextView)findViewById(R.id.tvAfter);
        tvPrim = (TextView)findViewById(R.id.tvPrim);
        tvOwe = (TextView)findViewById(R.id.tvOwe);

        cursID = getIntent().getIntExtra("id", 0);


        ReFill(cursID);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);



    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.itmEdit:

                CursDel(cursID);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ReFill(int id)
    {
        sqldb = new SQLdb(this);
        SQLiteDatabase db = sqldb.getWritableDatabase();
        Cursor c = db.query("mytable2", null, null, null, null, null, null);
        c.moveToPosition(id);

        int name = c.getColumnIndex("name");
        int owe = c.getColumnIndex("owe");
        int value = c.getColumnIndex("value");
        int currancy = c.getColumnIndex("currancy");
        int dateOwe = c.getColumnIndex("dateOwe");
        int dateBack = c.getColumnIndex("dateBack");
        int special = c.getColumnIndex("special");

        tvName.setText(c.getString(name));
        tvOwe.setText(c.getString(owe));
        tvMoney.setText(c.getString(value) + c.getString(currancy));
        tvToday.setText(c.getString(dateOwe));
        tvAfter.setText(c.getString(dateBack));
        tvPrim.setText(c.getString(special));



        sqldb.close();
    }

    private void CursDel(int id)
    {
        sqldb = new SQLdb(this);
        SQLiteDatabase db = sqldb.getWritableDatabase();
        Cursor c = db.query("mytable2", null, null, null, null, null, null);
        c.moveToPosition(id);
        int delID = c.getColumnIndex("id");
        String del = c.getString(delID);

        int delCount = db.delete("mytable2", "id = " + del, null);

        sqldb.close();

    }
}