package model

import play.api.libs.json.Json


case class CombinedData(sunInfo: SunInfo, temperature: Double)

object CombinedData {
  implicit val writes = Json.writes[CombinedData]
}
