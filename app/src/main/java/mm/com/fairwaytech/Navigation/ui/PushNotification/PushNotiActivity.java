package mm.com.fairwaytech.Navigation.ui.PushNotification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import mm.com.fairwaytech.BuildConfig;
import mm.com.fairwaytech.R;

public class PushNotiActivity extends AppCompatActivity {

    private EditText et_title, et_body;
    private TextView tv_Topic;
    final private String fcm_url = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = BuildConfig.serverKey;
    final private String contentType = "application/json";
    private RequestQueue requestQueue;
    private Bundle bundle;
    private String course_title, topic;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_noti);

        et_title = findViewById(R.id.push_noti_et_title);
        et_body = findViewById(R.id.push_noti_et_body);
        tv_Topic = findViewById(R.id.tv_push_Topic);

        bundle = getIntent().getExtras();
        course_title = bundle.getString("COURSE_TITLE");
        topic = course_title.trim().replaceAll(" +","").replaceAll("&+","").replaceAll("-+", "").toLowerCase();

        tv_Topic.setText("Group\n" + course_title);
        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
    }

    // onClick
    public void sendPushNoti(View view){

        String title = et_title.getText().toString().trim();
        String body = et_body.getText().toString().trim();

        if (TextUtils.isEmpty(title)){
            Toast.makeText(this, "Enter Message Title", Toast.LENGTH_SHORT).show();
            et_title.requestFocus();
        } else if (TextUtils.isEmpty(body)) {
            Toast.makeText(this, "Enter Message Body", Toast.LENGTH_SHORT).show();
            et_body.requestFocus();
        }else {

            progressDialog.setTitle("Message");
            progressDialog.setMessage("Sending");
            progressDialog.show();

            JSONObject mainObject = new JSONObject();
            JSONObject notification = new JSONObject();
            try {
                notification.put("title", et_title.getText());
                notification.put("message", et_body.getText());

                mainObject.put("to", "/topics/" + topic);
                mainObject.put("data", notification);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            sendNotification(mainObject);
        }
    }

    private void sendNotification(JSONObject mainObject) {
        JsonObjectRequest request = new JsonObjectRequest(fcm_url, mainObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                et_title.setText("");
                et_body.setText("");
                Toast.makeText(PushNotiActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(PushNotiActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "key=" + serverKey);
                params.put("Content-Type", contentType);

                return params;
            }
        };

        requestQueue.add(request);
    }
}

// This is my Json format
//        {
//            "send_to":"/topic/channel",
//            "data": {
//              "title": "Notification title",
//              "message": "Notification message"
//        },
//            "to":["fd-Ww2O6fB0:APA91bFa6iqhVUvL_phQSCKIjL6axi6dSCOwwYibwgUgRdJURdUMN4MFA0b-65r3bX0fXfZriPt-kVGeLO3sayqV0HBgt11TNYudJjpPHqSRQpcW-ywxvxR9hMujpcdQY8VpmUMtDAsq"]
//        }
