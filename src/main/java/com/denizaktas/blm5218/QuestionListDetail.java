package com.denizaktas.blm5218;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static com.denizaktas.blm5218.AddQuestion.database;

public class QuestionListDetail extends AppCompatActivity {

    ImageView questionImageDetail;
    EditText questionTextDetail, textADetail, textBDetail, textCDetail, textDDetail, textTrueDetail;
    Button addQuestionButtonDetail;
    int questionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list_detail);
        questionImageDetail = findViewById(R.id.questionImageDetail);
        questionTextDetail = findViewById(R.id.questionTextDetail);
        textADetail = findViewById(R.id.textADetail);
        textBDetail = findViewById(R.id.textBDetail);
        textCDetail = findViewById(R.id.textCDetail);
        textDDetail = findViewById(R.id.textDDetail);
        textTrueDetail = findViewById(R.id.textTrueDetail);
        addQuestionButtonDetail = findViewById(R.id.addQuestionButtonDetail);

        database = this.openOrCreateDatabase("questions",MODE_PRIVATE,null);

        Intent intent = getIntent();
        questionId = intent.getIntExtra("questionId",1);
        try {

            Cursor cursor = database.rawQuery("SELECT *  FROM questions WHERE id = ?",new String[] {String.valueOf(questionId)});
            Toast.makeText(QuestionListDetail.this, "abc", Toast.LENGTH_SHORT).show();
            int questionIx = cursor.getColumnIndex("question");
            int choiceAIx = cursor.getColumnIndex("choiceA");
            int choiceBIx = cursor.getColumnIndex("choiceB");
            int choiceCIx = cursor.getColumnIndex("choiceC");
            int choiceDIx = cursor.getColumnIndex("choiceD");
            int choiceTrueIx = cursor.getColumnIndex("choiceTrue");
            int imageQuestionIx = cursor.getColumnIndex("imageQuestion");



                Toast.makeText(QuestionListDetail.this, "abc", Toast.LENGTH_SHORT).show();

                questionTextDetail.setText(cursor.getString(questionIx));
                textADetail.setText(cursor.getString(choiceAIx));
                textBDetail.setText(cursor.getString(choiceBIx));
                textCDetail.setText(cursor.getString(choiceCIx));
                textDDetail.setText(cursor.getString(choiceDIx));
                textTrueDetail.setText(cursor.getString(choiceTrueIx));

                byte[] bytes = cursor.getBlob(imageQuestionIx);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                questionImageDetail.setImageBitmap(bitmap);


            cursor.close();

        } catch (Exception e){
        }
    }
}