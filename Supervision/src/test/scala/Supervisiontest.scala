import Child.Report
import akka.actor.{ActorRef, ActorSystem, Props, Terminated}
import akka.testkit.{EventFilter, ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class Supervisiontest extends TestKit(ActorSystem("supervision")) with ImplicitSender with WordSpecLike with BeforeAndAfterAll{

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "the supervisor" should {
    "go to the resume state"in {

      val supervisor=system.actorOf(Props[Suprervisor])
      supervisor ! Props[Child]
      val childrefexpexted=expectMsgType[ActorRef]

      childrefexpexted ! "I am good"
      childrefexpexted ! Report
      expectMsg(3)

      childrefexpexted ! "the state should be kept because runtime expetion will be drown by this message"
      childrefexpexted ! Report
      expectMsg(3)
    }

    "restart in empy sentence" in {
      val supervisor=system.actorOf(Props[Suprervisor])
      supervisor ! Props[Child]
      val childrefexpexted=expectMsgType[ActorRef]

      childrefexpexted ! "I am good"
      childrefexpexted ! Report
      expectMsg(3)

      childrefexpexted ! ""
      childrefexpexted ! Report
      expectMsg(0)


    }

    "stopping the child" in{

      val supervisor=system.actorOf(Props[Suprervisor])
      supervisor ! Props[Child]
      val childrefexpexted=expectMsgType[ActorRef]

      watch(childrefexpexted)

      childrefexpexted ! "here "
      val message=expectMsgType[Terminated]
      assert(message.actor==childrefexpexted)

    }

    "esclate when dosnt know what to do" in {

      val supervisor=system.actorOf(Props[Suprervisor])
      supervisor ! Props[Child]
      val childrefexpexted=expectMsgType[ActorRef]

      watch(childrefexpexted)
      childrefexpexted ! 90
      val message=expectMsgType[Terminated]
      assert(message.actor==childrefexpexted)

    }

    "all-for-one stratergy" should{
      "apply changes in all childs" in {

        val allforonesupervisor=system.actorOf(Props[AllforoneSupervision])
        allforonesupervisor ! Props[Child]
        val childref1=expectMsgType[ActorRef]

        allforonesupervisor ! Props[Child]
        val childref2=expectMsgType[ActorRef]

        childref2 ! "Hi there"
        childref2 ! Report
        expectMsg(2)

        EventFilter[NullPointerException]() intercept {
          childref1 ! ""
        }

        childref2 ! Report
        expectMsg(0)




      }
    }
  }


}
