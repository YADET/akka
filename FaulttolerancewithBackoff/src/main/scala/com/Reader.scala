package com

import java.io.File

import akka.actor.{Actor, ActorLogging}
import akka.pattern.{Backoff, BackoffSupervisor}
import com.Reader.Readfile

import scala.io.Source


object Reader{
  case object Readfile
}
class Reader extends Actor with ActorLogging{

  var dataSource:Source=null

  override def preStart(): Unit = {
    log.info("actor starting")
    dataSource=Source.fromFile(new File("src/main/resources/test.txt"))
  }



  override def receive: Receive = {
    case Readfile =>
      if (dataSource==null)
      log.info("the file is read"+ dataSource.getLines().toList)
  }

}
