package rew;

public class PieElement {

	public String name;
	public int value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public PieElement(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	
}
