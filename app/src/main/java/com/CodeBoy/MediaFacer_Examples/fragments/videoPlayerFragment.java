package com.CodeBoy.MediaFacer_Examples.fragments;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.CodeBoy.MediaFacer.mediaDataCalculator;
import com.CodeBoy.MediaFacer.mediaHolders.videoContent;
import com.CodeBoy.MediaFacer_Examples.R;

import java.util.ArrayList;

public class videoPlayerFragment extends Fragment {

    private VideoView playZone;
    private ImageButton play;
    private SeekBar seeker;
    private TextView progress_text;
    private TextView duration;
    private ArrayList<videoContent> videos;
    private int position;
    private Handler mHandler;
    private Runnable mSeekbarPositionUpdateTask;
    private ConstraintLayout playerParent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_player,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);

        //Set MediaController  to enable play, pause, forward, etc options.
        //MediaController mediaController= new MediaController(getActivity());
        //mediaController.setAnchorView(playZone);
        //playZone.setMediaController(mediaController);

        playZone.setVideoURI(Uri.parse(videos.get(position).getAssetFileStringUri()));
        playZone.requestFocus();
        seeker.setMax((int) videos.get(position).getVideoDuration());
        playZone.start();
        startUpdatingCallbackWithPosition();
        play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));

        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                playerParent.setVisibility(View.GONE);
            }
        };
        handler.postDelayed(run,2000);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpViews(View page){
        View control = page.findViewById(R.id.control);
        playerParent = page.findViewById(R.id.player_parent);
        playZone = page.findViewById(R.id.vid_zone);
        ImageButton previous = page.findViewById(R.id.previous);
        play = page.findViewById(R.id.play);
        ImageButton next = page.findViewById(R.id.next);
        ImageButton rewind = page.findViewById(R.id.rewind);
        ImageButton forward = page.findViewById(R.id.forward);
        seeker = page.findViewById(R.id.seeker);
        progress_text = page.findViewById(R.id.progress);
        duration = page.findViewById(R.id.duration);

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerParent.getVisibility() == View.GONE){
                    playerParent.setVisibility(View.VISIBLE);
                }else {
                    playerParent.setVisibility(View.GONE);
                }
            }
        });

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playZone != null){
                    int current = playZone.getCurrentPosition();
                    //rewind 10 seconds behind
                    if(current > 10000){
                        current = current - 10000;
                    }else{
                        current = 0;
                    }

                    playZone.seekTo(current);
                    seeker.setProgress(current);
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playZone != null){
                    int current = playZone.getCurrentPosition();
                    //forward 10 seconds ahead
                    int max = (int) videos.get(position).getVideoDuration();
                    int diff = max - current;
                    if(diff > 10000){
                        current = current + 10000;
                    }else{
                        current = max;
                    }

                    playZone.seekTo(current);
                    seeker.setProgress(current);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playprevious();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playnext();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playZone.isPlaying()){
                    playZone.pause();
                    play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                }else {
                    playZone.start();
                    play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
            }
        });

        duration.setText(mediaDataCalculator.convertDuration(videos.get(position).getVideoDuration()));

        seeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int userSelectedPosition = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    userSelectedPosition = progress;
                }
                progress_text.setText(mediaDataCalculator.milliSecondsToTimer(progress));
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                playZone.seekTo(userSelectedPosition);
            }
        });

        playZone.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playZone.stopPlayback();
                stopUpdatingCallbackWithPosition();
                playnext();
            }
        });

    }

    private void playnext(){
        if(position == videos.size()-1){
            position = 0;
            playZone.setVideoURI(Uri.parse(videos.get(position).getAssetFileStringUri()));
            seeker.setMax((int) videos.get(position).getVideoDuration());
            duration.setText(mediaDataCalculator.convertDuration(videos.get(position).getVideoDuration()));
            playZone.start();
            startUpdatingCallbackWithPosition();
        }else {
            position = position + 1;
            playZone.setVideoURI(Uri.parse(videos.get(position).getAssetFileStringUri()));
            seeker.setMax((int) videos.get(position).getVideoDuration());
            duration.setText(mediaDataCalculator.convertDuration(videos.get(position).getVideoDuration()));
            playZone.start();
            startUpdatingCallbackWithPosition();
        }
    }

    private void playprevious(){
        if(position == 0){
            position = videos.size() - 1;
            playZone.setVideoURI(Uri.parse(videos.get(position).getAssetFileStringUri()));
            seeker.setMax((int) videos.get(position).getVideoDuration());
            duration.setText(mediaDataCalculator.convertDuration(videos.get(position).getVideoDuration()));
            playZone.start();
            startUpdatingCallbackWithPosition();
        }else{
            position = position - 1;
            playZone.setVideoURI(Uri.parse(videos.get(position).getAssetFileStringUri()));
            seeker.setMax((int) videos.get(position).getVideoDuration());
            duration.setText(mediaDataCalculator.convertDuration(videos.get(position).getVideoDuration()));
            playZone.start();
            startUpdatingCallbackWithPosition();
        }
    }

    public void setVideosData(ArrayList<videoContent> videos, int position){
        this.videos = videos;
        this.position = position;
    }

    private void startUpdatingCallbackWithPosition() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallbackTask();
                    mHandler.postDelayed(this, 1000);
                }
            };
            mHandler.post(mSeekbarPositionUpdateTask);
        }
    }

    private void stopUpdatingCallbackWithPosition() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mSeekbarPositionUpdateTask);
            mHandler = null;
            mSeekbarPositionUpdateTask = null;
            seeker.setProgress(0);
        }
    }

    private void updateProgressCallbackTask() {
        if (playZone != null ) {
            int currentPosition = playZone.getCurrentPosition();
            seeker.setProgress(currentPosition);
        }
    }



}
