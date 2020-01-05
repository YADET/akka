package dispatcher

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.util.Random

object Dispatcher extends App{

  class Collect extends Actor with ActorLogging {

    var count=0

    override def receive: Receive = {
      case messeage =>
        count+=1
        log.info(s"here ${count} is ${messeage}")
    }
  }

  val system=ActorSystem("Dispatcher")

  val actors=for(i <- 1 to 4) yield system.actorOf(Props[Collect].withDispatcher("dispatcher"),s"collector_${i}")
  val r=new Random()
  for(i<-1 to 20) {
    actors(r.nextInt(4)) ! i
  }

  val actor2=system.actorOf(Props[Collect],"actor2")
  for(i<-1 to 20) {
    actors(r.nextInt(4)) ! i
  }



}
