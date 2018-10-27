package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.R;

/**
 * Custom adapter to get habit list items to show i listView
 */

public class Adapter extends ArrayAdapter<String>  {

    private LayoutInflater layoutInflater;
    private MainActivityVM mainActivityVM;
    private String title;
    private TextView streakNmbrView;
    private TextView textView;
    private CheckBox checkBox;
    private ImageView streakImage;

    public Adapter(Context context, MainActivityVM mainActivityVM){
        super(context, 0);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mainActivityVM = mainActivityVM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = layoutInflater.inflate(R.layout.habit_list_item, parent, false);

        initView(view);
        initVariables(position);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityVM.findHabit(title);
                mainActivityVM.clicked(v);
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHabit habit = mainActivityVM.getHabit(title);
                if(habit.getIsDone()){
                    habit.notDone();
                    mainActivityVM.model.getCurrentUser().checkAchievements();
                    checkBox.setChecked(false);
                }else{
                    habit.isDone();
                    checkBox.setChecked(true);
                }
                mainActivityVM.checked(v);
            }
        });

        return view;
    }

    private void initVariables(int position) {
        title = getItem(position);
        IHabit currentHabit = mainActivityVM.getHabit(title);
        Integer streak = currentHabit.getStreak();
        textView.setText(title);
        setCheckbox(title);
        if(streak >= 5){
            streakNmbrView.setText(streak.toString());
            streakImage.setImageResource(R.drawable.streak);
        }else{
            streakNmbrView.setText("");
            streakImage.setImageResource(0);
        }
    }

    private void initView(View view){
        textView = view.findViewById(R.id.listItemTextView);
        checkBox = view.findViewById(R.id.checkboxIsDone);
        streakNmbrView = view.findViewById(R.id.StreakNmbrShow);
        streakImage = view.findViewById(R.id.streakImageView);
    }

    private void setCheckbox(String string){
        IHabit habit = mainActivityVM.getHabit(string);
        Boolean bool = habit.getIsDone();
        checkBox.setChecked(bool);
    }


}
