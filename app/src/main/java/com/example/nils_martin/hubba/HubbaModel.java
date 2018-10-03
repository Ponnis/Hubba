package com.example.nils_martin.hubba;

import android.widget.EditText;

public class HubbaModel {
    HubbaServer hubbaServer = HubbaServer.getInstance();
    LoginView loginView = new LoginView();
    EditText currentUserName = loginView.getUsername();
    User currentUser = hubbaServer.getUser(currentUserName.toString());
}
