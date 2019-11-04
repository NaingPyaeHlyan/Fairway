package mm.com.fairwaytech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessageBoxActivity extends AppCompatActivity {

    String title, body;
    TextView tvTitle, tvBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_box);

        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        body = intent.getStringExtra("MESSAGE");


        tvTitle = findViewById(R.id.tv_msg_title);
        tvBody = findViewById(R.id.tv_msg_body);

        tvTitle.setText(title);
        tvBody.setText(body);
    }
}
