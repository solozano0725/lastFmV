package com.valid.lastfmclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.valid.lastfmclient.R;
import com.valid.lastfmclient.model.Artist;
import com.valid.lastfmclient.utils.ImageLoader;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopArtistsAdapter extends RecyclerView.Adapter<TopArtistsAdapter.ViewHolder> {
    private List<Artist> mDataset;
    private View.OnClickListener mOnClickListener;
    private Context mContext;

    public TopArtistsAdapter(List<Artist> items, Context context, View.OnClickListener onItemClickListener) {
        this.mDataset = items;
        this.mOnClickListener = onItemClickListener;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist item = mDataset.get(position);
        ImageLoader.loadImage(mContext, item.getImageUrl(), R.drawable.ic_lastfm, holder.img_artist);
        holder.txt_artist_name.setText(item.getName());
        holder.txt_play.setText(item.getPlaycount());
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    public void setDataset(List<Artist> items) {
        this.mDataset = items;
        notifyDataSetChanged();
    }

    public Artist getItemByPosition(int position) {
        return mDataset.get(position);
    }

    public void clearDataset() {
        if(mDataset!=null){
            mDataset.clear();
            notifyDataSetChanged();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_artist)
        ImageView img_artist;
        @BindView(R.id.txt_artist_name)
        TextView txt_artist_name;
        @BindView(R.id.txt_play)
        TextView txt_play;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv_artist_item)
        void onItemClicked(View view) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(view);
            }
        }
    }

}
