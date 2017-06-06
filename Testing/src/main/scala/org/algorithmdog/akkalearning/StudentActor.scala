package org.algorithmdog.akkalearning

import akka.actor.{Actor, ActorLogging, ActorRef}

/**
  * Created by stana on 05/06/2017.
  */

class StudentActor (remoteTeacher:ActorRef) extends Actor with ActorLogging{

  val remoteServerRef = remoteTeacher
  var DayInSchool = 0;
  def receive = {
      case res:String => {
        println ("Teacher's answer: "+res)
      }
      case time:Long => {
        sender ! "Disable clock!"

        DayInSchool += 1;
        log.info("It is "+ time.toString +" o'clock. DayInSchool is %d".format(DayInSchool))

        remoteServerRef ! "Hi teacher!";
      }
      case date:Int => {
        sender ! "Receiving date!"
        DayInSchool += 1;
        log.info("Receiving "+ date +". DayInSchool is %d".format(DayInSchool))

        remoteServerRef ! "What date is it?";
      }
    }
}


