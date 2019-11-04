package mm.com.fairwaytech.Navigation.ui.admin;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import mm.com.fairwaytech.Adapter.StudentCourseAdapter;
import mm.com.fairwaytech.Model.TitleDataModel;
import mm.com.fairwaytech.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminRegisteredCourseFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<TitleDataModel> dataModelList;
    private StudentCourseAdapter adapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_admin_registered_course, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView = root.findViewById(R.id.fg_admin_registered_course_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataModelList = new ArrayList<>();
        adapter = new StudentCourseAdapter(dataModelList, getContext());

        showCourse();

        return root;
    }

    private void showCourse() {

        db.collection("student_data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult()){
                            TitleDataModel titleDataModel = new TitleDataModel(doc.getString("course_name"));
                            dataModelList.add(titleDataModel);
                        }
                        recyclerView.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
