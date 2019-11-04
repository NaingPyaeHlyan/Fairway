package mm.com.fairwaytech.Navigation;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import mm.com.fairwaytech.LoginActivity;
import mm.com.fairwaytech.R;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class NavigationActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private TextView tvUserName, tvEmail;
    private String user_name, user_email;
    private ImageView imageView;
    private final String ADMIN_EMAIL = "naingpyaehlyan@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_registered, R.id.nav_adminPanel, R.id.nav_registered_course, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        tvUserName = headerView.findViewById(R.id.tv_username_nav_header);
        tvEmail = headerView.findViewById(R.id.tv_email_nav_header);
        imageView = headerView.findViewById(R.id.imageVeiw_nav_header);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            user_name = user.getDisplayName();
            user_email = user.getEmail();
            Uri imgUrl = user.getPhotoUrl();
        //    String uId = user.getUid();

            if (!user_email.equals(ADMIN_EMAIL)){

                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_adminPanel).setVisible(false);
                nav_Menu.findItem(R.id.nav_registered_course).setVisible(false);
            }else {
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_adminPanel).setVisible(true);
                nav_Menu.findItem(R.id.nav_registered_course).setVisible(true);
            }

            tvUserName.setText(user_name);
            tvEmail.setText(user_email);
            Glide.with(this).load(imgUrl).into(imageView);

        }else{
            goLoginScreen();
        }

        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                signOut();return true;
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == R.id.action_sigh_out){
//            signOut();
//        }
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    private void goLoginScreen(){
        Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // check permission Phone Call and External Storage Readable
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(NavigationActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(NavigationActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission, you can not use this service \n\nPlease turn on permission at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }


//    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//
//            if (currentAccessToken == null){
//                txtname.setText("User Name");
//                txtemail.setText("example@email.com");
//                circleImageView.setImageResource(0);
//                Toast.makeText(LoginActivity.this, "User Logged Out", Toast.LENGTH_SHORT).show();
//            }else{
//                loaduserProfile(currentAccessToken);
//            }
//        }
//    };
//
//    private void loaduserProfile(AccessToken newAccessToken){
//        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                try {
//                    String first_name = object.getString("first_name");
//                    String last_name = object.getString("last_name");
//                    String email = object.getString("email");
//                    String id = object.getString("id");
//                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
//
//                    txtname.setText(first_name + " " + last_name);
//                    txtemail.setText(email);
//
//                    RequestOptions requestOptions = new RequestOptions();
//                    requestOptions.dontAnimate();
//                    Glide.with(LoginActivity.this)
//                            .load(image_url)
//                            .into(circleImageView);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "first_name,last_name,email,id");
//        request.setParameters(parameters);
//        request.executeAsync();
//
//    }

}
