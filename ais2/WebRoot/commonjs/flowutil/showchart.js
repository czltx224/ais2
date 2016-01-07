var ajax = new sack();
var graph = null;
function showLoading(){
	$("flowLayoutLoadding").style.display="";
}
function hideLoading(){
	$("flowLayoutLoadding").style.display="none";
}
/**
 * 流转过程
 */
function showStep(){
  ajax.setVar("pipeId", pipeId);
  ajax.setVar("workflowId", workflowId);
  //ajax.requestURL = "http://121.9.243.136:8180/ServiceAction/com.velcro.workflow.layout.FlowChartAction";
  ajax.requestURL =sysPath+"/flow/workFlowbaseAction!getStepXml.action";
  ajax.method = "GET";
  ajax.onLoading = showLoading;
  ajax.onCompletion = processShowStep;
  ajax.runAJAX();
}
/**
 * 显示节点操作规则
 * @param {Object} id
 * @param {Object} oDiv
 */
function showRule(id, oDiv){
    var ruleDiv = $("rule" + id);
    if (ruleDiv) {
        if (ruleDiv.children[0]) {
            if (ruleDiv.style.display == "") {
                ruleDiv.style.display = "none";
            }
            else {
                ruleDiv.style.display = "";
            }
            return;
        }
        else {
            ruleDiv.removeNode(true);
        }
        
    }
    ajax.setVar("nodeId", id);
    ajax.setVar("pipeId",pipeId);
    ajax.setVar("workflowId",workflowId);
    ajax.requestURL = sysPath+"/flow/flowRalaruleAction!getRules.action";;
    ajax.method = "GET";
    ajax.onLoading = showLoading;
    ajax.onCompletion = function(){
        createRuleDiv(id, oDiv);
    };
    ajax.runAJAX();
}
function createRuleDiv(id,oDiv){
	var html = ajax.response;
	var div = document.createElement("div");
	div.id="rule"+id;
	div.style.left = oDiv.offsetLeft+"px";
	div.style.top = oDiv.offsetTop+75*1+"px";
	div.className="rule";
	div.innerHTML=html;
	$("ruleDiv").appendChild(div);
	hideLoading();
}
/**
 * 显示流程图
 */
