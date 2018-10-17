package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class User implements Observer {

    private String userName;
    private String email;
    private String password;
    private List<User> friends;
    private ArrayList<Habit> habits = new ArrayList<>();
    private ArrayList<Achievement> achievements;
    private boolean allowNotifications;
    private boolean soundOn;

    public User(String name, String email, String password) {
        this.userName = name;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public ArrayList getHabits() {
        return habits;
    }

    public void addHabit (Habit habit) {
        habits.add(habit);
    }

    public void removeHabit (Habit habit) {
        habits.remove(habit);
    }

    public List getAchievements() {
        return achievements;
    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //TODO test the update method
    @Override
    public void update(Observable o, Object arg) {
        checkHabitDone();
        for (Habit habit : habits) {
            if (habit.getStreak(habit) % 10 == 0) {
                achievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, habit.getStreak(habit) + " Days!"));
            }
            if (habits.size() % 10 == 0) {
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

    private void checkHabitDone(){


    }
}
