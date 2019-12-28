import akka.actor.{ActorSystem, Props}
import akka.testkit.{CallingThreadDispatcher, TestActorRef, TestProbe}
import com.HPC.Factory
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, WordSpecLike}

import scala.concurrent.duration.Duration

class SyncTest extends WordSpecLike with BeforeAndAfterAll {

  implicit  val system=ActorSystem("synctest")

  override def afterAll(): Unit = {
    system.terminate()
  }

  "in the app layer" should{
    "number of active threads for workers" in{
      val syncactor=TestActorRef[Factory](Factory.props(4,List("here i am")))
      syncactor.receive("start")
      assert(syncactor.underlyingActor.numberofworkers ==4)
//            val counter=system.actorOf(Factory.props(4,List("here i am")).withDispatcher(CallingThreadDispatcher.Id))
//            val probe=TestProbe()
//            probe.send(counter,"start")
//            probe.expectMsg(Duration.Zero,4)
    }


  }
}
