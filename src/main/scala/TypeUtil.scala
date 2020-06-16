/**
 * @Description
 * @Author Zhang Kun
 * @Date 2020-06-16 11:33
 */
object TypeUtil1 {
  implicit def str2Int(str: String): Int = str.toInt
}

object TypeUtil2 {
  implicit def str2Double(str: String): Double = str.toDouble

}

object TypeUtil3 {
  implicit def str2Boolean(str: String): Boolean = str.toBoolean
}
