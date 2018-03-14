package scala

import org.apache.spark.sql.{SparkSession, SQLContext}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2018/3/14.
 */
object MysqlOutput {

  val mysqlConfig = Conf.mysqlConfig

  def main(args: Array[String]) {
    newWayLoadMysql
  }

  def oldWayLoadMysql(): Unit = {
    val conf = new SparkConf().setAppName("WordCount").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val jdbcDf = sqlContext.read.format("jdbc")
      .options(Map("url" -> mysqlConfig.getProperty("url"),
        "driver" -> mysqlConfig.getProperty("driver-class-name"),
        "dbtable" -> "tieba",
        "user" -> mysqlConfig.getProperty("username"),
        "password" -> mysqlConfig.getProperty("password")))
      .load()
    jdbcDf.show()
  }

  def newWayLoadMysql(): Unit = {
    val spark = SparkSession
      .builder()
      .appName("SparkSessionZipsExample")
      .master("local")
      .getOrCreate()

    val df = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://localhost:3306/portals?user=root&password=123")
      .option("dbtable", "tieba") //必须写表名
      .load()
    df.createOrReplaceTempView("tieba")
    val ddf = spark.sql("select tieba_name,low_category,focus,post_total from tieba")

    ddf.sort(ddf("focus").desc).show()
  }

}
