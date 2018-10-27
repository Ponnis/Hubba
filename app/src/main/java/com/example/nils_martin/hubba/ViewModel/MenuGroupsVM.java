package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Rönning
 */
public class MenuGroupsVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private Themehandler themehandler = new Themehandler();

    private List<Group> groups = new ArrayList<>();
    private ArrayList<String> groupStrings = new ArrayList<>();
    private ListView yourGroupsListView;
    private ArrayAdapter<String> yourGroupsAdapter;
    private Button addGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_groups);
        themehandler.addThemeListener(this);
        init();
    }

    /**
     * Calls on other functions that initialize ListViews, lists, OnCLicks etc.
     */
    private void init() {
        initFindByView();
        updateGroupsListView();
        addGroupOnClick();
    }

    /**
     * Initialize all view attributes
     */
    private void initFindByView() {
        yourGroupsListView = (ListView) findViewById(R.id.yourGroupsListView);
        addGroupButton = (Button) findViewById(R.id.addGroupBtn);
    }

    /**
     * Gets the list of groups from the current user in HubbaModel.
     */
    private void getGroupsList() {
        groups = model.getCurrentUser().getGroups();
    }

    /**
     * Calls methods that update the list and the ListView in the interface.
     */
    private void updateGroupsListView() {
        getGroupsList();
        fillGroupStringsList();
        fillGroupListView();
    }

    /**
     * First clears the list groupStrings and then updates it with the
     * names of groups in groups.
     */
    private void fillGroupStringsList() {
        groupStrings.clear();
        for (Group group : groups) {
            groupStrings.add(group.getGroupName());
        }
    }

    /**
     * Fills the ListView with strings from groupStrings.
     */
    private void fillGroupListView () {
        yourGroupsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.menu_list_item,
                groupStrings);
        yourGroupsListView.setAdapter(yourGroupsAdapter);
    }

    public void recreateActivity () {
        recreate();
    }

    private void addGroupOnClick() {
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGroupsVM.this, CreateGroupVM.class);
                startActivity(intent);
            }
        });
    }
}
