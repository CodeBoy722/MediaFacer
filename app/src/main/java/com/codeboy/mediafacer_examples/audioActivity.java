package com.codeboy.mediafacer_examples;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.codeboy.mediafacer_examples.fragments.fragmentAudioContents;
import com.codeboy.mediafacer_examples.fragments.fragment_audio_albums;
import com.codeboy.mediafacer_examples.fragments.fragment_audio_artists;
import com.codeboy.mediafacer_examples.fragments.fragment_audio_folders;
import com.codeboy.mediafacer_examples.fragments.fragment_audio_genre;
import com.codeboy.utils.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;

public class audioActivity extends AppCompatActivity {

    fragment_audio_albums allAlums;
    fragment_audio_artists allArtist;
    fragment_audio_folders allFolders;
    fragment_audio_genre allGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        TabLayout tabs = findViewById(R.id.audiotabs);
        ViewPager dataPager = findViewById(R.id.data_pager);
        dataPager.setOffscreenPageLimit(4);
        dataPager.setPageTransformer(true, new ZoomOutPageTransformer());
        dataPager.setAdapter(new ContentPager(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        tabs.setupWithViewPager(dataPager);

        dataPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 1 :
                        allAlums.firstLoad();
                        break;
                    case 2 :
                        allArtist.firstLoad();
                        break;
                    case 3 :
                        allFolders.firstLoad();
                        break;
                    case 4 :
                        allGenres.firstLoad();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class ContentPager extends FragmentPagerAdapter {

        private final int[] TAB_TITLES = new int[]{R.string.all_audio,R.string.albums,R.string.artists,R.string.folders,R.string.Genres};

        ContentPager(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();

            switch (position){
                case 0 :
                    fragment = new fragmentAudioContents();
                    break;
                case 1 :
                    fragment = new fragment_audio_albums();
                    allAlums = (fragment_audio_albums) fragment;
                    break;
                case 2 :
                    fragment = new fragment_audio_artists();
                    allArtist = (fragment_audio_artists) fragment;
                    break;
                case 3 :
                    fragment = new fragment_audio_folders();
                    allFolders = (fragment_audio_folders) fragment;
                    break;
                case 4 :
                    fragment = new fragment_audio_genre();
                    allGenres = (fragment_audio_genre) fragment;
                    break;
            }
            return fragment;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }
    }

}
