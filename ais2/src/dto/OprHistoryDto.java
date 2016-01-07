package dto;

import java.util.Date;

/**
 * @project ais_edi
 * @author czl
 * @Time Feb 14, 2012 9:03:05 AM
 */
public class OprHistoryDto implements java.io.Serializable {

	private Long id;
	private String oprName;//节点名称
	private Long oprNode;//节点编号
	private String oprComment;//操作内容
	private Date oprTime;//操作时间
	private String oprMan;//操作人
	private String oprDepart;//操作部门
	private Long dno;//配送单号
	private Long oprType;//节点类型
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOprName() {
		return oprName;
	}
	public void setOprName(String oprName) {
		this.oprName = oprName;
	}
	public Long getOprNode() {
		return oprNode;
	}
	public void setOprNode(Long oprNode) {
		this.oprNode = oprNode;
	}
	public String getOprComment() {
		return oprComment;
	}
	public void setOprComment(String oprComment) {
		this.oprComment = oprComment;
	}
	public Date getOprTime() {
		return oprTime;
	}
	public void setOprTime(Date oprTime) {
		this.oprTime = oprTime;
	}
	public String getOprMan() {
		return oprMan;
	}
	public void setOprMan(String oprMan) {
		this.oprMan = oprMan;
	}
	public String getOprDepart() {
		return oprDepart;
	}
	public void setOprDepart(String oprDepart) {
		this.oprDepart = oprDepart;
	}
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public Long getOprType() {
		return oprType;
	}
	public void setOprType(Long oprType) {
		this.oprType = oprType;
	}
}
