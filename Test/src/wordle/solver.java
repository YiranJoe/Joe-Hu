package wordle;
import java.io.*;
import java.util.*;
public class solver {

	//the hint can be separated into 3 different symbol
	//- means it is grey
	//x means it is yellow
	//any other letter means green
	/*
	 * for example: when enter "hello", 
	 * it is grey, green, grey, grey, yellow
	 * input the hint "- a - - x"(you have to include the space!)
	 * it gives you the remaining options for the words
	 * repeat 1-4
	 * GOOD LUCK WITH WORDLE!
	 */
	
	public static ArrayList<Character> ban=new ArrayList<>();
	public static ArrayList<Character> contain=new ArrayList<>();
	public static Scanner io=new Scanner(System.in);
	public static ArrayList<String> words=new ArrayList<>();
	public static HashMap<Integer,Character> sure=new HashMap<>();
	public static HashMap<Integer,Character> unsure=new HashMap<>();
	
	
	public static void analysis(String word,ArrayList<String> list) {
		int length=word.length();
		System.out.print("input the hint:");
		for(int i=0;i<length;i++) {
			char a=io.next().charAt(0);
			if(a=='-') {
				ban.add(word.charAt(i));
				unsure.put(i, word.charAt(i));
			}else if(a=='x') {
				contain.add(word.charAt(i));
				unsure.put(i, word.charAt(i));
			}else {
				sure.put(i, word.charAt(i));
			}
		}
		for(int index:sure.keySet()) {
			if(contain.indexOf(sure.get(index))==-1) {
				contain.add(sure.get(index));
			}
			for(int i=0;i<list.size();i++) {
				if(list.get(i).charAt(index)!=sure.get(index)) {
					list.remove(i);
					i--;
				}
			}
		}
		for(int i=0;i<contain.size();i++) {
			for(int j=0;j<ban.size();j++) {
				if(ban.get(j)==contain.get(i)) {
					ban.remove(j);
					j--;
				}
			}
			for(int j=0;j<list.size();j++) {
				if(list.get(j).indexOf(contain.get(i))==-1) {
					list.remove(j);
					j--;
				}
			}
			for(int j=0;j<list.size();j++) {
				if(list.get(j).indexOf(contain.get(i))==word.indexOf(contain.get(i))&&!sure.containsValue(contain.get(i))) {
					list.remove(j);
					j--;
				}
			}
		}
//		System.out.println(ban);
//		System.out.println(contain);
//		System.out.println(sure);
		for(int i=0;i<ban.size();i++) {
			for(int j=0;j<list.size();j++) {
				if(list.get(j).indexOf(ban.get(i))!=-1) {
					list.remove(list.get(j));
					j--;
				}
			}
		}
		
		for(int index:unsure.keySet()) {
			for(int i=0;i<list.size();i++) {
				if(list.get(i).charAt(index)==unsure.get(index)) {
					list.remove(i);
					i--;
				}
			}
		}

		
		System.out.println(list);
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader in=new BufferedReader(new FileReader("answer"));
		String a=in.readLine();
		while(a!=null) {
			words.add(a);
			a=in.readLine();
		}
		while(true) {
			System.out.print("input your try:");
			String w=io.next();
			analysis(w,words);
		}
	}
}
