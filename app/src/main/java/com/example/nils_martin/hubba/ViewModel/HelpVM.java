package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

public class HelpVM extends AppCompatActivity implements ThemableObserver {
    Themehandler themehandler = new Themehandler();
    ImageButton backButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_help);
        initViews();
        initOnClickListeners();
        themehandler.addThemeListener(this);
    }

    private void initViews() {
        backButton = findViewById(R.id.backBtn9);
    }

    private void initOnClickListeners() {
        backButton.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
