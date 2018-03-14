package scala

import java.sql.Connection

import com.mchange.v2.c3p0.ComboPooledDataSource

/**
 * Created by Administrator on 2018/3/14.
 */
class MysqlPool extends Serializable {

  private val cpds: ComboPooledDataSource = new ComboPooledDataSource(true)
  private val conf = Conf.mysqlConfig;
  try {
    cpds.setJdbcUrl(conf.getProperty("url", "jdbc:mysql://127.0.0.1:3306/test_bee?useUnicode=true&;characterEncoding=UTF-8"))
    cpds.setDriverClass(conf.getProperty("driver-class-name", "com.mysql.jdbc.Driver"))
    cpds.setUser(conf.getProperty("username", "root"))
    cpds.setPassword(conf.getProperty("password", ""))
    cpds.setMaxPoolSize(20)
    cpds.setMinPoolSize(5)
    cpds.setAcquireIncrement(2)
    cpds.setMaxStatements(180)
  } catch {
    case e: Exception => e.printStackTrace()
  }

  def getConnection: Connection = {
    try {
      return cpds.getConnection();
    } catch {
      case ex: Exception =>
        ex.printStackTrace()
        null
    }
  }
}

object MysqlManager {
  var mysqlManager: MysqlPool = _

  def getMysqlManager: MysqlPool = {
    synchronized {
      if (mysqlManager == null) {
        mysqlManager = new MysqlPool
      }
    }
    mysqlManager
  }
}
