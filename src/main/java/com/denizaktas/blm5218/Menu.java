package com.denizaktas.blm5218;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.denizaktas.blm5218.MainActivity.denizaktas;

public class Menu extends AppCompatActivity {
    String usernameMainInfo,surnameMainInfo;
    Bitmap imageMainInfo;
    CardView cardWriting, cardList, cardQuestion, cardEmail, cardSettingBar, cardSetting;
    ImageView profilImage;
    TextView textDashboard;
    byte[] byteArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cardWriting = findViewById(R.id.cardWriting);
        cardList = findViewById(R.id.cardList);
        cardQuestion = findViewById(R.id.cardQuestion);
        cardEmail = findViewById(R.id.cardEmail);
        cardSettingBar = findViewById(R.id.cardSettingBar);
        cardSetting = findViewById(R.id.cardSetting);
        profilImage = findViewById(R.id.profilImage);
        textDashboard = findViewById(R.id.textDashboard);

        cardWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Menu.this, AddQuestion.class);
                startActivity(intent);
            }
        });
        cardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, QuestionList.class));
            }
        });
        cardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Question.class));
            }
        });
        cardEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Email.class));
            }
        });
        cardSettingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, QuestionSetting.class));
            }
        });
        cardSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Setting.class));
            }
        });

        if (getIntent().getExtras() != null) {

            usernameMainInfo = getIntent().getStringExtra("usernameInfo");
            surnameMainInfo = getIntent().getStringExtra("surnameInfo");
            /*byteArray = getIntent().getByteArrayExtra("imageInfo");
            imageMainInfo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            profilImage.setImageBitmap(imageMainInfo);*/
            textDashboard.setText(usernameMainInfo + " " + surnameMainInfo);
        }


    }
}