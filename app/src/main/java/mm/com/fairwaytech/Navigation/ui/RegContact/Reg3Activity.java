package mm.com.fairwaytech.Navigation.ui.RegContact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import mm.com.fairwaytech.R;

public class Reg3Activity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox cbWeb, cbSoftware, cbMobile, cbBackEnd, cbFrontEnd, cbGraphic, cbWebDesigner,cbUiUx;
    private Button btnPrevious, btnNext;
    private String title, name, email, phone, address, web = "", app = "", mobile = "", designer = "", student = "", other;
    private String web3 = "", software = "", mobile3 = "", backend = "", frontend = "", graphic = "", webdesigner = "", uiux = "";
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg3);

        cbWeb = findViewById(R.id.cbReg3WebDeveloper);
        cbSoftware = findViewById(R.id.cbReg3SoftwareDeveloper);
        cbMobile = findViewById(R.id.cbReg3MobileDeveloper);
        cbBackEnd = findViewById(R.id.cbReg3BackEndDeveloper);
        cbFrontEnd = findViewById(R.id.cbReg3FrontEndDeveloper);
        cbGraphic = findViewById(R.id.cbReg3GraphicDesigner);
        cbWebDesigner = findViewById(R.id.cbReg3WebDesigner);
        cbUiUx = findViewById(R.id.cbReg3UIUX);

        btnPrevious = findViewById(R.id.btnReg3Previous);
        btnNext = findViewById(R.id.btnReg3Next);

        cbWeb.setOnClickListener(this);
        cbSoftware.setOnClickListener(this);
        cbMobile.setOnClickListener(this);
        cbBackEnd.setOnClickListener(this);
        cbFrontEnd.setOnClickListener(this);
        cbGraphic.setOnClickListener(this);
        cbWebDesigner.setOnClickListener(this);
        cbUiUx.setOnClickListener(this);

        btnPrevious.setVisibility(View.INVISIBLE);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);

    }

    private void getStringFromReg2(){
        bundle = getIntent().getExtras();                 // Get bundle data from Reg1Activity
        title = bundle.getString("COURSE_TITLE");
        name = bundle.getString("REG1_NAME");               // Get bundle data from Reg1Activity
        email = bundle.getString("REG1_EMAIL");             // Get bundle data from Reg1Activity
        phone = bundle.getString("REG1_PHONE");             // Get bundle data from Reg1Activity
        address = bundle.getString("REG1_ADDRESS");         // Get bundle data from Reg1Activity

        web = bundle.getString("REG2_WEB");                 // Get bundle data from Reg2Activity
        app = bundle.getString("REG2_APP");                 // Get bundle data from Reg2Activity
        mobile = bundle.getString("REG2_MOBILE");           // Get bundle data from Reg2Activity
        designer = bundle.getString("REG2_DESIGNER");       // Get bundle data from Reg2Activity
        student = bundle.getString("REG2_STUDENT");         // Get bundle data from Reg2Activity
        other = bundle.getString("REG2_OTHER");             // Get bundle data from Reg2Activity
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReg3Previous:      // Button
                gotoReg2();
                break;
            case R.id.btnReg3Next:          // Button
                gotoReg4();
                break;
            case R.id.cbReg3WebDeveloper:
                web3 = "Web Developer";
                break;
            case R.id.cbReg3SoftwareDeveloper:
                software = "Software Developer";
                break;
            case R.id.cbReg3MobileDeveloper:
                mobile3 = "Mobile Developer";
                break;
            case R.id.cbReg3BackEndDeveloper:
                backend = "Back-End Developer";
                break;
            case R.id.cbReg3FrontEndDeveloper:
                frontend = "Front-End Developer";
                break;
            case R.id.cbReg3GraphicDesigner:
                graphic = "Graphic Designer";
                break;
            case R.id.cbReg3WebDesigner:
                webdesigner = "Web Designer";
                break;
            case R.id.cbReg3UIUX:
                uiux = "UI/UX Designer";
                break;
        }
    }

    private void gotoReg2() {
        startActivity(new Intent(Reg3Activity.this, Reg2Activity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void gotoReg4() {

        getStringFromReg2();

        Bundle extra = new Bundle();
        extra.putString("COURSE_TITLE", title);
        extra.putString("REG1_NAME", name);                          // Set bundle data from Reg1Activity to Reg4Activity
        extra.putString("REG1_EMAIL", email);                        // Set bundle data from Reg1Activity to Reg4Activity
        extra.putString("REG1_PHONE", phone);                        // Set bundle data from Reg1Activity to Reg4Activity
        extra.putString("REG1_ADDRESS", address);                    // Set bundle data from Reg1Activity to Reg4Activity

        extra.putString("REG2_WEB", web);                            // Set bundle data from Reg2Activity to Reg4Activity
        extra.putString("REG2_APP", app);                            // Set bundle data from Reg2Activity to Reg4Activity
        extra.putString("REG2_MOBILE", mobile);                      // Set bundle data from Reg2Activity to Reg4Activity
        extra.putString("REG2_DESIGNER", designer);                  // Set bundle data from Reg2Activity to Reg4Activity
        extra.putString("REG2_STUDENT", student);                    // Set bundle data from Reg2Activity to Reg4Activity
        extra.putString("REG2_OTHER", other);                        // Set bundle data from Reg2Activity to Reg4Activity

        extra.putString("REG3_WEB", web3);                           // Set bundle data from Reg3Activity to Reg4Activity
        extra.putString("REG3_SOFTWARE", software);                  // Set bundle data from Reg3Activity to Reg4Activity
        extra.putString("REG3_MOBILE", mobile3);                     // Set bundle data from Reg3Activity to Reg4Activity
        extra.putString("REG3_BACKEND", backend);                    // Set bundle data from Reg3Activity to Reg4Activity
        extra.putString("REG3_FRONTEND", frontend);                  // Set bundle data from Reg3Activity to Reg4Activity
        extra.putString("REG3_GRAPHIC", graphic);                    // Set bundle data from Reg3Activity to Reg4Activity
        extra.putString("REG3_WEBDESIGNER", webdesigner);            // Set bundle data from Reg3Activity to Reg4Activity
        extra.putString("REG3_UIUX", uiux);                          // Set bundle data from Reg3Activity to Reg4Activity


        Intent intent = new Intent(Reg3Activity.this, Reg4Activity.class);
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
