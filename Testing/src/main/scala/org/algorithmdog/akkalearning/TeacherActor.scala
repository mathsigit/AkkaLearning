package org.algorithmdog.akkalearning

/**
  * Created by stana on 05/06/2017.
  *
  */


import akka.actor.{Actor, ActorLogging}
import java.util.Calendar
import java.text.SimpleDateFormat

class TeacherActor extends Actor with ActorLogging {
  var coutAnswer = 0;
  def receive = {
    case "What's one plus one?"           => {
      sender ! "One plus one equals two."
      coutAnswer += 1;
    }
    case "Hi teacher!" => {
      sender ! "Hi teacher: +1s"
      coutAnswer += 1;
    }
    case "What date is it?"     => {
      val dateFormat = new SimpleDateFormat("yy-mm-dd")
      sender ! "It is " + dateFormat.format(Calendar.getInstance().getTime())
      coutAnswer += 1;
    }
    case _ => {
      log.info("Default case.") //For EventFilter testing
      sender ! "I don't understand this question!"
      coutAnswer += 1;
    }
  }
}

