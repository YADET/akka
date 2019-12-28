package testofmaster

import akka.actor.{ActorSystem, Props}
import akka.io.Tcp.Register
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.HPC.{Factory, Master}
import com.HPC.Master.{InitializationAck, Initialize}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

import scala.concurrent.duration._
class AsyncTest extends TestKit(ActorSystem("test",ConfigFactory.load().getConfig("timedassertion")))
  with ImplicitSender with WordSpecLike with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  /**
   * since master is stateless
   */
  "a master actor" should {
    "initialize some slaves" in {
      val master=system.actorOf(Props[Master])
      master ! Initialize(5)
      expectMsg(InitializationAck)
    }

    "count the words" in {
      within(1 millis, 1 second) {
        val master = system.actorOf(Props[Master])
        val testtext = "here is test base"
        master ! Initialize(5)
        expectMsg(InitializationAck)
        master ! testtext
        expectMsg(4)
      }

    }
  }



}
