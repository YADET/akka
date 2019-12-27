package com.HPC

import akka.actor.{Actor, ActorLogging, ActorRef}
import Master.{Wordcountreply, Wordcounttask}



object Worker{
  case class Account(actorref: ActorRef)
}

class Worker extends Actor with ActorLogging{


  override def receive: Receive = {

    case Wordcounttask(id, text) =>
      log.info(s"the worker id is ${id}")
      sender() ! Wordcountreply(id, text.split(" ").length)
  }
}
