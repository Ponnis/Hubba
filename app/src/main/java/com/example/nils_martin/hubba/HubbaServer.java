package com.example.nils_martin.hubba;

import java.util.ArrayList;

public class HubbaServer {

    //A server to serve as a mock up for a remote server to hold all the users and control them

    private static HubbaServer hubbaServer = null;

    private HubbaServer(){

    }

    /* get the singleton instance*/
    public static HubbaServer getInstance(){
        if (hubbaServer==null){
            hubbaServer = new HubbaServer();
        }
        return hubbaServer;
    }


}
