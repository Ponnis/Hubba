package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;

/**
 * @author Nils-Martin Robeling, Li Rönning
 */

public class HubbaModel implements IHubbaModel {
    private static HubbaModel instance = null;
    private ArrayList<IUser> users;
    private IUser currentUser;
    private UserFactory uerFactory = new UserFactory();

    public static HubbaModel getInstance() {
        if (instance == null) {
            instance = new HubbaModel();
        }
        return instance;
    }

    private HubbaModel() {
        users = new ArrayList<>();
    }

    /**
     * Returns a user based on a string Parameter for the users name
     */
    public IUser getUser(String userName) {
        int index = 0;
        for (IUser user : users) {
            if (user.getUserName().equals(userName)) {
                index = users.indexOf(user);
            }
        }
        return users.get(index);
    }

    @Override
    public IUser getNewUser(String name, String email, String password, ArrayList achievements) {
        return uerFactory.getNewUser(name,email,password,achievements);
    }

    @Override
    public IFriend getNewFriend(String name, String email, String password, ArrayList achievements) {
        return uerFactory.getNewFriend(name,email,password,achievements);
    }

    public ArrayList<IUser> getUsers() {
        return users;
    }

    public IUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(IUser user) {
        currentUser = user;
    }

    public void setUsers(ArrayList<IUser> users) {
        this.users = users;
    }

    public Themes getTheme() {
        return currentUser.getTheme();
    }

    // Law of demeter methods for themes
    public void setTheme(Themes theme) {
        currentUser.setTheme(theme);
    }

    public void addThemeListener(ThemableObserver observer) {
        currentUser.addThemeObserver(observer);
    }

    public void addUser(IUser user) {
        this.users.add(user);
    }

    public String themeEnumToString() {
        return currentUser.themeEnumToString();
    }
}

