package com.udacity.bakingapp.ui.step;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.common.SharedViewModel;
import com.udacity.bakingapp.data.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    public static final String TAG = "StepFragment";

    private SharedViewModel sharedViewModel;
    private Step step;

    @BindView(R.id.tb_step)
    @Nullable
    Toolbar toolbar;

    @BindView(R.id.tv_step_title)
    @Nullable
    TextView tvTitle;

    @BindView(R.id.exoplayer)
    PlayerView playerView;

    @BindView(R.id.tv_description)
    @Nullable
    TextView tvDescription;

    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private long playbackPosition = 0;

    public static StepFragment newInstance() {
        Bundle args = new Bundle();

        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong("position");
            playWhenReady = savedInstanceState.getBoolean("state");
        }

        sharedViewModel.getStep().observe(this, event -> {
            if (event == null || event.peek() == null) return;
            step = event.peek();

            if (tvTitle != null)
                tvTitle.setText(step.shortDescription);

            if (tvDescription != null)
                tvDescription.setText(step.description);

            if (!TextUtils.isEmpty(step.videoURL))
                initializePlayer(step.videoURL);
            else
                playerView.setVisibility(View.GONE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        if (params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            hideSystemUi();
        }

        if (step == null || TextUtils.isEmpty(step.videoURL))
            playerView.setVisibility(View.GONE);
        else
            initializePlayer(step.videoURL);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong("position", playbackPosition);
        outState.putBoolean("state", playWhenReady);
    }

    private void initializePlayer(String url) {
        if (player == null) {
            player = new SimpleExoPlayer.Builder(getActivity()).build();
        }

        playerView.setPlayer(player);

        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(playbackPosition);

        player.prepare(mediaSource, false, false);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            playWhenReady = player.isPlaying();

            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                getActivity(),
                Util.getUserAgent(getActivity(), "Baking App")
        );

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        return mediaSource;
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }
}
