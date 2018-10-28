package com.example.nils_martin.hubba.ViewModel;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Li Rönning
 */
public class ProfileVM extends AppCompatActivity implements ThemableObserver {

    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;

    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();

    private TextView Username;
    private TextView Email;
    private ImageView ProfilePic;
    private Button ChangePic;
    private Button EditInformation;
    private Button ChangePassword;
    private ImageButton backButton;

    /**
     * A method that tells the program what to do when this class is called.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile);

        init();
        themeHandler.addThemeListener(this);
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
        backButton = findViewById(R.id.backBtn11);
    }


    /**
     * A method that updates the textviews with the userinformation that exists
     */
    private void setUserInformation(){
        Username.setText(model.getCurrentUser().getUserName());
        Email.setText(model.getCurrentUser().getEmail());
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
        backButton.setOnClickListener(v -> onBackPressed());

    }

    /**
     * A method that sets the Profile picture to a default picture if there isn't one from the start, otherwise it sets the profilepicture to the one chosen
     */
    private void initImage() {
        if(model.getCurrentUser().getImagePath() == null){
            ProfilePic.setImageResource(R.drawable.profilepic);
        }
        else {
            ProfilePic.setImageBitmap(BitmapFactory.decodeFile(model.getCurrentUser().getImagePath()));
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
                    model.getCurrentUser().setImagePath(cursor.getString(columnIndex));
                    cursor.close();
                    ProfilePic.setImageBitmap(BitmapFactory.decodeFile(model.getCurrentUser().getImagePath()));
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

    public void save() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (User user : model.getUsers()) {
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("userName", user.getUserName());
            jsonUser.put("password", user.getPassword());
            jsonUser.put("email", user.getEmail());
            jsonUser.put("imagePath", user.getImagePath());

            JSONArray friendsList = new JSONArray();
            jsonUser.put("friendsList", friendsList);

            JSONArray habitsList = new JSONArray();
            jsonUser.put("habit", habitsList);

            JSONArray achievementsList = new JSONArray();
            jsonUser.put("achievements", achievementsList);

            jsonUser.put("theme", user.getTheme());

            jsonArray.put(jsonUser);
        }

        jsonObject.put("user", jsonArray);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("userlist", jsonObject.toString());
        editor.apply();

        for (User user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "habits", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("habitslist", habitsToJson(user));
            editor1.apply();
        }

        for (User user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "friends", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("friendslist", friendsToJson(user));
            editor1.apply();
        }


        for (User user : model.getUsers()) {
            for (IHabit habit : user.getHabits()) {
                SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + habit.getTitle() + "daysToInts", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                editor1.putString("dayToIntList", daysToDoJson(habit));
                editor1.apply();
            }
        }

        for (User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "groups", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("groupslist", groupsToJson(user));
            editor1.apply();
        }

        for (User user: model.getUsers()){
            for (Group group: user.getGroups()){
                SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + group.getGroupName() + "userInGroups", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                editor1.putString("groupFriendslist", groupFriendsToJson(group));
                editor1.apply();
            }
        }



    }

    private String habitsToJson(User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IHabit habit : model.getUser(user.getUserName()).getHabits()) {
            JSONObject jsonHabits = new JSONObject();
            jsonHabits.put("title", habit.getTitle());
            jsonHabits.put("getGroupMembersCount", habit.getGroupMembersDoneCount());
            jsonHabits.put("streak", habit.getStreak());
            jsonHabits.put("isDone", habit.getIsDone());
            jsonHabits.put("reminderOn", habit.isReminderOn());
            jsonHabits.put("state", habit.getSTATE().toString());
            jsonHabits.put("frequency", habit.getFREQUENCY());
            jsonHabits.put("daysToDoSize", habit.getDaysToDoSize());
            jsonHabits.put("previewsDayDone", habit.getPreviewsDayDone());
            jsonHabits.put("getTodayDate", habit.getTodayDate());

            JSONArray daysList = new JSONArray();
            jsonHabits.put("daysInteger", daysList);

            jsonArray.put(jsonHabits);
        }
        jsonObject.put("habit", jsonArray);
        return jsonObject.toString();
    }

    private String daysToDoJson(IHabit habit) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Integer integer : habit.getDaysToDo()) {
            JSONObject jsonDays = new JSONObject();
            jsonDays.put("daysInt", integer);
            jsonArray.put(jsonDays);
        }
        return jsonObject.put("daysToInt", jsonArray).toString();
    }

    private String friendsToJson(User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IFriend friend : model.getUser(user.getUserName()).getFriends()) {
            JSONObject jsonFriends = new JSONObject();
            jsonFriends.put("username", friend.getUserName());
            jsonArray.put(jsonFriends);
        }
        return jsonObject.put("friend", jsonArray).toString();
    }

    private String groupsToJson(User user) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Group group: model.getUser(user.getUserName()).getGroups()){
            JSONObject jsonGroup = new JSONObject();
            jsonGroup.put("groupName", group.getGroupName());

            JSONArray usersInGroup = new JSONArray();
            jsonGroup.put("usersInGroup", usersInGroup);

            jsonGroup.put("theGroupHabit", group.getHabit());
            jsonArray.put(jsonGroup);
        }
        return jsonObject.put("group", jsonArray).toString();
    }

    private String groupFriendsToJson(Group group) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IFriend iFriend: group.getUsersInGroup()){
            JSONObject jsonGroupFriends = new JSONObject();
            jsonGroupFriends.put("GroupFriendUserName", iFriend.getUserName());
            jsonArray.put(jsonGroupFriends);
        }
        return jsonObject.put("groupFriend", jsonArray).toString();
    }
}
