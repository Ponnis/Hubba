package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Services.IService;
import com.example.nils_martin.hubba.Services.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Rönning
 */
public class MenuGroupsVM extends AppCompatActivity implements ThemableObserver {

    private IService service = Service.getInstance();
    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();

    private List<Group> groups = new ArrayList<>();
    private ArrayList<String> groupStrings = new ArrayList<>();
    private ListView yourGroupsListView;
    private ArrayAdapter<String> yourGroupsAdapter;
    private Button addGroupButton;
    private Group openGroup;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_groups);
        themeHandler.addThemeListener(this);
        init();
    }

    /**
     * Calls on other functions that initialize ListViews, lists, OnCLicks etc.
     */
    private void init() {
        initFindByView();
        updateGroupsListView();
        addGroupOnClick();
        listViewOnClick();
        addBackOnClick();
    }

    /**
     * Initialize all view attributes
     */
    private void initFindByView() {
        yourGroupsListView = (ListView) findViewById(R.id.yourGroupsListView);
        addGroupButton = (Button) findViewById(R.id.addGroupBtn);
        backBtn =(ImageButton)findViewById(R.id.backBtn7);
    }

    /**
     * Gets the list of groups from the current user in HubbaModel.
     */
    private void getGroupsList() {
        if (model.getCurrentUser().getGroups() != null) {
            groups = model.getCurrentUser().getGroups();
        }
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

    /**
     * Gets the string from the item that is clicked and then finds a group with a name that
     * matches the item. This group is then opened on a new page.
     */
    private void listViewOnClick () {
        yourGroupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findGroup(yourGroupsListView.getItemAtPosition(position).toString());
                Intent intent = new Intent(MenuGroupsVM.this, RemoveGroupVM.class);
                intent.putExtra("GROUP", openGroup.getGroupName());
                startActivity(intent);
            }
        });
    }

    /**
     * Finds the group with the name corresponding to the string in the groups list
     * @param string is the string of a groups name
     */
    private void findGroup(String string) {
        for(Group group: groups) {
            if(group.getGroupName().equals(string)) {
                setOpenGroup(group);
            }
        }
    }
    private void addBackOnClick(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private  void setOpenGroup(Group openGroup) {
        this.openGroup = openGroup;
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

    @Override
    protected void onResume() {
        setTheme(themeHandler.getTheme());
        super.onResume();
        themeHandler.addThemeListener(this);
        updateGroupsListView();
    }

    @Override
    protected void onPause() {
        try {
            service.save(this.getApplicationContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

}
