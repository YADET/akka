package com

import akka.actor.{ActorSystem, Props}
import akka.pattern.{Backoff, BackoffSupervisor}

import scala.concurrent.duration._


object Driver extends App {

  val system=ActorSystem("eager")


  val repeatedcall=BackoffSupervisor.props(
    Backoff.onStop(
      Props[Reader],
      "reager",
      1 second,
      30 seconds,
      0.2
    )
  )

  val faulttoleranactor=system.actorOf(repeatedcall,"eager")

}
