package com.sourcey.materiallogindemo;
import JSSON.JSONMessage;
import JSSON.JSONMessageBuilder;
import JSSON.MessageType;
import logic.GameStateController;
import logic.Overseer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    public static SignupActivity getInstance() {
        return instance;
    }

    public static SignupActivity instance ;
    public NetworkTask networkTask ;
    public Overseer mainOverseer=Overseer.getInstance();
    @Bind(R.id.input_ip) EditText hostField;
    @Bind(R.id.input_login) EditText loginField;
    @Bind(R.id.input_port) EditText portField;
    @Bind(R.id.input_password) EditText passwordField;

    @Bind(R.id.btn_signup) Button signupButton;
    @Bind(R.id.btn_login) Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Overseer mainOverseer =Overseer.getInstance();
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        networkTask = new NetworkTask();
        instance = this;
        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                 networkTask.execute() ;
                //signupButton.setClickable(false);



        }});

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("tag", getHost().toString());
               // networkTask.execute();
               // loginButton.setClickable(false);
            }
        });

    }

    public void makeToast(){Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();}
    public String getHost(){
        return hostField.getText().toString();
    }
    public int getPort(){
        return Integer.parseInt(portField.getText().toString());
    }
    public String getLogin(){
        return loginField.getText().toString();
    }
    public String getPassword(){
        return passwordField.getText().toString();
    }
    public void clearPassword(){
        passwordField.setText("");
    }

    public void changeLogToGameFrame(){
        Intent intent = new Intent(getApplicationContext(), TableActivity.class);
        startActivityForResult(intent, 0);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }



}
