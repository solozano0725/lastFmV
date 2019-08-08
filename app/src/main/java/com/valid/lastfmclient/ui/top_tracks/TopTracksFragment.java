package com.valid.lastfmclient.ui.top_tracks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.valid.lastfmclient.BuildConfig;
import com.valid.lastfmclient.R;
import com.valid.lastfmclient.contract.TopTrackContract;
import com.valid.lastfmclient.model.Track;
import com.valid.lastfmclient.network.Repository;
import com.valid.lastfmclient.persistence.SQLiteController;
import com.valid.lastfmclient.ui.BaseFragment;
import com.valid.lastfmclient.ui.adapter.TopTracksAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopTracksFragment extends BaseFragment implements TopTrackContract.TopTracksView {

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.rvFragment)
    RecyclerView tracksRecyclerView;
    @BindView(R.id.progress)
    ProgressBar mainProgressBar;
    @BindView(R.id.empty_layout)
    View emptyLayout;
    @Inject
    TopTracksPresenter mPresenter;
    TopTracksInteractor mInteractor;
    TopTracksAdapter mAdapter;
    SQLiteController sqLiteController;

    public TopTracksFragment() {
        // Required empty public constructor
    }

    @Override
    public void instance() {
        if (mAdapter != null) {
            mAdapter.clearDataset();
        }
        mInteractor = new TopTracksInteractor(getContext());
        mPresenter = new TopTracksPresenter(this, mInteractor);
        mPresenter.getTopTracks(BuildConfig.LIMIT, BuildConfig.KEYAPI);
    }

    public static TopTracksFragment newInstance() {
        TopTracksFragment fragment = new TopTracksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteController = new SQLiteController(getContext());
       // DaggerTopTracksComponent.builder().topTracksModule(new TopTracksModule(this)).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instance();
        mPresenter.getTopTracks(BuildConfig.LIMIT, BuildConfig.KEYAPI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        sqLiteController = new SQLiteController(getContext());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showProgress() {
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mainProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateData(List<Track> tracks) {
        Repository r = new Repository(getContext());
        if(r.isConnectionOn()){
            if (mAdapter == null) {
                for(Track track : tracks){
                    savingData(track);
                }
                mAdapter = new TopTracksAdapter(tracks, getContext(), onTrackClickedListener);
                @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                tracksRecyclerView.setLayoutManager(linearLayoutManager);
                tracksRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.setDataset(tracks);
            }
        }else{
            List<Track> topTrackOff = new ArrayList<>();
            for (Track track : tracks){
                topTrackOff.add(sqLiteController.getTrackDetail(track.getName()));
            }
            mAdapter = new TopTracksAdapter(topTrackOff, getContext(), onTrackClickedListener);
            @SuppressLint("WrongConstant") LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            tracksRecyclerView.setLayoutManager(layoutManager);
            tracksRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }

    }

    //Saving data to SQLite for OFFLINE mode
    private boolean savingData(Track track){
        return sqLiteController.saveTrack(track);
    }


    @Override
    public void showEmpty() {
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidEmpty() {
        emptyLayout.setVisibility(View.GONE);
    }

    public interface OnFragmentInteractionListener {
        void onTrackClicked(Track track);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    View.OnClickListener onTrackClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = tracksRecyclerView.getChildAdapterPosition(view);
                Track track = mAdapter.getItemAt(position);
                mListener.onTrackClicked(track);
            }
        }
    };
}
