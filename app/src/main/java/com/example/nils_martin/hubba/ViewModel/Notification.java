package com.example.nils_martin.hubba.ViewModel;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.nils_martin.hubba.Model.AchivementObserver;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHubbaModel;

public class Notification extends AppCompatActivity implements AchivementObserver {
    IHubbaModel model = HubbaModel.getInstance();
    public Notification(){
        model.addAchivementListener(this);
    }
    public void alert (String title, String message){
        AlertDialog.Builder Alert = new AlertDialog.Builder(this);
        Alert.setTitle(title).setMessage(message).create();
        Alert.show().setCanceledOnTouchOutside(true);
    }

    @Override
    public void update(String achievementTitle) {
        alert("New Achivement", achievementTitle );
    }
}
