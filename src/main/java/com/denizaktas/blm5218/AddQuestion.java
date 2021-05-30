package com.denizaktas.blm5218;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddQuestion extends AppCompatActivity {

    static SQLiteDatabase database;
    Bitmap selectedQuestionImage,smallImage;
    ImageView questionImage;
    EditText questionText, textA, textB, textC, textD, textTrue;
    Button addQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        questionImage = findViewById(R.id.questionImage);
        questionText = findViewById(R.id.questionText);
        textA = findViewById(R.id.textA);
        textB = findViewById(R.id.textB);
        textC = findViewById(R.id.textC);
        textD = findViewById(R.id.textD);
        textTrue = findViewById(R.id.textTrue);
        addQuestionButton = findViewById(R.id.addQuestionButtonDetail);




        /*if(info.matches("new")){
            Toast toastMessage;
            toastMessage = Toast.makeText(AddQuestion.this, "New.", Toast.LENGTH_SHORT);
            toastMessage.show();
            questionText.setText("");
            textA.setText("");
            textB.setText("");
            textC.setText("");
            textD.setText("");
            textTrue.setText("");

            Bitmap selectImage = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.photo_icon);
            questionImage.setImageBitmap(selectImage);

        }
        else{*/



        }



    public void selectQuestionImage(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if( requestCode == 2 && resultCode == RESULT_OK && data !=null){
            Uri imageData = data.getData();
            try{
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source =ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedQuestionImage = ImageDecoder.decodeBitmap(source);
                    questionImage.setImageBitmap(selectedQuestionImage);
                }
                selectedQuestionImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                questionImage.setImageBitmap(selectedQuestionImage);
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void selectAddQuestion(View view){
        String question = questionText.getText().toString();
        String choiceA = textA.getText().toString();
        String choiceB = textB.getText().toString();
        String choiceC = textC.getText().toString();
        String choiceD = textD.getText().toString();
        String choiceTrue = textTrue.getText().toString();

        smallImage = makeSmallerImage(selectedQuestionImage,300);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] byteArray = outputStream.toByteArray();

        try {
            database = this.openOrCreateDatabase("Questions",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY,question VARCHAR,choiceA VARCHAR,choiceB VARCHAR,choiceC VARCHAR,choiceD VARCHAR,choiceTrue VARCHAR,imageQuestion BLOB)");

            String sqlString = "INSERT INTO questions (question,choiceA,choiceB,choiceC,choiceD,choiceTrue,imageQuestion) VALUES (?,?,?,?,?,?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,question);
            sqLiteStatement.bindString(2,choiceA);
            sqLiteStatement.bindString(3,choiceB);
            sqLiteStatement.bindString(4,choiceC);
            sqLiteStatement.bindString(5,choiceD);
            sqLiteStatement.bindString(6,choiceTrue);
            sqLiteStatement.bindBlob(7,byteArray);
            sqLiteStatement.execute();

            Toast toastMessage;
            toastMessage = Toast.makeText(AddQuestion.this, "Soru başarıyla eklenmiştir.", Toast.LENGTH_SHORT);
            toastMessage.show();
        }
        catch(Exception e)
        {
         e.printStackTrace();
        }
        Intent intent =new Intent (AddQuestion.this,Menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }



    public Bitmap makeSmallerImage(Bitmap image,int maximumSize){
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height ;
        if(bitmapRatio > 1){
            width = maximumSize;
            height = (int) (width / bitmapRatio);
        }
        else{
            height = maximumSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image,width,height,true);
    }
}

