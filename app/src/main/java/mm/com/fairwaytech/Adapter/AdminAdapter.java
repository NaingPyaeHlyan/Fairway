package mm.com.fairwaytech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mm.com.fairwaytech.Navigation.ui.admin.AdminInsertActivity;
import mm.com.fairwaytech.Model.CourseDataModel;
import mm.com.fairwaytech.R;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    private List<CourseDataModel> dataModels;
    private Context context;

    public AdminAdapter(List<CourseDataModel> dataModels, Context context) {
        this.dataModels = dataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_admin_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CourseDataModel item = dataModels.get(position);

        holder.course.setText(item.getTitle());
        holder.type.setText(item.getType());

        Glide.with(context)
                .load(item.getImg_url())
                .error(R.drawable.fairway)
                .into(holder.imageView);

        // TODO ADD IMAGE

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Send Image path

                String id = item.getDocId();
                String img_url = item.getImg_url();
                String title = item.getTitle();
                String info = item.getInfo();
                String date = item.getDate();
                String time = item.getTime();
                String duration = item.getDuration();
                String fee = item.getFee();
                String trainer = item.getTrainer();
                String type = item.getType();
                String openDate = item.getOpendate();
                String ph = item.getPh();
                String address = item.getAddress();

                // Send put extra data to DetailsActivity
                Intent intent = new Intent(context, AdminInsertActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("IMAGE_URL",img_url );
                bundle.putString("COURSE_ID", id);
                bundle.putString("COURSE_TITLE", title);
                bundle.putString("COURSE_INFO", info);

//                bundle.putString("COURSE_DATE",date);
//                bundle.putString("COURSE_TIME", time);
//                bundle.putString("COURSE_DURATION", duration);
//                bundle.putString("COURSE_FEE", fee);
//                bundle.putString("COURSE_TRAINER", trainer);
//                bundle.putString("COURSE_TYPE", type);

                bundle.putString("COURSE_OPEN_DATE", openDate);
                bundle.putString("COURSE_PH", ph);
                bundle.putString("COURSE_ADDRESS", address);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView course, opendate, type;
        public CircleImageView imageView;
        public RelativeLayout viewBackground, viewForeground, getViewBackgroundEdit;

        private View rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            getViewBackgroundEdit = itemView.findViewById(R.id.view_background_admin_edit);
            viewBackground = itemView.findViewById(R.id.view_background_admin_delete);
            viewForeground = itemView.findViewById(R.id.view_foreground_admin);

            imageView = itemView.findViewById(R.id.recycler_viewe_admin_imageView);
            course = itemView.findViewById(R.id.recycler_view_admin_course_title);
            type = itemView.findViewById(R.id.recycler_view_admin_course_type);
        }
    }

    public void removeItem(int position){

        dataModels.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);

    }

    public void restoreItem(CourseDataModel item, int position){

        dataModels.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
