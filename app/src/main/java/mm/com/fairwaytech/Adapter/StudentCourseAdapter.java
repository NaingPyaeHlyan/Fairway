package mm.com.fairwaytech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;

import mm.com.fairwaytech.Navigation.ui.admin.AdminRegisteredCourseFragment;
import mm.com.fairwaytech.Navigation.ui.admin.AdminStudentInfoActivity;
import mm.com.fairwaytech.Model.TitleDataModel;
import mm.com.fairwaytech.R;

public class StudentCourseAdapter extends RecyclerView.Adapter<StudentCourseAdapter.ViewHolder> {

    private List<TitleDataModel> studentRegDataModels;
    private Context context;
    private FirebaseFirestore db;

    public StudentCourseAdapter(List<TitleDataModel> studentRegDataModels, Context context) {
        this.studentRegDataModels = studentRegDataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_course_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentCourseAdapter.ViewHolder holder, final int position) {
        final TitleDataModel item_data = studentRegDataModels.get(position);

        db = FirebaseFirestore.getInstance();

        holder.tvTitle.setText(item_data.getTitle());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("COURSE_TITLE", item_data.getTitle());

                Intent intent = new Intent(context, AdminStudentInfoActivity.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create popup menu
                PopupMenu popup = new PopupMenu(context, holder.tvDelete);
                // Inflate menu item
                popup.inflate(R.menu.admin_delete_menu_item);
                // Add click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    final String getTitle = item_data.getTitle();
                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete_menu_item:
                                // Handle item click

                                /*
                                *
                                * Delete Document
                                *
                                * */
                                db.collection("student_data")
                                        .document(getTitle)
                                        .delete();

                                /*
                                *
                                * Read
                                *
                                * */
                                db.collection("student_data")
                                        .document("course")
                                        .collection(getTitle)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (DocumentSnapshot doc: task.getResult()){

                                                    /*
                                                    *
                                                    * Delete Document in subCollection
                                                    *
                                                    * */
                                                    db.collection("student_data")
                                                            .document("course")
                                                            .collection(getTitle)
                                                            .document(doc.getId())
                                                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            studentRegDataModels.remove(position);
                                                            notifyItemRemoved(position);

                                                            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                break;
                        }
                        return false;
                    }
                });
                // Display the popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentRegDataModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDelete;
        private View root;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            root = itemView;

            tvDelete = itemView.findViewById(R.id.textView_recycler_std_course_delete);
            tvTitle = itemView.findViewById(R.id.textView_std_course_title);
        }
    }
}
