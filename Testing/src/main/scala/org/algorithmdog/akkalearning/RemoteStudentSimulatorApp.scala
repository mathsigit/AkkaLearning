package org.algorithmdog.akkalearning

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by stana on 05/06/2017.
  */
object RemoteStudentSimulatorApp extends App{
  val config = ConfigFactory
    .parseResources("example.conf")
    .getConfig("RemoteClientSideActor")

  val actorSystem = ActorSystem("StudentClient",  config);

  implicit val resolveTimeout = Timeout(5 seconds)
  val teacherActor = Await.result(actorSystem.actorSelection("akka.tcp://TeacherService@127.0.0.1:4999/user/teacherActor").resolveOne(), resolveTimeout.duration)
  val studentActor = actorSystem.actorOf(Props(new StudentActor(teacherActor)))

  while(true){
    studentActor ! 7.toLong
    studentActor ! "What Time".toLong
    Thread.sleep(5000)
  }
}
