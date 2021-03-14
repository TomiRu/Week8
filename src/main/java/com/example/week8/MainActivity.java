package com.example.week8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottleDispenser myObj;
    EditText textLine;
    TextView textView;
    TextView balanceView;
    SeekBar seekBar;
    Spinner spinner;
    int amount;
    double balance;
    static int MAX = 25;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myObj = BottleDispenser.getInstance(); //Luodaan BottleDispenser olio

        textLine = (EditText) findViewById(R.id.textLine);
        textView = (TextView) findViewById(R.id.textView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        spinner = (Spinner) findViewById(R.id.spinner);

        context = MainActivity.this;
        System.out.println("Sijainti kirjastossa: " + context.getFilesDir());

        initializeUI();

        seekBar.setMax(MAX);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amount = progress;
                textView.setText("" + progress + "€");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void addMethod (View v){
        myObj.addMoney(textLine, amount);
        seekBar.setProgress(0);
    }

    public void returnMethod (View v){
        myObj.returnMoney(textLine);
    }

    public void buyMethod (View v){
        String choice = spinner.getSelectedItem().toString();
        System.out.println("Valinta on: " + choice);
        myObj.buyBottle(textLine, choice, context);

    }

    public void initializeUI(){
        ArrayList list = myObj.getList();

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<Bottle> adapter = new ArrayAdapter<Bottle>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


}
