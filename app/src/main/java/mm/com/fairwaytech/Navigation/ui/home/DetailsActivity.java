package mm.com.fairwaytech.Navigation.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import mm.com.fairwaytech.Navigation.ui.RegContact.Reg1Activity;
import mm.com.fairwaytech.R;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvtitle, tvinfo, tvdate, tvtime, tvduration, tvfee, tvtrainer, tvph, tvaddress;
    private Button btnRegister;
    private Bundle bundle;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(R.string.title_detail);

        bundle = getIntent().getExtras();
        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        tvtitle = findViewById(R.id.tv_details_title);
        tvinfo = findViewById(R.id.tv_details_info);
        tvdate = findViewById(R.id.tv_details_opening_date);
        tvtime = findViewById(R.id.tv_details_opening_time);
        tvduration = findViewById(R.id.tv_details_course_duration);
        tvfee = findViewById(R.id.tv_details_course_fee);
        tvtrainer = findViewById(R.id.tv_details_course_trainer);
        tvph = findViewById(R.id.tv_details_contact_phone);
        tvaddress = findViewById(R.id.tv_details_address);

        btnRegister = findViewById(R.id.btn_details_register);
        btnRegister.setOnClickListener(this);


        readFirebaseDatabase();


        // TODO ADD PUT EXTRA TO REG1, REG2, REG3, REG4:
     }

    private void readFirebaseDatabase() {

        if (bundle != null) {

            final String docId = bundle.getString("COURSE_ID");
            // change Activity
            docRef = db.collection("fairway_data").document(docId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        DocumentSnapshot snapshot = task.getResult();

                        //  Set data


                        tvtitle.setText(snapshot.getString("title"));
                        tvinfo.setText(snapshot.getString("course_info"));
                        tvdate.setText(snapshot.getString("teach_date"));
                        tvtime.setText(snapshot.getString("teach_time"));
                        tvduration.setText(snapshot.getString("duration"));
                        tvfee.setText(snapshot.getString("course_fee"));
                        tvtrainer.setText(snapshot.getString("course_trainer"));
                        tvph.setText(snapshot.getString("contact_phone"));
                        tvaddress.setText(snapshot.getString("address"));

                        progressDialog.dismiss();

                        /* // this is not need now
                        if (snapshot.exists()) {
                            Toast.makeText(DetailsActivity.this, "DoscumentSnapshot data: " + snapshot.getData(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailsActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                        }
                        */
                    } else {
                        Toast.makeText(DetailsActivity.this, "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }

    @Override
    public void onClick(View v) {

        // TODO ADD PUT EXTRA TO REG1, REG2, REG3, REG4:

        String title = tvtitle.getText().toString();
        bundle = new Bundle();
        bundle.putString("COURSE_TITLE", title);

        Intent intent = new Intent(this, Reg1Activity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

}
