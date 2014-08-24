package com.wix.akka.examples

import akka.actor.{Actor, ActorSystem}
import akka.testkit.{ImplicitSender, TestActorRef, TestActors, TestKit}
import com.wix.akka.examples.Crashing.{CrashingActor, CrashingProtocol}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class HelloAkkaTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender with Matchers with WordSpecLike with BeforeAndAfterAll {

  def this() = this(ActorSystem("akka-tests"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "An Echo Actor" must {
    "send back messages unchanged" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      val hello: String = "Hello World"
      echo ! hello
      expectMsg(hello)
    }
  }

  "A Crashing Actor" must {
    "throw an exception when crashing" in {
      val crashingActor = TestActorRef(new CrashingActor)
      intercept[Exception] { crashingActor.receive(CrashingProtocol.Crash) }
    }
  }

  object StatefulActorProtocol {
    case class ChangeTo(num: Int)
  }
  class StatefulActor extends Actor {
    var myValue = 0;

    def receive = {
      case c: StatefulActorProtocol.ChangeTo => myValue = c.num
    }
  }

  "A stateful Actor" must {
    "change state when message arrives" in {
      val statefulActor = TestActorRef(new StatefulActor)
      statefulActor ! StatefulActorProtocol.ChangeTo(5)
      assert(statefulActor.underlyingActor.myValue == 5)
    }
  }
}
