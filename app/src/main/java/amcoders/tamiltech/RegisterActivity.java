package amcoders.tamiltech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button LoginButton, CreateButton;
    private EditText UsernameET, EmailET, PasswordET, ConfPassET,PhoneET;
    private String username, email, password, conf_pass, phone, currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    ProgressDialog  progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        LoginButton = (Button) findViewById(R.id.register_login_btn);
        CreateButton = (Button) findViewById(R.id.create_button);
        UsernameET = (EditText) findViewById(R.id.register_username_et);
        EmailET = (EditText) findViewById(R.id.register_email_et);
        PasswordET = (EditText) findViewById(R.id.register_pass_et);
        ConfPassET = (EditText) findViewById(R.id.register_pass_et1);
        PhoneET = (EditText) findViewById(R.id.register_phone_et);
        progressDialog = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = UsernameET.getText().toString();
                email = EmailET.getText().toString();
                password = PasswordET.getText().toString();
                conf_pass = ConfPassET.getText().toString();
                phone = PhoneET.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conf_pass) || TextUtils.isEmpty(phone))
                {
                    Toast.makeText(RegisterActivity.this, "Please fill all the credentials", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (!password.equals(conf_pass))
                    {
                        Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.setTitle("Please wait");
                        progressDialog.setMessage("We are adding you to our database");
                        progressDialog.show();
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            mAuth.signInWithEmailAndPassword(email,password)
                                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                        @Override
                                                        public void onSuccess(AuthResult authResult)
                                                        {
                                                            currentUserID = mAuth.getCurrentUser().getUid();
                                                            usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
                                                            HashMap<String, Object> hashMap = new HashMap<String,Object>();
                                                            hashMap.put("username",username);
                                                            hashMap.put("email",email);
                                                            hashMap.put("phone",phone);
                                                            usersRef.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    if (task.isSuccessful())
                                                                    {
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                                                        Intent dashIntent = new Intent(RegisterActivity.this, DashboardActivity.class);
                                                                        dashIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                        startActivity(dashIntent);
                                                                    }
                                                                    else
                                                                    {
                                                                        progressDialog.dismiss();
                                                                        String msg = task.getException().getMessage();
                                                                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });

                                        }
                                        else
                                        {
                                            progressDialog.dismiss();
                                            String msg = task.getException().getMessage();
                                            Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }

                }

            }
        });



    }
}