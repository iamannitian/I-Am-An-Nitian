package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayer;


public class YoutubePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerFragment youTubePlayerFragment;
    private final String key = "AIzaSyCS2xPXQmwF38bORANCW8Out-tTbX5noWI";
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        Intent intent = getIntent();
        videoId = intent.getStringExtra("videoId");

        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.playerFragment);
        youTubePlayerFragment.initialize(key, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if(!b) {
        youTubePlayer.cueVideo(videoId);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult
            errorReason) {
        Toast.makeText(YoutubePlayer.this, "sorry unable to play the video", Toast.LENGTH_SHORT).show();
    }
}
