package userend;

import zech.stupidDB.PrimaryKey;
import zech.stupidDB.modelBase;

public class TestModel extends modelBase{
	private String firstName;
	private String lastName;
	private int age;
	
	@PrimaryKey(autoincrement=true)
	private int id;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
