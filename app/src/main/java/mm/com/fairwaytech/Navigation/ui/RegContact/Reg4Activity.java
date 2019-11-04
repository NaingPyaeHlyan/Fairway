package mm.com.fairwaytech.Navigation.ui.RegContact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import mm.com.fairwaytech.Navigation.NavigationActivity;
import mm.com.fairwaytech.R;

public class Reg4Activity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox cbHtml, cbJavaScript, cbPhp, cbRuby, cbCSharp, cbJava, cbBasicDesign, cbDrawing, cbPhotography, cbPrint, cbFilm, cbIllustrator;
    private EditText etOther;
    private Button btnSignUp;
    private String title , name , email , phone , address ;
    private String web = "" , app = "" , mobile = "" , designer = "" , student = "" , other = "" ;
    private String web3 = "" , software = "" , mobile3 = "" , backend = "" , frontend = "" , graphic = "" , webdesigner = "" , uiux = "" ;
    private String html = "" , js = "" , php = "" , ruby = "" , vb = "" , java = "" , basicdesign = "" , drawing = "" , photography = "" , print = "" , film = "" , illustrator = "" , other4 = "" ;
    private ProgressDialog progressDialog;
    private Bundle bundle;
    private FirebaseFirestore db;
    private Map<String, Object> student_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg4);

        student_data = new HashMap<>();
        db = FirebaseFirestore.getInstance();

        cbHtml = findViewById(R.id.cbReg4Html);
        cbJavaScript = findViewById(R.id.cbReg4JavaScript);
        cbPhp = findViewById(R.id.cbReg4Php);
        cbRuby = findViewById(R.id.cbReg4Ruby);
        cbCSharp = findViewById(R.id.cbReg4CSharp);
        cbJava = findViewById(R.id.cbReg4Java);
        cbBasicDesign = findViewById(R.id.cbReg4BasicDesign);
        cbDrawing = findViewById(R.id.cbReg4Drawing);
        cbPhotography = findViewById(R.id.cbReg4Photography);
        cbPrint = findViewById(R.id.cbReg4PrintMaking);
        cbFilm = findViewById(R.id.cbReg4Film);
        cbIllustrator = findViewById(R.id.cbReg4Illustration);
        etOther = findViewById(R.id.etReg4Other);

        btnSignUp = findViewById(R.id.btnReg4SignUp);
        btnSignUp.setOnClickListener(this);

        cbHtml.setOnClickListener(this);
        cbJavaScript.setOnClickListener(this);
        cbPhp.setOnClickListener(this);
        cbRuby.setOnClickListener(this);
        cbCSharp.setOnClickListener(this);
        cbJava.setOnClickListener(this);
        cbBasicDesign.setOnClickListener(this);
        cbDrawing.setOnClickListener(this);
        cbPhotography.setOnClickListener(this);
        cbPrint.setOnClickListener(this);
        cbFilm.setOnClickListener(this);
        cbIllustrator.setOnClickListener(this);
    }


    public void getStringFromReg3(){
        bundle = getIntent().getExtras();                      // Get bundle data from Reg1Activity
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

        web3 = bundle.getString("REG3_WEB");                // Get bundle data from Reg3Activity
        software = bundle.getString("REG3_SOFTWARE");       // Get bundle data from Reg3Activity
        mobile3 = bundle.getString("REG3_MOBILE");          // Get bundle data from Reg3Activity
        backend = bundle.getString("REG3_BACKEND");         // Get bundle data from Reg3Activity
        frontend = bundle.getString("REG3_FRONTEND");       // Get bundle data from Reg3Activity
        graphic = bundle.getString("REG3_GRAPHIC");         // Get bundle data from Reg3Activity
        webdesigner = bundle.getString("REG3_GRAPHIC");     // Get bundle data from Reg3Activity
        uiux = bundle.getString("REG3_UIUX");               // Get bundle data from Reg3Activity
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReg4SignUp:                    // Button
                doForRegister();
                break;
            case R.id.cbReg4Html:
                html = "HTML/CSS";
                break;
            case R.id.cbReg4JavaScript:
                js = "JavaScript";
                break;
            case R.id.cbReg4Php:
                php = "PHP";
                break;
            case R.id.cbReg4Ruby:
                ruby = "Ruby/Python";
                break;
            case R.id.cbReg4CSharp:
                vb = "C#/.NET/VB";
                break;
            case R.id.cbReg4Java:
                java = "JAVA/Android";
                break;
            case R.id.cbReg4BasicDesign:
                basicdesign = "Basic Design";
                break;
            case R.id.cbReg4Drawing:
                drawing = "Drawing";
                break;
            case R.id.cbReg4Photography:
                photography = "Photography";
                break;
            case R.id.cbReg4PrintMaking:
                print = "Print Making";
                break;
            case R.id.cbReg4Film:
                film = "Film & Video Making";
                break;
            case R.id.cbReg4Illustration:
                illustrator = "Illustration";
                break;
        }
    }

    private void doForRegister(){

        getStringFromReg3();

        other4 = etOther.getText().toString().trim();


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("တက်ရောက်ရန်စာရင်းပေးမည်");
        dialog.setPositiveButton("ဆက်လုပ်မည်", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                progressDialog = new ProgressDialog(Reg4Activity.this);
                progressDialog.setMessage("Sending...");
                progressDialog.show();

                updateStudentData();
                sendToSubscribeTopic();


            }
        });
        dialog.setNegativeButton("မလုပ်တော့ပါ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // finish();
            }
        });
        dialog.show();
    }

    // This is SubScript with topic to FCM Server
    private void sendToSubscribeTopic(){
        String trimTitle = title.trim().replaceAll(" +","").replaceAll("&+","").replaceAll("-+","").toLowerCase();
        FirebaseMessaging.getInstance().subscribeToTopic(trimTitle).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
          //      Toast.makeText(Reg4Activity.this, "SubscribeToTopic is Success", Toast.LENGTH_SHORT).show();
                if (!task.isSuccessful()){
          //          Toast.makeText(Reg4Activity.this, "SubscribeToTopic is Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateStudentData(){

        String register_date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        String docId = UUID.randomUUID().toString();

        student_data.put("attend_class", title);       // တက်ေရာက်မည့်သင်တန်း

        student_data.put("student_information", Arrays.asList(name, email, phone, address));
        student_data.put("current_job", Arrays.asList(web, app, mobile, designer, student, other));
        student_data.put("want_job", Arrays.asList(web3, software, mobile3, backend, frontend, graphic, webdesigner, uiux));
        student_data.put("expert_technology", Arrays.asList(html, js, php, ruby, vb, java, basicdesign, drawing, photography, print, film, illustrator, other4));
        student_data.put("register_date", register_date);

        Map<String, Object> course_title = new HashMap<>();
        course_title.put("course_name", title);
        db.collection("student_data")
                .document(title)
                .set(course_title);

        db.collection("student_data")
                .document("course")
                .collection(title)
                .document(docId)
                .set(student_data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI();
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Reg4Activity.this, "Something was Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUI(){
        Intent intent = new Intent(Reg4Activity.this, NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog.dismiss();
        Toast.makeText(Reg4Activity.this, "Thank for your registered", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
