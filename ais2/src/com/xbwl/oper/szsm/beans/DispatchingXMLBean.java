package com.xbwl.oper.szsm.beans;

/**
 * 配送数据对接xml输出Bean
 * @author czl
 * @date 2012-06-27
 */
public class DispatchingXMLBean {
	private String carrierno;
	private String vehicleno;
	private String drivername;
	private String driverphone;
	private String transporttype = "01";
	private String dispense_time;
	private String dono;

	public String getXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"GB2312\"?><el-express><tracking name=\"dispatching\">");
		xml.append("<vehicleinfo>");
		xml.append("<carrierno>").append(this.carrierno).append("</carrierno>")
		   .append("<vehicleno>").append(this.vehicleno).append("</vehicleno>")
		   .append("<drivername>").append(this.drivername).append("</drivername>")
		   .append("<driverphone>").append(this.driverphone).append("</driverphone>")
		   .append("<transporttype>").append(this.transporttype).append("</transporttype>")
		   .append("<dispense_time>").append(this.dispense_time).append("</dispense_time>");
	    xml.append("</vehicleinfo>");
		xml.append("<goodsinfo>")
		   .append("<dono>").append(this.dono).append("</dono>")
		   .append("</goodsinfo>");
		xml.append("</tracking></el-express>");
		return xml.toString();
	}

	public String getCarrierno() {
		return carrierno;
	}

	public void setCarrierno(String carrierno) {
		this.carrierno = carrierno;
	}

	public String getVehicleno() {
		return vehicleno;
	}

	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public String getDriverphone() {
		return driverphone;
	}

	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}

	public String getTransporttype() {
		return transporttype;
	}

	public void setTransporttype(String transporttype) {
		this.transporttype = transporttype;
	}

	public String getDispense_time() {
		return dispense_time;
	}

	public void setDispense_time(String dispense_time) {
		this.dispense_time = dispense_time;
	}

	public String getDono() {
		return dono;
	}

	public void setDono(String dono) {
		this.dono = dono;
	}
}