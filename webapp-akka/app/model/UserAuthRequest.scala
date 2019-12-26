package model

import play.api.mvc._


case class UserAuthRequest[A](user: User, request: Request[A]) extends  WrappedRequest[A](request)
