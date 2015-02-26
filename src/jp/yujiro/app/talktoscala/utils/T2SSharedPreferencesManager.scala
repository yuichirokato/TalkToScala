package jp.yujiro.app.talktoscala.utils

import android.content.SharedPreferences.Editor
import android.content.{SharedPreferences, Context}

object T2SSharedPreferencesManager {

  def apply(context: Context) = new T2SSharedPreferencesManager(context)

}

class T2SSharedPreferencesManager(context: Context) {

  private val preferences = context.getSharedPreferences("tmpCache", Context.MODE_PRIVATE)

  def put[T](key: String, value: T) = value match {
    case v: String => createEditor(preferences).putString(key, v).apply()
    case v: Int => createEditor(preferences).putInt(key, v).apply()
    case v: Float => createEditor(preferences).putFloat(key, v).apply()
    case v: Long => createEditor(preferences).putLong(key, v).apply()
    case v: Boolean => createEditor(preferences).putBoolean(key, v).apply()
  }

  def getString(key: String, default: String = "none"): String = preferences.getString(key, default)

  def getInt(key: String, default: Int = -1): Int = preferences.getInt(key, default)

  def getFloat(key: String, default: Float = 0.0f): Float = preferences.getFloat(key, default)

  private def createEditor(preferences: SharedPreferences): Editor = preferences.edit()
}