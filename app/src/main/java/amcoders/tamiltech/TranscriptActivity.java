package amcoders.tamiltech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TranscriptActivity extends AppCompatActivity {
    private EditText trans_nameET,transcriptionET;
    private Button startButton, saveButton;
    private static final int RECOGNIZER_RESULT = 1;
    private FirebaseAuth mAuth;
    private DatabaseReference transRef;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript);

        trans_nameET = (EditText) findViewById(R.id.transcript_name_et);
        transcriptionET = (EditText) findViewById(R.id.transcript_et);
        startButton = (Button) findViewById(R.id.start_recording_btn);
        saveButton = (Button) findViewById(R.id.save_recording_button);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        transRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title, transcription;
                title = trans_nameET.getText().toString();
                transcription = transcriptionET.getText().toString();
                if (TextUtils.isEmpty(title))
                {
                    Toast.makeText(TranscriptActivity.this, "Please provide a title for the transcript", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(transcription))
                {
                    Toast.makeText(TranscriptActivity.this, "We cannot find any transcription", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                    String format = s.format(new Date());
                    HashMap transMap = new HashMap();
                    transMap.put(title,transcription);
                    transRef.child("Transcriptions").child(format).updateChildren(transMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(TranscriptActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else
                                    {
                                        String msg = task.getException().getMessage().toString();
                                        Toast.makeText(TranscriptActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
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