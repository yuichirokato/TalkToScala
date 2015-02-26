package jp.yujiro.app.talktoscala.action

import android.content.Context
import android.widget.Toast

object Talk {

  def talk(context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

}
