package de.kp.spark.weblog.actor
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

import akka.actor.{Actor,ActorLogging}

import de.kp.spark.core.Names
import de.kp.spark.core.actor.RootActor

import de.kp.spark.core.model._
import de.kp.spark.core.redis.RedisCache

import de.kp.spark.weblog.model._
import de.kp.spark.weblog.{Configuration,RemoteContext}

abstract class BaseActor extends RootActor(Configuration) {

  /**
   * Notify all registered listeners about a certain status
   */
  protected def notify(req:ServiceRequest,status:String) {

    val response = new ServiceResponse(req.service,req.task,req.data,status)	
    val message = Serializer.serializeResponse(response)    

    RemoteContext.notify(message)
    
  }

}