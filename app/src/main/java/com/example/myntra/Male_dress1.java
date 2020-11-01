package com.example.myntra;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.Locale;
import java.util.Objects;

public class Male_dress1 extends AppCompatActivity {
    int disease;
    Boolean colorblind;
    int imgID = R.drawable.male1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_male_dress1);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        boolean flag = extras.getBoolean("flag");
        boolean blind=extras.getBoolean("blind");
        findViewById(R.id._men).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Male_dress1.this, doors_to_trial_room.class);
                intent.putExtra("productId", imgID);
                startActivity(intent);
            }
        });

        if (flag||blind) {
            AudioMode();
        }
        disease=extras.getInt("disease");
        colorblind=extras.getBoolean("colorblind");
        if(colorblind){
            Utils.fetchColourBlindImage(this, BitmapFactory.decodeResource(getResources(), imgID), disease, new Utils.ImageReceived() {
                @Override
                public void onImageReceivedSuccess(Bitmap bitmap) {
                    ((ImageView) findViewById(R.id.male_dress1)).setImageBitmap(bitmap);
                }

                @Override
                public void onImageReceivedError(VolleyError e) {
                    e.printStackTrace();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (tts != null)
            tts.stop();
        super.onBackPressed();
    }

    private void AudioMode() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    initTTs();
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }, "com.google.android.tts");


    }

    TextToSpeech tts;


    public void initTTs() {
        int ttsLang = tts.setLanguage(Locale.US);

        if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TTS", "The Language is not supported!");
        } else {
            Log.i("TTS", "Language Supported.");
        }
        tts.speak("The product displayed is a Abrakadabra men Black  printed round-neck t-shirt. The print consists of a rainbow colored mushroom garden, clouds and an eye depicted as a sun. It's material is cotton, elastane and can be machine washed. The dress from Abrakadabra is both stylish and durable, making it a must have item. It is available in in 5 sizes-  small, medium, large, extra large and extra extra large. It's current price is 599 rupees inclusive of all taxes.",
                TextToSpeech.QUEUE_FLUSH, null, "InitText");
        Log.i("TTS", "Initialization success.");
    }

}