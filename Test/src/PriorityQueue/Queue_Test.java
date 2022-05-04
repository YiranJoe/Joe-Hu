package PriorityQueue;

public class Queue_Test {

	public static void main(String[] args) {
		PriorityQueue<String> list=new PriorityQueue<>();
		list.put("Condy", 10);
		list.put("Yiran", 9);
		list.put("Cooper", 8);
		list.put("Gregory", 7);
		list.put("Daniel", 19);
		list.put("Mr. Friedman", 4);
		list.put("David", 6);
		list.put("Luis", 7);
		list.put("Crystal", 25);
		list.put("Jason", 7);
		list.put("LUO", 100);
		System.out.println(list);
		System.out.println(list.size());
		System.out.println(list.pop());
		System.out.println(list);
		System.out.println(list.size());
//		PriorityQueue<Integer> listInt=new PriorityQueue<>();
//		int count=0;
//		for(int i=0;i<50;i++) {
//			int a=(int) (Math.random()*200);
//			count++;
//			listInt.put(count, a);
//		}
//		System.out.println(listInt);
	}
}
