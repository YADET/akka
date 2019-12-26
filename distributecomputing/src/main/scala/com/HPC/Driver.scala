package com.HPC

import akka.actor.{ActorSystem, Props}


object Driver extends App {

  val system=ActorSystem("roundrobin")

  val actor=system.actorOf(Props[Factory],"factory")

  actor ! "start"


}
