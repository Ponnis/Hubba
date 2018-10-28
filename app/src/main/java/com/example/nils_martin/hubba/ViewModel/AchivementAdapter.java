package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Acheievement;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.R;

import java.util.List;

/**
 * @author Johannes Gustavsson
 *  A custom RecyclerViewAdapter which contains custom ViewHolder.
 */

public class AchivementAdapter extends RecyclerView.Adapter<AchivementAdapter.ViewHolder> {
    private List<Acheievement> achivements;

    public AchivementAdapter(List<Acheievement> achivements){
        this.achivements = achivements;
    }
    @NonNull
    @Override
    public AchivementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View achivementView = inflater.inflate(R.layout.achievemennt_list_item, parent, false);
        return new ViewHolder(achivementView);
    }

    @Override
    public void onBindViewHolder(@NonNull AchivementAdapter.ViewHolder viewHolder, int position) {
        Acheievement acheievement = achivements.get(position);

        TextView achivementTitle = viewHolder.achivementTitle;
        ImageView achivementPicture = viewHolder.achivementPicture;
        try {
            if (acheievement.getsAchieved()) {
                achivementPicture.setImageResource(getImage(acheievement));
                achivementTitle.setText(acheievement.getTitle());
            } else {
                achivementPicture.setImageResource(R.drawable.achivement_locked);
                achivementTitle.setText(R.string.lockedAchievement);
            }
        }catch (NullPointerException e){
            System.out.println("Nullpointerexception at AchivementAdapter when trying to set Imageview");
            System.out.println(e.toString());
            achivementPicture.setImageResource(R.drawable.achivement_locked);
        }



    }

    @Override
    public int getItemCount() {
        return achivements.size();
    }
    // Choses picture depening on Achivement type
    public int getImage(Acheievement acheievement) {
       AchievementType achievementType =  acheievement.getAchievementType();
       if (achievementType == AchievementType.NumOHabitsAchievement){
           return R.drawable.num_of_habits;
       }
       else if (achievementType == AchievementType.StreakAchievement){
            return R.drawable.streak;
       }
       else return R.drawable.profilepic;
    }
    // An custom viewHolder to be able to have more variables in the view
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView achivementTitle;
        public ImageView achivementPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            achivementTitle = (TextView) itemView.findViewById(R.id.achievementTitleText);
            achivementPicture = (ImageView) itemView.findViewById(R.id.AchivementShownImage);
        }

    }
}
