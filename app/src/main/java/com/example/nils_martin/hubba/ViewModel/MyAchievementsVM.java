package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Acheievement;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

import java.util.List;

/**
 * @author Johannes Gustavsson
 *
 * The VM Class for MyAchievements. Handles inputs from the user.
 */
public class MyAchievementsVM extends AppCompatActivity implements ThemableObserver {
    ThemeHandler themeHandler = new ThemeHandler();
    List<Acheievement> acheievements;
    IHubbaModel model = HubbaModel.getInstance();

    private RecyclerView rvAchivements;
    private TextView youHaveAchievedNothing;
    private ImageButton backbutton;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_myachievements);
        init();
    }

    private void init(){
        findByViewInit();
        initOnClickListeners();
        initRecyclerView();
        themeHandler.addThemeListener(this);
    }

    /**
     * Tries to get the ArrayList of Achievements from CurrentUser and sets up the AchievementAdapter
     * and recycler view.
     *
     * If achievements = null we will get a nullpointer exception and instead of the achievements we
     * show a text line.
     */
    private void initRecyclerView() {
        try {
            acheievements = model.getCurrentUser().getAcheievements();
            AchivementAdapter achivementAdapter = new AchivementAdapter(acheievements);
            rvAchivements.setAdapter(achivementAdapter);
            rvAchivements.setLayoutManager(new LinearLayoutManager(this));
        }catch (NullPointerException e){
            youHaveAchievedNothing.setText(R.string.youAchievedNothing);
        }
    }

    private void initOnClickListeners() {
        backbutton.setOnClickListener(v -> onBackPressed());
    }

    /**
     * Initiate all findViewById
     */
    private void findByViewInit() {
        backbutton =findViewById(R.id.backBtn10);
        rvAchivements = findViewById(R.id.AchievementsShowList);
        youHaveAchievedNothing = findViewById(R.id.ifNothingAchievedTextView);
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
