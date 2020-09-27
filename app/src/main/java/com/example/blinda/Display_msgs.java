package com.example.blinda;

import android.app.FragmentManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Display_msgs extends DialogFragment implements TextToSpeech.OnInitListener {
    TextToSpeech textToSpeech;
    TextView tv_dispay;
    IsSpeaking i;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.display_msgs, null);
        tv_dispay = (TextView) view.findViewById(R.id.tv_display);
        tv_dispay.setText(GmailModule.mOutputText);
        textToSpeech = new TextToSpeech(getActivity().getBaseContext(), this);
        i = new IsSpeaking(textToSpeech, this);
        return view;

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tv_dispay.setText("TTS  This Language is not supported");
            } else {
                //bl.setEnabled(true);
                speakOut(GmailModule.mOutputText);
                i.start();
                // dismiss();
            }

        } else {
            tv_dispay.setText("TTS Initilization Failed!");
        }
    }
    private void speakOut(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        // Shuts Down TTS
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    public void show(FragmentManager fragmentManager, Object o) {
    }
}

