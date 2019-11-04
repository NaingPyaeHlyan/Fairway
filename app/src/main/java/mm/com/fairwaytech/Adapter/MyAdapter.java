package mm.com.fairwaytech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mm.com.fairwaytech.Navigation.ui.home.DetailsActivity;
import mm.com.fairwaytech.Model.CourseDataModel;
import mm.com.fairwaytech.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
   // ArrayList courseName;
    private List<CourseDataModel> dataModels;
    private Context context;

    public MyAdapter(List<CourseDataModel> dataModels, Context context) {
        this.dataModels = dataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final CourseDataModel item = dataModels.get(position);



        Glide.with(context)
                .load(item.getImg_url())
                .error(R.drawable.fairway)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.imageView);

        holder.course.setText(item.getTitle());
        holder.type.setText(item.getType());
        holder.opendate.setText(item.getOpendate());

        // TODO ADD IMAGE
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = item.getDocId();

                // Send put extra data to DetailsActivity
                Intent intent = new Intent(context, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("COURSE_ID",id);
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
        public TextView course, opendate, type ;
        public CircleImageView imageView;
        private View rootView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            imageView = itemView.findViewById(R.id.recycler_view_image);
            course =(TextView) itemView.findViewById(R.id.recycler_view_course_title);
            type = (TextView)itemView.findViewById(R.id.recycler_view_course_type);
            opendate = (TextView)itemView.findViewById(R.id.recycler_view_open_class_date);      // this is opening time (to change)
        }
    }
}
