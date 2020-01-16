





public class minHeapBuild {
public Building[] heap;
public int size;

public final int minIndex=1;

public minHeapBuild() {
	this.heap= new Building[2001];
	//Building min=new Building(Integer.MIN_VALUE,Integer.MIN_VALUE,0);
	//heap[0]=min;
	this.size=0;
}

public void buildCityHeap() {
	for(int i=(size/2);i>=1;i--) {
		minHeapify(i);
	}
}

// to see the top of the heap or top of a priority queue
public Building peekMin() {
	return heap[0];
}

//to check if heap is empty
public boolean isEmpty() {
	return size==0 ;
}



//to calculate parent index
public int parent(int index) {
	return (index-1)/2;
	
}


// to insert a building into the heap 
public void pushBuilding(Building building) {
	if(size==2001) {
		return;
	}
	
	int current=size;
	size++;
	heap[current]=building;
	
	while( current!=0 && heap[current].executed_time <= heap[parent(current)].executed_time) {
		if(heap[current].executed_time< heap[parent(current)].executed_time || heap[current].buildingNum< heap[parent(current)].buildingNum) {
		swap(current,parent(current));
		current= parent(current);
		}
		else {
			break;
		}
	}
}

// to remove a building from the top of the heap (same as priority queue)
public Building pollBuilding() {
	if (size == 1){
		Building min = heap[0];
        size--;
        heap[0] = null;
        return min;
    }

	Building min = heap[0];
	heap[0]=heap[size-1];
	heap[size-1]=null;
	size--;
	minHeapify(0);
	return min;
}

// method to maintain the min heap property of the heap. 
public void minHeapify(int i) {
	 int l = i*2 + 1;//left index
     int r = i*2 + 2;//right index
     //replace ith with smallest among left child and right child
     int smallest = i;
     if (l < size && heap[i].executed_time >= heap[l].executed_time && (heap[i].executed_time > heap[l].executed_time|| heap[i].buildingNum > heap[l].buildingNum) ){
         smallest = l;
     }
     if (r < size && heap[smallest].executed_time >= heap[r].executed_time && (heap[smallest].executed_time > heap[r].executed_time || heap[smallest].buildingNum > heap[r].buildingNum)){
         smallest = r;
     }
     if (smallest != i && heap[i].executed_time >= heap[smallest].executed_time && (heap[i].executed_time > heap[smallest].executed_time || heap[i].buildingNum > heap[smallest].buildingNum)){
         swap(i,smallest);
         minHeapify(smallest);//recur if a swap occurs
     }
	}


public void swap(int i,int j) {
	Building temp=heap[i];
	heap[i]=heap[j];
	heap[j]=temp;
}



public void printHeap() {
	for(int i=1;i<=size;i++) {
		System.out.println(heap[i].buildingNum+" "+heap[i].executed_time+"\n");
	}
}

}
