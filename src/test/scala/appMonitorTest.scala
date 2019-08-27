import com.pharbers.yarn.monitor.appmonitor.applicationMonitor
import org.scalatest.FunSuite

class appMonitorTest extends FunSuite{
	test("Application Monitor Test") {
//		val result: String = applicationMonitor("application_1564458724969_0743").startMonitor()
		val result: String = applicationMonitor("application_156445872496").startMonitor()
		println("监控结果为：" + result)
	}
}
