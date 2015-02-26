package jp.yujiro.app.talktoscala.utils

import java.io._

import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import android.os.{Environment, Handler}

import scala.util.control.Exception._

object T2SUtils {

  def getT2SVersionCode(context: Context): Int = {
    val pkg = catching(classOf[NameNotFoundException]) opt context.getPackageManager.getPackageInfo(context.getPackageName, 0)
    pkg.map(_.versionCode).getOrElse(0)
  }

  private def getSDCardDir = Environment.getExternalStorageDirectory().getAbsolutePath

  private def getFileAbsolutePath(fileName: String) = getSDCardDir + File.separator + fileName

  def loadTextFromAssets(fileName: String, context: Context): Either[Throwable, List[String]] = {
    val assetManager = context.getAssets
    loadText(assetManager.open(fileName))
  }

  def loadTextFromStrage(fileName: String) = {
    val path = getFileAbsolutePath(fileName)
    loadText(new FileInputStream(path))
  }

  private def loadText(is: InputStream) = {
    val inputStreamReader = catching(classOf[UnsupportedEncodingException]) either new InputStreamReader(is, "UTF-8")

    inputStreamReader match {
      case Right(isr) =>
        using(new BufferedReader(isr))(br => Right(Stream.continually(br.readLine()).takeWhile(_ != null).toList))

      case Left(t) =>
        is.close()
        Left(t)
    }
  }

  def using[T, R <: {def close()}](r: R)(f: R => T): T = try f(r) finally r.close()

  def actionRunAfterFewSecond(context: Context, second: Long)(action: Context => Unit) = {
    new Handler().postDelayed(new Runnable {
      override def run() = action(context)
    }, second)
  }

}
