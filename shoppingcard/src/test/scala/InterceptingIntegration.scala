import akka.actor.{ActorSystem, Props}
import akka.testkit.{EventFilter, ImplicitSender, TestKit}
import com.shop.Checkout
import com.shop.Checkout.Checkingout
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class InterceptingIntegration extends TestKit(ActorSystem("Interception", ConfigFactory.load().getConfig("interceptinglog")))
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterAll{

  override def afterAll(): Unit = {
      TestKit.shutdownActorSystem(system)
  }

  val item="book"
  val creditcard="12-34"
  val invalidcard="000-0"
  "ordered item" should{
    "be dispatched" in {
      EventFilter.info(pattern = s"order 46 for item ${item} ordered.", occurrences = 1) intercept {
        val checkoutactor=system.actorOf(Props[Checkout])
        checkoutactor ! Checkingout(item,creditcard)
      }
    }
  }


  "make an exceptio if denied" in{
      EventFilter[RuntimeException](occurrences = 1) intercept {
        val checkoutactor=system.actorOf(Props[Checkout])
        checkoutactor ! Checkingout(item,invalidcard)
      }

  }





}
