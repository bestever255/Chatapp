package com.example.bestever.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    MaterialEditText username , password;
    TextView toggleLoginMood;
    Button button;

    Boolean loginModeActive = false;

    public void redirectIfLoggedIn(){
        if(ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext() , userListActivity.class);
            startActivity(intent);
        }
    }

    public void toggleLoginMode(View view){

        if(loginModeActive){

            loginModeActive = false;
            button.setText("sign up");
            toggleLoginMood.setText("or, log in");

        }else{

            loginModeActive = true;
            button.setText("log in");
            toggleLoginMood.setText("or, sign up");

        }

    }

    public void signUpLogin(View view){

        if(loginModeActive){

            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null){

                        Log.i("Info" , "user logged in");

                        redirectIfLoggedIn();
                    }else{

                        String message = e.getMessage();

                        if(message.toLowerCase().contains("java")){
                            message =  e.getMessage().substring(e.getMessage().indexOf(" "));
                        }

                        Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();

                    }
                }
            });



        }else{

            ParseUser user = new ParseUser();

            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {
                        Log.i("user", "User Signed up");
                        redirectIfLoggedIn();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (MaterialEditText)findViewById(R.id.username);
        password = (MaterialEditText)findViewById(R.id.password);
        toggleLoginMood = (TextView)findViewById(R.id.toggleLoginMood);
        button = (Button)findViewById(R.id.button);

        setTitle("Whatsapp Login");

        redirectIfLoggedIn();
    }
}
