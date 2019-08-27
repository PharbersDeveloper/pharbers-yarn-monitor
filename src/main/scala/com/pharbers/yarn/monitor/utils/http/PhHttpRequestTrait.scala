package com.pharbers.yarn.monitor.utils.http

import scalaj.http.{HttpRequest, HttpResponse}

trait PhHttpRequestTrait {
	val PhHttpRequest: HttpRequest
	def getResponseAsStr: HttpResponse[String]
}