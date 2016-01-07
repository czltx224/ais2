function searchDnoInfo(v_dno,v_departId){
	if(v_dno=='undefined'||v_departId=='undefined' || null==v_dno|| null==v_departId){
		return;
	}
	var node=new Ext.tree.TreeNode({
		leaf :true,
		text:"详细信息"
	});
	node.attributes={
		href1:'fax/oprFaxInAction!info.action?dno='+v_dno+'&rightDepart='+v_departId
	};
  	parent.toAddTabPage(node,true);
}