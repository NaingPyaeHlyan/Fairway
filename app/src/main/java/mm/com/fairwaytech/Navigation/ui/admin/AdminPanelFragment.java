package mm.com.fairwaytech.Navigation.ui.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import mm.com.fairwaytech.Adapter.AdminAdapter;
import mm.com.fairwaytech.Helper.AdminRecyclerItemTouchHelper;
import mm.com.fairwaytech.Model.CourseDataModel;
import mm.com.fairwaytech.R;

public class AdminPanelFragment extends Fragment implements View.OnClickListener, AdminRecyclerItemTouchHelper.AdminRecyclerItemTouchHelperListener {

    private static final String TAG = AdminPanelFragment.class.getName();
    private List<CourseDataModel> dataModelList;
    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;

    private FirebaseFirestore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_panel, container, false);

        recyclerView = root.findViewById(R.id.fg_admin_recyclerView);
        coordinatorLayout = root.findViewById(R.id.coordinator_layout);

        db = FirebaseFirestore.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        dataModelList = new ArrayList<>();
        adapter = new AdminAdapter(dataModelList, getContext());

        fab = root.findViewById(R.id.fg_admin_floatingActionButton);
        fab.setOnClickListener(this);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new AdminRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        showRecyclerData();

        return root;
    }

    @Override
    public void onClick(View v) {
        String string = "";
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), AdminInsertActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        bundle.putString("COURSE_ID", string);
        intent.putExtras(bundle);
        startActivity(intent);

        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void showRecyclerData() {
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


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdminAdapter.ViewHolder){
            // get the removed item name to display it in snack bar
            final String name = dataModelList.get(viewHolder.getAdapterPosition()).getDocId();

            // backup or removed item for undo purpose
            final CourseDataModel deletedItem = dataModelList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the  item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            final CountDownTimer cdtimer = new CountDownTimer(3200, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    // TODO delete
                    db.collection("fairway_data")
                            .document(name)
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(),name + " Delete Success", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            };

            cdtimer.start();


            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // TODO cancel count down timer
                    cdtimer.cancel();
                    // undo is  selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
