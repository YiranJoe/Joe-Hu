package PriorityQueue;
import java.io.*;
import java.util.*;
public class PriorityQueue<T> {
	
	private class Bundle {
		T element;
		int priority;
		
		public Bundle(T e,int p) {
			element=e;
			priority=p;
		}
		
		public String toString() {
			return element+": "+priority;
		}
	}
	
	private HashMap<T,Bundle> map=new HashMap<>();
	private ArrayList<Bundle> queue=new ArrayList<>();
	
	public void put(T info,int p) {
		if(map.containsKey(info)) {
			queue.remove(map.get(info));
			map.remove(info);
		}
		if(queue.isEmpty()) {
			Bundle b = new Bundle(info,p);
			queue.add(b);
			map.put(info, b);
			return;
		}else if(queue.get(0).priority<p) {
			Bundle a=new Bundle(info,p);
			queue.add(0,a);
			map.put(info, a);
			return;
		}else if(queue.get(queue.size()-1).priority>p) {
			Bundle a=new Bundle(info,p);
			queue.add(a);
			map.put(info, a);
			return;
		}else {
			int start=0;
			int end=queue.size()-1;
			while(end>start) {
				Bundle a=queue.get((start+end)/2);
				if(a.priority>p) {
					start=(start+end)/2+1;
				}else {
					end=(start+end)/2;
				}
			}
			Bundle a=new Bundle(info,p);
			queue.add(start,a);
			map.put(info, a);
		}
	}
	
	public int size() {
		return queue.size();
	}
	
	public String toString() {
		return queue.toString();
	}
	
	public T pop() {
		return queue.remove(0).element;
	}
	
}
