package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nils_martin.hubba.R;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<HabitListItemVM>  {
    public Adapter(Context context, ArrayList<HabitListItemVM> listItems){
        super(context, 0, listItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HabitListItemVM habitListItemVM = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.habit_list_item, parent, false);
        }
        TextView listItem = (TextView) convertView.findViewById(R.id.listItemTextView);
        listItem.setText(habitListItemVM.textView.toString());
        return convertView;
    }

}
