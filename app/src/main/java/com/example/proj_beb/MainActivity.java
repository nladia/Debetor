package com.example.proj_beb;

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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class MainActivity extends AppCompatActivity
{


    TextView tvBalance;
    FrameLayout flActive;
    SQLdb sqldb;
    LinearLayout llMain, llDeb;
    ScrollView scrlMain, scrlDeb;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
    ImageButton IBtnAdd;



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
            case R.id.itmRates:

                Intent intent_rates_info;
                intent_rates_info = new Intent(this, Rates_info.class);
                startActivity(intent_rates_info);

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
        IBtnAdd = (ImageButton)findViewById(R.id.IBtnAdd);

        IBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1;
                intent1 = new Intent(MainActivity.this, Add_contact.class);
                startActivity(intent1);
            }
        });



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

    private LinearLayout PlagFill(String name, String summ, String currancy, String owe, String dateOwe, String dateBack,  final int plagID)
    {

        LinearLayout llNew = new LinearLayout(this,null);
        llNew.setOrientation(LinearLayout.VERTICAL);

        final TextView tvNew = new TextView(this);
        final TextView tvNew2 = new TextView(this);
        final TextView tvNew3 = new TextView(this);
        final SeekBar sbNew = new SeekBar(this);


        Date today = new Date();

        String[] sowe = dateOwe.replace("."," ").split(" ");
        Date dowe = new Date(Integer.parseInt(sowe[2]) - 1900 , Integer.parseInt(sowe[1]) - 1, Integer.parseInt(sowe[0]));

        String[] sback = dateBack.replace("."," ").split(" ");
        Date dback = new Date(Integer.parseInt(sback[2]) - 1900, Integer.parseInt(sback[1]) - 1, Integer.parseInt(sback[0]));

        if (today.getTime() < dback.getTime()) {
            int s = (int) (dback.getTime() - dowe.getTime()) / (1000 * 60 * 60);

            sbNew.setMax(s + 24);

            int s2 = (int) (dback.getTime() - today.getTime()) / (1000 * 60 * 60);
            s2 = s - s2;

            sbNew.setProgress(s2);
        } else {
            sbNew.setMax(1);
            sbNew.setProgress(1);
        }


        tvNew.setText(name);
        tvNew.setTextSize(25);
        tvNew3.setText(dateOwe + "                                              " + dateBack);
        tvNew2.setText(owe + " " + summ + " " + currancy);
        tvNew2.setTextSize(20);
        tvNew.setTextColor(Color.BLACK);
        tvNew2.setTextColor(Color.BLACK);
        sbNew.setEnabled(false);


        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);
        LinearLayout.LayoutParams sblParams = new LinearLayout.LayoutParams(matchParent, matchParent);


        lParams.gravity = Gravity.START;
        lParams.setMargins(15,5,5,20);

        sblParams.gravity = Gravity.START;
        sblParams.setMargins(15,5,5,20);


        llNew.addView(tvNew, lParams);
        llNew.addView(tvNew2, lParams);
        llNew.addView(tvNew3, sblParams);
        llNew.addView(sbNew, sblParams);
        llNew.setBackgroundColor(Color.parseColor("#E8EAF6"));

        llNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return llNew;
    }


    private void PlagCreate()
    {
        sqldb = new SQLdb(this);
        Double totalEUR = 0.0, totalBYR = 0.0, totalUSD = 0.0;

        SQLiteDatabase db = sqldb.getWritableDatabase();
        Cursor c = db.query("mytable2", null, null, null, null, null, null);


        if (c.moveToFirst())
        {

            // определяем номера столбцов по имени в выборке
            int name = c.getColumnIndex("name");
            int owe = c.getColumnIndex("owe");
            int value = c.getColumnIndex("value");
            int currancy = c.getColumnIndex("currancy");
            int dateOwe = c.getColumnIndex("dateOwe");
            int dateBack = c.getColumnIndex("dateBack");
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
                        llMain.addView(PlagFill(c.getString(name), c.getString(value), c.getString(currancy), c.getString(owe), c.getString(dateOwe), c.getString(dateBack), plagID++), lParams);
                        break;
                    case "Мне должны":
                        llDeb.addView(PlagFill(c.getString(name), c.getString(value), c.getString(currancy), c.getString(owe), c.getString(dateOwe), c.getString(dateBack), plagID++), lParams);
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

}