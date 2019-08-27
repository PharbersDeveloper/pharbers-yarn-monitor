package com.pharbers.yarn.monitor.appmonitor

import com.pharbers.yarn.monitor.utils.http.PhAppMonitorHttpRequest
import com.pharbers.yarn.monitor.utils.phClusterInfo

import scala.util.parsing.json.JSON

case class applicationMonitor(applicationID: String,
                              protocol: String = "http",
                              restAPIVersion: String = "v1",
                              RSPort: String = "8088",
                              timeOut: Int = 3600) {
	val RSHostName: String = phClusterInfo.RSHostName
//	val url: String = protocol + "://" + RSHostName + ":" + RSPort + "/ws/" + "restAPIVersion" + "/cluster/apps/" + applicationID
	val url: String = s"$protocol://$RSHostName:$RSPort/ws/$restAPIVersion/cluster/apps/$applicationID"
	var time: Int = 0
	def startMonitor(): String = {
		val finalStatus: String = getFinalStatus(url)
		println(s"已经监控第 $time 秒,目前状态为:$finalStatus")
		val result: String = finalStatus match {
			case "SUCCEEDED" => "SUCCEEDED"
			case "FAILED" => "FAILED"
			case "UNDEFINED" => {
				Thread.sleep(1000)
				time += 1
				if (time > timeOut) "timeOut"
				else startMonitor()
			}
			case _ => "FAILED"
		}
		result
	}

	def getFinalStatus(url: String): String = {
		val response = PhAppMonitorHttpRequest(url).getResponseAsStr
//		if (response.headers)
		val status = response.headers.find(x => x._1 == "Status").get._2.head
		if (status == "HTTP/1.1 200 OK") {
			val bodyMap: Map[String, Any] = JSON.parseFull(response.body).get.asInstanceOf[Map[String, Any]]
			bodyMap("app").asInstanceOf[Map[String, Any]]("finalStatus").asInstanceOf[String]
		} else {
			status
		}
	}
}
