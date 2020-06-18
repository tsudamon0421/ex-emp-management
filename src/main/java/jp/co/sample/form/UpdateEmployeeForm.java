package jp.co.sample.form;

/**
 * 従業員情報を更新するフォーム.
 * 
 * @author hikaru.tsuda
 *
 */
public class UpdateEmployeeForm {
	
	/** 従業員ID */
	private String id;
	/**	扶養人数 */
	private String dependentsCount;
	
	
	
	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDependentsCount() {
		return dependentsCount;
	}
	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}
	
	

}
