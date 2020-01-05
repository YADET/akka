package mailbox

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.Config

object CustomPriorityMailbox extends App{

  class Myactor extends Actor with ActorLogging{

    override def receive: Receive = {
      case message => log.info("i got the message " + message.toString)
    }
  }

  /**
   * P0 most important
   * ..
   * p3 last one
   */

  class SupportPriority(settings:ActorSystem.Settings, config: Config)
    extends UnboundedPriorityMailbox(
    PriorityGenerator{
      case message:String if message.startsWith("[p0]") => 0
      case message:String if message.startsWith("[p1]") => 1
      case message:String if message.startsWith("[p2]") => 2
      case message:String if message.startsWith("[p3]") => 3
      case _ => 4
    })

  val system=ActorSystem("priority")
  val prirotizedactorActor=system.actorOf(Props[Myactor].withDispatcher("custommailboxdispatcher"))
  prirotizedactorActor ! "[p3] 3"
  prirotizedactorActor ! "[p0] 1"
  prirotizedactorActor ! "[p2] 2 "





}
