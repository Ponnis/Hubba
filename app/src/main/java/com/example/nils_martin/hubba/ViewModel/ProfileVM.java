package com.example.nils_martin.hubba.ViewModel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

public class ProfileVM extends AppCompatActivity implements ThemableObserver {

    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;


    private HubbaModel hubbaModel = HubbaModel.getInstance();
    private Themehandler themehandler = new Themehandler();
    private TextView Username;
    private TextView Email;
    private ImageView ProfilePic;
    private Button ChangePic;
    private Button EditInformation;
    private Button ChangePassword;

    /**
     * A method that tells the program what to do when this class is called.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile);

        init();
        themehandler.addThemeListener(this);
    }

    /**
     * A method that calls other initializing methods
     */
    private void init(){
        initFindByView();
        setUserInformation();
        initButtons();
        initImage();
    }


    /**
     * A method that initializes the different objects in the View
     */
    private void initFindByView(){
        Username = findViewById(R.id.userNameTextView);
        Email = findViewById(R.id.usersEmailTextView);
        ChangePic = findViewById(R.id.changePicBtn);
        ProfilePic = findViewById(R.id.profilePicImg);
        EditInformation = findViewById(R.id.editInfBtn);
        ChangePassword = findViewById(R.id.changePassBtn);
    }


    /**
     * A method that updates the textviews with the userinformation that exists
     */
    private void setUserInformation(){
        Username.setText(hubbaModel.getCurrentUser().getUserName());
        Email.setText(hubbaModel.getCurrentUser().getEmail());
    }

    /**
     * A method that initializes the buttons with their onClickListeners
     */
    private void initButtons() {
        ChangePic.setOnClickListener(new View.OnClickListener() {

            /**
             * Gives the user the opportunity in the app to allow access to the users private pictures
             *
             * @param v
             */
            @Override
            public void onClick (View v){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                }

                try {
                    if (ActivityCompat.checkSelfPermission(ProfileVM.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProfileVM.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        EditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileVM.this, ProfileEditInformationVM.class);
                startActivity(intent);
            }
        });

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileVM.this, ProfileEditPasswordVM.class);
                startActivity(intent);
            }
        });

    }

    /**
     * A method that sets the Profile picture to a default picture if there isn't one from the start, otherwise it sets the profilepicture to the one chosen
     */
    private void initImage() {
        if(hubbaModel.getCurrentUser().getImagePath() == null){
            ProfilePic.setImageResource(R.drawable.profilepic);
        }
        else {
            ProfilePic.setImageBitmap(BitmapFactory.decodeFile(hubbaModel.getCurrentUser().getImagePath()));
        }
    }

    /**
     * A method that checks if the user has agreed that the application can access the users private photos
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                } else {
                    Toast.makeText(this, "Premission Denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * A method that sets an imported picture path to an imageView.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    hubbaModel.getCurrentUser().setImagePath(cursor.getString(columnIndex));
                    cursor.close();
                    ProfilePic.setImageBitmap(BitmapFactory.decodeFile(hubbaModel.getCurrentUser().getImagePath()));
                }
        }
    }


    @Override
    public void recreateActivity() {
        recreate();
    }

    /**
     * A method that updates the shown userinformation when the activity restarts from being paused due to starting another activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        setUserInformation();

    }
}
