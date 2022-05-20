import java.util.*;
public class testProblem {

	/*
	 * Write a method that takes in an 2D array of random characters—
	 * all in random order, output the numbers of all appeared set of character ordered in alphabetical order. 
	 */
	public static String get(char[][] arr2D) {
		HashMap<String,Integer> map=new HashMap<>();
//		PriorityQueue<Character> qForMap=new PriorityQueue<>();
		for(int i=0;i<arr2D.length;i++) {
			PriorityQueue<Character> q=new PriorityQueue<>();
			for(int j=0;j<arr2D[i].length;j++) {
				q.add(arr2D[i][j]);
			}
			String a="";
			while(!q.isEmpty()) {
				a+=q.poll();
			}
			map.put(a, map.containsKey(a)?map.get(a)+1:1);
		}
		return map.toString().substring(1,map.toString().length()-1);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[][] c={{'y','u','o','t'},{'b','a','c'},{'h','y','a'},{'d','r','t'},{'a','b','c'},{'a','c','b'},{'c','d','e'}};
		System.out.println(get(c));
	}

}
