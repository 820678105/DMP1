import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @Description
 * @Author Zhang Kun
 * @Date 2020-06-16 10:24
 */
object GitTest {
  def main(args: Array[String]): Unit = {
    //参数验证
    if (args.length !=2) {
        println("数据参数个数不正确")
        return;
    }
    val inputPath: String = args(0)
    val outputPath: String = args(1)
    //创建执行入口,设置序列化方式为kryoSerializer
    val conf: SparkConf = new SparkConf().set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val spark: SparkSession = SparkSession
      .builder()
      .config(conf)
      .appName("log2parquet")
      .master("local").getOrCreate()
    //读取数据
    val lines: RDD[String] = spark.sparkContext.textFile(inputPath)
    //过滤数据,在切分的时候，如果有字符相连或相连过场，
    // 程序使用split切分时，会默认把它们当成一个字符处理，同时过滤的数据太多，那么
    //在split中加入总的切分长度即可(-1)
    import TypeUtil1.str2Int
    import TypeUtil2.str2Double
    import TypeUtil3.str2Boolean
    val logRDD= lines.filter(t => t.split(",", t.length).length >= 85)
      .map(line => {
        val arr: Array[String] = line.split(",", -1)
          new Log("1","2","3","4","5","true")
      }
      )
    import spark.implicits._
    logRDD.toDF


  }
}
//普通类创建DF，需要继承product和serializable，建议使用样例类
class Log(arr:String,arr1:Int,arr2:Double,arr3:Int,arr4:Double,arr5:Boolean) extends Product with Serializable {
  //设置标识和属性的关系
  override def productElement(n: Int): Any = n
  //总共多少成员属性
  override def productArity: Int = 85
  //判断当前类型和传入类型是否一致
  override def canEqual(that: Any): Boolean = that.isInstanceOf[Log]
}
