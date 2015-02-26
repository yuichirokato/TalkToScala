package jp.yujiro.app.talktoscala

import android.os.Bundle
import com.google.android.youtube.player.YouTubePlayer.Provider
import com.google.android.youtube.player.{YouTubeBaseActivity, YouTubeInitializationResult, YouTubePlayer, YouTubePlayerView}
import jp.yujiro.app.talktoscala.action.MusicAction

class YoutubeActivity extends YouTubeBaseActivity with YouTubePlayer.OnInitializedListener {

  val API_KEY = MusicAction.API_KEY

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_youtube)

    val youtubeView = findViewById(R.id.youtube_view).asInstanceOf[YouTubePlayerView]

    youtubeView.initialize(API_KEY, this)
  }

  override def onInitializationFailure(provider: Provider, errorReason: YouTubeInitializationResult) = {
    val errorMessage = String.format("ERR", errorReason.toString)
    println(s"error = $errorMessage")
  }

  override def onInitializationSuccess(provider: Provider, player: YouTubePlayer, wasRestored: Boolean) = {
    if (!wasRestored) {
      player.loadVideo(getIntent.getStringExtra("yt_id"));
    }
  }

}
