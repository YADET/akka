package actors

import akka.actor.{ActorSystem, Props}

object Actors extends App {

  val actorsystem=ActorSystem("myactor")

  val acotr1=actorsystem.actorOf(props = Props[WorkCount],name = "actor1")
  acotr1 ! "akka damn prefect"

  val actor2=actorsystem.actorOf(props = Address.props("Avenue no where") ,"actor2")
  actor2 ! "here"

  val actor3 = actorsystem.actorOf(Props[Chat])
  val actor4= actorsystem.actorOf(Props[Chat])
  actor3 ! ChatSchema(actor4)

}
