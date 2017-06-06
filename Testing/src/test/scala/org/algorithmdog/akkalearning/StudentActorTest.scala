package org.algorithmdog.akkalearning

import java.text.SimpleDateFormat

import akka.actor.{ActorSystem, Props}
import akka.testkit.{EventFilter, ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}



@RunWith(classOf[JUnitRunner])
/**
  * @author stana
  *
  */
class StudentActorTest extends TestKit(ActorSystem("School", ConfigFactory.parseString("""akka.loggers = ["akka.testkit.TestEventListener"]""")))
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterAll{

  override def afterAll() {
    system.terminate()
  }

  val teacherActor = system.actorOf(Props[TeacherActor])

  //test its responses a correct answer order
  "The countAnswer " must {
    "response a correct answer order" in {
      val studentActor = system.actorOf(Props(new StudentActor(teacherActor )))
      val testProb   = new TestProbe(system);
      testProb.send(studentActor, 7.toLong);
      testProb.send(studentActor, 7.toLong)

      testProb.expectMsg("Disable clock!")
      testProb.expectMsg("Disable clock!")
    }
  }

  //test its response with simple
  "StudentActor" must {
    "response correctly" in {
      val studentActor = system.actorOf(Props(new StudentActor(teacherActor )))

      val dateFormat = new SimpleDateFormat("yy-mm-dd")
      studentActor ! 7.toLong
      expectMsg("Disable clock!")
      studentActor ! 87
      expectMsg("Receiving date!")
    }
  }

  //test internal state
  "StudentActor" must {
    "increase the DayInSchool" in {
      val testActorRef = TestActorRef(new StudentActor(teacherActor))
      testActorRef ! 7.toLong;
      assert(testActorRef.underlyingActor.DayInSchool == 1);
    }
  }
  //test logging
  "StudentActor" must {
    "logging" in {
      val testActorRef = system.actorOf(Props(new StudentActor(teacherActor)))
      EventFilter.info(pattern = ".*", occurrences = 1).intercept({
        testActorRef ! 7.toLong;
      })
    }
  }

  //test it sending a message
  "StudentActor " must{
    val questionReceiver = TestProbe()
    val studentActorRef = system.actorOf(Props(new StudentActor(questionReceiver.ref)))
    "send a question after waking up" in {

      studentActorRef ! 7.toLong
      questionReceiver.expectMsg("Hi teacher!")
      studentActorRef ! 87
      questionReceiver.expectMsg("What date is it?")
    }
  }

}
