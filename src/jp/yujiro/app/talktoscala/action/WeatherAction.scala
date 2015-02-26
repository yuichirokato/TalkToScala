package jp.yujiro.app.talktoscala.action

import android.content.Context
import jp.yujiro.app.talktoscala.T2SVolleyWrapper
import jp.yujiro.app.talktoscala.implicit_conversions.T2SConversions._
import org.json.{JSONArray, JSONObject}

object WeatherAction {

  val WEATHER_CLOUDY = "曇り"
  val WEATHER_SUNNY = "晴れ"
  val WEATHER_RAINY = "雨"
  val WEATHER_FAILED = "すみません。天気の取得に失敗してしまいました・・・。"

  val STATUS_RAIN = "Rain"
  val STATUS_CLEAR = "Clear"
  val STATUS_CLOUDS = "Clouds"

  def showWeather(context: Context, routingItem: T2SRoutingItem) = {
    new LocateAction(context, routingItem).startGettingLocationWith { location =>
      val longitude = location.getLongitude
      val latitude = location.getLatitude
      val url = s"http://api.openweathermap.org/data/2.5/forecast/daily?lat=$latitude&lon=$longitude&mode=json&cnt=1"
      new T2SVolleyWrapper(context).sendRequest(url) { json =>
        println(s"result = ${json.toString}")
        val weather = getWeather(json)

        if (weather == WEATHER_FAILED) {
          Talk.talk(context, weather)
        } else {

          Talk.talk(context, routingItem.text.replace("$", weather))
        }
      }
    }
  }

  private def getWeather(json: JSONObject): String = {
    val list = json.getParamOpt[JSONArray]("list").map(_.getJSONObject(0))
    val weather = list.map(_.getJSONArray("weather").getJSONObject(0).getParamOrElse("main", "none"))

    weather match {
      case Some(w) if w == STATUS_RAIN => WEATHER_RAINY
      case Some(w) if w == STATUS_CLEAR => WEATHER_SUNNY
      case Some(w) if w == STATUS_CLOUDS => WEATHER_CLOUDY
      case _ => WEATHER_FAILED
    }
  }

}
