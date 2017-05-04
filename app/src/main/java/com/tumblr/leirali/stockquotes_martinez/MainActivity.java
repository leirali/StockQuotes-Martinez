package com.tumblr.leirali.stockquotes_martinez;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button getButton;
    private EditText infoEditText;

    //input text strings
    private TextView getSymbol;
    private TextView getName;
    private TextView lastPrice;
    private TextView lastTime;
    private TextView getChange;
    private TextView getRange;

    private String inputString;

    //output text strings
    private String getSymbolOutput;
    private String getNameOutput;
    private String lastPriceOutput;
    private String lastTimeOutput;
    private String getChangeOutput;
    private String getRangeOutput;

    private Stock inputStock;
    private View mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getButton = (Button)findViewById(R.id.getInfoButton);
        getSymbol = (TextView)findViewById(R.id.outputTextView1);
        getName = (TextView)findViewById(R.id.outputTextView2);
        lastPrice = (TextView)findViewById(R.id.outputTextView3);
        lastTime = (TextView)findViewById(R.id.outputTextView4);
        getChange = (TextView)findViewById(R.id.outputTextView5);
        getRange = (TextView)findViewById(R.id.outputTextView6);
        infoEditText = (EditText)findViewById(R.id.infoEditText);
        mainView = findViewById(R.id.activity_main);

        getButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(infoEditText.getText().toString() != null) {
                    inputString = infoEditText.getText().toString();
                }
                if(inputString != null && !inputString.contains(" ") && inputString.length() <= 4){
                    inputStock = new Stock(inputString);
                    new Thread() {
                        public void run() {
                            try {
                                inputStock.load();

                            } catch (Exception e) {
                            }
                            getSymbolOutput = inputStock.getSymbol();
                            getNameOutput = inputStock.getName();
                            lastPriceOutput = inputStock.getLastTradePrice();
                            lastTimeOutput = inputStock.getLastTradeTime();
                            getChangeOutput = inputStock.getChange();
                            getRangeOutput = inputStock.getRange();

                            mainView.post(new Runnable(){
                                public void run(){
                                    getSymbol.setText(getSymbolOutput);
                                    getName.setText(getNameOutput);
                                    lastPrice.setText(lastPriceOutput);
                                    lastTime.setText(lastTimeOutput);
                                    getChange.setText(getChangeOutput);
                                    getRange.setText(getRangeOutput);
                                }
                            });

                            if(inputString.length() == 0 || inputStock.getName().contains("/")){
                                getSymbolOutput = "Symbol Not Found";
                                getNameOutput = "N/A";
                                lastPriceOutput = "N/A";
                                lastTimeOutput = "N/A";
                                getChangeOutput = "N/A";
                                getRangeOutput = "N/A";
                            }
                        }
                    }.start();
                }
            }

        });


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("outputText1", getSymbol.getText().toString());
        outState.putString("outputText2", getName.getText().toString());
        outState.putString("outputText3", lastPrice.getText().toString());
        outState.putString("outputText4", lastTime.getText().toString());
        outState.putString("outputText5", getChange.getText().toString());
        outState.putString("outputText6", getRange.getText().toString());
        outState.putString("editTextInfo",infoEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        getSymbol.setText(savedInstanceState.getString("outputText1"));
        getName.setText(savedInstanceState.getString("outputText2"));
        lastPrice.setText(savedInstanceState.getString("outputText3"));
        lastTime.setText(savedInstanceState.getString("outputText4"));
        getChange.setText(savedInstanceState.getString("outputText5"));
        getRange.setText(savedInstanceState.getString("outputText6"));
        infoEditText.setText(savedInstanceState.getString("editTextInfo"));
    }
}