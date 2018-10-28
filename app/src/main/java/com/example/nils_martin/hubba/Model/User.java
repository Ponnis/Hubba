package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class User implements Observer, IFriend, IUser {

    private String userName;
    private String email;
    private String password;
    private String imagePath;
    private List<IFriend> friends = new ArrayList<>();
    private ArrayList<IHabit> habits = new ArrayList<>();
    private ArrayList<Acheievement> acheievements;
    private ArrayList<ThemableObserver> themeObservers;
    //User Settings
    private boolean allowNotifications;
    private boolean soundOn;
    private Themes ActiveTheme;
    private List<Group> groups = new ArrayList<>();

    public User(String name, String email, String password, ArrayList<Acheievement> achivements) {
        this.userName = name;
        this.email = email;
        this.password = password;
        this.ActiveTheme = Themes.STANDARD;
        this.themeObservers = new ArrayList<>();
        this.acheievements = achivements;
    }

    // Takes an ENUM from Themes and set
    public void setTheme(Themes theme) {
        this.ActiveTheme = theme;
        notifyThemeObservers(themeObservers);

    }

    //  Returns the int R.style associated with a specific theme
    public String themeEnumToString() {
        return ActiveTheme.toString();
    }

    public Themes getTheme() {
        return ActiveTheme;
    }

    // Call recreateActivity on all that's in the Arrayist themeObservers.
    private void notifyThemeObservers(ArrayList<ThemableObserver> themeObservers) {
        for (ThemableObserver theme : themeObservers) {
            theme.recreateActivity();
        }
    }

    // Adds the ThemableObserver to the observer list themeObservers.
    public void addThemeObserver(ThemableObserver observer) {
        themeObservers.add(observer);
    }


    public void addHabit(IHabit habit) {
        habits.add(habit);
        checkAchievements();
    }

    public void checkAchievements() {
        for (Acheievement i : acheievements) {
            i.assessAchievement();
        }
    }

    public void removeHabit(IHabit habit) {
        habits.remove(habit);
    }

    public void setHabits(ArrayList<IHabit> habitArrayList) {
        this.habits = habitArrayList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String string) {
        this.userName = string;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String string) {
        this.email = string;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String string) {
        this.password = string;
    }

    public void setImagePath(String string) {
        this.imagePath = string;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<IHabit> getHabits() {
        return habits;
    }

    public List<Acheievement> getAcheievements() {
        return acheievements;
    }

    public void setAchievements(ArrayList<Acheievement> achievementArrayList) {
        this.acheievements = achievementArrayList;
    }

    public IHabit getHabit(String string) {
        int index = 0;
        for (IHabit habit : habits) {
            if (habit.getTitle().equals(string)) {
                index = habits.indexOf(habit);
            }
        }
        return this.habits.get(index);
    }


    @Override
    public void addAchivement(Acheievement acheievement) {
        acheievements.add(acheievement);
    }


    /**
     * Finds the friend to remove in friends list and then removes the friend.
     */

    public void removeFriend(IFriend friend) {
        for (IFriend user : friends) {
            if (user.getUserName().equals(friend.getUserName())) {

                friends.remove(friend);
            }
        }
    }

    /**
     * Adds user to list of friends
     */
    public void addFriend(IFriend friend) {
        friends.add(friend);

    }


    private void checkHabitDone() {


    }

    public List<IFriend> getFriends() {
        return friends;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public void initThemableObserver() {
        themeObservers = new ArrayList<>();
    }

    public void initHabit() {
        this.habits = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
    public void setFriends(ArrayList<IFriend> iFriends){
        this.friends = iFriends;
    }

    public void initFriends(){
        this.friends = new ArrayList<>();
    }
}




