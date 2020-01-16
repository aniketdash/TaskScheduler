

public class Building {
public int buildingNum;
public int executed_time;
public int total_time;


public Building(int buildingNum, int executed_time, int total_time) {
	super();
	this.buildingNum = buildingNum;
	this.executed_time = executed_time;
	this.total_time = total_time;
}

public void setNum(int buildingNum) {
	this.buildingNum=buildingNum;
}
public int getNum() {
	return this.buildingNum;
}
public void setExt(int executed_time) {
	this.executed_time=executed_time;
}
public int getExt() {
	return this.executed_time;
}
public void setTt(int total_time) {
	this.total_time=total_time;
}
public int getTt() {
	return this.total_time;
}
}
