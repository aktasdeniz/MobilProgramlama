package com.denizaktas.blm5218;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText textEmail,textPassword;
    Button signIn,signUp;
    int control;
    ArrayList<Persons> checkList;
    static Bitmap bird = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.bird);
    static Bitmap denizaktas = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.denizaktas);
    String usernameInfo,surnameInfo;
    Bitmap imageInfo;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkList = fetchPersonsList();
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);
        control = 0 ;



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    Toast.makeText(MainActivity.this, "Giriş başarılı", Toast.LENGTH_SHORT).show();
/*
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    denizaktas.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
*/
                    Intent intent = new Intent(getApplicationContext(), Menu.class);

                    intent.putExtra("usernameInfo", usernameInfo);
                    intent.putExtra("surnameInfo", surnameInfo);
                    /*intent.putExtra("imageInfo", byteArray);
*/
                    startActivity(intent);

                } else {
                    control += 1;
                    Toast.makeText(MainActivity.this, "Hatalı kullanıcı adı/parola", Toast.LENGTH_SHORT).show();
                    if (control >= 3) {
                        Toast.makeText(MainActivity.this, "Üç hatalı giriş yapıldı, 15 dakika sonra tekrar deneyiniz", Toast.LENGTH_SHORT).show();
                        signIn.setEnabled(false);
                        System.exit(0);
                                     }
                    textEmail.setText("");
                    textPassword.setText("");
                     }
            }});

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }});

    }
    private boolean check() {
        for (Persons person : checkList) {
            if (textEmail.getText().toString().equals(person.getEmail()) && textPassword.getText().toString().equals(person.getPassword())) {
                usernameInfo = person.getName();
                surnameInfo = person.getSurname();



                return true;
            }
        }
        return false;
    }

    private ArrayList<Persons> fetchPersonsList() {
        if (getIntent().getExtras() != null) {
            checkList = (ArrayList<Persons>) getIntent().getSerializableExtra("personList");
        } else {
            checkList = Persons.getPersonsList();
        }
        return checkList;
    }

}