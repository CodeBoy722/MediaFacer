package com.Codeboy.MediaFacer_Examples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.Codeboy.MediaFacer_Examples.fragments.fragmentAudioContents;
import com.Codeboy.MediaFacer_Examples.fragments.fragmentPictureContent;
import com.Codeboy.MediaFacer_Examples.fragments.fragmentVideoContents;

public class videoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ViewPager dataPager = findViewById(R.id.data_pager);
        dataPager.setOffscreenPageLimit(3);
        dataPager.setAdapter(new ContentPager(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));


    }

    private class ContentPager extends FragmentPagerAdapter {
        ContentPager(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();

            switch (position){
                case 0 :
                    fragment = new fragmentPictureContent();
                    break;
                case 1 :
                    fragment = new fragmentAudioContents();
                    break;
                case 2 :
                    fragment = new fragmentVideoContents();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
