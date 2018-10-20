package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

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

    private void init(){
        initFindByView();
        getGroupsList();
        updateList();
    }

    private void initFindByView(){
        yourGroupsListView = (ListView) findViewById(R.id.yourGroupsListView);
        addGroupButton = (Button) findViewById(R.id.addGroupBtn);
    }

    private void getGroupsList(){
       groups = model.getCurrentUser().getGroups();
    }

    private void updateList(){
        groupStrings.clear();
        fillGroupStringsList();
        fillGroupListView();
    }

    private void fillGroupStringsList(){
        for(Group group : groups){
            groupStrings.add(group.getGroupName());
        }
    }

    private void fillGroupListView(){
        yourGroupsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.menu_list_item,
                groupStrings );
        yourGroupsListView.setAdapter(yourGroupsAdapter);
    }


    @Override
    public void recreateActivity() {
        recreate();
    }
}
