package wordle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
public class solver {

	/*
	 * ALWAYS start with "soare" and "until"
	 * the hint can be separated into 3 different symbol
	 * - means it is grey
	 * x means it is yellow
	 * any other letter means green
	 * for example: when enter "hello", 
	 * it is grey, green, grey, grey, yellow
	 * input the hint "- a - - x"(you have to include the space!)
	 * it gives you the remaining options for the words
	 * repeat 1-4
	 * GOOD LUCK WITH WORDLE!
	 */
	 
	public static int len=5;//the length of the words the user wants to guess
	public static ArrayList<Character> ban=new ArrayList<>();//the list for storing the "grey" letters
	public static ArrayList<Character> contain=new ArrayList<>();//the list for storing the "yellow" letters
	public static Scanner io=new Scanner(System.in);//where user can input 
	public static ArrayList<String> words=new ArrayList<>();//the remaining word list, which we are always cutting words from
	public static ArrayList<String> vocab=new ArrayList<>();//the whole word list, which we never modify
	public static HashMap<Integer,Character> sure=new HashMap<>();//a HashMap that keep track of the "green" letters
//	public static HashMap<Integer,Character> unsure=new HashMap<>();
	public static ArrayList<Character>[] unsure=new ArrayList[len];//this is an Array of ArrayList, containing letters that cannot appear in the current digit
	public static boolean delima=false;//this is the boolean that we begin using the unmatured filtering system
	public static String fileName="answer";
	
	public static void analysis(String word,ArrayList<String> list) {
		int length=len;//I do not know why I wrote this
		System.out.println("input the hint:");//every time the user has to input the output yielded from the game
		for(int i=0;i<length;i++) {
			char a=io.next().charAt(0);
			//identify the character the user writes for each corresponding letter
			if(a=='-') {
				//if the letter is "grey", add the letter to the 'ban' ArrayList and its corresponding digit-ban ArrayList
				ban.add(word.charAt(i));
				unsure[i].add(word.charAt(i));
			}else if(a=='x') {
				//if the letter is "yellow", add the letter to the 'contain' ArrayList and its corresponding digit-ban ArrayList
				contain.add(word.charAt(i));
				unsure[i].add(word.charAt(i));
			}else {
				//if it is "green", use the HashMap which key is the digit and value is the letter
				sure.put(i, word.charAt(i));
			}
		}
		
		//every time we go through the HashMap through a for-each loop. 
		//the Wordle is weird, that it prioritize "green" over "yellow": if the letter is already in the right place,
		//then if the same letter appear somewhere else, the game calls the wrong-placed letter a "grey" letter
		for(int index:sure.keySet()) {
			//so here, we have to add the letter in the HashMap to the ArrayList<> contain
			if(contain.indexOf(sure.get(index))==-1) {
				contain.add(sure.get(index));
			}
			//this is the first round of words-cutting: 
			//we go through the list of words, 
			//if the letter at the position 'index' does not match what the key 'index' produce in the map
			//we remove the word
			for(int i=0;i<list.size();i++) {
				if(list.get(i).charAt(index)!=sure.get(index)) {
					list.remove(i);
					i--;
				}
			}
		}
		//the game is so weird. It prioritizes "grey" over "yellow", that sometimes the same letter
		//appears multiple times in one word, one of them is yellow and the others are grey
		//which means we need to remove the item in the ArrayList<> ban if the item also appears in contain
		for(int i=0;i<contain.size();i++) {
			for(int j=0;j<ban.size();j++) {
				if(ban.get(j)==contain.get(i)) {
					ban.remove(j);
					j--;
				}
			}
			//here comes the second round of cut
			//if the word does not contains the letters in the list contain
			//we remove the word
			for(int j=0;j<list.size();j++) {
				if(list.get(j).indexOf(contain.get(i))==-1) {
					list.remove(j);
					j--;
				}
			}
			//after the previous cut, the remaining words of the list all contains the letter in the "contain"
			//here comes the third cut: if the index of the letter in the current word is the same as 
			//the index of the letter of the user-input word, and in the HashMap we do not have the letter as our value
			//that means that the letter cannot be in this current position, so we remove the word
			for(int j=0;j<list.size();j++) {
				if(list.get(j).indexOf(contain.get(i))==word.indexOf(contain.get(i))&&!sure.containsValue(contain.get(i))) {
					list.remove(j);
					j--;
				}
			}
		}
		
//this is when we want to find bugs--print everything and see if every section of code does what it needs to 
//		System.out.println(ban);
//		System.out.println(contain);
//		System.out.println(sure);
		
		
		//here comes the forth round of cut: 
		//go through the ban list and the word list using a nested for-loop
		//if the words have any letter that the ban list contains
		//remove the word
		for(int i=0;i<ban.size();i++) {
			for(int j=0;j<list.size();j++) {
				if(list.get(j).indexOf(ban.get(i))!=-1) {
					list.remove(list.get(j));
					j--;
				}
			}
		}
		
		//here comes the fifth round of cutting:
		//we go through the Array of ArrayList using a nested for-loop
		//inside the nested for-loop, we go through the word list 
		//if the word's 'i' digit matches with any letters of unsure[i], remove the word from the list
		for(int i=0;i<unsure.length;i++) {
			for(int k=0;k<unsure[i].size();k++) {
				for(int j=0;j<list.size();j++) {
					if(list.get(j).charAt(i)==unsure[i].get(k)) {
						list.remove(j);
						j--;
					}
				}
			}
		}
//		System.out.println(Arrays.toString(unsure));
		
		//this is the key of my still-improving filtering method
		//if there are at least 2 digit that are sure
		//the boolean goes true
		if(sure.size()>=2) {
			delima=true;
		}

		//every time after 5 rounds of cutting, we provide the remaining words for user to make the next pick
		System.out.println("Choices:");
		System.out.println(list);
	}
	
