package scala

import java.util.Properties

/**
 * Created by Administrator on 2018/3/14.
 */
class Conf {

}

object Conf {
  val mysqlConfig = {
    val properties = new Properties();
    properties.load(this.getClass.getClassLoader.getResourceAsStream("mysql.properties"))
    properties
  }
}
