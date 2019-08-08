package com.valid.lastfmclient.ui.top_tracks.di;
import com.valid.lastfmclient.ui.top_tracks.TopTracksFragment;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {TopTracksModule.class})
public interface TopTracksComponent {
    void inject(TopTracksFragment target);
}
