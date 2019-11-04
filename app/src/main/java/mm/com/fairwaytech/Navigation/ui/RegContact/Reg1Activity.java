package mm.com.fairwaytech.Navigation.ui.RegContact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mm.com.fairwaytech.Navigation.NavigationActivity;
import mm.com.fairwaytech.R;

public class Reg1Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName,  etEmail, etPh, etAddress;
    private Button btnPrevious, btnNext;
    private Bundle bundle;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg1);

        bundle = getIntent().getExtras();

        etName = findViewById(R.id.etReg1Name);
        etEmail = findViewById(R.id.etReg1Email);
        etPh = findViewById(R.id.etReg1Ph);
        etAddress = findViewById(R.id.etReg1Address);

        btnPrevious = findViewById(R.id.btnReg1Previous);
        btnNext = findViewById(R.id.btnReg1Next);

        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReg1Previous:
                gotoHomePage();
                break;
            case R.id.btnReg1Next:
                gotoReg2();
                break;
        }
    }
    private void gotoHomePage() {
        Intent intent = new Intent(Reg1Activity.this, NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void gotoReg2() {

        title = bundle.getString("COURSE_TITLE");

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPh.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            etName.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Invalid: Email");
            etEmail.setFocusable(true);
        }else if (!Patterns.PHONE.matcher(phone).matches()){
            etPh.setError("Invalid: Number");
            etPh.setFocusable(true);
        }else if (TextUtils.isEmpty(address)){
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
        }else {

            Bundle bundle = new Bundle();
            bundle.putString("COURSE_TITLE", title);
            bundle.putString("REG1_NAME", name);
            bundle.putString("REG1_EMAIL", email);
            bundle.putString("REG1_PHONE", phone);
            bundle.putString("REG1_ADDRESS", address);


            Intent intent = new Intent(Reg1Activity.this, Reg2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }

}
