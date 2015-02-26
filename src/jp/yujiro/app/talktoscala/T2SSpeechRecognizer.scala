package jp.yujiro.app.talktoscala

import android.content.{Intent, Context}
import android.speech.{RecognizerIntent, SpeechRecognizer}

class T2SSpeechRecognizer(context: Context) {

  val mSpeechRecognizer = {
    val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    recognizer.setRecognitionListener(new T2SRecognitionListener(context))
    recognizer
  }

  def startListening = {
    println("startListening!")
    val intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName)
    mSpeechRecognizer.startListening(intent)
  }

  def destroy = mSpeechRecognizer.destroy()
}