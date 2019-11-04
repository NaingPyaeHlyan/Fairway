package mm.com.fairwaytech.Navigation.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import java.util.ArrayList;
import java.util.List;
import mm.com.fairwaytech.Adapter.MyAdapter;
import mm.com.fairwaytech.Model.CourseDataModel;
import mm.com.fairwaytech.R;

public class HomeFragment extends Fragment {

    private List<CourseDataModel> dataModelList;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView = root.findViewById(R.id.fg_home_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        db = FirebaseFirestore.getInstance();


        // firebase push notification token key
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()){
                            Log.w("getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("\nTOKEN :", token + "\n");

                    }
                });

        dataModelList = new ArrayList<>();
        adapter = new MyAdapter(dataModelList, getContext());
        showData();

        return root;
    }


    private void showData() {
        db.collection("fairway_data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult()){

                            CourseDataModel cdm = new CourseDataModel(
                                    doc.getString("id"),
                                    doc.getString("img_url"),
                                    doc.getString("title"),
                                    doc.getString("course_info"),
                                    doc.getString("teach_date"),
                                    doc.getString("teach_time"),
                                    doc.getString("duration"),
                                    doc.getString("course_fee"),
                                    doc.getString("course_trainer"),
                                    doc.getString("course_type"),
                                    doc.getString("open_class_date"),
                                    doc.getString("contact_phone"),
                                    doc.getString("address")
                            );
                            dataModelList.add(cdm);
                        }
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}