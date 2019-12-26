package actors

import akka.actor.{Actor, Props}

class Address(name:String) extends Actor{
  override def receive: Receive = {
    case "here" =>
      println("[actor2] I recived the message")
      println(s"[actor2] here is the address: ${name}")
    case _ =>
  }
}

object Address {

  def props(name:String)= Props(new Address(name))
}
