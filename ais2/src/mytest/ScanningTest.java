package mytest;

import javax.annotation.Resource;

import org.junit.Test;

import junit.framework.TestCase;

import com.xbwl.oper.edi.scanning.AISCtEstimateScanning;


public class ScanningTest extends TestCase  {

	@Resource(name="aisCtEstimateScanning")
	private AISCtEstimateScanning aisCtEstimateScanning;
	
	@Test
	public void ctEstimateScanningTest(){
		System.out.println("begin......");
		this.aisCtEstimateScanning.execute();
		System.out.println("end..........");
	}
}
