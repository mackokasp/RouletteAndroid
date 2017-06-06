package com.sourcey.materiallogindemo;
import JSSON.* ;
import logic.Overseer;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Bind;

public class TableActivity extends AppCompatActivity {
    public static TableActivity getInstance() {
        return instance;
    }
    public Overseer mainOverseer = Overseer.getInstance() ;
    public static TableActivity instance ;
    private static final String TAG = "TableActivity";
    private static final int REQUEST_SIGNUP = 0;
    @Bind(R.id.chips) TextView accountLabel;
    @Bind(R.id.gamestate) TextView gameStateInfo;
    @Bind(R.id.btn_bet) Button betButton;
    @Bind(R.id.result) TextView resultLabel;
    @Bind(R.id.betnumber) EditText pickedNum;
    @Bind(R.id.betsize) EditText betAmount;
    @Bind(R.id.betresult) TextView betResult;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);
        instance = this ;
        betButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
                {
                    mainOverseer.comFlagSemaphore.tryAcquire();
                    if(getBetAmount().isEmpty())
                        return;
                    if(getAccountValue() < getBetAmountValue())
                        return;
                    JSONMessage betMsg = JSONMessageBuilder.create_message(MessageType.SET_BET,mainOverseer.gameStateController.clientLogin,"red" ,getBetAmount(),"session1234" ,mainOverseer.gameStateController.clientPassword);
                    try{
                        mainOverseer.gameStateController.sendMessage(betMsg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    lockBettingGUI(); // lockuje przycisk do betowania az nie ..
                    // dostaniemy odpowiedzi czy bet ok, zeby nie slac bez sensu duzo komunikatow
                    mainOverseer.comFlagSemaphore.release();
                }});


     setResultLabel("win");

    }









    public  void addBetActionListener (View.OnClickListener ActionListener)
    {
        betButton.setOnClickListener(ActionListener);
    }
    public String getBetAmount(){
        return betAmount.getText().toString();
    }
    public int getBetAmountValue(){
        return Integer.parseInt(betAmount.getText().toString());
    }

    public void clearBetAmountTextField(){
        betAmount.setText("");
    }


    public void setGameStateInfoLabel(MessageType type) {
        switch (type) {
            case TIMESTAMP_TO_BET:
                gameStateInfo.setText("Time to bet..");
                break;
            case TIMESTAMP_TO_RESULT:
                gameStateInfo.setText("Results:");
                break;

            case TIMESTAMP_TO_ROLL:
                gameStateInfo.setText("Rolling...");
                break;
        }

    }
    public void lockBettingGUI(){
        betButton.setEnabled(false);
        betAmount.setEnabled(false);
    }
    public void unlockBettingGUI(){
        betButton.setEnabled(true);
        betAmount.setEnabled(true);
    }
    public int getAccountValue(){
        return Integer.parseInt(accountLabel.getText().toString());
    }

    public void setAccountLabel(String result){
        accountLabel.setText(result);
    }
    public void setResultLabel(String result){
        if(result.equals("win"))
            resultLabel.setText("You WON");
        if(result.equals("lose"))
            resultLabel.setText("You LOST...");
    }
    public void setBetResult(MessageType type) {
        switch (type) {
            case BET_OK:
                betResult.setText("BET OK");
                break;
            case BAD_SESSION_ID:
                betResult.setText("WRONG SESSION");
                break;
            case BET_UNABLE:
                betResult.setText("UNABLE TO BET, TRY AGAIN");
                break;
            default:
                clearBetResult();
                break;
        }
    }

    public void clearBetResult() {
        betResult.setText("");
    }


}