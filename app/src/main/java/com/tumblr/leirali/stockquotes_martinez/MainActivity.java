package com.tumblr.leirali.stockquotes_martinez;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button getInfoButton;
    private EditText infoEditText;
    private TextView outputTextView1;
    private TextView outputTextView2;
    private TextView outputTextView3;
    private TextView outputTextView4;
    private TextView outputTextView5;
    private TextView outputTextView6;
    private String inputStr;
    private String outputStr1;
    private String outputStr2;
    private String outputStr3;
    private String outputStr4;
    private String outputStr5;
    private String outputStr6;
    private Stock inputStock;
    private View mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInfoButton = (Button)findViewById(R.id.getInfoButton);
        outputTextView1 = (TextView)findViewById(R.id.outputTextView1);
        outputTextView2 = (TextView)findViewById(R.id.outputTextView2);
        outputTextView3 = (TextView)findViewById(R.id.outputTextView3);
        outputTextView4 = (TextView)findViewById(R.id.outputTextView4);
        outputTextView5 = (TextView)findViewById(R.id.outputTextView5);
        outputTextView6 = (TextView)findViewById(R.id.outputTextView6);
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
                            outputStr1 = inputStock.getSymbol();
                            outputStr2 = inputStock.getName();
                            outputStr3 = inputStock.getLastTradePrice();
                            outputStr4 = inputStock.getLastTradeTime();
                            outputStr5 = inputStock.getChange();
                            outputStr6 = inputStock.getRange();

                            mainView.post(new Runnable(){
                                public void run(){
                                    outputTextView1.setText(outputStr1);
                                    outputTextView2.setText(outputStr2);
                                    outputTextView3.setText(outputStr3);
                                    outputTextView4.setText(outputStr4);
                                    outputTextView5.setText(outputStr5);
                                    outputTextView6.setText(outputStr6);
                                }
                            });

                            if(inputStr.length() == 0 || inputStock.getName().contains("/")){
                                outputStr1 = "Symbol Not Found";
                                outputStr2 = "N/A";
                                outputStr3 = "N/A";
                                outputStr4 = "N/A";
                                outputStr5 = "N/A";
                                outputStr6 = "N/A";
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

        outState.putString("outputText1",outputTextView1.getText().toString());
        outState.putString("outputText2",outputTextView2.getText().toString());
        outState.putString("outputText3",outputTextView3.getText().toString());
        outState.putString("outputText4",outputTextView4.getText().toString());
        outState.putString("outputText5",outputTextView5.getText().toString());
        outState.putString("outputText6",outputTextView6.getText().toString());
        outState.putString("editTextInfo",infoEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        outputTextView1.setText(savedInstanceState.getString("outputText1"));
        outputTextView2.setText(savedInstanceState.getString("outputText2"));
        outputTextView3.setText(savedInstanceState.getString("outputText3"));
        outputTextView4.setText(savedInstanceState.getString("outputText4"));
        outputTextView5.setText(savedInstanceState.getString("outputText5"));
        outputTextView6.setText(savedInstanceState.getString("outputText6"));
        infoEditText.setText(savedInstanceState.getString("editTextInfo"));
    }
}