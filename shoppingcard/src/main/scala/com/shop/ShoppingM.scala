package com.shop

import akka.actor.Actor
import com.shop.Checkout.Authorize
import com.shop.ShoppingM.{PaymentAccepted, PaymentDenied}


object ShoppingM {

  case object PaymentAccepted
  case object PaymentDenied

}

class ShoppingM extends Actor{

  override def receive: Receive = {

    case Authorize(card) =>
      if (card.startsWith("0")) sender() ! PaymentDenied
      else sender() ! PaymentAccepted
  }

}
