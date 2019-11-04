package mm.com.fairwaytech.Navigation.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import mm.com.fairwaytech.Adapter.StudentAdapter;
import mm.com.fairwaytech.Model.StudentRegDataModel;
import mm.com.fairwaytech.Navigation.ui.PushNotification.PushNotiActivity;
import mm.com.fairwaytech.R;

public class AdminStudentInfoActivity extends AppCompatActivity {

    private List<StudentRegDataModel> studentRegDataModels;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private FirebaseFirestore db;
    private Bundle bundle;
    private String course_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_info);

        recyclerView = findViewById(R.id.admin_student_registration_recycler_view);

        db = FirebaseFirestore.getInstance();

        bundle = new Bundle();
        bundle = getIntent().getExtras();

        studentRegDataModels = new ArrayList<>();
        adapter = new StudentAdapter(studentRegDataModels, getApplicationContext());

        stdshowData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_push_noti_menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case  R.id.push_noti_menu_item:

            bundle = new Bundle();
            bundle.putString("COURSE_TITLE", course_title);

            Intent intent = new Intent(this, PushNotiActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        return super.onOptionsItemSelected(item);
    }

    private void stdshowData() {

        course_title = bundle.getString("COURSE_TITLE");

        db.collection("student_data")
                .document("course")
                .collection(course_title)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult()){

                            ArrayList<String> stdinfo = ((ArrayList<String>)doc.get("student_information"));
                            ArrayList<String> currentJob = (ArrayList<String>)doc.get("current_job");
                            ArrayList<String> wantJob = (ArrayList<String>)doc.get("want_job");
                            ArrayList<String> expertTechnology = (ArrayList<String>)doc.get("expert_technology");

                            String name = stdinfo.get(0);
                            String email = stdinfo.get(1);
                            String ph = stdinfo.get(2);
                            String address = stdinfo.get(3);

                            String cweb = currentJob.get(0);
                            String cappdev = currentJob.get(1);
                            String cmobile = currentJob.get(2);
                            String cdesigner = currentJob.get(3);
                            String cstudent = currentJob.get(4);
                            String cother = currentJob.get(5);

                            String wweb = wantJob.get(0);
                            String wsoftware = wantJob.get(1);
                            String wmobile = wantJob.get(2);
                            String wbackend = wantJob.get(3);
                            String wfrontend = wantJob.get(4);
                            String wgraphic = wantJob.get(5);
                            String wwebdesigner = wantJob.get(6);
                            String wuiux = wantJob.get(7);

                            String ehtml = expertTechnology.get(0);
                            String ejavascript = expertTechnology.get(1);
                            String ephp = expertTechnology.get(2);
                            String eruby = expertTechnology.get(3);
                            String ecsharp = expertTechnology.get(4);
                            String ejava = expertTechnology.get(5);
                            String ebasic = expertTechnology.get(6);
                            String edraw = expertTechnology.get(7);
                            String ephoto = expertTechnology.get(8);
                            String eprint = expertTechnology.get(9);
                            String efilm = expertTechnology.get(10);
                            String eillustrator = expertTechnology.get(11);
                            String eother = expertTechnology.get(12);

                            String attend_cass = doc.getString("attend_class");
                            String reg_date = doc.getString("register_date");

                            StudentRegDataModel dataModel = new StudentRegDataModel(

                                    attend_cass , name , email , ph , address , cweb , cappdev , cmobile , cdesigner , cstudent , cother , wweb , wsoftware , wmobile , wbackend , wfrontend , wgraphic , wwebdesigner , wuiux , ehtml ,
                                    ejavascript , ephp , eruby , ecsharp , ejava , ebasic , edraw , ephoto , eprint , efilm , eillustrator , eother , reg_date
                            );

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setHasFixedSize(true);
                            studentRegDataModels.add(dataModel);
                        }

                        recyclerView.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminStudentInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
