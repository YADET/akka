package com.HPC

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory


object Driver extends App {


  val env=ConfigFactory.load("dev/config.conf").getConfig("devakka")
  val system=ActorSystem("roundrobin", env)

  val actor=system.actorOf(Props[Factory],"factory")

  actor ! "start"


}
