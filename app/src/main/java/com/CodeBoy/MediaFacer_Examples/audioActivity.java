package com.CodeBoy.MediaFacer_Examples;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.CodeBoy.MediaFacer_Examples.fragments.fragmentAudioContents;
import com.CodeBoy.MediaFacer_Examples.fragments.fragment_audio_albums;
import com.CodeBoy.MediaFacer_Examples.fragments.fragment_audio_artists;
import com.CodeBoy.MediaFacer_Examples.fragments.fragment_audio_folders;
import com.CodeBoy.utils.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;

public class audioActivity extends AppCompatActivity {

    fragment_audio_albums allalums;
    fragment_audio_artists allartist;
    fragment_audio_folders allfolders;

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
                        allalums.firstLoad();
                        break;
                    case 2 :
                        allartist.firstLoad();
                        break;
                    case 3 :
                        allfolders.firstLoad();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class ContentPager extends FragmentPagerAdapter {

        private final int[] TAB_TITLES = new int[]{R.string.all_audio,R.string.albums,R.string.artists,R.string.folders};

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
                    allalums = (fragment_audio_albums) fragment;
                    break;
                case 2 :
                    fragment = new fragment_audio_artists();
                    allartist = (fragment_audio_artists) fragment;
                    break;
                case 3 :
                    fragment = new fragment_audio_folders();
                    allfolders = (fragment_audio_folders) fragment;
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
