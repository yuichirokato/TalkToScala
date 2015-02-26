package jp.yujiro.app.talktoscala.action

import android.content.Context
import jp.yujiro.app.talktoscala.{TR, R, MainActivity, T2SSpeechRecognizer}
import jp.yujiro.app.talktoscala.utils.T2SUtils

case class T2SRoutingItem(text: String, category: String, action: Int, feelings: Int)

object T2SActionRouter {

  val ACTION_SEARCH_WEATHER = 1
  val ACTION_LISTENING = 2
  val ACTION_SEARCH_SOMEONE = 3
  val ACTION_SLEEP = 4
  val ACTION_STUDY = 5
  val ACTION_SEARCH_MUSIC = 6
  val ACTION_CHECK_SCHEDULE = 7
  val ACTION_PLAY_MUSIC = 8
  val ACTION_DEFAULT = -1

  val FEELINGS_SMILE = 1
  val FEELINGS_SAD = 2
  val FEELINGS_ANGRY = 3
  val FEELINGS_EMBARRASSED = 4
  val FEELINGS_NEUTRAL = 0

  def routing(routingItem: T2SRoutingItem, context: Context) = {
    routingItem.action match {
      case ACTION_SEARCH_WEATHER =>
        changeScalaWithFeelings(routingItem.feelings, context)
        WeatherAction.showWeather(context, routingItem)

      case ACTION_LISTENING =>
        changeScalaWithFeelings(routingItem.feelings, context)
        Talk.talk(context, routingItem.text)
        T2SUtils.actionRunAfterFewSecond(context, 2000)(c => new T2SSpeechRecognizer(c).startListening)

      case ACTION_SEARCH_SOMEONE => // search some one

      case ACTION_SLEEP =>
        changeScalaWithFeelings(routingItem.feelings, context)
        Talk.talk(context, routingItem.text)

      case ACTION_STUDY => // study

      case ACTION_SEARCH_MUSIC =>
        changeScalaWithFeelings(routingItem.feelings, context)
        Talk.talk(context, routingItem.text)
        T2SUtils.actionRunAfterFewSecond(context, 2000)(c => MusicAction.searchMusic(c))

      case ACTION_CHECK_SCHEDULE => // check schedule

      case ACTION_PLAY_MUSIC => MusicAction.playMusic(context)

      case _ =>
        changeScalaWithFeelings(routingItem.feelings, context)
        Talk.talk(context, routingItem.text)
    }
  }

  private def changeScalaWithFeelings(feelings: Int, context: Context) = {
    val activity = context.asInstanceOf[MainActivity]
    feelings match {
      case FEELINGS_SMILE => activity.findView(TR.imageView).setImageResource(R.drawable.scalachan_smile)
      case FEELINGS_SAD => activity.findView(TR.imageView).setImageResource(R.drawable.scalachan_smile_2)
      case FEELINGS_EMBARRASSED => activity.findView(TR.imageView).setImageResource(R.drawable.scalachan_shy_2)
      case FEELINGS_NEUTRAL => activity.findView(TR.imageView).setImageResource(R.drawable.scalachan_smile_2)
      case _=> activity.findView(TR.imageView).setImageResource(R.drawable.scalachan_smile_2)
    }
  }
}
