package personal.weizeng.portals.scala

import org.apache.spark.{SparkContext, SparkConf}


/**
 * Created by Administrator on 2018/3/14.
 */
class WordCount {

}

object WordCount {
  def main(args: Array[String]) {
    val conf = new SparkConf()
    conf.setAppName("WordCount")
    conf.setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("D:\\IdeaProjects\\portals-analyse\\README.md", 1)
    val word = lines.flatMap(line => line.split("，||。")).map(w => (w.trim, 1)).reduceByKey(_ + _).sortBy(x => x._2)
    word.foreach(println(_))

  }
}
