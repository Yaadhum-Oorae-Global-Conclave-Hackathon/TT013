package amcoders.tamiltech;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class LanguageActivity extends AppCompatActivity {

    private TextView EngSelect, TamSelect;
    private String language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        EngSelect = (TextView) findViewById(R.id.eng_select);
        TamSelect = (TextView) findViewById(R.id.tamil_select);

        EngSelect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                EngSelect.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                EngSelect.setTextColor(getResources().getColorStateList(R.color.white));
                TamSelect.setBackgroundTintList(getResources().getColorStateList(R.color.transparent));
                TamSelect.setTextColor(getResources().getColorStateList(R.color.grey));
                language = "eng";
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
                language = "tam";
                Intent loginIntent = new Intent(LanguageActivity.this, LoginActivity.class);
                loginIntent.putExtra("language",language);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);

            }
        });
    }
}