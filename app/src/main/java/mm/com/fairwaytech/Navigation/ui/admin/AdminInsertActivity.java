package mm.com.fairwaytech.Navigation.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.provider.MediaStore;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mm.com.fairwaytech.Navigation.NavigationActivity;
import mm.com.fairwaytech.R;

public class AdminInsertActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerDate, spinnerTime, spinnerDuration, spinnerFee, spinnerTrainer, spinnerType;
    private EditText etTitle, etInfo, etPh, etAddress;
    private TextView tvOpenDate;
    private Button btnCancel, btnInsert;
    private ImageButton btnCalendar;
    private ImageView imageView;
    private TextView textViewPhoto;

    private Bundle bundle;

    private DatePickerDialog datePickerDialog;
    private int dayOfMonth, month, year;
    private Calendar calendar;
    private ProgressDialog progressDialog;
    private static final int GALLERY_REQUEST_CODE = 1;
    private  Uri filePathUri;

    private Map<String, Object> data;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_insert);


        spinnerDate = findViewById(R.id.spinner_adminInsert_date);
        spinnerTime = findViewById(R.id.spinner_adminInsert_time);
        spinnerDuration = findViewById(R.id.spinner_adminInsert_duration);
        spinnerFee = findViewById(R.id.spinner_adminInsert_fee);
        spinnerTrainer = findViewById(R.id.spinner_adminInsert_trainer);
        spinnerType = findViewById(R.id.spinner_adminInsert_course_type);

        etTitle = findViewById(R.id.et_adminInsert_title);
        etInfo = findViewById(R.id.et_adminInsert_information);
        etPh = findViewById(R.id.et_adminInsert_ph);
        etAddress = findViewById(R.id.et_adminInsert_address);
        tvOpenDate = findViewById(R.id.et_adminInsert_openclass_date);

        btnCancel = findViewById(R.id.btn_adminInsert_cancel);
        btnInsert = findViewById(R.id.btn_adminInsert_insert);
        btnCalendar = findViewById(R.id.btn_adminInsert_calendar);

        imageView = findViewById(R.id.img_adminInsert_imageView);
        imageView.setOnClickListener(this);

        textViewPhoto = findViewById(R.id.et_adminInsert_selectPhoto);
        // TODO imageView request photo from phone

        db = FirebaseFirestore.getInstance();
        data = new HashMap<>();

        // for Upload image
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        bundle = getIntent().getExtras();
        String id = bundle.getString("COURSE_ID");

        btnCancel.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnCalendar.setOnClickListener(this);

        // for Update Activity
        if (!id.equals("")){
            btnInsert.setText("Update");
            setDataforUpdate();
        }
        // for Insert Activity
        else {
            btnInsert.setText("Insert");
            textViewPhoto.setText("Select Image");
        }

       // databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

    }

    // for Button Update
    private void setDataforUpdate() {
            String img_url, title, info, date, time, duration, fee, trainer, type, opendate, ph, address;

            // TODO SET IMAGE VEIW TO BUNDLE DATA

            title = bundle.getString("COURSE_TITLE");
            info = bundle.getString("COURSE_INFO");
            img_url = bundle.getString("IMAGE_URL");

//            date = bundle.getString("COURSE_DATE");
//            time = bundle.getString("COURSE_TIME");
//            duration = bundle.getString("COURSE_DURATION");
//            fee = bundle.getString("COURSE_FEE");
//            trainer = bundle.getString("COURSE_TRAINER");
//            type = bundle.getString("COURSE_TYPE");

            opendate = bundle.getString("COURSE_OPEN_DATE");
            ph = bundle.getString("COURSE_PH");
            address = bundle.getString("COURSE_ADDRESS");


        filePathUri = Uri.parse(img_url);

              Glide.with(getApplicationContext())
                    .load(filePathUri)
                    .into(imageView);

            etTitle.setText(title);
            etInfo.setText(info);
            tvOpenDate.setText(opendate);
            etPh.setText(ph);
            etAddress.setText(address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_adminInsert_imageView:
                choosePhotofromGallery();
                break;
            case R.id.btn_adminInsert_cancel:
                buttonOnClickCancel();
                break;
            case R.id.btn_adminInsert_insert:
                buttonOnClickInsert();
                break;
            case R.id.btn_adminInsert_calendar:
                datePickFromCalendar();
                break;
        }
    }

    private void datePickFromCalendar(){

        final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(AdminInsertActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String mon = MONTHS[month];

                final String day = String.valueOf(dayOfMonth);

                tvOpenDate.setText(mon + ", " + day + " ရက်နေ့ အတန်းသစ်ဖွင့်မည်");
            }
        },year, month, dayOfMonth);

        datePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvOpenDate.setText(R.string.admin_open_class);
            }
        });

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void choosePhotofromGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);      //, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //TODO: action

            filePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathUri);

                Glide.with(getApplicationContext())
                        .load(bitmap)
                        .into(imageView);

                textViewPhoto.setText("");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void buttonOnClickCancel(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Are you sure");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(AdminInsertActivity.this, NavigationActivity.class);       // Change Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void buttonOnClickInsert(){
        // TODO Insert to dabase
        // TODO need new Admin Activity (with recyclerView)

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Are you sure");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (imageView.getDrawable() == null){
                    Toast.makeText(AdminInsertActivity.this, "Choice Image", Toast.LENGTH_SHORT).show();
                    textViewPhoto.setText("Select Image");
                    textViewPhoto.requestFocus();
                    //   return;
                }else if (etTitle.getText().toString().trim().equals(null)){
                    Toast.makeText(AdminInsertActivity.this, "Enter Title", Toast.LENGTH_SHORT).show();
                    etTitle.requestFocus();
                }else if (etInfo.getText().toString().trim().equals(null)){
                    Toast.makeText(AdminInsertActivity.this, "Enter Information", Toast.LENGTH_SHORT).show();
                    etInfo.requestFocus();
                }else if (tvOpenDate.getText().toString().trim().equals(null)){
                    tvOpenDate.setText("သင်တန်းသစ် စမည့်ရက် ကြေညာပါမည်။");
                }else if (etPh.getText().toString().trim().equals(null)){
                    Toast.makeText(AdminInsertActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                    etPh.requestFocus();
                }else if (etAddress.getText().toString().trim().equals(null)){
                    Toast.makeText(AdminInsertActivity.this, "Enter you address", Toast.LENGTH_SHORT).show();
                    etAddress.requestFocus();
                }else {
                    progressDialog = new ProgressDialog(AdminInsertActivity.this);
                    progressDialog.setMessage("Uploading");
                    progressDialog.show();


                    bundle = getIntent().getExtras();
                    String id = bundle.getString("COURSE_ID");
                    if (!id.equals("")){
                        updateData();
                    }else {
                        insertData();
                    }
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }


    private void insertData(){

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            if (!filePathUri.equals(null)) {

                ref.putFile(filePathUri)                   // put image to firebase storage
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //         Toast.makeText(AdminInsertActivity.this, "Success Image", Toast.LENGTH_SHORT).show();


                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                String docId = UUID.randomUUID().toString();

                                String title = etTitle.getText().toString().trim();
                                String info = etInfo.getText().toString().trim();
                                String openDate = tvOpenDate.getText().toString().trim();
                                String phone = etPh.getText().toString().trim();
                                String address = etAddress.getText().toString().trim();

                                String date = spinnerDate.getSelectedItem().toString();
                                String time = spinnerTime.getSelectedItem().toString();
                                String duration = spinnerDuration.getSelectedItem().toString();
                                String fee = spinnerFee.getSelectedItem().toString();
                                String trainer = spinnerTrainer.getSelectedItem().toString();
                                String type = spinnerType.getSelectedItem().toString();


                                data.put("img_url", uri.toString());
                                data.put("id", docId);
                                data.put("title", title);
                                data.put("course_info", info);
                                data.put("teach_date", date);
                                data.put("teach_time", time);
                                data.put("duration", duration);
                                data.put("course_fee", fee);
                                data.put("course_trainer", trainer);
                                data.put("course_type", type);
                                data.put("open_class_date", openDate);
                                data.put("contact_phone", phone);
                                data.put("address", address);


                                // TODO imageveiw
                                // TODO add Open Class text

                                // Auto Generate Document
                                db.collection("fairway_data").document(docId)
                                        .set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                updateUI();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AdminInsertActivity.this, "Insert Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminInsertActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminInsertActivity.this, "Image : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
    }

    private void updateData(){
        bundle = getIntent().getExtras();
        final String bundledocId = bundle.getString("COURSE_ID");
        final String img_url = bundle.getString("IMAGE_URL");

        final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

        if (filePathUri.equals(Uri.parse(img_url))){
            dbCollection();
        }else {
            ref.putFile(filePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String title = etTitle.getText().toString().trim();
                            String info = etInfo.getText().toString().trim();
                            String openDate = tvOpenDate.getText().toString().trim();
                            String phone = etPh.getText().toString().trim();
                            String address = etAddress.getText().toString().trim();

                            String date = spinnerDate.getSelectedItem().toString();
                            String time = spinnerTime.getSelectedItem().toString();
                            String duration = spinnerDuration.getSelectedItem().toString();
                            String fee = spinnerFee.getSelectedItem().toString();
                            String trainer = spinnerTrainer.getSelectedItem().toString();
                            String type = spinnerType.getSelectedItem().toString();



                            //                  data.put("id", bundledocId);
                            data.put("title", title);
                            data.put("course_info", info);
                            data.put("teach_date", date);
                            data.put("teach_time", time);
                            data.put("duration", duration);
                            data.put("course_fee", fee);
                            data.put("course_trainer", trainer);
                            data.put("course_type", type);
                            data.put("open_class_date", openDate);
                            data.put("contact_phone", phone);
                            data.put("address", address);
                            data.put("img_url", uri.toString());

                            db.collection("fairway_data").document(bundledocId)
                                    .update(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            updateUI();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminInsertActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminInsertActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminInsertActivity.this, "Update : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void dbCollection(){

        final String bundledocId = bundle.getString("COURSE_ID");

        String img_url = filePathUri.toString();
        String title = etTitle.getText().toString().trim();
        String info = etInfo.getText().toString().trim();
        String openDate = tvOpenDate.getText().toString().trim();
        String phone = etPh.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        String date = spinnerDate.getSelectedItem().toString();
        String time = spinnerTime.getSelectedItem().toString();
        String duration = spinnerDuration.getSelectedItem().toString();
        String fee = spinnerFee.getSelectedItem().toString();
        String trainer = spinnerTrainer.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();


        //                  data.put("id", bundledocId);
        data.put("title", title);
        data.put("course_info", info);
        data.put("teach_date", date);
        data.put("teach_time", time);
        data.put("duration", duration);
        data.put("course_fee", fee);
        data.put("course_trainer", trainer);
        data.put("course_type", type);
        data.put("open_class_date", openDate);
        data.put("contact_phone", phone);
        data.put("address", address);
        data.put("img_url", img_url);

        db.collection("fairway_data").document(bundledocId)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateUI();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminInsertActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUI(){

        Intent intent = new Intent(AdminInsertActivity.this, NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Toast.makeText(AdminInsertActivity.this, "Success", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}