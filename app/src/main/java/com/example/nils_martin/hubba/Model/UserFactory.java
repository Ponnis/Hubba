package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;

public class UserFactory {

    public IUser getNewUser(String name, String email, String password, ArrayList achievements){
        return new User(name, email, password, achievements);
    }
    public IFriend getNewFriend(String name, String email, String password, ArrayList achievements){
        return new User(name, email,password,achievements);
    }
}
