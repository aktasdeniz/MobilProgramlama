package com.denizaktas.blm5218;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionSetting extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private SeekBar seekBarQuestionQuantity,seekBarChoiceQuantity;
    private EditText questionScore,examTime;
    private Button btnSettings;
    private TextView textQuestionQuantity,textChoiceQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_setting);



        seekBarQuestionQuantity = findViewById(R.id.seekBarQuestionQuantity);
        seekBarChoiceQuantity = findViewById(R.id.seekBarChoiceQuantity);
        questionScore = findViewById(R.id.questionScore);
        examTime = findViewById(R.id.examTime);
        btnSettings = findViewById(R.id.btnSettings);
        textQuestionQuantity = findViewById(R.id.textQuestionQuantity);
        textChoiceQuantity = findViewById(R.id.textChoiceQuantity);



        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
                Toast.makeText(QuestionSetting.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });


        seekBarChoiceQuantity.setOnSeekBarChangeListener(this);
        seekBarQuestionQuantity.setOnSeekBarChangeListener(this);

        readFromSharedPref();
    }




    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar==seekBarQuestionQuantity){
            textQuestionQuantity.setText(String.valueOf(progress));
        }
        if (seekBar==seekBarChoiceQuantity){
            textChoiceQuantity.setText(String.valueOf(progress));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("settingsinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int questionNumber = Integer.parseInt(textQuestionQuantity.getText().toString());
        int choiceNumber = Integer.parseInt(textChoiceQuantity.getText().toString());
        int examTimeTotal = Integer.parseInt(examTime.getText().toString());
        int questScore = Integer.parseInt(questionScore.getText().toString());

        editor.putInt("textQuestionQuantity", questionNumber);
        editor.putInt("textChoiceQuantity", choiceNumber);
        editor.putInt("examTime", examTimeTotal);
        editor.putInt("questionScore", questScore);
        editor.commit();


    }
    public void readFromSharedPref(){
        SharedPreferences sharedPreferences = getSharedPreferences("settingsinfo", Context.MODE_PRIVATE);
        String soruSayısı = String.valueOf(sharedPreferences.getInt("textQuestionQuantity",10));
        String sıkSayısı = String.valueOf(sharedPreferences.getInt("textChoiceQuantity",4));
        String sınavSuresi = String.valueOf(sharedPreferences.getInt("examTime",30));
        String soruPuanı = String.valueOf(sharedPreferences.getInt("questionScore",10));

        textQuestionQuantity.setText(soruSayısı);
        textChoiceQuantity.setText(sıkSayısı);
        examTime.setText(sınavSuresi);
        questionScore.setText(soruPuanı);


    }
}