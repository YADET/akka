package com.HPC

import akka.actor.{Actor, ActorRef}
import Master.{ Wordcountreply, Wordcounttask}
import Worker.Account



object Worker{
  case class Account(actorref: ActorRef)
}

class Worker extends Actor{


  override def receive: Receive = {

    case Wordcounttask(id, text) =>
      println(s"[worker] I got the task id ${id}")
      sender() ! Wordcountreply(id, text.split(" ").length)
  }
}
