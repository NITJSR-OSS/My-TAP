package com.nitjsr.tapcell.PastRecruiters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nitjsr.tapcell.Modals.InterviewExperience;
import com.nitjsr.tapcell.R;
import com.nitjsr.tapcell.ViewInterviewExperiences.UserProfileActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class PastRecruitersAdapter extends RecyclerView.Adapter<PastRecruitersAdapter.PastRecruitersViewHolder> {

    private Context myContext;
    private List<Recruiters> imagesList;

    public PastRecruitersAdapter(Context myContext, List<Recruiters> imagesList){
        this.myContext = myContext;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public PastRecruitersAdapter.PastRecruitersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //inflating and returning our view holder
        LayoutInflater layoutInflater = LayoutInflater.from(myContext);
        View view = layoutInflater.inflate(R.layout.cardview_past_recruiters, null);

        return new PastRecruitersAdapter.PastRecruitersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PastRecruitersAdapter.PastRecruitersViewHolder pastRecruitersViewHolder, final int position) {
        final Recruiters recruiters = imagesList.get(position);

        Glide.with(myContext)
                .load(recruiters.getImages())
                .apply(new RequestOptions()
                        .fitCenter())
                .into(pastRecruitersViewHolder.recruitersImage);

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }


    public static class PastRecruitersViewHolder extends RecyclerView.ViewHolder {

        ImageView recruitersImage;
        CardView mCardView;

        public PastRecruitersViewHolder(@NonNull final View itemView) {
            super(itemView);
            recruitersImage = itemView.findViewById(R.id.recruitersImage);
            mCardView = itemView.findViewById(R.id.cardView);
        }

    }

}

