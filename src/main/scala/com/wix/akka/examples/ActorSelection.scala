package com.wix.akka.examples

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

object ActorSelection extends App{
  implicit val system = ActorSystem("actor-selection-example")

  object AnnouncingActorProtocol {
    case object Announce
  }

  class AnnouncingActor(val message: String) extends Actor {
    val log = Logging(context.system, this)
    def receive = {
      case AnnouncingActorProtocol.Announce => log.info(message)
    }
  }

  (1 to 10).foreach(i => system.actorOf(Props(classOf[AnnouncingActor], "Hello from " + i), "announcing-actor-" + i))

  system.actorSelection("/user/*") ! AnnouncingActorProtocol.Announce
}
