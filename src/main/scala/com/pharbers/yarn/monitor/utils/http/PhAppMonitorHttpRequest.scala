package com.pharbers.yarn.monitor.utils.http
import scalaj.http.{Http, HttpRequest, HttpResponse}

case class PhAppMonitorHttpRequest(url: String) extends PhHttpRequestTrait {
	override val PhHttpRequest: HttpRequest = Http(url)
	override def getResponseAsStr: HttpResponse[String] = PhHttpRequest.asString
}
