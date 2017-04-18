package com.tumblr.leirali.stockquotes_martinez;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //keeps crashing

    private Button getInfoButton;
    private EditText infoEditText;
    private TextView symbol;
    private TextView name;
    private TextView previousPrice;
    private TextView previousTime;
    private TextView weekRange;
    private TextView difference;
    private String inputStr;
    private String symbol1;
    private String name1;
    private String previousPrice1;
    private String previousTime1;
    private String weekRange1;
    private String difference1;
    private Stock inputStock;
    private View mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInfoButton = (Button)findViewById(R.id.getInfoButton);
        symbol = (TextView)findViewById(R.id.outputTextView1);
        name = (TextView)findViewById(R.id.outputTextView2);
        previousPrice = (TextView)findViewById(R.id.outputTextView3);
        previousTime = (TextView)findViewById(R.id.outputTextView4);
        weekRange = (TextView)findViewById(R.id.outputTextView5);
        difference = (TextView)findViewById(R.id.outputTextView6);
        infoEditText = (EditText)findViewById(R.id.infoEditText);
        mainView = findViewById(R.id.activity_main);

        getInfoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(infoEditText.getText().toString() != null) {
                    inputStr = infoEditText.getText().toString();
                }
                if(inputStr != null && !inputStr.contains(" ") && inputStr.length() <= 4){
                    inputStock = new Stock(inputStr);
                    new Thread() {
                        public void run() {
                            try {
                                inputStock.load();

                            } catch (Exception e) {
                            }
                            symbol1 = inputStock.getSymbol();
                            name1 = inputStock.getName();
                            previousPrice1 = inputStock.getLastTradePrice();
                            previousTime1 = inputStock.getLastTradeTime();
                            weekRange1 = inputStock.getChange();
                            difference1 = inputStock.getRange();

                            mainView.post(new Runnable(){
                                public void run(){
                                    symbol.setText(symbol1);
                                    name.setText(name1);
                                    previousPrice.setText(previousPrice1);
                                    previousTime.setText(previousTime1);
                                    weekRange.setText(weekRange1);
                                    difference.setText(difference1);
                                }
                            });

                            if(inputStr.length() == 0 || inputStock.getName().contains("/")){
                                symbol1 = "Symbol Not Found";
                                name1 = "N/A";
                                previousPrice1 = "N/A";
                                previousTime1 = "N/A";
                                weekRange1 = "N/A";
                                difference1 = "N/A";
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

        outState.putString("outputText1", symbol.getText().toString());
        outState.putString("outputText2", name.getText().toString());
        outState.putString("outputText3", previousPrice.getText().toString());
        outState.putString("outputText4", previousTime.getText().toString());
        outState.putString("outputText5", weekRange.getText().toString());
        outState.putString("outputText6", difference.getText().toString());
        outState.putString("editTextInfo",infoEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        symbol.setText(savedInstanceState.getString("outputText1"));
        name.setText(savedInstanceState.getString("outputText2"));
        previousPrice.setText(savedInstanceState.getString("outputText3"));
        previousTime.setText(savedInstanceState.getString("outputText4"));
        weekRange.setText(savedInstanceState.getString("outputText5"));
        difference.setText(savedInstanceState.getString("outputText6"));
        infoEditText.setText(savedInstanceState.getString("editTextInfo"));
    }
}