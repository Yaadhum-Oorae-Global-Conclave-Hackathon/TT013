package amcoders.tamiltech;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotLink;
    private Button LoginButton;
    private EditText emailET, passET;
    private Button RegisterButton;
    String email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgotLink = (TextView) findViewById(R.id.forgot_link);
        LoginButton = (Button) findViewById(R.id.login_button);
        emailET = (EditText) findViewById(R.id.login_email_et);
        passET = (EditText) findViewById(R.id.login_pass_et);
        RegisterButton = (Button) findViewById(R.id.login_register_btn);

        forgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(LoginActivity.this, "Please provide the email address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        String msg = task.getException().getMessage();
                                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}