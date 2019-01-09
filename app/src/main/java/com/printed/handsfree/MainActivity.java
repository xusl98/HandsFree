package com.printed.handsfree;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btn_record;

    private AudioManager mAudioManager;
    private int streamType = AudioManager.STREAM_MUSIC;
    private MediaMetadataRetriever metadataRetriever;
    private TextToSpeech mTTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_record = (Button) findViewById(R.id.btn_record);


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Text to Speech constructor
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    Locale locSpanish = new Locale("es", "ES");
                    int result = mTTS.setLanguage(locSpanish);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialitation failed");
                }
            }
        });


        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput();
            }
        });


    }

    public void getSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Speech input not supported", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    //----------
                    //REPRODUCIR
                    //----------
                    if (result.get(0).toLowerCase().contains("repro")) {
                        if (mAudioManager == null)
                            mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

                        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY);
                        mAudioManager.dispatchMediaKeyEvent(event);

                        //-----
                        //PAUSE
                        //-----
                    } else if (result.get(0).toLowerCase().contains("paus") || result.get(0).toLowerCase().contains("para")) {

                        if (mAudioManager == null)
                            mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

                        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PAUSE);
                        mAudioManager.dispatchMediaKeyEvent(event);

                        //---------
                        //SIGUIENTE
                        //---------
                    } else if (result.get(0).toLowerCase().contains("sigu") || result.get(0).toLowerCase().contains("otr")) {

                        if (mAudioManager == null)
                            mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

                        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT);
                        mAudioManager.dispatchMediaKeyEvent(event);
                        //ANTERIOR
                    } else if (result.get(0).toLowerCase().contains("anter")) {

                        if (mAudioManager == null)
                            mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

                        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                        mAudioManager.dispatchMediaKeyEvent(event);

                        //-----------------------
                        //VOLUMEN SUBIR 9 NIVELES
                        //-----------------------
                    } else if (result.get(0).toLowerCase().contains("sub")) {
                        int veces = 0;
                        if (result.get(0).toLowerCase().contains("uno") || result.get(0).toLowerCase().contains("1") || result.get(0).toLowerCase().contains("una")) {
                            veces = 1;
                        } else if (result.get(0).toLowerCase().contains("dos") || result.get(0).toLowerCase().contains("2")) {
                            veces = 2;
                        } else if (result.get(0).toLowerCase().contains("tres") || result.get(0).toLowerCase().contains("3")) {
                            veces = 3;
                        } else if (result.get(0).toLowerCase().contains("cuat") || result.get(0).toLowerCase().contains("4")) {
                            veces = 4;
                        } else if (result.get(0).toLowerCase().contains("cin") || result.get(0).toLowerCase().contains("5")) {
                            veces = 5;
                        } else if (result.get(0).toLowerCase().contains("sei") || result.get(0).toLowerCase().contains("6")) {
                            veces = 6;
                        }

                        for (int i = 0; i < veces; i++) {
                            mAudioManager.adjustStreamVolume(streamType,
                                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                        }

                        //-----------------------
                        //VOLUMEN BAJAR 9 NIVELES
                        //-----------------------
                    } else if (result.get(0).toLowerCase().contains("baj")) {
                        int veces = 0;
                        if (result.get(0).toLowerCase().contains("uno") || result.get(0).toLowerCase().contains("1") || result.get(0).toLowerCase().contains("una")) {
                            veces = 1;
                        } else if (result.get(0).toLowerCase().contains("dos") || result.get(0).toLowerCase().contains("2")) {
                            veces = 2;
                        } else if (result.get(0).toLowerCase().contains("tres") || result.get(0).toLowerCase().contains("3")) {
                            veces = 3;
                        } else if (result.get(0).toLowerCase().contains("cuat") || result.get(0).toLowerCase().contains("4")) {
                            veces = 4;
                        } else if (result.get(0).toLowerCase().contains("cin") || result.get(0).toLowerCase().contains("5")) {
                            veces = 5;
                        } else if (result.get(0).toLowerCase().contains("sei") || result.get(0).toLowerCase().contains("6")) {
                            veces = 6;
                        } else if (result.get(0).toLowerCase().contains("sie") || result.get(0).toLowerCase().contains("7")) {
                            veces = 7;
                        } else if (result.get(0).toLowerCase().contains("och") || result.get(0).toLowerCase().contains("8")) {
                            veces = 8;
                        } else if (result.get(0).toLowerCase().contains("nue") || result.get(0).toLowerCase().contains("9")) {
                            veces = 9;
                        }

                        for (int i = 0; i < veces; i++) {
                            mAudioManager.adjustStreamVolume(streamType,
                                    AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                        }

                        //---------------
                        //TE DICE LA HORA
                        //---------------
                    } else if (result.get(0).toLowerCase().contains("hora")) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                        Date date = new Date();
                        String x = simpleDateFormat.format(date);


                        String toSpeak = "son las " + hora(x.substring(0, 2)) + " y " + x.substring(3, 5) + " minutos";
                        speak(toSpeak);


                        //----------------
                        //TE DICE LA FECHA
                        //----------------
                    } else if (result.get(0).toLowerCase().contains("fecha") || result.get(0).toLowerCase().contains("dia") || result.get(0).toLowerCase().contains("día")) {

                        SimpleDateFormat simpleDateFormatMes = new SimpleDateFormat("MMMM", Locale.getDefault());
                        Date dateMes = new Date();
                        String mes = simpleDateFormatMes.format(dateMes);

                        SimpleDateFormat simpleDateFormatDia = new SimpleDateFormat("dd", Locale.getDefault());
                        Date dateDia = new Date();
                        String dia = simpleDateFormatDia.format(dateDia);

                        SimpleDateFormat simpleDateFormatAnio = new SimpleDateFormat("yyyy", Locale.getDefault());
                        Date dateAnio = new Date();
                        String anio = simpleDateFormatAnio.format(dateAnio);

                        speak("hoy es " + dia + " de " + mes + " de " + anio);
                    }
                }
                break;
        }
    }

    private String hora(String hora) {
        String hour = "";

        switch (hora) {
            case "1":
                hour = "una";
                break;
            case "2":
                hour = "dos";
                break;
            case "3":
                hour = "tres";
                break;
            case "4":
                hour = "cuatro";
                break;
            case "5":
                hour = "cinco";
                break;
            case "6":
                hour = "seis";
                break;
            case "7":
                hour = "siete";
                break;
            case "9":
                hour = "nueve";
                break;
            case "10":
                hour = "diez";
                break;
            case "11":
                hour = "once";
                break;
            case "12":
                hour = "doce";
                break;
            case "13":
                hour = "una";
                break;
            case "14":
                hour = "dos";
                break;
            case "15":
                hour = "tres";
                break;
            case "16":
                hour = "cuatro";
                break;
            case "17":
                hour = "cinco";
                break;
            case "18":
                hour = "seis";
                break;
            case "19":
                hour = "siete";
                break;
            case "20":
                hour = "ocho";
                break;
            case "21":
                hour = "nueve";
                break;
            case "22":
                hour = "diez";
                break;
            case "23":
                hour = "once";
                break;
            case "24":
                hour = "doce";
                break;
            case "00":
                hour = "doce";
                break;
            default:
                hour = "error";
                break;

        }

        return hour;
    }

    private void speak(String text) {
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }


}
