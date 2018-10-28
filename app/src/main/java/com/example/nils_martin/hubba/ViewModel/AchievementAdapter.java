package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.R;

import java.util.List;

/**
 * @author Johannes Gustavsson
 * A custom RecyclerViewAdapter which contains custom ViewHolder.
 */

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {
    private List<Achievement> achivements;
    private IHubbaModel model = HubbaModel.getInstance();


    public AchievementAdapter(List<Achievement> achivements) {

        this.achivements = achivements;
    }

    @NonNull
    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View achivementView = inflater.inflate(R.layout.achievemennt_list_item, parent, false);
        return new ViewHolder(achivementView);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementAdapter.ViewHolder viewHolder, int position) {
        Achievement achievement = achivements.get(position);

        TextView achivementTitle = viewHolder.achivementTitle;
        ImageView achivementPicture = viewHolder.achivementPicture;
        try {
            if (achievement.getsAchieved(model.getCurrentUser().getHabits())) {
                achivementPicture.setImageResource(getImage(achievement));
                achivementTitle.setText(achievement.getTitle());
            } else {
                achivementPicture.setImageResource(R.drawable.achivement_locked);
                achivementTitle.setText(R.string.lockedAchievement);
            }
        } catch (NullPointerException e) {
            System.out.println("Nullpointerexception at AchievementAdapter when trying to set Imageview");
            System.out.println(e.toString());
            achivementPicture.setImageResource(R.drawable.achivement_locked);
            achivementTitle.setText(R.string.lockedAchievement);
        }


    }

    @Override
    public int getItemCount() {
        return achivements.size();
    }

    // Choses picture depening on Achivement type


    public int getImage(Achievement achievement) {
        AchievementType achievementType = achievement.getAchievementType();
        if (achievementType == AchievementType.NumOHabitsAchievement) {
            return R.drawable.num_of_habits;
        } else if (achievementType == AchievementType.StreakAchievement) {

            return R.drawable.streak;
        } else return R.drawable.profilepic;
    }


    // An custom viewHolder to be able to have more variables in the view
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView achivementTitle;
        public ImageView achivementPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            achivementTitle = itemView.findViewById(R.id.achievementTitleText);
            achivementPicture = itemView.findViewById(R.id.AchivementShownImage);
        }

    }
}