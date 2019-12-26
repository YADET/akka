package actors

import akka.actor.Actor

class Chat extends Actor{

  override def receive: Receive = {

    case "HI" =>
      println("I recived HI")
      context.sender() ! "hello"
    case ChatSchema(ref) => ref ! "HI"
    case "hello" => println("I revcieved hello back")
  }
}
