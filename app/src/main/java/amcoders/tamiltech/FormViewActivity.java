package amcoders.tamiltech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class FormViewActivity extends AppCompatActivity {

    private EditText FullnameET, FatherNameET, MotherNameET, AgeET, AddressET;
    private Button SubmitButton;
    private ImageView fullname_m, father_m, mother_m, age_m, address_m;
    private String fullname, fathername, mothername, age, address,currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference formRef;
   // private static final int RECOGNIZER_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_view);
        FullnameET = (EditText) findViewById(R.id.form_fullname_et);
        FatherNameET  = (EditText) findViewById(R.id.form_father_name_et);
        MotherNameET = (EditText) findViewById(R.id.form_mothername_et);
        AgeET = (EditText) findViewById(R.id.form_age_et);
        AddressET = (EditText) findViewById(R.id.form_address_et);
        SubmitButton = (Button) findViewById(R.id.form_submit_button);
        fullname_m = (ImageView) findViewById(R.id.fullname_rec);
        father_m = (ImageView) findViewById(R.id.fathername_rec);
        mother_m = (ImageView) findViewById(R.id.mothername_red);
        age_m = (ImageView) findViewById(R.id.age_rec);
        address_m = (ImageView) findViewById(R.id.address_rec);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        formRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("Forms");

        fullname_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ta");
                startActivityForResult(speechIntent, 1);
            }
        });
        father_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ta");
                startActivityForResult(speechIntent, 2);
            }
        });
        mother_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ta");
                startActivityForResult(speechIntent, 3);
            }
        });
        age_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ta");
                startActivityForResult(speechIntent, 4);
            }
        });
        address_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ta");
                startActivityForResult(speechIntent, 5);
            }
        });


        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = FullnameET.getText().toString();
                fathername = FatherNameET.getText().toString();
                mothername = MotherNameET.getText().toString();
                age = AgeET.getText().toString();
                address = AddressET.getText().toString();
                if(TextUtils.isEmpty(fullname) || TextUtils.isEmpty(fathername) || TextUtils.isEmpty(mothername)
                    || TextUtils.isEmpty(age) || TextUtils.isEmpty(address))
                {
                    Toast.makeText(FormViewActivity.this, "Please fill all the credentials", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    HashMap hashMap = new HashMap();
                    hashMap.put("fullname",fullname);
                    hashMap.put("fathername",fathername);
                    hashMap.put("mothername",mothername);
                    hashMap.put("age",age);
                    hashMap.put("address",address);
                    formRef.child("Personal_Details_Form").updateChildren(hashMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(FormViewActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else
                                    {
                                        String msg = task.getException().getMessage();
                                        Toast.makeText(FormViewActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
        {

            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (requestCode == 1)
                FullnameET.setText(matches.get(0).toString());
            if (requestCode == 2)
                FatherNameET.setText(matches.get(0).toString());
            if (requestCode == 3)
                MotherNameET.setText(matches.get(0).toString());
            if (requestCode == 4)
                AgeET.setText(matches.get(0).toString());
            if (requestCode == 5)
                AddressET.setText(matches.get(0).toString());

        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}