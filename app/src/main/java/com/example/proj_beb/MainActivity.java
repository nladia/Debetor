package com.example.proj_beb;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{


    TextView tvBalance;
    FrameLayout flActive;
    SQLdb sqldb;
    LinearLayout llMain, llDeb;
    ScrollView scrlMain, scrlDeb;
    Button btnInfo;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0,0,0,"Сменить пароль");
        menu.add(1,1,1,"Очистить базу");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 0:
                Intent intent;
                intent = new Intent(this, Pwd_change.class);
                startActivity(intent);
                break;
            case 1:
                SQLiteDatabase db = sqldb.getWritableDatabase();
                db.delete("mytable2", null, null);
                sqldb.close();
                Toast.makeText(this, "Вы очищенны", Toast.LENGTH_SHORT).show();
                PlagRenew();
                break;
            case R.id.itmEdit:
                Intent intent1;
                intent1 = new Intent(this, Add_contact.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvBalance.setText("Total:");
        llMain = (LinearLayout)findViewById(R.id.llMain);
        scrlMain = (ScrollView)findViewById(R.id.scrlMain);
        llDeb = (LinearLayout)findViewById(R.id.llDeb);
        scrlDeb = (ScrollView)findViewById(R.id.scrlDeb);
        btnInfo = (Button)findViewById(R.id.btnInfo);

        btnInfo.setOnClickListener(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);


        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;
        flActive = (FrameLayout) findViewById(android.R.id.tabcontent);

        tabSpec = tabHost.newTabSpec("deb");
        tabSpec.setIndicator("Я должен");
        tabSpec.setContent(R.id.tvTab1);


        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("kred");
        tabSpec.setIndicator("Мне должны");
        tabSpec.setContent(R.id.tvTab2);

        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("deb");

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            public void onTabChanged(String tabId)
            {
                if (tabId == "deb")
                {
                    scrlMain.setVisibility(View.INVISIBLE);
                    scrlDeb.setVisibility(View.VISIBLE);


                }
                else
                {
                    scrlDeb.setVisibility(View.INVISIBLE);
                    scrlMain.setVisibility(View.VISIBLE);

                }
            }
        });


        scrlMain.setVisibility(View.INVISIBLE);
        scrlDeb.setVisibility(View.VISIBLE);

        PlagCreate();



    }

    private LinearLayout PlagFill(String name, String summ, String currancy, String owe, final int plagID)
    {

        LinearLayout llNew = new LinearLayout(this,null);
        llNew.setOrientation(LinearLayout.VERTICAL);

        final Button btnNew = new Button(this);
        final TextView tvNew = new TextView(this);
        final TextView tvNew2 = new TextView(this);


        btnNew.setText("Full Info");
        tvNew.setText(name);
        tvNew.setTextSize(25);
        tvNew2.setText(owe + " " + summ + " " + currancy);
        tvNew2.setTextSize(20);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intentinfo = new Intent(MainActivity.this, Info.class);
                intentinfo.putExtra("id", plagID);
                startActivity(intentinfo);
            }
        });

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);

        lParams.gravity = Gravity.START;
        lParams.setMargins(15,5,5,20);

        llNew.addView(tvNew, lParams);
        llNew.addView(tvNew2, lParams);
        llNew.addView(btnNew, lParams);
        llNew.setBackgroundColor(Color.LTGRAY);


        return llNew;

    }

    private void PlagCreate()
    {
        sqldb = new SQLdb(this);
        float totalEUR = 0, totalBYR = 0, totalUSD = 0;

        SQLiteDatabase db = sqldb.getWritableDatabase();
        Cursor c = db.query("mytable2", null, null, null, null, null, null);


        if (c.moveToFirst())
        {

            // определяем номера столбцов по имени в выборке
            int id = c.getColumnIndex("id");
            int name = c.getColumnIndex("name");
            int owe = c.getColumnIndex("owe");
            int value = c.getColumnIndex("value");
            int currancy = c.getColumnIndex("currancy");
            int dateOwe = c.getColumnIndex("dateOwe");
            int dateBack = c.getColumnIndex("dateBack");
            int specialBack = c.getColumnIndex("special");
            int plagID=0;



            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(matchParent, wrapContent);
            lParams.setMargins(5,5,5,10);

            lParams.gravity = Gravity.START;

            do
            {
                switch (c.getString(currancy))
                {
                    case "BYR":
                        if (c.getString(owe).equals("Мне должны"))
                            totalBYR += Float.parseFloat(c.getString(value));
                        if (c.getString(owe).equals("Я должен"))
                            totalBYR -= Float.parseFloat(c.getString(value));
                        break;
                    case "USD":
                        if (c.getString(owe).equals("Мне должны"))
                            totalUSD += Float.parseFloat(c.getString(value));
                        if (c.getString(owe).equals("Я должен"))
                            totalUSD -= Float.parseFloat(c.getString(value));
                        break;
                    case "EUR":
                        if (c.getString(owe).equals("Мне должны"))
                            totalEUR += Float.parseFloat(c.getString(value));
                        if (c.getString(owe).equals("Я должен"))
                            totalEUR -= Float.parseFloat(c.getString(value));
                        break;
                }

                switch (c.getString(owe))
                {
                    case "Я должен":
                        llMain.addView(PlagFill(c.getString(name), c.getString(value), c.getString(currancy), c.getString(owe), plagID++), lParams);
                        break;
                    case "Мне должны":
                        llDeb.addView(PlagFill(c.getString(name), c.getString(value), c.getString(currancy), c.getString(owe), plagID++), lParams);
                        break;


                }
            } while (c.moveToNext());
        }
        tvBalance.setText("TotalBYN: "+ totalBYR + "\nTotalUSD: " + totalUSD + "\nTotalEUR: " + totalEUR);
        sqldb.close();
    }

    private void PlagRenew()
    {
        llDeb.removeAllViews();
        llMain.removeAllViews();
        PlagCreate();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        PlagRenew();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnInfo:
            {

                break;
            }
        }

    }
}