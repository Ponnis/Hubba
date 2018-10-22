package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.R;

import java.util.List;

public class AchivementAdapter extends RecyclerView.Adapter<AchivementAdapter.ViewHolder> {
    private List<Achievement> achivements;
    private String unlockedPicture = "";

    public AchivementAdapter(List<Achievement> achivements){
        this.achivements = achivements;
    }
    @NonNull
    @Override
    public AchivementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View achivementView = inflater.inflate(R.layout.achievemennt_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(achivementView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AchivementAdapter.ViewHolder viewHolder, int position) {
        Achievement achievement = achivements.get(position);

        TextView achivementTitle = viewHolder.achivementTitle;
        achivementTitle.setText(achievement.getTitle());
        ImageView achivementPicture = viewHolder.achivementPicture;
        try {
            if (achievement.getsAchieved()) {
                achivementPicture.setImageResource(getImage(achievement));
            } else {
                achivementPicture.setImageResource(R.drawable.achivement_locked);
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

    public int getImage(Achievement achievement) {
       AchievementType achievementType =  achievement.getAchievementType();
       if (achievementType == AchievementType.NumOHabitsAchievement){
           return R.drawable.streak;
       }
       else if (achievementType == AchievementType.StreakAchievement){
            return R.drawable.num_of_habits;
       }
       else return R.drawable.profilepic;
    }

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
