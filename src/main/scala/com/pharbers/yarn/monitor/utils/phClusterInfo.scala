package com.pharbers.yarn.monitor.utils

import scala.xml.XML

object phClusterInfo {
	private val xml = XML.loadFile("./src/main/resources/yarn-site.xml")
	private val temp = xml \\ "property"
	private val result = temp.find(x => (x \\ "name").text == "yarn.resourcemanager.hostname").get \\ "value"
	val RSHostName: String = result.text
}
