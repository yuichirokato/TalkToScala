package jp.yujiro.app.talktoscala.action

import android.content.{Context, Intent}
import jp.yujiro.app.talktoscala.{T2SSpeechRecognizer, YoutubeActivity, T2SVolleyWrapper}
import jp.yujiro.app.talktoscala.utils.{T2SUtils, T2SSharedPreferencesManager}
import org.json.JSONObject
import jp.yujiro.app.talktoscala.implicit_conversions.T2SConversions._

object MusicAction {

  private val SEARCH_MUSIC_URL = "http://133.242.238.252:8080/music_search?status=favorite"
  private val SEARCH_YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails,statistics,status&id=%s&key=%s"
  val API_KEY = "AIzaSyBFe3CmiGLGaM5B4WLi5ded9nG0GSDQLYA"

  def searchMusic(context: Context) = {
    new T2SVolleyWrapper(context).sendRequest(SEARCH_MUSIC_URL) { json =>
      val youtubeId = json.getParamOrElse("youtubeId", "default")
      val title = json.getParamOpt[String]("title")
      val artists = json.getParamOpt[String]("artists")
      T2SSharedPreferencesManager(context).put("yt_id", youtubeId)
      sendYoutubeRequest(context, youtubeId)
    }
  }

  def playMusic(context: Context) = {
    val youtubeId = T2SSharedPreferencesManager(context).getString("yt_id", "failed")
    val intent = new Intent(context, classOf[YoutubeActivity])

    intent.putExtra("yt_id", youtubeId)
    context.startActivity(intent)
  }

  private def sendYoutubeRequest(context: Context, youtubeId: String) = {
    new T2SVolleyWrapper(context).sendRequest(SEARCH_YOUTUBE_URL.format(youtubeId, API_KEY)) { json =>
      val items = json.getJSONArray("items")
      items.getJSONObject(0).getParamOpt[JSONObject]("snippet") match {
        case Some(snippet) =>
          val title = snippet.getParamOrElse("title", "missing key title")
          val artists = snippet.getParamOrElse("artists", "missing key artists")
          Talk.talk(context, s"$title ($artists)でよろしいですか？")
          T2SUtils.actionRunAfterFewSecond(context, 2000) { c =>
            new T2SSpeechRecognizer(context).startListening
          }

        case None => println("missing key snippet")
      }
    }
  }

}
