package jp.yujiro.app.talktoscala

import android.content.{Context, Intent}
import android.os.Bundle
import android.speech.{RecognitionListener, RecognitionService, SpeechRecognizer}
import android.util.Log
import android.widget.Toast

import scala.collection.JavaConversions._

/**
 * 今後の参a考に取っておく
 * 現在は使用していない
 */
class T2SRecognitionService extends RecognitionService {

  private var speechRecognizer: Option[SpeechRecognizer] = None
  private val context = this

  override def onCreate() = {
    super.onCreate()
    Log.d("SimpleVoiceService", "Service start")
  }

  override def onDestroy() = {
    super.onDestroy()
    Log.d("SimpleVoiceService", "Service stoped")
  }

  override def onCancel(listner: RecognitionService#Callback) = speechRecognizer.map(_.cancel)

  override def onStartListening(intent: Intent, listner: RecognitionService#Callback) = {
    speechRecognizer.map { recognizer =>
      recognizer.setRecognitionListener(new T2SRecognitionListener(getApplicationContext))
      recognizer.startListening(intent)
    }
  }

  override def onStopListening(listner: RecognitionService#Callback) = speechRecognizer.map(_.stopListening)

//  private class T2SRecognitionListener(listner: RecognitionService#Callback) extends RecognitionListener {
//
//    private val mUserSpecifiedListener = listner
//
//    override def onReadyForSpeech(bundle: Bundle) = {
//      showToast("音声認識完了")
//    }
//
//    override def onBeginningOfSpeech = {
//      showToast("入力開始")
//    }
//
//    override def onBufferReceived(buffer: Array[Byte]) = {
//      Log.v("onBufferReceived", "start")
//    }
//
//    override def onRmsChanged(rmsDB: Float) = {
//      Log.v("onRmsChanged", "recieve : " + rmsDB + "dB")
//    }
//
//    override def onEndOfSpeech = {
//      showToast("入力終了")
//    }
//
//    override def onError(error: Int) = error match {
//      case SpeechRecognizer.ERROR_AUDIO => showToast("音声データ保存失敗")
//      case SpeechRecognizer.ERROR_CLIENT => showToast("Android端末内のエラー")
//      case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS => showToast("権限無し")
//      case SpeechRecognizer.ERROR_NETWORK => showToast("ネットワークエラー(その他)")
//      case SpeechRecognizer.ERROR_NETWORK_TIMEOUT => showToast("ネットワークタイムアウトエラー")
//      case SpeechRecognizer.ERROR_NO_MATCH => showToast("ごめんなさいうまく聞き取れませんでした\nもう一度お願いします")
//      case SpeechRecognizer.ERROR_RECOGNIZER_BUSY => showToast("RecognitionServiceへ要求出せず")
//      case SpeechRecognizer.ERROR_SERVER => showToast("Server側からエラー通知")
//      case SpeechRecognizer.ERROR_SPEECH_TIMEOUT => showToast("ごめんなさいうまく聞き取れませんでした\nもう一度お願いします")
//    }
//
//    override def onEvent(eventType: Int, bundle: Bundle) = {
//      Log.v("onEvent", "onEvent")
//    }
//
//    override def onPartialResults(partialResults: Bundle) = {
//
//    }
//
//    override def onResults(results: Bundle) = {
//      val list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).toList
//      val getData = list.foldRight("")(_ + _)
//      showToast(getData)
//    }
//
//    private def showToast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show
//
//    private def showResult(message: String) = {
//      showToast(message)
//    }
//  }

}
