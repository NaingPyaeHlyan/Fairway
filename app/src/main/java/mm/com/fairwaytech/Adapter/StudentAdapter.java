package mm.com.fairwaytech.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mm.com.fairwaytech.Model.StudentRegDataModel;
import mm.com.fairwaytech.R;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<StudentRegDataModel> studentRegDataModels;
    private Context context;

    public StudentAdapter(List<StudentRegDataModel> studentRegDataModels, Context context) {
        this.studentRegDataModels = studentRegDataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_info_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, final int position) {
        final StudentRegDataModel dataModel = studentRegDataModels.get(position);

        holder.bind(dataModel);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean expanded = dataModel.isExpanded();
                dataModel.setExpanded(!expanded);
                notifyItemChanged(position);
            }
        });

        holder.imgBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phno = "tel:" + dataModel.getPhone();
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse(phno));
                call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                context.startActivity(call);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentRegDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvNameInside, tvCourse, tvEmail, tvPh, tvAddress, tvCurrentJob, tvWantJob, tvExpert, tvDateFormat;
        private ImageButton imgBtnCall;
        private View rootView, subItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            tvNameInside = itemView.findViewById(R.id.recycler_std_name_inside);

            tvName = itemView.findViewById(R.id.recycler_std_name);
            tvCourse = itemView.findViewById(R.id.recycler_std_course);
            tvEmail = itemView.findViewById(R.id.recycler_std_email);
            tvPh = itemView.findViewById(R.id.recycler_std_ph);
            tvAddress = itemView.findViewById(R.id.recycler_std_address);


//            tvCurrentJob = itemView.findViewById(R.id.recycler_std_current_job);
//            tvWantJob = itemView.findViewById(R.id.recycler_std_want_job);
//            tvExpert = itemView.findViewById(R.id.recycler_std_expert_technology);


            tvDateFormat = itemView.findViewById(R.id.recycler_std_date_format);
            subItem = itemView.findViewById(R.id.sub_item);

            imgBtnCall = itemView.findViewById(R.id.img_btn_recycler_std_call);
        }

        private void bind(final StudentRegDataModel dataModel){
            boolean expend = dataModel.isExpanded();
            subItem.setVisibility(expend ? View.VISIBLE : View.GONE);

            tvNameInside.setText(dataModel.getName());

            tvName.setText(dataModel.getName());
            tvCourse.setText(dataModel.getAttend_class());
            tvEmail.setText(dataModel.getEmail());
            tvPh.setText(dataModel.getPhone());
            tvAddress.setText(dataModel.getAddress());
//            tvCurrentJob.setText(dataModel.getCur_web_dev() + " " + dataModel.getCur_app_dev() + " " + dataModel.getCur_mobile_dev() + " " + dataModel.getCur_designer() + " " + dataModel.getCur_student() + " " + dataModel.getCur_other());
//            tvWantJob.setText(dataModel.getWant_web_dev() + " " + dataModel.getWant_software_dev() + " " + dataModel.getWant_mobile_dev() + " " + dataModel.getWant_backend_dev() + " " + dataModel.getWant_frontend_dev() + " " + dataModel.getWant_graphic() + " " + dataModel.getWant_web_designer() + " " + dataModel.getWant_uiux_designer());
//            tvExpert.setText(dataModel.getExpert_html() + " " + dataModel.getExpert_javascript() + " " + dataModel.getExpert_php() + " " + dataModel.getExpert_ruby() + " " + dataModel.getExpert_csharp() + " " + dataModel.getExpert_java() + " " + dataModel.getExpert_basic_design() + " " + dataModel.getExpert_drawing() + " " + dataModel.getExpert_photography() + " " + dataModel.getExpert_print() + " " + dataModel.getExpert_flim() + " " + dataModel.getExpert_illustrator() + " " + dataModel.getExpert_other());
            tvDateFormat.setText(dataModel.getRegister_date());

        }
    }
}
