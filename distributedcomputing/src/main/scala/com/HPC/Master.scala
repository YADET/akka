package com.HPC

import akka.actor.{Actor, ActorRef, Props}
import com.HPC.Master.{Wordcountreply, Wordcounttask, initialize}


object Master{
  case class initialize(nworkers: Int)
  case class Wordcounttask(id:Int,text:String)
  case class Wordcountreply(id:Int,count:Int)
}

class Master extends Actor{

  override def receive: Receive = {

    case initialize(nworkers) =>
      val workerRefs= for (i <- 1 to nworkers) yield context.actorOf(Props[Worker],s"worker_${i}")
      context.become(workers(workerRefs,0,0,Map()))


  }

  def workers(workersref:Seq[ActorRef],workerindex:Int, taskid: Int, mapper:Map[Int,ActorRef]):Receive={
    case text:String =>
      val originalsender=sender()
      val newmapper=mapper+(taskid->originalsender)
      val task=Wordcounttask(taskid,text)
      val workerref=workersref(workerindex)
      workerref ! task
      val nextworker=(workerindex+1)%workersref.length
      val newtaskid=taskid+1
      context.become(workers(workersref,nextworker,newtaskid,newmapper))
    case Wordcountreply(id,count) =>
      val mainsender=mapper(id)
      mainsender ! count
      context.become(workers(workersref,workerindex,taskid,mapper-id))



  }
}
