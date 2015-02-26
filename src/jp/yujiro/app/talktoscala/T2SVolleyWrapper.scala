package jp.yujiro.app.talktoscala

import android.content.Context
import com.android.volley.Request.Method
import com.android.volley.VolleyError
import com.android.volley.toolbox.{JsonObjectRequest, Volley}
import jp.yujiro.app.talktoscala.implicit_conversions.T2SConversions._
import org.json.JSONObject

class T2SVolleyWrapper(context: Context) {

  def sendRequest(url: String)(body: JSONObject => Unit): Unit = {
    val requestQueue = Volley.newRequestQueue(context)

    val request = new JsonObjectRequest(
      Method.GET,
      url,
      null,
      (json: JSONObject) => body(json),
      (error: VolleyError) => println(s"error = ${error.toString}")
    )

    requestQueue.add(request)
  }
}
