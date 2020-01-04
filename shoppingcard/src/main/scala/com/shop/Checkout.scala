package com.shop

import akka.actor.{Actor, Props}
import com.shop.Checkout.{Authorize, Checkingout, Confirmed, Dispatchorder}
import com.shop.ShoppingM.{PaymentAccepted, PaymentDenied}


object Checkout {

  case class Checkingout(item:String,card:String)
  case class Authorize(card:String)
  case class Dispatchorder(item:String)
  case object Confirmed
}

class Checkout extends Actor {

  private val shoppingmanage=context.actorOf(Props[ShoppingM])
  private val fullfillment=context.actorOf(Props[Fullfill])

  override def receive: Receive = awaiting

  def awaiting:Receive={

    case Checkingout(item,card) =>
      shoppingmanage ! Authorize(card)
      context.become(pendingpayment(item))
  }

  def pendingpayment(item: String):Receive={
    case PaymentAccepted =>
      fullfillment ! Dispatchorder(item)
      context.become(pendingorder(item))
    case PaymentDenied => throw new RuntimeException("i cant handle")
  }

  def pendingorder(item: String): Receive={
    case Confirmed => context.become(awaiting)
  }

}
