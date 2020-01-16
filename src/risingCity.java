


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;







public class risingCity {
    private static final String OUT_FILE = "output_file.txt";
    private static  String INPUT_PATH;
    private static redBlackTree rbt = new redBlackTree();
    private static minHeapBuild heap = new minHeapBuild();
    private static redBlackTree.TreeNode t;
    private static FileWriter fileWriter;
    private static Building  currentBuilding=null;//Current building  being built
    private static int globalCounter=0;//Global time counter
    private static int builtLimitTime=0;//to check time limit of 5 days
    private static int working_days = 0;//to count the days we actually work on building not sitting idle




//Method to perform the daily construction on a current building. 
public static boolean construction() throws IOException {
	if(currentBuilding==null){
		if(heap.isEmpty()){
		return false;
		}
		else{
			currentBuilding=heap.pollBuilding();
			currentBuilding.executed_time++;
			builtLimitTime++;
		}
	}
	else {
		if(builtLimitTime>4 || currentBuilding.executed_time==currentBuilding.total_time) {
				if(currentBuilding.executed_time==currentBuilding.total_time) {
					printDelete(currentBuilding);
					currentBuilding=null;
					builtLimitTime=0;
					/* if a building had reached completion on previous day 
					  we need to pick up another building on today and work for one day */
					construction();
				}
				else{
					heap.pushBuilding(currentBuilding);
					currentBuilding=null;
					builtLimitTime=0;
					/* if a building reaches construction limit of 5 on previous day building reinserted into heap 
					 and new building to be picked today and worked for one day*/
					construction(); 
				}
		}
		else{
		currentBuilding.executed_time++;
				builtLimitTime++;
		}	
	}
	return true;
}



public static void printDelete(Building b) throws IOException {
	printBuildOut(b,true);
	redBlackTree.TreeNode z= rbt.locateBuild(b.buildingNum);
	rbt.delete(z);
}

public static void printBuildOut(Building b,boolean isDelete) throws IOException {
	if(isDelete) {
		fileWriter.write("("+b.buildingNum+","+globalCounter+")\n");
	}
	else {
	fileWriter.write("("+b.buildingNum+","+b.executed_time+","+b.total_time+")\n");
	}
}

public static void main(String[] args) throws IOException {
	fileWriter = new FileWriter(OUT_FILE);
	INPUT_PATH=args[0];
	File inpFile = new File(INPUT_PATH);
	Scanner fScan = new Scanner(inpFile);
	int total_working_days = 0;
	//Scan through the file and finding out the total time required by all buildings to finish construction
	while(fScan.hasNext()){
		String inpLine = fScan.nextLine();
		if(inpLine.contains("Insert")){
			total_working_days = total_working_days + Integer.parseInt(inpLine.split(",")[1].replace(")", ""));
		}			
	} 
	Scanner secondScan = new Scanner(inpFile);
	String [] inputParams;
	// we know the total days required to construct all the buildings 
	// we construct the buildings and check if all buildings are constructed 
	// when working days is equal to total working days all the buildings have been constructed in the city
	while(working_days <= total_working_days){
		if(secondScan.hasNext()){
			String inpLine = secondScan.nextLine();
            	inputParams=inpLine.substring(inpLine.indexOf("(")+1,inpLine.indexOf(")")).split(",");
           	 int builtStartTime = Integer.parseInt((inpLine.substring(0,inpLine.indexOf(":"))));
           	while(globalCounter < builtStartTime){ //increase the counter till input time is reached so that we can read the input
				boolean isConstructed = construction();
				if(isConstructed){ // increment no of days only if some operation is done on a building on that day
					working_days++;
				}
				globalCounter++;
			}
           	//if insert command and global count is equal to command insert time
           	if(inpLine.indexOf("Insert")!= -1) {
           		int buildingNum=Integer.parseInt(inputParams[0]);
           		int totalTime=Integer.parseInt(inputParams[1]);
           		Building b= new Building(buildingNum,0,totalTime);
           		t=rbt.new TreeNode(b);
           		if(rbt.locateBuild(buildingNum)== null) {
           		rbt.insertBuild(t);
           		}
           		else {
           			fileWriter.write("dupliate building inserted at gloabal time:"+"globalCounter");
           			return;
           		}
           		heap.pushBuilding(b);
            }
           	
           	else if(inpLine.indexOf("PrintBuilding")!= -1 || inpLine.indexOf("Print")!= -1 ) {
           		if (inputParams.length == 1){
           	        int buildingNum = Integer.parseInt(inputParams[0]);
           	        redBlackTree.TreeNode pt = rbt.locateBuild(buildingNum);
           	        if (pt == null) {
           	        	fileWriter.write("(0,0,0)\n");
           	        }
           	        else {
           	            printBuildOut(pt.val,false);
           	        }
           	    }
           	    else {
           	        int buildingNum1 = Integer.parseInt(inputParams[0]);
           	        int buildingNum2 = Integer.parseInt(inputParams[1]);
           	        List<redBlackTree.TreeNode> Treelist = rbt.locteBuildInRange(buildingNum1, buildingNum2);//Search in tree

           	        if (!Treelist.isEmpty()){
           	            StringBuilder sb = new StringBuilder();
           	            for (redBlackTree.TreeNode node: Treelist){
           	                sb.append("("+node.val.buildingNum+","+node.val.executed_time+","+node.val.total_time + "),");
           	            }
           	            sb.deleteCharAt(sb.length()-1);//remove last comma
           	            sb.append("\n");
           	            fileWriter.write(sb.toString());
           	        }
           	        else {
           	        	fileWriter.write("(0,0,0)\n");
           	        }
            }
            }
           	else {
           		fileWriter.write("Invalid Input");
           	}
		}
		//To keep on the construction of buildings after all the input lines are over 
		//if some buildings are still not completely constructed
		boolean isConstructed = construction();
		if(isConstructed){
			working_days++;
		}
		globalCounter++;
		}
	globalCounter--;
	fileWriter.write("City Completion Date:  "+globalCounter);
	fScan.close();
	secondScan.close();
	fileWriter.close();
}

}


