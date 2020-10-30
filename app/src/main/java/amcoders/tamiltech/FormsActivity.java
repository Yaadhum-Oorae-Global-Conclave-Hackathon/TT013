package amcoders.tamiltech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class FormsActivity extends AppCompatActivity {
    private LinearLayout formLLT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
        formLLT = (LinearLayout) findViewById(R.id.form_item_llt);

        formLLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fIntent = new Intent(FormsActivity.this, FormViewActivity.class);
                startActivity(fIntent);
            }
        });
    }
}