package com.denizaktas.blm5218;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    ArrayList<Persons> personsList ;
    private EditText textEmail, textPassword, textRePassword, textName, textSurname, textPhone;
    private Button signUpButton;
    ImageView imageView;
    Bitmap selectedImage;
    private DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textRePassword = findViewById(R.id.textRePassword);
        textName = findViewById(R.id.textName);
        textSurname = findViewById(R.id.textSurname);
        textPhone = findViewById(R.id.textPhone);
        imageView = findViewById(R.id.imageView);

        signUpButton = findViewById(R.id.signUpButton);
        personsList = Persons.getPersonsList();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (save()) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


                textEmail.setText("");
                textPassword.setText("");
                textRePassword.setText("");
                textName.setText("");
                textSurname.setText("");
                textPhone.setText("");

                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }
        });
}


    public void selectImage(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if( requestCode == 2 && resultCode == RESULT_OK && data !=null){
            Uri imageData = data.getData();
            try{
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source =ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);
                }
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                imageView.setImageBitmap(selectedImage);
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean emailCheck() {

        if (!Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString().trim()).matches()) {
            textEmail.setError("Lütfen geçerli bir e-mail adresi giriniz.");
            return false;
        } else {
            textEmail.setError(null);
            return true;
        }
    }

    private boolean saveCheck() {

        for (Persons person : personsList) {
            if (person.getEmail().equals(textEmail.getText().toString().trim())) {
                Toast.makeText(SignUp.this, "Bu e-mail ile daha önce kayıt oluşturulmuş. Lütfen farklı bir e-mail giriniz.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean passwordCheck() {
        if ((textPassword.getText().toString().trim()).length() < 8) {
            textPassword.setError("Lütfen en az 8 karakter içeren bir şifre giriniz.");
            return false;
        } else if (!((textPassword.getText().toString().trim()).equals((textRePassword.getText().toString().trim())))) {
            textRePassword.setError("Lütfen doğrulama parolasını tekrar giriniz.");
            textRePassword.setText("");
            return false;
        } else {
            textPassword.setError(null);
            return true;
        }
    }

    private boolean save() {
        if (emailCheck() && passwordCheck() && saveCheck()) {
            Toast toastMessage;
            personsList.add(new Persons(textEmail.getText().toString(),textName.getText().toString(),textSurname.getText().toString(),textPassword.getText().toString(),textPhone.getText().toString(),selectedImage));
            toastMessage = Toast.makeText(SignUp.this, "Kaydınız başarıyla oluşturulmuştur.", Toast.LENGTH_SHORT);
            toastMessage.show();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toastMessage.cancel();
            return true;
        } else {
            Toast.makeText(SignUp.this, "Lütfen eksik ya da hatalı alanları kontrol ediniz.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}


