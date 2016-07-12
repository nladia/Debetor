package com.example.proj_beb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etPassword;
    Button btnLogin;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
                sPref = getSharedPreferences("Saved",MODE_PRIVATE);
                String savedText = sPref.getString("Saved", "");

                if (etPassword.getText().toString().equals(savedText))
                {
                    Intent intent;
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}


