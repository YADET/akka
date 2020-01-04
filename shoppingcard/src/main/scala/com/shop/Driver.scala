package com.shop

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object Driver extends App {

  val env=ConfigFactory.load("dev/config.conf").getConfig("devakka")
  val system=ActorSystem("shop", env)


}