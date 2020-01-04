import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, AllForOneStrategy, OneForOneStrategy, Props}

class AllforoneSupervision extends Actor{


  override val supervisorStrategy=AllForOneStrategy(){
    case _:NullPointerException => Restart
    case _:IllegalArgumentException => Stop
    case _:RuntimeException => Resume
    case _:Exception => Escalate
  }

    override def receive: Receive = {

      case props:Props =>
        val childRef=context.actorOf(props)
        sender() ! childRef
    }



}