function showChart(){
	//var start = (new Date()).getTime();
	var xml = $("flowChartXML").value;
	var oXmlDom = createXMLDOM();
	oXmlDom.loadXML(xml);
	var xGraph = oXmlDom.documentElement;
	var xNodes = xGraph.childNodes[0];
	var xEdges = xGraph.childNodes[1];
	var nodelength = xNodes.childNodes.length;
	var edgelength = xEdges.childNodes.length;
	graph = new Graph(xGraph.getAttribute("id"));
	graph.flowid = xGraph.getAttribute("flowid");
	//设置节点
	for(var i=0;i<nodelength;i++){
		var xNode = xNodes.childNodes[i];
		var id = xNode.getAttribute("id");
		var name = xNode.getAttribute("name");
		var viewX = xNode.getAttribute("viewX");
		var viewY = xNode.getAttribute("viewY");
		var node = new Node(id,name);
		node.viewX=viewX;
		node.viewY=viewY;
		graph.addNode(node);
	}
	//设置边
	for(var j=0;j<edgelength;j++){
		var xEdge = xEdges.childNodes[j];
		var id = xEdge.getAttribute("id");
		var linkFrom = xEdge.getAttribute("linkFrom");
		var linkTo = xEdge.getAttribute("linkTo");
		var fromId = xEdge.getAttribute("fromId");
		var toId = xEdge.getAttribute("toId");
		
		var tmemo= xEdge.getAttribute("tmemo");
		var edge = new Edge(id);
		var fromNode = graph.getNode(fromId);
		var toNode = graph.getNode(toId);
		if(fromNode){
			edge.addFromNode(fromNode,linkFrom);
		}
		if(toNode){
			edge.addToNode(toNode,linkTo);
		}
		if(tmemo){
			edge.addMemo(tmemo);
		}
		var p1 =  edge.getStartPoint();//开始节点
		var p2 =  edge.getEndPoint();//结束节点
		edge.cornerPoints.push(p1);
		var xCorners =xEdge.childNodes[0];
		var lenCorners = xCorners.childNodes.length;
		var pointFlag = 0;
		for(var k = 1;k<=lenCorners;k++){
			var x = xCorners.childNodes[k-1].getAttribute("X");
			var y = xCorners.childNodes[k-1].getAttribute("Y");
			var point =new CornerPoint(x,y,"p"+k);
			point.onEdge=edge;
			if(x<0){
				point.moveable=false;
				pointFlag =pointFlag*1+1*1;
			}
			edge.cornerPoints.push(point);			
		}
		edge.cornerPoints.push(p2);
		edge.updateUnmoveablePoint(pointFlag);
		graph.addEdge(edge);
	}
	graph.draw(true);
	//var stop = (new Date()).getTime();
	//alert("This operation took " + (stop-start) + "ms");
	showStep();
}
/**
*显示流转过程
*/
function processShowStep(){
    var xml = ajax.response;
    var oXmlDom = createXMLDOM();
	oXmlDom.loadXML(xml);
    var xGraph = oXmlDom.documentElement;
    var xNodes = xGraph.childNodes[0];
    var xEdges = xGraph.childNodes[1];
	var nodelength = xNodes.childNodes.length;
	var edgelength = xEdges.childNodes.length;
    //设置节点
    for (var i = 0; i < nodelength; i++) {
        var xNode = xNodes.childNodes[i];
        var id = xNode.getAttribute("id");
        var nodeType = xNode.getAttribute("nodeType");
        var node = graph.getNode(id);  
        if (node) {
            node.nodeType = nodeType;
            if (nodeType == 2) {//已操作节点
                var xDoUsers = xNode.childNodes[0];
                var doUsersLen = xDoUsers.childNodes.length;
                var doUsers = new Array();
                for (var u = 0; u < doUsersLen; u++) {
                    var id = xDoUsers.childNodes[u].getAttribute("id");
                    var rtx = xDoUsers.childNodes[u].getAttribute("rtx");
                    var name = xDoUsers.childNodes[u].getAttribute("name");
                    doUsers.push({
                        "id": id,
                        "rtx": rtx,
                        "name": name
                    });
                }
                node.doUsers = doUsers;
            }else if (nodeType == 1) {//当前节点
                var xUndoUsers = xNode.childNodes[0];
                var xDoUsers = xNode.childNodes[1];
                var xViewUsers = xNode.childNodes[2];
                var undoUsersLen = xUndoUsers.childNodes.length;
                var doUsersLen = xDoUsers.childNodes.length;
                var viewUsersLen = xViewUsers.childNodes.length;
                var undoUsers = new Array();
                var doUsers = new Array();
                var viewUsers = new Array();
                for (var u = 0; u < undoUsersLen; u++) {
                    var id = xUndoUsers.childNodes[u].getAttribute("id");
                    var rtx = xUndoUsers.childNodes[u].getAttribute("rtx");
                    var name = xUndoUsers.childNodes[u].getAttribute("name");
                    undoUsers.push({
                        "id": id,
                        "rtx": rtx,
                        "name": name
                    });
                }
                for (var u = 0; u < doUsersLen; u++) {
                    var id = xDoUsers.childNodes[u].getAttribute("id");
                    var rtx = xDoUsers.childNodes[u].getAttribute("rtx");
                    var name = xDoUsers.childNodes[u].getAttribute("name");
                    doUsers.push({
                        "id": id,
                        "rtx": rtx,
                        "name": name
                    });
                }
                for (var u = 0; u < viewUsersLen; u++) {
                    var id = xViewUsers.childNodes[u].getAttribute("id");
                    var rtx = xViewUsers.childNodes[u].getAttribute("rtx");
                    var name = xViewUsers.childNodes[u].getAttribute("name");
                    viewUsers.push({
                        "id": id,
                        "rtx": rtx,
                        "name": name
                    });
                }
                node.undoUsers = undoUsers;
                node.doUsers = doUsers;
                node.viewUsers = viewUsers;
            }
        }
        node.reDraw(true);
    }
	//设置边
	for(var j=0;j<edgelength;j++){
		var xEdge = xEdges.childNodes[j];
		var id = xEdge.getAttribute("id");
		var edge = graph.getEdge(id);
		edge.hasFlow = true;
		edge.draw(true);
	}
	hideLoading();
}