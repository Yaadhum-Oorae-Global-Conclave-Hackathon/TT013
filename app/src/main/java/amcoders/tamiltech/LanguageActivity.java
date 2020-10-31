package amcoders.tamiltech;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    private TextView EngSelect, TamSelect;
    private String language;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);


        mAuth = FirebaseAuth.getInstance();
        EngSelect = (TextView) findViewById(R.id.eng_select);
        TamSelect = (TextView) findViewById(R.id.tamil_select);

        if (mAuth.getCurrentUser() != null)
        {
            Intent dIntent = new Intent(LanguageActivity.this, DashboardActivity.class);
            dIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(dIntent);
        }

        EngSelect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                EngSelect.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                EngSelect.setTextColor(getResources().getColorStateList(R.color.white));
                TamSelect.setBackgroundTintList(getResources().getColorStateList(R.color.transparent));
                TamSelect.setTextColor(getResources().getColorStateList(R.color.grey));
                language = "en-US";
                setLocale(language);
                Intent loginIntent = new Intent(LanguageActivity.this, LoginActivity.class);
                loginIntent.putExtra("language",language);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
            }
        });
        TamSelect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                TamSelect.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                TamSelect.setTextColor(getResources().getColorStateList(R.color.white));
                EngSelect.setTextColor(getResources().getColorStateList(R.color.grey));
                EngSelect.setBackgroundTintList(getResources().getColorStateList(R.color.transparent));
                language = "ta";
                setLocale(language);
                Intent loginIntent = new Intent(LanguageActivity.this, LoginActivity.class);
                loginIntent.putExtra("language",language);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);

            }
        });
    }
    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }
    public void loadLocale()
    {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }
}