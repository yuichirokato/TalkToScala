package jp.yujiro.app.talktoscala.utils

object AndroidHelper {

  import android.database.Cursor

  private object EmptyCursorIter extends Iterator[Cursor]{
    def hasNext = false
    def next:Cursor = throw new java.util.NoSuchElementException()
  }

  private class CursorIter(cur: Cursor) extends Iterator[Cursor]{
    def hasNext = !cur.isLast()
    def next:Cursor =
      if (cur.moveToNext) cur
      else throw new java.util.NoSuchElementException()
  }

  implicit def cursorToIterator(cur: Cursor): Iterator[Cursor] =
    if(!cur.moveToFirst) {
      EmptyCursorIter
    }
    else {
      cur.moveToPrevious
      new CursorIter(cur)
    }
}
