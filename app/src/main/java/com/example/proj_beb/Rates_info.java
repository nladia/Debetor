package com.example.proj_beb;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class Rates_info extends AppCompatActivity
{
    TextView tvRUB, tvUSD, tvEUR;
    Rates rateRUB, rateUSD, rateEUR;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rates_info);

        btnBack = (Button)findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        tvRUB = (TextView) findViewById(R.id.tvRUB);
        tvUSD = (TextView) findViewById(R.id.tvUSD);
        tvEUR = (TextView) findViewById(R.id.tvEUR);

        rateRUB = new Rates("rub");
        rateUSD = new Rates("usd");
        rateEUR = new Rates("EUR");

        tvRUB.setText(rateRUB.getRates());
        tvUSD.setText(rateUSD.getRates());
        tvEUR.setText(rateEUR.getRates());





    }
}


