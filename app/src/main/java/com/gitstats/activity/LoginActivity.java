package com.gitstats.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deneme.R;

/**
 * Created by ALP on 1.1.2016.
 */
public class LoginActivity extends Activity {
    private EditText username;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                //Get username from EditText and login if it's not empty
                if (user.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter username to continue", Toast.LENGTH_LONG).show();
                } else {
                    //Save it username in SharedPreferences for later
                    SharedPreferences sharedPreferences = getSharedPreferences("usernames", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", user.toString());
                    editor.commit();
                    //Login
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
