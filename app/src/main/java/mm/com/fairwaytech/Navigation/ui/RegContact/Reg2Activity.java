package mm.com.fairwaytech.Navigation.ui.RegContact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import mm.com.fairwaytech.R;

public class Reg2Activity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox cbWeb, cbApplication, cbMobile, cbDesigner, cbStudent;
    private EditText etOther;
    private Button btnPrevious, btnNext;
    private Bundle bundle;
    private String title = "", web = "", app = "", mobile = "", designer = "", student = "", other = "", name = "", email = "", phone = "", address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);


        cbWeb = findViewById(R.id.cbReg2WebDeveloper);
        cbApplication = findViewById(R.id.cbReg2ApplicationDeveloper);
        cbMobile = findViewById(R.id.cbReg2MobileDeveloper);
        cbDesigner = findViewById(R.id.cbReg2Designer);
        cbStudent = findViewById(R.id.cbReg2Student);
        etOther = findViewById(R.id.etReg2Other);

        btnPrevious = findViewById(R.id.btnReg2Previous);
        btnNext = findViewById(R.id.btnReg2Next);

        cbWeb.setOnClickListener(this);
        cbApplication.setOnClickListener(this);
        cbMobile.setOnClickListener(this);
        cbDesigner.setOnClickListener(this);
        cbStudent.setOnClickListener(this);

        btnPrevious.setVisibility(View.INVISIBLE);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private void getStringFromReg1(){
        bundle = getIntent().getExtras();                 // Get bundle data from Reg1Activity
        title = bundle.getString("COURSE_TITLE");
        name = bundle.getString("REG1_NAME");               // Get bundle data from Reg1Activity
        email = bundle.getString("REG1_EMAIL");             // Get bundle data from Reg1Activity
        phone = bundle.getString("REG1_PHONE");             // Get bundle data from Reg1Activity
        address = bundle.getString("REG1_ADDRESS");         // Get bundle data from Reg1Activity
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.btnReg2Previous:      // Button
                gotoReg1();
                break;
            case R.id.btnReg2Next:          // Button
                goToReg3();
                break;
            case R.id.cbReg2WebDeveloper:
                web = "Web Developer";
                break;
            case R.id.cbReg2ApplicationDeveloper:
                app = "Application Developer";
                break;
            case R.id.cbReg2MobileDeveloper:
                mobile = "Mobile Developer";
                break;
            case R.id.cbReg2Designer:
                designer = "Designer";
                break;
            case R.id.cbReg2Student:
                student = "Student";
                break;
        }
    }

    private void gotoReg1() {
        Intent intent = new Intent(Reg2Activity.this, Reg1Activity.class);
   //     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private void goToReg3(){

        getStringFromReg1();

        other = etOther.getText().toString().trim();

        Bundle extra = new Bundle();
        extra.putString("COURSE_TITLE", title);
        extra.putString("REG1_NAME", name);                          // Set bundle data from Reg1Activity to Reg3Activity
        extra.putString("REG1_EMAIL", email);                        // Set bundle data from Reg1Activity to Reg3Activity
        extra.putString("REG1_PHONE", phone);                        // Set bundle data from Reg1Activity to Reg3Activity
        extra.putString("REG1_ADDRESS", address);                    // Set bundle data from Reg1Activity to Reg3Activity

        extra.putString("REG2_WEB", web);                            // Set bundle data to Reg3Activity
        extra.putString("REG2_APP", app);                            // Set bundle data to Reg3Activity
        extra.putString("REG2_MOBILE", mobile);                      // Set bundle data to Reg3Activity
        extra.putString("REG2_DESIGNER", designer);                  // Set bundle data to Reg3Activity
        extra.putString("REG2_STUDENT", student);                    // Set bundle data to Reg3Activity
        extra.putString("REG2_OTHER", other);


        Intent intent = new Intent(Reg2Activity.this, Reg3Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(extra);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
