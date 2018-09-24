package com.example.nils_martin.hubba;

import android.app.AlertDialog;
import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;

public class Notification extends AppCompatActivity{
    //Unknown why can't use set Icon
    public void AchievementAlert (String title, String message, Icon icon){
        AlertDialog.Builder Alert = new AlertDialog.Builder(this);
        Alert.setTitle(title).setMessage(message).create();
        Alert.show().setCanceledOnTouchOutside(true);
    }
}
