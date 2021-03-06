package jp.yujiro.app.talktoscala

import android.os.Bundle
import android.view.Window
import implicit_conversions.T2SConversions._


class MainActivity extends TypedActivity {
  val mSpeechRecognizer = new T2SSpeechRecognizer(this)

  lazy val talkBtn = findView(TR.btn_talk)

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.main)

    talkBtn.setOnClickListener(mSpeechRecognizer.startListening)
  }

  override def onDestroy() = {
    super.onDestroy()
    mSpeechRecognizer.destroy
  }
}
