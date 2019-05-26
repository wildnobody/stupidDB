package zech.stupidDB;

public class FieldDissect {
	private String name;
	private String type;
	private String value;
	private boolean isPK;
	public boolean autoincrement;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isPK() {
		return isPK;
	}
	public void setPK(boolean isPK) {
		this.isPK = isPK;
	}
	public FieldDissect(String name, String type) {
		this.name = name;
		this.type = type;
		this.isPK = false;
		this.autoincrement = false;
	}
	public boolean isAutoincrement() {
		return autoincrement;
	}
	public void setAutoincrement(boolean autoincrement) {
		this.autoincrement = autoincrement;
	}
	public String getValue() {
		switch (this.type) {
		case "String":
			return "'" + this.value + "'";
			
		default:
			return this.value;
		}
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
