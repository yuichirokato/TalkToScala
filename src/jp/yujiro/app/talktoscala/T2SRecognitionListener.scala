package jp.yujiro.app.talktoscala

import java.net.URLEncoder

import android.content.Context
import android.os.Bundle
import android.speech.{RecognitionListener, SpeechRecognizer}
import android.util.Log
import android.widget.Toast
import jp.yujiro.app.talktoscala.action.T2SActionRouter
import jp.yujiro.app.talktoscala.analyze.AnalyzeUtils
import jp.yujiro.app.talktoscala.utils.T2SSharedPreferencesManager

import scala.collection.JavaConversions._
import scala.util.Random


class T2SRecognitionListener(context: Context) extends RecognitionListener {

  override def onReadyForSpeech(bundle: Bundle) = {
    println("音声認識準備完了")
  }

  override def onBeginningOfSpeech = {
    println("入力開始")
  }

  override def onBufferReceived(buffer: Array[Byte]) = {
    println("start")
  }

  override def onRmsChanged(rmsDB: Float) = {
    println("recieve : " + rmsDB + "dB")
  }

  override def onEndOfSpeech = {
    println("入力終了")
  }

  override def onError(error: Int) = error match {
    case SpeechRecognizer.ERROR_AUDIO => showToast("音声データ保存失敗")
    case SpeechRecognizer.ERROR_CLIENT => showToast("Android端末内のエラー")
    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS => showToast("権限無し")
    case SpeechRecognizer.ERROR_NETWORK => showToast("ネットワークエラー(その他)")
    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT => showToast("ネットワークタイムアウトエラー")
    case SpeechRecognizer.ERROR_NO_MATCH => showToast("ごめんなさいうまく聞き取れませんでした\nもう一度お願いします")
    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY => showToast("RecognitionServiceへ要求出せず")
    case SpeechRecognizer.ERROR_SERVER => showToast("Server側からエラー通知")
    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT => showToast("ごめんなさいうまく聞き取れませんでした\nもう一度お願いします")
  }

  override def onEvent(eventType: Int, bundle: Bundle) = {
    Log.v("onEvent", "onEvent")
  }

  override def onPartialResults(partialResults: Bundle) = {
    val result = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).toList
    result.foreach(r => Log.d("onPartialResults", s"r = $r"))
  }

  override def onResults(results: Bundle) = {
    val list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
    println(s"size = ${list.size()}")
    val random = if (list.size() > 1) Random.nextInt(list.size) else 0
    val encodedText = URLEncoder.encode(list(random), "UTF-8")

    val previousAction = T2SSharedPreferencesManager(context).getInt("action", -1)

    if (previousAction == T2SActionRouter.ACTION_SEARCH_MUSIC) {
      AnalyzeUtils.sendAnalayzeRequest(context, Map("text" -> encodedText, "action_design" -> "music_confirm"))
    } else {
      AnalyzeUtils.sendAnalayzeRequest(context, Map("text" -> encodedText, "action_design" -> "default"))
    }

    println(s"input = ${list(random)}")
  }

  private def showToast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show
}
