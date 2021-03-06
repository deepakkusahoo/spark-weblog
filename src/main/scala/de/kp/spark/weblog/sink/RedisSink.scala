package de.kp.spark.weblog.sink

import java.util.Date
/* Copyright (c) 2014 Dr. Krusche & Partner PartG
* 
* This file is part of the Spark-Weblog project
* (https://github.com/skrusche63/spark-weblog).
* 
* Spark-Weblog is free software: you can redistribute it and/or modify it under the
* terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* Spark-Weblog is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
* A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* You should have received a copy of the GNU General Public License along with
* Spark-Weblog. 
* 
* If not, see <http://www.gnu.org/licenses/>.
*/

import de.kp.spark.core.Names

import de.kp.spark.core.model._
import de.kp.spark.core.redis.RedisClient

import de.kp.spark.weblog.Configuration
import scala.collection.JavaConversions._

class RedisSink {

  val (host,port) = Configuration.redis
  val client = RedisClient(host,port.toInt)
  
  def addModel(req:ServiceRequest,model:String) {
   
    val now = new Date()
    val timestamp = now.getTime()
    
    val k = "model:" + req.data(Names.REQ_SITE) + ":" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME) 
    val v = "" + timestamp + ":" + model
    
    client.zadd(k,timestamp,v)
    
  }
  
  def modelExists(req:ServiceRequest):Boolean = {

    val k = "model:" + req.data(Names.REQ_SITE) + ":" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME) 
    client.exists(k)
    
  }
  
  def model(req:ServiceRequest):String = {

    val k = "model:" + req.data(Names.REQ_SITE) + ":" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME) 
    val models = client.zrange(k, 0, -1)

    if (models.size() == 0) {
      null
    
    } else {
      
      val last = models.toList.last
      last.split(":")(1)
      
    }
  
  }

}