package com.xbwl.flow.service.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FlowExport;
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.entity.FlowPipeinfo;
import com.xbwl.flow.dao.IFlowPipeinfoDao;
import com.xbwl.flow.service.IFlowExportService;
import com.xbwl.flow.service.IFlowNodeinfoService;
import com.xbwl.flow.service.IFlowPipeinfoService;

/**
 *@author LiuHao
 *@time Feb 17, 2012 8:58:20 AM
 */
@Service("flowPipeinfoServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FlowPipeinfoServiceImpl extends BaseServiceImpl<FlowPipeinfo,Long> implements
		IFlowPipeinfoService {
	@Resource(name="flowPipeinfoHibernateDaoImpl")
	private IFlowPipeinfoDao flowPipeinfoDao;
	@Resource(name="flowNodeinfoServiceImpl")
	private IFlowNodeinfoService flowNodeinfoService;
	@Resource(name="flowExportServiceImpl")
	private IFlowExportService flowExportService;
	@Override
	public IBaseDAO getBaseDao() {
		return flowPipeinfoDao;
	}
	public Document getLayoutXml(Long pipeId) throws Exception {
		List<FlowNodeinfo> nodeList=flowNodeinfoService.getNodeByPipeid(pipeId);
		List<FlowExport> exportList=flowExportService.getExportByPipeid(pipeId);
		Element nodes = new Element("nodes");
	    Element edges = new Element("edges");
	    Element graph = new Element("graph");
	    graph.setAttribute("id", pipeId+"");
	    Document document = new Document(graph);
	    //生成XML 文档中的<node>节点
	    for (int i = 0; i < nodeList.size(); ++i) {
	    	FlowNodeinfo nodeInfo = (FlowNodeinfo)nodeList.get(i);
	        Element node = new Element("node");
	        node.setAttribute("id", nodeInfo.getId()+"");
	        node.setAttribute("name", nodeInfo.getObjName());
	        if(nodeInfo.getDrawxpos() == null || nodeInfo.getDrawypos() ==  null){
	    	  throw new ServiceException("数据异常，节点的X或者Y坐标为空了！");
	        }
	        node.setAttribute("viewX", Integer.toString(nodeInfo.getDrawxpos().intValue()));
	        node.setAttribute("viewY", Integer.toString(nodeInfo.getDrawypos().intValue()));
	        nodes.addContent(node);
	    }
	    graph.addContent(nodes);
	  //生成XML 文档中的<edge>节点
	    for (int m = 0; m < exportList.size(); ++m) {
	      FlowExport export = (FlowExport)exportList.get(m);
	      Element edge = new Element("edge");
	      edge.setAttribute("id", export.getId()+"");
	      if(export.getLinkFrom() == null || export.getLinkTo() == null){
	    	  throw new ServiceException("数据异常，出口信息连接点坐标为空了！！");
	      }
	      edge.setAttribute("linkFrom", export.getLinkFrom().toString());
	      edge.setAttribute("linkTo", export.getLinkTo().toString());
	      FlowNodeinfo fromNode = getFromNode(export, nodeList);
	      FlowNodeinfo toNode = getToNode(export, nodeList);
	      edge.setAttribute("fromId", fromNode.getId().toString());
	      edge.setAttribute("toId", toNode.getId().toString());
	      Element corners = new Element("corners");
	    //生成XML 文档中的<corner>节点
	      for (int n = 1; n <= 3; ++n) {
	        Element corner = new Element("corner");
	        corner.setAttribute("index", Integer.toString(n));
	        corner.setAttribute("X", export.getX(n).toString());
	        corner.setAttribute("Y", export.getY(n).toString());
	        corners.addContent(corner);
	      }
	      edge.addContent(corners);
	      edges.addContent(edge);
	    }
	    graph.addContent(edges);
	    return document;
	}
	
	/**
	 * 获得开始节点
	 * @author LiuHao
	 * @time Feb 22, 2012 4:26:26 PM 
	 * @param export
	 * @param allNodes
	 * @return
	 */
	 private FlowNodeinfo getFromNode(FlowExport export, List<FlowNodeinfo> allNodes)
	  {
		 if(export.getStartnodeId() == null){
			 throw new ServiceException("数据异常，出口开始前一节点ID为空了");
		 }
	    for (int i = 0; i < allNodes.size(); ++i) {
	    	FlowNodeinfo node = (FlowNodeinfo)allNodes.get(i);
	      if (node.getId().equals(export.getStartnodeId()))
	        return node;
	    }
	    return null;
	  }
	 /**
	  * 获得结束节点
	  * @author LiuHao
	  * @time Feb 22, 2012 4:26:33 PM 
	  * @param export
	  * @param allNodes
	  * @return
	  */
	  private FlowNodeinfo getToNode(FlowExport export, List<FlowNodeinfo> allNodes)
	  {
		 if(export.getEndnodeId() == null){
			 throw new ServiceException("数据异常，出口开始前一节点ID为空了");
		 }
	    for (int i = 0; i < allNodes.size(); ++i) {
	    	FlowNodeinfo node = (FlowNodeinfo)allNodes.get(i);
	      if (node.getId().equals(export.getEndnodeId()))
	        return node;
	    }
	    return null;
	  }
	  /**
	   * 设置并保存节点的X、Y坐标
	   * @author LiuHao
	   * @time Feb 22, 2012 6:02:06 PM 
	   * @param pipeid
	   */
	  public void setDrawpos(Long flowId) throws Exception
	  {
	    List<FlowNodeinfo> nodeList = flowNodeinfoService.getNodeByPipeid(flowId);
	    List<FlowExport> exportList = flowExportService.getExportByPipeid(flowId);
	    for (int i = 0; i < nodeList.size(); ++i) {
	      FlowNodeinfo nodeInfo = nodeList.get(i);
	      nodeInfo.setDrawxpos(Long.valueOf(150*i+20));
	      nodeInfo.setDrawypos(Long.valueOf(100));
	      flowNodeinfoService.save(nodeInfo);
	    }
	    for (int i = 0; i < exportList.size(); ++i) {
	      FlowExport exportInfo = (FlowExport)exportList.get(i);
	      exportInfo.setLinkFrom(6L);
	      exportInfo.setLinkTo(14L);
	      flowExportService.save(exportInfo);
	    }
	  }
	public void saveLayoutXml(String xmlStr) throws Exception {
		SAXBuilder sb = new SAXBuilder();
	    StringReader read = new StringReader(xmlStr);
	    InputSource source = new InputSource(read);
	      Document doc = sb.build(source);
	      Element graph = doc.getRootElement();
	      List nodes = graph.getChild("nodes").getChildren();
	      List edges = graph.getChild("edges").getChildren();
	      //循环保存节点信息
	      for (int i = 0; i < nodes.size(); ++i) {
	        Element node = (Element)nodes.get(i);
	        String nodeId = node.getAttributeValue("id");
	        String viewX = node.getAttributeValue("viewX");
	        String viewY = node.getAttributeValue("viewY");
	        if(nodeId == null || "".equals(nodeId) || viewX == null || "".equals(viewX) || viewY ==null || "".equals(viewY)){
	        	throw new ServiceException("数据异常:节点ID、节点X或者节点Y为空了！");
	        }
	        FlowNodeinfo nodeinfo = this.flowNodeinfoService.getAndInitEntity(Long.valueOf(nodeId));
	        if(nodeinfo == null){
	        	throw new ServiceException("数据异常:节点ID："+nodeId+"对应的节点信息不存在！");
	        }
	        nodeinfo.setDrawxpos(Long.valueOf(viewX));
	        nodeinfo.setDrawypos(Long.valueOf(viewY));
	        flowNodeinfoService.save(nodeinfo);
	      }
	      //循环保存出口信息
	      for (int j = 0; j < edges.size(); ++j) {
	        Element edge = (Element)edges.get(j);
	        List corners = edge.getChild("corners").getChildren();
	        String edgeId = edge.getAttributeValue("id");
	        if(edgeId == null || edgeId ==""){
	        	throw new ServiceException("数据异常，出口ID为空！");
	        }
	        FlowExport export = this.flowExportService.getAndInitEntity(Long.valueOf(edgeId));
	        export.setLinkFrom(Long.valueOf(edge.getAttributeValue("linkFrom")));
	        export.setLinkTo(Long.valueOf(edge.getAttributeValue("linkTo")));
	        for (int k = 0; k < corners.size(); ++k) {
	          Element corner = (Element)corners.get(k);
	          int index = Integer.parseInt(corner.getAttributeValue("index"));
	          String moveable = corner.getAttributeValue("moveable");
	          if ("true".equals(moveable)) {
	        	String sx=corner.getAttributeValue("X");
	        	String sy=corner.getAttributeValue("Y");
	        	if(sx == null || "".equals(sx) || sy == null || "".equals(sy)){
	        		throw new ServiceException("数据异常 ，出口对应的X/Y坐标为空了！");
	        	}
	            double x = Double.valueOf(sx).doubleValue();
	            double y = Double.valueOf(sy).doubleValue();
	            int X = new BigDecimal(x).setScale(0, 4).intValue();
	            int Y = new BigDecimal(y).setScale(0, 4).intValue();
	            export.setX(index, Long.valueOf(X));
	            export.setY(index, Long.valueOf(Y));
	          } else {
	            export.setX(index, Long.valueOf(-1));
	            export.setY(index, Long.valueOf(-1));
	          }
	        }
	        flowExportService.save(export);
	      }
	}
	public String getShowChartStr(Long pipeId, Long workflowId)
			throws Exception {
		Document document = getShowChartXml(pipeId, workflowId);
	    XMLOutputter outputter = new XMLOutputter();
	    return outputter.outputString(document);
	}
	public Document getShowChartXml(Long pipeId, Long workflowId)
			throws Exception {
		FlowNodeinfo startNode = this.flowNodeinfoService.getSEnodeinfoByPipeId(pipeId, "start");
	    if ((startNode.getDrawxpos().intValue() <= 0) || (startNode.getDrawypos().intValue() <= 0))
	      setDrawpos(pipeId);

	    List nodeList = this.flowNodeinfoService.getNodeByPipeid(pipeId);
	    List exportList = this.flowExportService.getExportByPipeid(pipeId);
	    Element nodes = new Element("nodes");
	    Element edges = new Element("edges");
	    Element graph = new Element("graph");
	    graph.setAttribute("id", pipeId.toString());
	    graph.setAttribute("flowid", workflowId.toString());
	    Document document = new Document(graph);
	    //生成XML 文档中的<node>节点
	    for (int i = 0; i < nodeList.size(); ++i) {
	      FlowNodeinfo nodeInfo = (FlowNodeinfo)nodeList.get(i);
	      Element node = new Element("node");
	      node.setAttribute("id", nodeInfo.getId().toString());
	      node.setAttribute("name", nodeInfo.getObjName());
	      node.setAttribute("viewX", Integer.toString(nodeInfo.getDrawxpos().intValue()));
	      node.setAttribute("viewY", Integer.toString(nodeInfo.getDrawypos().intValue()));
	      nodes.addContent(node);
	    }
	    graph.addContent(nodes);
	    //生成XML 文档中的<edge>节点
	    for (int m = 0; m < exportList.size(); ++m) {
	      FlowExport export = (FlowExport)exportList.get(m);
	      Element edge = new Element("edge");
	      edge.setAttribute("id", export.getId().toString());
	      edge.setAttribute("linkFrom", export.getLinkFrom().toString());
	      edge.setAttribute("linkTo", export.getLinkTo().toString());
	      FlowNodeinfo fromNode = getFromNode(export, nodeList);
	      FlowNodeinfo toNode = getToNode(export, nodeList);
	      edge.setAttribute("fromId", fromNode.getId().toString());
	      edge.setAttribute("toId", toNode.getId().toString());
	      if(export.getConditionRemark()!=null && !"".equals(export.getConditionRemark())){
	    	  edge.setAttribute("tmemo", export.getConditionRemark());
	      }
	      Element corners = new Element("corners");
	      //生成XML 文档中的<corner>节点
	      for (int n = 1; n <= 3; ++n) {
	        Element corner = new Element("corner");
	        corner.setAttribute("index", Integer.toString(n));
	        corner.setAttribute("X", export.getX(n).toString());
	        corner.setAttribute("Y", export.getY(n).toString());
	        corners.addContent(corner);
	      }
	      edge.addContent(corners);
	      edges.addContent(edge);
	    }
	    graph.addContent(edges);
	    return document;
	}
}
