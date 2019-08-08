package com.valid.lastfmclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.valid.lastfmclient.R;
import com.valid.lastfmclient.model.Track;
import com.valid.lastfmclient.utils.DurationConverter;
import com.valid.lastfmclient.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.ViewHolder> {
    List<Track> mDataset;
    Context mContext;
    View.OnClickListener mOnItemClickListener;

    public TopTracksAdapter(List<Track> items, Context context, View.OnClickListener onClickListener) {
        this.mDataset = items;
        this.mContext = context;
        this.mOnItemClickListener = onClickListener;
    }

    @Override
    public TopTracksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        return new TopTracksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track item = mDataset.get(position);
        ImageLoader.loadImage(mContext, item.getImageUrl(), R.drawable.ic_lastfm, holder.img_track);
        holder.txt_track_name.setText(item.getName());
        holder.txt_track_artist.setText(item.getArtist().getName());
        holder.txt_play.setText(item.getPlaycount());
        holder.txt_duration.setText(DurationConverter.getDurationInMinutesText(Long.parseLong(item.getDuration())));
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    public void setDataset(List<Track> items) {
        this.mDataset = items;
        notifyDataSetChanged();
    }

    public Track getItemAt(int position) {
        return mDataset.get(position);
    }

    public void clearDataset() {
        if (mDataset != null) {
            mDataset.clear();
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_track)
        ImageView img_track;
        @BindView(R.id.txt_track_name)
        TextView txt_track_name;
        @BindView(R.id.txt_play)
        TextView txt_play;
        @BindView(R.id.txt_track_artist)
        TextView txt_track_artist;
        @BindView(R.id.txt_duration)
        TextView txt_duration;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv_track_item)
        void onTrackClicked(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(view);
            }
        }
    }
}
