/**
 * www.velcro.soft.com.cn
 * 2008.1.14 Ricky.zhou
 * Graph图形类，主要属性id:ID	nodes:节点的集合	edges:边的集合 
 * @param {String} id
 */
function Graph(id){
	this.id = id;
	this.edges=new Array();
	this.nodes=new Array();
}
/**
 * 创建节点
 * @param {Node} node
 */
Graph.prototype.addNode=function(node){
	this.nodes.push(node);
}
Graph.prototype.addEdge=function(edge){
	this.edges.push(edge);
}
/**
 * 删除指定的节点
 * @param {Node} node
 */
Graph.prototype.removeNode=function(node){
	var nNodes = this.nodes.length;
	for(var i=0;i<nNodes;i++){
		var oNode = this.nodes[i];
		if(oNode.id==node.id){
			this.nodes.splice(i,1);
		}
	}
}
/**
 * 获取节点
 * @param {String} nodeId
 */
Graph.prototype.getNode=function(nodeId){
	var nNodes = this.nodes.length;
	for(var i=0;i<nNodes;i++){
		var oNode = this.nodes[i];
		if(oNode.id==nodeId){
			return oNode;
		}
	}
	return null;
}
/**
 * 获取边
 * @param {String} edgeId
 */
Graph.prototype.getEdge=function(edgeId){
	var nEdges = this.edges.length;
	for(var i=0;i<nEdges;i++){
		var oEdge = this.edges[i];
		if(oEdge.id==edgeId){
			return oEdge;
		}
	}
	return null;
}
/**
 * 删除指定的边
 * @param {Edge} edge
 */
Graph.prototype.removeEdge=function(edge){
	var nEdges = this.edges.length;
	for(var i=0;i<nEdges;i++){
		var oEdge = this.edges[i];
		if(oEdge.id==edge.id){
			this.edges.splice(i,1);
		}
	}
}
/**
 * 设置拐点信息(第一次进入时设置所有边的拐点信息)
 */
Graph.prototype.createCornerPoint = function(){
	var length = this.edges.length;
	for(var j=0;j<length;j++){
		var edge = this.edges[j];
		edge.createCornerPoint();
	}
}
/**
 * 链接两个节点
 * @param {Node} fromNode
 * @param {Node} toNode
 */
Graph.prototype.link=function(fromNode,toNode){
	var id = this.edges.length+1*1;
	var edge = new Edge("edge"+id);
	edge.addFromNode(fromNode,6);
	edge.addToNode(toNode,14);
	fromNode.addOutEdge(edge);
	toNode.addInEdge(edge);
	this.edges.push(edge);
}
/**
 * 删除两个节点之间的链接
 * @param {Node} fromNode
 * @param {Node} toNode
 */
Graph.prototype.unlink=function(fromNode,toNode){
	//var edge = this.getEdge(fromNode,toNode);
}
/**
 * 画出graph中的节点和边
 * @param {Object} readonly 是否只读，只读情况下节点不可拖动
 */
Graph.prototype.draw=function(readonly){
	//var start = (new Date()).getTime();
	this.drawNodes(readonly);
	this.drawEdges(readonly);
	//var stop = (new Date()).getTime();
	//alert("This operation took " + (stop-start) + "ms");
}
Graph.prototype.drawNodes=function(readonly){
	var length = this.nodes.length;
	for(var i=0;i<length;i++){
		var node = this.nodes[i];
		node.draw(readonly);
	}
}
Graph.prototype.drawEdges=function(readonly){
	var length = this.edges.length;
	for(var i=0;i<length;i++){
		var edge = this.edges[i];
		edge.draw(readonly);
	}
}
/**
 * 移动一个节点时需要重新生成与该节点连接的边和边上的拐点信息
 * @param {Node} oNode
 */
Graph.prototype.reDrawNode=function(oNode){
	if(oNode){
		oNode.reDraw();//重新绘制节点
		//重新设置与节点链接的边上的拐点然后重新绘制与节点相链的边	
		for(var i=0;i<oNode.inEdges.length;i++){
			var edge = oNode.inEdges[i];
			edge.updateCornerPoint(edge.getEndPoint());
			edge.draw();
		}
		for(var j=0;j<oNode.outEdges.length;j++){
			var edge = oNode.outEdges[j];
			edge.updateCornerPoint(edge.getStartPoint());
			edge.draw();
		}
	}	
}
/**
 * 移动一个拐点时重新绘制该拐点上的边
 * @param {CornerPoint} oPoint
 */
Graph.prototype.reDrawEdge=function(oPoint){
	if(oPoint){
		var edge = oPoint.onEdge;
		edge.updateCornerPoint(oPoint);
		edge.draw();
	}
}