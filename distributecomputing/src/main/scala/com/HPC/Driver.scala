package com.HPC

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory


object Driver extends App {


  val env=ConfigFactory.load("dev/config.conf").getConfig("devakka")
  val system=ActorSystem("roundrobin", env)

  val texts=List("hi", "here i am","second one","did that carefully","new men")

  val actor=system.actorOf(Factory.props(4,texts),"factory")

  actor ! "start"


}
