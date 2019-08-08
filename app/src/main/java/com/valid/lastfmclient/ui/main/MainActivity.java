package com.valid.lastfmclient.ui.main.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.valid.lastfmclient.R;
import com.valid.lastfmclient.model.Artist;
import com.valid.lastfmclient.model.Track;
import com.valid.lastfmclient.ui.BaseFragment;
import com.valid.lastfmclient.ui.main.adapter.SectionsPagerAdapter;
import com.valid.lastfmclient.ui.top_artists.TopArtistsFragment;
import com.valid.lastfmclient.ui.top_tracks.TopTracksFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TopArtistsFragment.OnFragmentInteractionListener, TopTracksFragment.OnFragmentInteractionListener {

    @BindView(R.id.tl_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_main)
    ViewPager mViewPager;
    SectionsPagerAdapter mAdapter;
    @BindView(R.id.searchBar)
    MaterialSearchBar searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        looseSearchEditTextFocus();
        initializeFragments();

        searchEditText.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isValidSearch(searchEditText.getText().toString())) {
                    searchUser(searchEditText.getText().toString());
                } else {
                    showEnterValidUserNameToast();
                }
            }
        });
    }

    private void initializeFragments() {
        mAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void showEnterValidUserNameToast() {
        Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidSearch(String search) {
        if (TextUtils.isEmpty(search))
            return false;
        return true;
    }

    // loops the base fragments and notify them to search with the given userName
    private void searchUser(String userName) {
        for (Fragment fr : mAdapter.getFragments()
        ) {
            if (fr instanceof BaseFragment) {
                ((BaseFragment) fr).instance();
            }
        }

    }


    @Override
    public void onArtistClicked(Artist artist) {
        // open artist url
        openUrl(artist.getUrl());
    }

    @Override
    public void onTrackClicked(Track track) {
        openUrl(track.getUrl());
    }

    void openUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    // hide keyboard after search
    private void looseSearchEditTextFocus() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        searchEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }
    }
}