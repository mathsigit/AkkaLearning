package org.algorithmdog.akkalearning

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by stana on 05/06/2017.
  */
object RemoteTeacherServicesApp {
  val config = ConfigFactory
    .parseResources("example.conf")
    .getConfig("RemoteServerSideActor")

  val system = ActorSystem("TeacherService",  config)
  //Entry point. Something like SparkContext。

  system.actorOf(Props[TeacherActor], "teacherActor")
}
