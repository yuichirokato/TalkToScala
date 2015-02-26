package jp.yujiro.app.talktoscala.implicit_conversions

import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import com.android.volley.Response.{ErrorListener, Listener}
import com.android.volley.{Response, VolleyError}
import org.json.JSONObject

object T2SConversions {

  implicit class ArrayImprovements[T](val array: Array[T]) {

    import scala.util.control.Exception._

    def getElemOpt(elemNum: Int) = catching(classOf[IndexOutOfBoundsException]) opt array(elemNum)

    def getElemOrElse(elemNum: Int, default: T) = catching(classOf[IndexOutOfBoundsException]).opt(array(elemNum)).getOrElse(default)
  }

  implicit class JSONObjectImprovements(val json: JSONObject) {

    import scala.util.control.Exception._

    def getParamOrElse[T](key: String, default: T): T = allCatch opt json.get(key).asInstanceOf[T] match {
      case Some(value) => value
      case None => default
    }

    def getParamOpt[T](key: String): Option[T] = allCatch opt json.get(key).asInstanceOf[T]
  }

//  implicit class ButtonImprovements(val button: Button) {
//
//    def onClick(body: => Unit) = button.setOnClickListener(func2ViewOnClickListener(body))
//
//    private def func2ViewOnClickListener(func: => Unit): OnClickListener = {
//      new OnClickListener {
//        override def onClick(v: View) = func
//      }
//    }
//  }

  implicit def func2Listener[T](func: T => Unit): Listener[T] = {
    new Listener[T]() {
      override def onResponse(response: T) = func(response)
    }
  }

  implicit def func2ErrorListener(errorHandler: VolleyError => Unit): Response.ErrorListener = {
    new ErrorListener() {
      override def onErrorResponse(error: VolleyError) = errorHandler(error)
    }
  }

  implicit def func2ViewOnClickListener(func: => Unit): OnClickListener = {
    new OnClickListener {
      override def onClick(v: View) = func
    }
  }
}
