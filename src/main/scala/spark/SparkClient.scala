package spark

import org.apache.spark._
import org.apache.hadoop.hbase.{HBaseConfiguration, 
                                HTableDescriptor, 
                                TableName
                                }
import org.apache.hadoop.hbase.client.{ HBaseAdmin,
                                        Result,
                                        ConnectionFactory
                                        }
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.io.ImmutableBytesWritable

class SparkClient {
  def main(args: Array[String]) {
    val sconf = new SparkConf().setAppName("HBaseTest")
    val sc = new SparkContext(sconf)
    val conf = HBaseConfiguration.create()
    conf.set(TableInputFormat.INPUT_TABLE, "test")
    val conn = ConnectionFactory.createConnection(conf)
    val tn = TableName.valueOf("test")
    val admin = conn.getAdmin()
    
    if (!admin.isTableAvailable(tn))
      admin.createTable(new HTableDescriptor(tn))
    
    val HBaseRDD = sc.newAPIHadoopRDD(
        conf,
        classOf[TableInputFormat],
        classOf[ImmutableBytesWritable],
        classOf[Result])
    
  }
}