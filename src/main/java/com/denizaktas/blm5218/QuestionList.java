package com.denizaktas.blm5218;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class QuestionList extends AppCompatActivity {
    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<Integer> idArray;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        listView = findViewById(R.id.listView);
        nameArray = new ArrayList<String>();
        idArray = new ArrayList<Integer>();

        arrayAdapter = new ArrayAdapter(QuestionList.this, android.R.layout.simple_list_item_1,idArray);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (QuestionList.this, QuestionListDetail.class );
                intent.putExtra("questionId",idArray.get(position));
                startActivity(intent);

            }
        });


        getData();

    }

    public void getData(){
        try{
        SQLiteDatabase database = QuestionList.this.openOrCreateDatabase("Questions",MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM questions",null);
        int nameIx = cursor.getColumnIndex("question");
        int idIx = cursor.getColumnIndex("id");

        while (cursor.moveToNext()){
            nameArray.add(cursor.getString(nameIx));
            idArray.add((cursor.getInt(idIx)));
        }
         arrayAdapter.notifyDataSetChanged();
         cursor.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}