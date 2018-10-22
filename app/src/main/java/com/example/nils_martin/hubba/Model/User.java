package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class User implements Observer, Friend, IUser {

    private String userName;
    private String email;
    private String password;
    private String imagePath;
    private List<User> friends;
    private ArrayList<Habit> habits = new ArrayList<>();
    private ArrayList<Achievement> achievements;
    private ArrayList<ThemableObserver> themeObservers;
    //User Settings
    private boolean allowNotifications;
    private boolean soundOn;
    private Themes ActiveTheme;

    public User(String name, String email, String password, String imagePath) {
        this.userName = name;
        this.email = email;
        this.password = password;
        this.ActiveTheme = Themes.STANDARD;
        this.themeObservers = new ArrayList<>();
    }
    // Takes an ENUM from Themes and set
    public void setTheme(Themes theme){
        this.ActiveTheme = theme;
        notifyThemeObservers(themeObservers);

    }
   //  Returns the int R.style associated with a specific theme
    public String themeEnumToString(){
        return ActiveTheme.toString();
    }
    public Themes getTheme(){
        return ActiveTheme;
    }
    // Call recreateActivity on all that's in the Arrayist themeObservers.
    private void notifyThemeObservers(ArrayList<ThemableObserver> themeObservers){
        for (ThemableObserver theme: themeObservers) {
            theme.recreateActivity();
        }
    }
    // Adds the ThemableObserver to the observer list themeObservers.
    public void addThemeObserver(ThemableObserver observer ){
        themeObservers.add(observer);
    }


    public void addHabit (Habit habit) {
        habits.add(habit);
    }

    public void removeHabit (Habit habit) {
        habits.remove(habit);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String string){
        this.userName = string;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String string){
        this.email = string;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String string){
        this.password = string;
    }

    public void setImagePath(String string){
        this.imagePath = string;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList getHabits() {
        return habits;
    }

    public List getAchievements() {
        return achievements;
    }

    //TODO test the update method
    @Override
    public void update(Observable o, Object arg) {
        checkHabitDone();
        for (Habit habit : habits) {
            if (habit.getStreak(habit) % 5 == 0) {
                achievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, habit.getStreak(habit) + " Days!"));
            }
            if (habits.size() % 5 == 0) {
                achievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, habits.size() + " Habits!"));
            }
        }
    }


    // Adds another user to the list of friends.
    public void addFriend(User user) {
        friends.add(user);
    }

    // Finds the friend to remove in friends list and then removes the friend.
    public void removeFriend(User friend) {
        for (User user : friends) {
            if (user.userName == friend.userName) {
                friends.remove(friend);
            }
        }
    }

    public List<User> getFriends(){
        return friends;
    }

    private void checkHabitDone(){


    }


}
