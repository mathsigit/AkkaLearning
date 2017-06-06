package org.algorithmdog.akkalearning

import akka.actor.{ActorSystem, Props}
import akka.testkit.{EventFilter, ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner


/**
  * @author stana
  */

@RunWith(classOf[JUnitRunner])
class TeacherActorTest extends TestKit(ActorSystem("School", ConfigFactory.parseString("""akka.loggers = ["akka.testkit.TestEventListener"]""")))
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterAll{

  override def afterAll() {
    system.terminate()
  }

  //test internal state
  "The countAnswer " must {
    "increase the number" in {
      val teacherRef = TestActorRef[TeacherActor]
      teacherRef ! "test"
      assert(teacherRef.underlyingActor.coutAnswer == 1)
      teacherRef ! "good!"
      assert(teacherRef.underlyingActor.coutAnswer == 2)
      teacherRef ! "hi"
      assert(teacherRef.underlyingActor.coutAnswer == 3)
    }
  }

  //test logging, eventFilter
  "The countAnswer" must {
    "logging when receive a question" in  {
      val teacherRef = TestActorRef[TeacherActor]
      EventFilter.info(pattern = ".*",occurrences = 1).intercept({
        teacherRef ! "abc";
      })
    }
  }

  //test its responses a correct answer order
  "The countAnswer " must {
    "response a correct answer order" in {
      val teacherRef = TestActorRef[TeacherActor]
      val testProb   = new TestProbe(system);
      testProb.send(teacherRef, "aa");
      testProb.send(teacherRef,"Hi teacher!")

      testProb.expectMsg("I don't understand this question!")
      testProb.expectMsg("Hi teacher: +1s")
    }
  }


}
