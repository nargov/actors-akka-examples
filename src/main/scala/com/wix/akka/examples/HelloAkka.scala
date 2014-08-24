package com.wix.akka.examples

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

object HelloAkka extends App{
  implicit val system = ActorSystem("hello-akka-example")

  case object Hello

  class HelloAkkaActor extends Actor {
    val log = Logging(context.system, this)
    def receive = {
      case Hello => log.info("Hello Akka!")
    }
  }

  val helloAkka = system.actorOf(Props[HelloAkkaActor], "hello-akka-actor")
  helloAkka ! Hello
}
