/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kududb.spark

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.util.ShutdownHookManager
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.kududb.annotations.InterfaceStability
import org.kududb.client.{AsyncKuduClient, KuduClient, RowResult}
import org.kududb.mapreduce.KuduTableInputFormat

/**
  * KuduContext is a façade for Kudu operations.
  *
  * If a Kudu client connection is needed as part of a Spark application, a
  * [[KuduContext]] should used as a broadcast variable in the job in order to
  * share connections among the tasks in a JVM.
  */
@InterfaceStability.Unstable
class KuduContext(kuduMaster: String) extends Serializable {

  /**
    * Set to
    * [[org.apache.spark.util.ShutdownHookManager.DEFAULT_SHUTDOWN_PRIORITY]].
    * The client instances are closed through the JVM shutdown hook
    * mechanism in order to make sure that any unflushed writes are cleaned up
    * properly. Spark has no way of notifying the [[DefaultSource]] on shutdown.
    */
  private val ShutdownHookPriority = 100

  @transient lazy val syncClient = {
    val syncClient = new KuduClient.KuduClientBuilder(kuduMaster).build()
    ShutdownHookManager.get().addShutdownHook(new Runnable {
      override def run() = syncClient.close()
    }, ShutdownHookPriority)
    syncClient
  }
  @transient lazy val asyncClient = {
    val asyncClient = new AsyncKuduClient.AsyncKuduClientBuilder(kuduMaster).build()
    ShutdownHookManager.get().addShutdownHook(
      new Runnable {
        override def run() = asyncClient.close()
      }, ShutdownHookPriority)
    asyncClient
  }

  /**
    * Create an RDD from a Kudu table.
    *
    * @param tableName          table to read from
    * @param columnProjection   list of columns to read
    *
    * Not specifying this at all (i.e. setting to null) or setting to the special string
    * '*' means to project all columns.
    * @return a new RDD that maps over the given table for the selected columns
    */
  def kuduRDD(sc: SparkContext,
              tableName: String,
              columnProjection: Seq[String] = Nil): RDD[RowResult] = {

    val conf = new Configuration
    conf.set("kudu.mapreduce.master.address", kuduMaster)
    conf.set("kudu.mapreduce.input.table", tableName)
    if (columnProjection.nonEmpty) {
      conf.set("kudu.mapreduce.column.projection", columnProjection.mkString(","))
    }

    val rdd = sc.newAPIHadoopRDD(conf, classOf[KuduTableInputFormat],
                                 classOf[NullWritable], classOf[RowResult])

    val columnNames = if (columnProjection.nonEmpty) columnProjection.mkString(", ") else "(*)"
    rdd.values.setName(s"KuduRDD { table=$tableName, columnProjection=$columnNames }")
  }
}
