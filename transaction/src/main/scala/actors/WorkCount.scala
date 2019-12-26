package actors

import akka.actor.Actor

class WorkCount extends Actor{

  var totalwords=0

  override def receive: Receive = {

    case message:String =>
      println("[actor1] I have recieved message")
      totalwords += message.split(" ").length
      println(s"[actor1] length of message is ${totalwords}")
    case others => println("nothing found bro")
  }

}
