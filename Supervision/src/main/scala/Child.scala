import Child.Report
import akka.actor.Actor


object Child{
  case object  Report

}
class Child extends Actor {

  var count=0

  override def receive: Receive = {
    case Report => sender() ! count
    case "" => throw new NullPointerException("sentence is empty")
    case input:String =>
      if (input.length>20) throw new RuntimeException("that is too large")
      else if (!Character.isUpperCase(input(0))) throw new IllegalArgumentException("sentence should start with uppercase")
      else count += input.split(" ").length
    case _ => throw new Exception("give me string")
  }

}
