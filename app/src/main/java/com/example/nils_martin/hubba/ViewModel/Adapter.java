package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Custom adapter to get habit list items to show i listView
 */

public class Adapter extends ArrayAdapter<String>  {

    LayoutInflater layoutInflater;
    MainActivityVM mainActivityVM;
    String title;

    public Adapter(Context context, MainActivityVM mainActivityVM){
        super(context, 0);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mainActivityVM = mainActivityVM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = layoutInflater.inflate(R.layout.habit_list_item, parent, false);
        String string = getItem(position);
        title = string;
        TextView name = (TextView) view.findViewById(R.id.listItemTextView);
        name.setText(string);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityVM.findHabit(title);
                mainActivityVM.clicked(v);
            }
        });
        return view;
    }




}
