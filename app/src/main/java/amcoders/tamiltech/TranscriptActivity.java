package amcoders.tamiltech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TranscriptActivity extends AppCompatActivity {
    private EditText trans_nameET,transcriptionET;
    private Button startButton, saveButton;
    private static final int RECOGNIZER_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript);

        trans_nameET = (EditText) findViewById(R.id.transcript_name_et);
        transcriptionET = (EditText) findViewById(R.id.transcript_et);
        startButton = (Button) findViewById(R.id.start_recording_btn);
        saveButton = (Button) findViewById(R.id.save_recording_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ta");
                startActivityForResult(speechIntent, RECOGNIZER_RESULT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK)
        {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            transcriptionET.setText(matches.get(0).toString());
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}