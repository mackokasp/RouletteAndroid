package com.sourcey.materiallogindemo;
import logic.GameStateController ;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import JSSON.JSONMessage;
import JSSON.JSONMessageBuilder;
import JSSON.MessageType;
import logic.Overseer;

public class NetworkTask extends AsyncTask<Void, Void, Void> {

 Overseer mainOverseer =Overseer.getInstance() ;

    protected void onPreExecute() {
        Log.i("AsyncTask", "onPreExecute");
    }

    @Override
    protected Void doInBackground(Void... params) { //This runs on a different thread
        mainOverseer.gameStateController.connect("127.0.0.1",1234);

        return null;
    }








}