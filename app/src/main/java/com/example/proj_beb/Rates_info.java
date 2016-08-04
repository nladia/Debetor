package com.example.proj_beb;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        rateRUB = new Rates("rub");
        tvRUB = (TextView) findViewById(R.id.tvRUB);
        tvRUB.setText(rateRUB.getRates());

        rateUSD = new Rates("usd");
        tvUSD = (TextView) findViewById(R.id.tvUSD);
        tvUSD.setText(rateUSD.getRates());

        rateEUR = new Rates("EUR");
        tvEUR = (TextView) findViewById(R.id.tvEUR);
        tvEUR.setText(rateEUR.getRates());
    }
}


