
var ajax = new sack();
var graph = null;
function showLoading() {
	$("flowLayoutLoadding").style.display = "";
}
function hideLoading() {
	$("flowLayoutLoadding").style.display = "none";
}
function showSaving() {
	$("flowLayoutSaving").style.display = "";
}
function showMSG() {
	$("flowLayoutSaving").style.display = "none";
	$("flowLayoutSaved").style.display = "";
	setTimeout(hideSaving, 1000);
}
function hideSaving() {
	$("flowLayoutSaved").style.display = "none";
}
function whenCompleted() {
	var xml = ajax.response;
	processXML(xml);
}
function doit() {
	ajax.setVar("flowId", pipeId);
	ajax.requestURL = sysPath+"/flow/flowPipeinfoAction!getLayoutXml.action";
	ajax.method = "GET";
	ajax.onLoading = showLoading;
	ajax.onCompletion = whenCompleted;
	ajax.runAJAX();
}
//function doit() {
//	//var pipeid = $("pipeid").value;
//	ajax.setVar("pipeid", "402803082b461e65012b6dd8e46e4ef3");
//	ajax.setVar("action", "getxml");
//	ajax.requestURL = "http://121.9.243.136:8180/ServiceAction/com.velcro.workflow.layout.FlowChartAction";
//	ajax.method = "GET";
//	ajax.onLoading = showLoading;
//	ajax.onCompletion = whenCompleted;
//	ajax.runAJAX();
//}
function processXML(xml) {
	//alert(xml);
	var oXmlDom = createXMLDOM();
	oXmlDom.loadXML(xml);
	var xGraph = oXmlDom.documentElement;
	var gId = xGraph.getAttribute("id");
	if (!gId) {
		hideLoading();
		return;
	}
	var xNodes = xGraph.childNodes[0];
	var xEdges = xGraph.childNodes[1];
	var nodelength = xNodes.childNodes.length;
	var edgelength = xEdges.childNodes.length;
	graph = new Graph(gId);
	//设置节点
	for (var i = 0; i < nodelength; i++) {
		var xNode = xNodes.childNodes[i];
		var id = xNode.getAttribute("id");
		var name = xNode.getAttribute("name");
		var viewX = xNode.getAttribute("viewX");
		var viewY = xNode.getAttribute("viewY");
		var node = new Node(id, name);
		node.viewX = viewX;
		node.viewY = viewY;
		graph.addNode(node);
	}
	//设置边
	for (var j = 0; j < edgelength; j++) {
		var xEdge = xEdges.childNodes[j];
		var id = xEdge.getAttribute("id");
		var linkFrom = xEdge.getAttribute("linkFrom");
		var linkTo = xEdge.getAttribute("linkTo");
		var fromId = xEdge.getAttribute("fromId");
		var toId = xEdge.getAttribute("toId");
		var edge = new Edge(id);
		var fromNode = graph.getNode(fromId);
		var toNode = graph.getNode(toId);
		if (fromNode) {
			edge.addFromNode(fromNode, linkFrom);
		}
		if (toNode) {
			edge.addToNode(toNode, linkTo);
		}
		var p1 = edge.getStartPoint();//开始节点
		var p2 = edge.getEndPoint();//结束节点
		edge.cornerPoints.push(p1);
		var xCorners = xEdge.childNodes[0];
		var lenCorners = xCorners.childNodes.length;
		var pointFlag = 0;
		for (var k = 1; k <= lenCorners; k++) {
			var x = xCorners.childNodes[k - 1].getAttribute("X");
			var y = xCorners.childNodes[k - 1].getAttribute("Y");
			var point = new CornerPoint(x, y, "p" + k);
			point.onEdge = edge;
			if (x < 0) {
				point.moveable = false;
				pointFlag = pointFlag * 1 + 1 * 1;
			}
			edge.cornerPoints.push(point);
		}
		edge.cornerPoints.push(p2);
		edge.updateUnmoveablePoint(pointFlag);
		//edge.printPoint();
		graph.addEdge(edge);
	}
	graph.draw();
	hideLoading();
}
/**
 * 保存数据
 */
function onSave() {
	if (!graph) {
		return;
	}
	var nodes = graph.nodes;
	var edges = graph.edges;
	var XML = new XMLWriter();
	XML.BeginNode("graph");
	XML.Attrib("id", graph.id);
	XML.BeginNode("nodes");
	for (var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		XML.BeginNode("node");
		XML.Attrib("id", node.id);
		XML.Attrib("viewX", node.viewX);
		XML.Attrib("viewY", node.viewY);
		XML.EndNode();
	}
	XML.EndNode();
	XML.BeginNode("edges");
	for (var j = 0; j < edges.length; j++) {
		var edge = edges[j];
		var points = edge.cornerPoints;
		var len = points.length - 1;
		XML.BeginNode("edge");
		XML.Attrib("id", edge.id);
		XML.Attrib("linkFrom", edge.fromDirection);
		XML.Attrib("linkTo", edge.toDirection);
		XML.Attrib("fromId", edge.fromNode.id);
		XML.Attrib("toId", edge.toNode.id);
		XML.BeginNode("corners");
		for (var k = 1; k < len; k++) {//1-3
			var point = points[k];
			XML.BeginNode("corner");
			XML.Attrib("index", k);
			XML.Attrib("moveable", point.moveable.toString());
			XML.Attrib("X", point.X);
			XML.Attrib("Y", point.Y);
			XML.EndNode();
		}
		XML.EndNode();
		XML.EndNode();
	}
	XML.EndNode();
	XML.EndNode();
	ajax.reset();
	ajax.setVar("xmlStr", XML.ToString());
	ajax.requestURL = sysPath+"/flow/flowPipeinfoAction!saveLayoutXML.action";
	ajax.method = "POST";
	ajax.onLoading = showSaving;
	ajax.onCompletion = showMSG;
	ajax.runAJAX();
}
function openNode(pipeId,pipeName,nodeId) {
	addNodeinfo(null,pipeId,pipeName,nodeId)
}
function editUser(pipeId,nodeId) {
	setRalarule(pipeId,nodeId);
}

