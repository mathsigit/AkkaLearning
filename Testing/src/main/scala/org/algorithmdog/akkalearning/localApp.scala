package org.algorithmdog.akkalearning

import akka.actor.{ActorSystem, Props}

/**
  * Created by lietal on 2017/1/16.
  */
object localApp {

  def main(args:Array[String]) = {
    val actorSystem = ActorSystem("SummerSchool")
    val teacher = actorSystem.actorOf(Props[TeacherActor], "teacher")
    val student = actorSystem.actorOf(Props(new StudentActor(teacher)), "student")

    student ! 7.toLong;// It's time to 7 o'clock
    Thread.sleep(1000);

    actorSystem.terminate()
  }

}
