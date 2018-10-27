package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

import java.util.List;

public class MyAchievementsVM extends AppCompatActivity implements ThemableObserver {
    Themehandler themehandler = new Themehandler();
    List<Achievement> achievements;
    IHubbaModel model;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_myachievements);
        themehandler.addThemeListener(this);
        init();
    }

    private void init(){
        RecyclerView rvAchivements = (RecyclerView) findViewById(R.id.AchievementsShowList);
        TextView youHaveAchievedNothing = (TextView) findViewById(R.id.ifNothingAchievedTextView);
        try {
            achievements = model.getCurrentUser().getAchievements();
            AchivementAdapter achivementAdapter = new AchivementAdapter(achievements);
            rvAchivements.setAdapter(achivementAdapter);
            rvAchivements.setLayoutManager(new LinearLayoutManager(this));
        }catch (NullPointerException e){
            youHaveAchievedNothing.setText(R.string.youAchievedNothing);
        }


    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
