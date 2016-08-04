package com.example.proj_beb;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Влад on 02.08.2016.
 */
public class Rates {
    String _url;
    MyTask mt;
    private static final String EUR = "292";
    private static final String USD = "145";
    private static final String RUB = "298";

    String Cur_Scale;
    String Cur_Rates;
    String Cur_Name;

    Rates(String str)
    {
        switch(str.toLowerCase())
        {
            case "usd": {getData(USD); break;}
            case "eur": {getData(EUR); break;}
            case "rub": {getData(RUB); break;}
        }
    }

    private void procData(String str)
    {
        if (!str.equals("Нет доступа"))
        {
            str = str.substring(1, str.length() - 2);
            String tmp[] = str.split(",");
            Cur_Rates = tmp[5].substring(tmp[5].lastIndexOf(":") + 1, tmp[5].length());
            Cur_Name = tmp[2].substring(tmp[2].lastIndexOf(":") + 1, tmp[2].length());
            Cur_Scale = tmp[3].substring(tmp[3].lastIndexOf(":") + 1, tmp[3].length());
        }

    }


    private void getData(String url_Cur)
    {
        String str = "";

        _url = "http://www.nbrb.by/API/ExRates/Rates/";
        Date date = new Date();
        int year = 2016;
        int month = date.getMonth() + 1;
        int day = date.getDay();
        _url += url_Cur;
        _url += "?onDate=" + year + "-" + month + "-" + day + "&Periodicity=0";


        mt = new MyTask();
        mt.execute();

        try
        {
            str = mt.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        procData(str);

    }

    public String getRates()
    {
        String str;

        if (Cur_Scale != null && Cur_Name != null && Cur_Rates != null) {
            str = Cur_Scale + " " + Cur_Name + " = " + Cur_Rates + " BYN";
            return str;
        }
        else
        {
            return "Нет доступа";
        }
    }


    class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LOG_TAG", "Begin");
        }


        @Override
        protected String doInBackground(Void... params) {
            String content = null;
            try {
                content = getContent(_url);
            } catch (IOException ex) {
                String con = ex.getMessage();
            }

            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("LOG_TAG", "End Result = " + result);
        }

        private String getContent(String path) throws IOException {
            BufferedReader reader = null;
            try {


                URL url = new URL(path);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(10000);
                c.connect();
                reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buf.append(line + "\n");
                }
                return (buf.toString());


            } finally {
                if (reader != null) {
                    reader.close();
                }
                else
                {
                    return "Нет доступа";
                }
            }
        }
    }
}
