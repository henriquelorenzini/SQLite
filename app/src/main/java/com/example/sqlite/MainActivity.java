package com.example.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMovie;
    private RatingBar ratingBar;
    private Button buttonInsert;
    private Button buttonLoad;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovie = findViewById(R.id.editTextMovie);
        ratingBar = findViewById(R.id.ratingBar);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonLoad = findViewById(R.id.buttonLoad);
        textViewResult = findViewById(R.id.textViewResult);

//            database.execSQL("INSERT INTO movies(name, rating) VALUES('Back To The Future', 5)");
//            database.execSQL("INSERT INTO movies(name, rating) VALUES('Donnie Darko', 5)");

       final SQLiteDatabase database = getConnection();

       buttonInsert.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               insertData(database);
           }
       });
       buttonLoad.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               loadData(database);
           }
       });
    }
    private void insertData(SQLiteDatabase database){
        String name = editTextMovie.getText().toString();
        float rating = ratingBar.getRating();

        database.execSQL("INSERT INTO movies(name, rating) VALUES ('"+ name + "', " + rating + ")");
    }
    private void loadData(SQLiteDatabase database){
        Cursor cursor = database.rawQuery("SELECT name, rating FROM movies", null);

        if(cursor.getCount() >= 1){
            int indexName = cursor.getColumnIndex("name");
            int indexRating = cursor.getColumnIndex("rating");

            StringBuilder result = new StringBuilder();
            while(cursor.moveToNext()){
                result.append("Movie: "+ cursor.getString(indexName)+"\n");
                result.append("Rating: "+ cursor.getString(indexRating)+"\n");
            }
            textViewResult.setText(result.toString());
            return;
        }
        textViewResult.setText("Empty");
    }

    private SQLiteDatabase getConnection(){
        SQLiteDatabase database = openOrCreateDatabase("sqlite.db", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS movie(name VARCHAR (200),rating DOUBLE)");
        return database;
    }
}