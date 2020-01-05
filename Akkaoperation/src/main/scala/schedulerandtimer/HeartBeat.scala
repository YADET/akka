package schedulerandtimer

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Timers}

import scala.concurrent.duration._

object HeartBeat extends App {


  val system=ActorSystem("heartbeat")

  case object TimerKey
  case object Start
  case object Reminder
  case object Stop

  class HeartBeatActor extends Actor with ActorLogging with Timers {

    timers.startSingleTimer(TimerKey,Start,500 millis)

    override def receive: Receive = {

      case Start =>
        log.info("bootstrapping")
        timers.startPeriodicTimer(TimerKey,Reminder,1 second)
      case Reminder =>
        log.info("I am alive")
      case Stop =>
        log.warning("i got stop command")
        timers.cancel(TimerKey)
        context.stop(self)
    }
  }

  import system.dispatcher

  val timerhearbeat= system.actorOf(Props[HeartBeatActor], "timeActor")
  system.scheduler.scheduleOnce(4 seconds){
    timerhearbeat ! Stop
  }



}