	//after several rounds, the words in the list are going to be decreasing to only a few
	public static void main(String[] args) throws Exception{
		//we have to innitialize the Array of ArrayList in the first place so that it works
		for(int i=0;i<len;i++) {
			unsure[i]=new ArrayList<Character>();
		}
		//read in the file using BufferedReader
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		//read the file line by line and store the information into the 2 lists, the modifying list and the unmodifying list
		String a=in.readLine();
		while(a!=null) {
			words.add(a);
			vocab.add(a);
			a=in.readLine();
		}
		//an infinite loop which stimulate the process of user analyzing words
		while(true) {
			System.out.println("input your try:");//giving the procedure hint
			String w=io.next();//user input
			if(w.equals("end")) {
				System.out.println("CONGRATULATIONS");
				return;
			}
			analysis(w,words);//analyze the words in the list
			if(delima==true&&words.size()>=3) {//if the boolean is true at the same time the remaining word list has more than 2 words
				System.out.println(best(vocab,words));//the method best return an ArrayList of words which gives the user 
				//the best words to select in order to eliminate more words
			}
		}
	}
	
	//a method that take in two parameters: the whole list of words and the remaining options of words
	//return an ArrayList which gives the user the best words to choose
	public static ArrayList<String> best(ArrayList<String> listOfWords,ArrayList<String> remainingWords){
		ArrayList<String> returnList=new ArrayList<>();//list we return
		//we go through the whole list of words, choose the words that satisfy several requirement and add to the list
		//containLetter: see below method containLetter
		//mostCommonLetters: see below method mostCommonLetters
		//isBest: see below method isBest
		for(int i=0;i<listOfWords.size();i++) {
			if(containLetter(listOfWords.get(i),mostCommonLetters(remainingWords))
					&&isBest(listOfWords.get(i))) {
				returnList.add(listOfWords.get(i));
			}
		}
		//I will delete the following TWO lines of code when I fully test my program
		System.out.println("Common letters:");
		System.out.println(mostCommonLetters(remainingWords));
		//give the hint to the user that he can choose from the list returned
		System.out.println("Suggest you to choose:");
		return returnList;
	}
	
	//this is a helper method that takes in 2 parameters: a word and a list of characters
	//the word must be a five-letter word without repeating letters: see below method single
	//it returns true if the word contains all characters in the list
	//false otherwise
	public static boolean containLetter(String words, ArrayList<Character> list) {
		int count=0;
		if(!single(words)) {
			return false;
		}
		//the count keep tract of how often the characters in the list appear in the word
		for(int i=0;i<words.length();i++) {
				for(int j=0;j<list.size();j++) {
					if(words.charAt(i)==list.get(j)) {
						count++;
						break;
					}
				}
		}
		//if it contains all the character, the boolean is true
		if(count>=list.size()) {
			return true;
		}else {
			return false;
		}
	}
	//this is a helper method that help identify if the parameter word is a word
	//that do not have repeating letters
	public static boolean single(String word) {
		//using a nested for-loop to see if the word have repeating letters
		for(int i=0;i<word.length()-1;i++) {
			for(int j=i+1;j<word.length();j++) {
				if(word.charAt(i)==word.charAt(j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	//this is a helper method that determine if it is meaningful to try the word
	//if the characters on the word's digit are in the ban list of the corresponding Array of ArrayList,return false
	//otherwise return true
	public static boolean isBest(String word) {
		for(int i=0;i<unsure.length;i++) {
			if(unsure[i].contains(word.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	//FINALLY this is the most complicated helper method
	//that takes in a list of remaining words 
	//and return the 3 most common character THAT
	//appears in the UNSURE digit of the remainig words
	//I use the PriorityQueue I built for this part
	public static ArrayList<Character> mostCommonLetters(ArrayList<String> remainingWords) {
		ArrayList<Integer> unsureDigit=new ArrayList<>();
		ArrayList<Character> returnList=new ArrayList<>();
		PriorityQueue<Character> numlist=new PriorityQueue<>();
		HashMap<Character,Integer> list=new HashMap<>();//this HashMap contains the letter as its key, and the times it appear as its value
		//add the unsure digit to the arraylist created
		for(int i=0;i<len;i++) {
			if(!sure.containsKey(i)) {
				unsureDigit.add(i);
			}
		}
		//for each word's unsure digits, 
		for(int i=0;i<remainingWords.size();i++) {
			for(int j=0;j<unsureDigit.size();j++) {
				//if the list contains the key, increase its value by 1
				//if the list does not contain the key, put the new key and have it a value of 0
				if(list.containsKey(remainingWords.get(i).charAt(unsureDigit.get(j)))) {
					list.put(remainingWords.get(i).charAt(unsureDigit.get(j)),1+list.get(remainingWords.get(i).charAt(unsureDigit.get(j))));
				}else {
					list.put(remainingWords.get(i).charAt(unsureDigit.get(j)), 0);
				}
			}
		}

		//use the data structure that I build
		for(char Character:list.keySet()) {
			numlist.put(Character,list.get(Character));
		}
		//the top three (or at least the length of the numlist) are returned
		for(int i=0;i<3&&i<numlist.size();i++) {
			returnList.add(numlist.pop());
		}
//		System.out.println(numlist);
		return returnList;
	}
}
