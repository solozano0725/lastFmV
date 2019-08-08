package com.valid.lastfmclient.ui.top_artists.di;

import com.valid.lastfmclient.ui.top_artists.TopArtistsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TopArtistsModule.class})
public interface TopArtistsComponent {
    void inject(TopArtistsFragment target);
}
