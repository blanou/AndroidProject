package com.example.saraalaswad.moviebox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DisplaySongsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_songs);

        Intent IntentExtra = getIntent();
        Bundle bundle = IntentExtra.getExtras();

        if (!bundle.isEmpty()) {
            String title = bundle.getString("name");

            TextView txt = findViewById(R.id.txt_list_of_songs);


            try {
                JSONObject file = new JSONObject(loadJSON());

                JSONArray movies = file.getJSONArray("movies");

                String songs = "";

                for (int i = 0; i < movies.length(); i++) {

                    JSONObject tmpObj = movies.getJSONObject(i);

                    if (tmpObj.getString("title").equals(title)) {
                        JSONArray tmpAry = tmpObj.getJSONArray("song");

                        for (int index = 0; index < tmpAry.length(); index++) {
                            songs += tmpAry.getString(index) + "\n\n";
                        }
                    }
                }

                if (songs.length() == 0) {
                    songs = "No data about songs of this movie";
                }

                txt.setText(songs);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public String loadJSON() {
        String json;
        try {
            InputStream is = getAssets().open("movies_songs.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
