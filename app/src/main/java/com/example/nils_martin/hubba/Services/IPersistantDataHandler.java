package com.example.nils_martin.hubba.Services;

import android.content.Context;

import org.json.JSONException;

public interface IPersistantDataHandler {

    void save(Context context) throws JSONException;

    void load (Context context) throws JSONException, NullPointerException;



}
