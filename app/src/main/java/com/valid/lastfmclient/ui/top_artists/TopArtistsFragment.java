package com.valid.lastfmclient.ui.top_artists;

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
import com.valid.lastfmclient.contract.TopArtistsContract;
import com.valid.lastfmclient.model.Artist;
import com.valid.lastfmclient.network.Repository;
import com.valid.lastfmclient.persistence.SQLiteController;
import com.valid.lastfmclient.ui.BaseFragment;
import com.valid.lastfmclient.ui.adapter.TopArtistsAdapter;
import com.valid.lastfmclient.ui.top_artists.di.TopArtistsModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopArtistsFragment extends BaseFragment implements TopArtistsContract.TopArtistsView {
    @BindView(R.id.rvFragment)
    RecyclerView artistsRecyclerView;
    @BindView(R.id.progress)
    ProgressBar mainProgressBar;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.empty_layout)
    View emptyLayout;
    @Inject
            TopArtistsInteractor mInteractor;
    TopArtistsPresenter mPresenter;
    TopArtistsAdapter mAdapter;
    SQLiteController sqLiteController;


    public TopArtistsFragment() {
        // Required empty public constructor
    }

    @Override
    public void instance() {
        if (mAdapter != null) {
            mAdapter.clearDataset();
        }
        mInteractor = new TopArtistsInteractor(getContext());
        mPresenter = new TopArtistsPresenter(this, mInteractor);
        mPresenter.getUserTopArtists(BuildConfig.LIMIT, BuildConfig.KEYAPI);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteController = new SQLiteController(getContext());
       // DaggerTopArtistsComponent.builder().topArtistsModule(new TopArtistsModule(this)).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instance();
        mPresenter.getUserTopArtists(BuildConfig.LIMIT, BuildConfig.KEYAPI);
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
    public void updateData(List<Artist> topArtists) {
        Repository r = new Repository(getContext());
        if(r.isConnectionOn()){
            if (mAdapter == null) {
                for(Artist artist : topArtists){
                    savingData(artist);
                }
                mAdapter = new TopArtistsAdapter(topArtists, getContext(), onArtistclickedListener);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                artistsRecyclerView.setLayoutManager(linearLayoutManager);
                artistsRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.setDataset(topArtists);
            }
        }else{
            List<Artist> topArtistOff = new ArrayList<>();
            for (Artist artist : topArtists){
                topArtistOff.add(sqLiteController.getArtistDetail(artist.getName()));
            }
            mAdapter = new TopArtistsAdapter(topArtistOff, getContext(), onArtistclickedListener);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            artistsRecyclerView.setLayoutManager(layoutManager);
            artistsRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }
    }


    public void sqlitedata(List<Artist> topArtists) {
        List<Artist> topArtistOff = new ArrayList<>();
        for (Artist artist : topArtists){
            topArtistOff.add(sqLiteController.getArtistDetail(artist.getName()));
        }
        mAdapter = new TopArtistsAdapter(topArtistOff, getContext(), onArtistclickedListener);
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        artistsRecyclerView.setLayoutManager(layoutManager);
        artistsRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    //Saving data to SQLite for OFFLINE mode
    private boolean savingData(Artist artist){
        return sqLiteController.saveArtist(artist);
    }


    View.OnClickListener onArtistclickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = artistsRecyclerView.getChildLayoutPosition(view);
            Artist artist = mAdapter.getItemByPosition(position);
            if (mListener != null) {
                mListener.onArtistClicked(artist);
            }
        }
    };

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmpty() {
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidEmpty() {
        emptyLayout.setVisibility(View.GONE);

    }

    public static TopArtistsFragment newInstance() {
        return new TopArtistsFragment();
    }

    public interface OnFragmentInteractionListener {

        void onArtistClicked(Artist artist);

    }
}
