package com.HPC

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.HPC.Master.{Wordcountreply, Wordcounttask, initialize}


object Master{
  case class initialize(nworkers: Int)
  case class Wordcounttask(id:Int,text:String)
  case class Wordcountreply(id:Int,count:Int)
}

class Master extends Actor with ActorLogging{

  override def receive: Receive = {

    case initialize(nworkers) =>
      val workerRefs= for (i <- 1 to nworkers) yield context.actorOf(Props[Worker],s"worker_${i}")
      log.info(s"the number of initialaized workers are ${nworkers}")
      context.become(workers(workerRefs,0,0,Map()))


  }

  def workers(workersref:Seq[ActorRef],workerindex:Int, taskid: Int, mapper:Map[Int,ActorRef]):Receive={
    case text:String =>
      val originalsender=sender()
      log.info(s"the original sender is ${originalsender}")
      val newmapper=mapper+(taskid->originalsender)
      val task=Wordcounttask(taskid,text)
      val workerref=workersref(workerindex)
      workerref ! task
      val nextworker=(workerindex+1)%workersref.length
      val newtaskid=taskid+1
      log.info(s"the next worker and taskid are ${nextworker} ${newtaskid}")
      context.become(workers(workersref,nextworker,newtaskid,newmapper))
    case Wordcountreply(id,count) =>
      val mainsender=mapper(id)
      mainsender ! count
      context.become(workers(workersref,workerindex,taskid,mapper-id))



  }
}
