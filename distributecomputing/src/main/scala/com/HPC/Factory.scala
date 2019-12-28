package com.HPC

import akka.actor.{Actor, Props}
import com.HPC.Master.Initialize


object Factory {

  def props(initnumber:Int,texts:List[String])= Props(new Factory(initnumber,texts))
}

class Factory(initnumber:Int,texts:List[String]) extends Actor{

  var numberofworkers=0
  var collection=0

  override def receive: Receive = {

    case "start" =>
      val master=context.actorOf(Props[Master], "master")
      master ! Initialize(initnumber)
      texts.foreach(text => master ! text)
      numberofworkers+=initnumber
      context.become(collector(0))
  }


  def collector(totalcount: Int):Receive={

    case count: Int =>
      collection+=count
      println(s"Here is the last count ${collection}")
      context.become(collector(collection))

  }

}