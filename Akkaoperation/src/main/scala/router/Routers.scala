package router

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.{Broadcast, FromConfig, RoundRobinGroup, RoundRobinPool}
import com.typesafe.config.ConfigFactory

object Routers extends App {


  val system=ActorSystem("router", ConfigFactory.load().getConfig("router"))

  class Slave extends Actor with ActorLogging {

    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }


  val poolroutermaster=system.actorOf(RoundRobinPool(5).props(Props[Slave]), "poolmaster")
  for (i <- 1 to 8) {
    poolroutermaster ! s"helllo  i am ${i}"
  }

  val poolroutermaster2=system.actorOf(FromConfig.props(Props[Slave]), "poolrouter")
  for (i <- 1 to 5) {
    poolroutermaster2 ! s"second  i am ${i}"
  }

  val slaves=(1 to 6).map(i => system.actorOf(Props[Slave], s"slave_${i}")).toList
  val slavePaths=slaves.map(slaveRef=>slaveRef.path.toString)
  val grouprouter=system.actorOf(RoundRobinGroup(slavePaths).props())
  for (i <- 1 to 15) {
    grouprouter ! s"group router  i am ${i}"
  }

  val grouproutermmaster=system.actorOf(FromConfig.props(),"grouprouter")
  for (i <- 1 to 8) {
    grouprouter ! s"group router master i am ${i}"
  }

  grouproutermmaster ! Broadcast("i am brodcasted")






}
