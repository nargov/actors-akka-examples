import akka.actor.SupervisorStrategy.Stop
import akka.actor._
import akka.event.Logging

object Crashing extends App{
  implicit val system = ActorSystem("crashing-example")

  object CrashingProtocol {
    case object Crash
    case object Start
  }

  system.actorOf(Props[SupervisingActor], "supervising-actor") ! CrashingProtocol.Start

  class SupervisingActor extends Actor {

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(){
      case e: Exception => Stop
    }

    def receive = {
      case CrashingProtocol.Start => childActor ! CrashingProtocol.Crash
    }

    val childActor = context.actorOf(Props[CrashingActor], "crashing-actor")
  }

  class CrashingActor extends Actor {
    val log = Logging(context.system, this)

    override def preStart(): Unit = {
      super.preStart()
      log.info("Starting...")
    }

    override def postStop(): Unit = {
      super.postStop()
      log.info("Stopping")
    }

    def receive = {
      case CrashingProtocol.Crash => throw new Exception("Crashed!")
    }
  }
}
