package controllers

import controllers.Assets.Asset
import javax.inject._
import model.{CombinedData, UserLoginData}
import play.api.mvc._
import services._
import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json


class Application(components: ControllerComponents, assets: Assets,
    sunService: SunService, authService: AuthService,
    weatherService: WeatherService, userAuthService: UserAuthService) extends AbstractController(components) {


  def index = Action.async {
    val lat = 36.955597
    val lon = 45.387893
    val sunInfoF = sunService.getSunInfo(lat, lon)
    val temperatureF = weatherService.getTemperature(lat, lon)
    for {
      sunInfo <- sunInfoF
      temperature <- temperatureF
    } yield {
      Ok(views.html.index(sunInfo, temperature))
    }
  }


  def jsondata = Action.async {
    val lat = 36.955597
    val lon = 45.387893
    val sunInfoF = sunService.getSunInfo(lat, lon)
    val temperatureF = weatherService.getTemperature(lat, lon)
    for {
      sunInfo <- sunInfoF
      temperature <- temperatureF
    } yield {
      Ok(Json.toJson(CombinedData(sunInfo, temperature)))
    }
  }


  def versioned(path: String, file: Asset) = assets.versioned(path, file)


  def login = Action {
    Ok(views.html.login(None))
  }


  val userDataForm = Form {
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(UserLoginData.apply)(UserLoginData.unapply)
  }


  def doLogin = Action { implicit request =>
    userDataForm.bindFromRequest.fold(
      formWithErrors => Ok(views.html.login(Some("Wrong data"))),
      userData => {
        val maybeCookie = authService.login(userData.username, userData.password)
        maybeCookie match {
          case Some(cookie) =>
            Redirect("/").withCookies(cookie)
          case None =>
            Ok(views.html.login(Some("Login failed")))
        }
      }
    )
  }


  def restricted = userAuthService { userAuthRequest =>
    Ok(views.html.restricted(userAuthRequest.user))
  }

}
