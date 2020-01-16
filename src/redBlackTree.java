
import java.util.ArrayList;
import java.util.List;


public class redBlackTree {
//Defining the color of the nodes globally
private final int RED=0;
private final int BLACK=1;

//Creating the the object that represents the nodes of the red black tree
public class TreeNode{
	public Building val;
	TreeNode left=externalNode;
	TreeNode right=externalNode;
	TreeNode parent=externalNode;
	int colour=BLACK;
	
	public TreeNode(Building val){
		this.val=val;
	}
}
	Building externalBuilding= new Building(-1,0,0);
	public final TreeNode externalNode= new TreeNode(externalBuilding);
TreeNode root=externalNode;

public boolean isEmpty() {
	return root==externalNode;
}

//The method of type tree that returns the node being searched.
public TreeNode locateBuild(int buildingNum) {
	TreeNode temp=root;
	while(temp!=externalNode) {
		if(buildingNum<temp.val.buildingNum) {
			temp=temp.left;
		}
		else if(buildingNum>temp.val.buildingNum) {
			temp=temp.right;
		}
		else {
			return temp;
		}
	}
	return null;
}

// searching the range of trees
public List<TreeNode> locteBuildInRange(int buildingNum1,int buildingNum2){
	List<TreeNode> treeList = new ArrayList();
	searchAndAdd(root,treeList,buildingNum1,buildingNum2);
	return treeList;
}

//saving the tress in a list in in-order traversal
public void searchAndAdd(TreeNode temp,List<TreeNode> treeList,int buildingNum1,int buildingNum2) {
	if(temp==externalNode) {
		return;
	}
	if(buildingNum1<temp.val.buildingNum) {
		searchAndAdd(temp.left,treeList,buildingNum1,buildingNum2);
	}
	if(buildingNum1<=temp.val.buildingNum && buildingNum2>=temp.val.buildingNum) {
		treeList.add(temp);
	}
	if(buildingNum2>temp.val.buildingNum) {
		searchAndAdd(temp.right,treeList,buildingNum1,buildingNum2);
	}
}
//The method to insert the given node object into the present tree
public void insertBuild(TreeNode node) {
	TreeNode temp=root;
	if(root==externalNode) {
		root=node;
		node.colour=BLACK;
		node.parent=externalNode;
	}
	else {
		node.colour=RED;
		while(true) {
		if(node.val.buildingNum<temp.val.buildingNum) {
			if(temp.left==externalNode) {
				temp.left=node;
				node.parent=temp;
				//node.colour=RED;
				break;
			}
			else {
				temp=temp.left;
			}
		}
		else if(node.val.buildingNum>temp.val.buildingNum) {
			if(temp.right==externalNode) {
				temp.right=node;
				node.parent=temp;
				//node.colour=RED;
				break;
			}
			else {
				temp=temp.right;
			}
		}
		}
		fixInsertBuild(node);
		}
	}

public void fixInsertBuild(TreeNode node) {
	while(node.parent.colour==RED) {
	TreeNode uncle=externalNode;
	//parents sibling is on the right
	if(node.parent==node.parent.parent.left) {
		uncle=node.parent.parent.right;
		//the case where only color change is required and rotation is not required
		if(uncle!=externalNode && uncle.colour==RED) {
			//parent becomes red
			node.parent.colour=BLACK;
			//uncle becomes black
			uncle.colour=BLACK;
			//grand parent becomes red. if root then needs to changed to black;
			node.parent.parent.colour=RED;
			node=node.parent.parent;
			continue;
		}
		//Double rotation case for the inserted node in the right side of parent
		if(node==node.parent.right) {
			node=node.parent;
			leftRotate(node);
		}
		//if the above condition is not true then single rotation required 
		//after color change
		node.parent.colour=BLACK;
		node.parent.parent.colour=RED;
		rightRotate(node.parent.parent);
		
	}
	//parent's sibling on the left 
	else {
		uncle=node.parent.parent.left;
		//the case where only color change is required and rotation is not required
		if(uncle!=externalNode && uncle.colour==RED) {
			 node.parent.colour = BLACK;
             uncle.colour = BLACK;
             node.parent.parent.colour = RED;
             node = node.parent.parent;
             continue;
         }
         if (node == node.parent.left) {
             //Double rotation needed for the inserted node in the left side of root
             node = node.parent;
             rightRotate(node);
         }
         node.parent.colour = BLACK;
         node.parent.parent.colour = RED;
       //if the above condition is not true then single rotation required 
         //after color change
         leftRotate(node.parent.parent);
     }
 }
 root.colour = BLACK;
}
public void leftRotate(TreeNode node) {
	if(node.parent!=externalNode) {
		//if the node to be rotated is on the left of parent
		if(node==node.parent.left) {
			node.parent.left = node.right;
		}
		//if the node to be rotated is on the right of parent
		else {
			node.parent.right = node.right;
		}
		//fixing the nodes parent pointer after rotation
		node.right.parent=node.parent;
		node.parent = node.right;
        if (node.right.left != externalNode) {
            node.right.left.parent = node;
        }
        node.right = node.right.left;
        node.parent.left = node;
    } else {// if nodes parent is external node means root node . 
    	//root needs to be rotated and pointers adjusted.
        TreeNode temp = root.right;
        root.right = temp.left;
        temp.left.parent = root;
        root.parent = temp;
        temp.left = root;
        temp.parent = externalNode;
        root = temp;
    }
	}

public void rightRotate(TreeNode node){
	if (node.parent != externalNode) {
		//if the node to be rotated is on the left of parent
        if (node == node.parent.left) {
            node.parent.left = node.left;
        } 
      //if the node to be rotated is on the right of parent
        else {
            node.parent.right = node.left;
        }
      //fixing the nodes parent pointer after rotation
        node.left.parent = node.parent;
        node.parent = node.left;
        if (node.left.right != externalNode) {
            node.left.right.parent = node;
        }
        node.left = node.left.right;
        node.parent.right = node;
    } else {// if nodes parent is external node means root node . 
    	//root needs to be rotated and pointers adjusted.
        TreeNode temp = root.left;
        root.left = root.left.right;
        temp.right.parent = root;
        root.parent = temp;
        temp.right = root;
        temp.parent = externalNode;
        root = temp;
    }
	
}
/*The method does not implicitly handle the connections of the new node with 
the previous node's left and right it just simply changes parent pointer to point to new node
. This needs to be handled explicitly */
public void replace(TreeNode target, TreeNode with){ 
    if(target.parent == externalNode){
        root = with;
    }else if(target == target.parent.left){
        target.parent.left = with;
    }else
        target.parent.right = with;
    with.parent = target.parent;
}

public boolean  delete(TreeNode removeNode){
 if((removeNode = locateBuild(removeNode.val.buildingNum))==null)return false ;
  TreeNode child;
  TreeNode temp = removeNode; // temporary reference node to be deleted
  int temp_original_color = temp.colour;
  //for 1 degree node removal
  if(removeNode.left == externalNode){
      child = removeNode.right;  
      replace(removeNode, removeNode.right);  
  }else if(removeNode.right == externalNode){
      child = removeNode.left;
      replace(removeNode, removeNode.left); 
  }
  //for greater than one degree node 
  else{
      temp = smallestLeftChild(removeNode.right);
      temp_original_color = temp.colour;
      child = temp.right;
      if(temp.parent == removeNode)
          child.parent = temp;
      else{
          replace(temp, temp.right);
          temp.right = removeNode.right;
          temp.right.parent = temp;
      }
      replace(removeNode, temp);
      temp.left = removeNode.left;
      temp.left.parent = temp;
      temp.colour = removeNode.colour; 
  }
  //if the deleted  node was black. Violations need to be fixed
  if(temp_original_color==BLACK)
      deleteFixup(child);
  return true;
}

public void deleteFixup(TreeNode child){
  while(child!=root && child.colour == BLACK){ 
	  //if the child is left child 
      if(child == child.parent.left){
          TreeNode sibling = child.parent.right;
          /*Case 1 child's sibling is red 
          1 exchange color of parent and sibling
          2 siblings children(black in color as sibling was previously red) become new sibling  
          3 we successfuly convert case 1 into case 2,3 or 4*/
          if(sibling.colour == RED){
        	  sibling.colour = BLACK;
              child.parent.colour = RED;
              leftRotate(child.parent);
              sibling = child.parent.right;
          }
          /*  Case 2 if sibling is black and both children are black
           sibling becomes red with removal of one black
           child stays black with removal of second black
           if case 2 comes through case 1 child node moves up to parent
           and loop should be terminated */
          if(sibling.left.colour == BLACK && sibling.right.colour == BLACK){
        	  sibling.colour = RED;
              child = child.parent;
              continue;
          }
          /*  Case 3 if sibling is black and siblings left child is red and right child is  black
           switch color between sibling left child and sibling and right rotate
           after rotation child's sibling becomes black with red left child 
           case 3 converted to case 4*/
          else if(sibling.right.colour == BLACK){
        	  sibling.left.colour = BLACK;
        	  sibling.colour = RED;
              rightRotate(sibling);
              sibling = child.parent.right;
          }
          /*  Case 4 if sibling is black and sibling's right child is black
           make color changes to the parent , sibling and siblings right child 
           left rotate the parent and point to the root node to terminate loop*/
          if(sibling.right.colour == RED){
        	  sibling.colour = child.parent.colour;
              child.parent.colour = BLACK;
              sibling.right.colour = BLACK;
              leftRotate(child.parent);
              child = root;
          }
      }// if the child is right child . symmetric cases with opposite rotation.
      else{
          TreeNode sibling = child.parent.left;
          if(sibling.colour == RED){
        	  sibling.colour = BLACK;
              child.parent.colour = RED;
              rightRotate(child.parent);
              sibling = child.parent.left;
          }
          if(sibling.right.colour == BLACK && sibling.left.colour == BLACK){
        	  sibling.colour = RED;
              child = child.parent;
              continue;
          }
          else if(sibling.left.colour == BLACK){
        	  sibling.right.colour = BLACK;
        	  sibling.colour = RED;
              leftRotate(sibling);
              sibling = child.parent.left;
          }
          if(sibling.left.colour == RED){
        	  sibling.colour = child.parent.colour;
              child.parent.colour = BLACK;
              sibling.left.colour = BLACK;
              rightRotate(child.parent);
              child = root;
          }
      }
  }
  child.colour = BLACK; 
}

//finding the left most child from a given root
public TreeNode smallestLeftChild(TreeNode subTreeRoot){
  while(subTreeRoot.left!=externalNode){
      subTreeRoot = subTreeRoot.left;
  }
  return subTreeRoot;
}

public void callPrint() {
	printTree(root);
}
public void printTree(TreeNode node) {
    if (node == externalNode) {
        return;
    }
    printTree(node.left);
    System.out.print(((node.colour==RED)?"Color: Red ":"Color: Black ")+"buildingnum: "+node.val.buildingNum+" executedtime: "+node.val.executed_time+" toataltime: "+node.val.total_time+"\n");
    printTree(node.right);
}
}


