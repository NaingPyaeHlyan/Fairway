package mm.com.fairwaytech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import mm.com.fairwaytech.Navigation.NavigationActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.splashImage);
        handler = new Handler();

        Glide.with(this)
                .load(R.drawable.fairwayrotate)
                .into(imageView);

        flashDelay();
 }

    private void flashDelay() {
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        };
        handler.postDelayed(runnable, 2000);
    }
}
