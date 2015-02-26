package jp.yujiro.app.talktoscala.analyze

import android.content.Context
import jp.yujiro.app.talktoscala.T2SVolleyWrapper
import jp.yujiro.app.talktoscala.utils.T2SSharedPreferencesManager
import jp.yujiro.app.talktoscala.implicit_conversions.T2SConversions._
import jp.yujiro.app.talktoscala.action.T2SActionRouter
import jp.yujiro.app.talktoscala.action.T2SRoutingItem

object AnalyzeUtils {

  private val IPA_DIC_BASE_FORM_NUM: Int = 0
  private val IPA_DIC_POS_NUM: Int = 4
  private val IPA_DIC_CPOS_NUM: Int = 5
  val ANARAIZE_URL = "http://133.242.238.252:8080/analyze?text=%s&action_design=%s"

  def sendAnalayzeRequest(context: Context, params: Map[String, String]): Unit = {
    val wrapper = new T2SVolleyWrapper(context)

    wrapper.sendRequest(AnalyzeUtils.ANARAIZE_URL.format(params("text"), params("action_design"))) { json =>
      val template = json.getParamOrElse("template", "すみません。データの取得に失敗してしました")
      val action = json.getParamOrElse("action", "-1").toInt
      val category = json.getParamOrElse("category", "none")
      val feelings = json.getParamOrElse("feelings", "0").toInt
      val pref = T2SSharedPreferencesManager(context)
      pref.put("tmp", template)
      pref.put("action", action)
      pref.put("ctegory", category)
      pref.put("feelings", feelings)

      println(s"tmp = $template, action = $action, category = $category")

      T2SActionRouter.routing(T2SRoutingItem(template, category, action, feelings), context)
    }
  }
}
