import com.pharbers.yarn.monitor.utils.phClusterInfo
import org.scalatest.FunSuite

import scala.xml.XML
import scalaj.http.{Http, HttpOptions, HttpRequest, HttpResponse}

import scala.util.parsing.json._

class xmlTest extends FunSuite {
	test("xml Test") {
		val xml = XML.loadFile("./src/main/resources/yarn-site.xml")
		val temp = xml \\ "property"
		val result = temp.find(x => (x \\ "name").text == "yarn.resourcemanager.hostname").get \\ "value"

		val url = "http://" + result.text + ":8088/ws/v1/cluster/apps/application_1564458724969_0743"
		val h = Http(url).asString
		val js: Map[String, Any] = JSON.parseFull(h.body).get.asInstanceOf[Map[String, Any]]
		val finalStatus = js("app").asInstanceOf[Map[String, Any]]("finalStatus")
		val a = finalStatus match {
			case "SUCCEEDED" => "SUCCEEDED"
			case "FAILED" => "FAILED"
			case "UNDEFINED" => "UNDEFINED"
			case _ => ""
		}
		println(a)
		val b = phClusterInfo.RSHostName

	}
}
