package com.HPC

import akka.actor.{Actor, Props}
import com.HPC.Master.initialize

class Factory extends Actor{

  override def receive: Receive = {

    case "start" =>
      val master=context.actorOf(Props[Master], "master")
      master ! initialize(4)
      val texts=List("hi", "here i am","second one","did that carefully","new men")
      texts.foreach(text => master ! text)
    case count: Int =>
      println(s"Here is the count ${count}")

  }

}
