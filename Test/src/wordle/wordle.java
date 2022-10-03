package wordle;
import java.io.*;
import java.util.*;
public class wordle {

	
	private static String fileName="answer";//this is the file that used for selecting words that user is guessing
	private static String bigWordBank="vocab";//this is a bigger file with words that user can input to their guesses
	private static ArrayList<String> inputWordBank=new ArrayList<>();//this is a list that includes all the words in the word bank
	//that the user can choose from
	private static ArrayList<String> wordBank=new ArrayList<>();//this is the wordbank for the smaller file
	private static ArrayList<Character> alphabet=new ArrayList<>();//this includes the alphabet a through z
	private static ArrayList<Character> usedlist=new ArrayList<>();//this includes the word that is used
	private static String word="";//this will be the random word selected
	private static int chances=6;//since I am not using magic number, changeing this makes user have more chances
	private static int letter=5;//indicating how many letters does that the word we are guessing have 
	private static char[][] grid=new char[chances][letter];//the grid to show the progress
	private static char[][] hintGrid=new char[chances][letter];//the grid to tell user the progress
	private static int currRow=0;//dynamic location of the current row of the above grids
	private static Scanner input=new Scanner(System.in);//user's input tool
	private static boolean lost=false;//determine if lost
	private static boolean win=false;//determine if won
	private static char[] bar=new char[letter];//a dynamic bar help to check if the user wins
	
	//the function I used to read in words
	//reads in 2 files of words used for choosing words and checking if the user input a applicable word
	public static void gettingwords() throws Exception{
		BufferedReader in=new BufferedReader(new FileReader(fileName));//read in first file
		String str="";
		while(str!=null) {
			str=in.readLine();
			wordBank.add(str);//adding the word to the bank
		}
		in=new BufferedReader(new FileReader(bigWordBank));//reading the second file
		str=in.readLine();
		while(str!=null) {
			inputWordBank.add(str);//adding the word to the bank
			str=in.readLine();
		}
		
		//I wrote a binary search for word bank search, so before I binary search in the arraylist I have to sort
		//them in alphabetical order
		Collections.sort(inputWordBank);
	}
	
	public static void main(String[] args) throws Exception {
		//adding the letters to the alphabet
		for(int i=0;i<26;i++) {
			alphabet.add((char)(i+97));
		}
		gettingwords();//read in words
		int wordNum=(int)(Math.random()*wordBank.size());//choose a random word
		word=wordBank.get(wordNum);
		System.out.println(word);
		//setting up the grids for better understanding for users
		for(int i=0;i<chances;i++) {
			for(int j=0;j<letter;j++) {
				grid[i][j]='-';
				hintGrid[i][j]='*';
			}
		}
		process(word);//the interactive process with users
		if(lost) {
			System.out.println("YOU LOST");
			System.out.println("the word is: "+word);//tell the user the right words
		}else if(win){
			System.out.println("Congratulations! You win! You used "+currRow+" tries!");//tell the user how many times they have guessed
		}
	}
	
	//using the dynamic bar to check if the user got the word correctly
	public static boolean check() {
		for(int i=0;i<letter;i++) {
			if(bar[i]!='@') {
				return false;
			}
		}
		return true;
	}
	
	//this is a recursive function that keeps going until either user guess the right word or the user used all the chances
	public static void process(String w) {
		int hintTime=0;//I used this variable to see how many lines of hints should I display in my program
		System.out.println("current status");
		for(int i=0;i<chances;i++) {
			for(int j=0;j<letter;j++) {
				System.out.print(grid[i][j]+" ");
			}
			System.out.println();
			if(hintTime<currRow){//display the hint grid for the guesses of user
				for(int j=0;j<letter;j++) {
					System.out.print(hintGrid[i][j]+" ");
				}
				System.out.println();
			}
			hintTime++;
		}
		//check if user won or lost
		if(check()) {
			win=true;
			return;
		}
		if(currRow>=chances) {
			lost=true;
			return;
		}
		System.out.println("input your guess:");
		String guess=input.next();//input the user guess
		
		//check if the input is valid in 2 ways: the length of the input and if the word exist in the word bank
		while(guess.length()!=letter||!find(guess)) {
			System.out.println("guess not applicable,try again:");
			guess=input.next();
		}
		//use the dynamic variable currRow to record the user's guess in the 2d array
		for(int j=0;j<letter;j++) {
			grid[currRow][j]=guess.charAt(j);
		}
		//check whether for each part of the input word is corresponded to the selected word
		for(int i=0;i<letter;i++) {
			if(word.charAt(i)==guess.charAt(i)) {//if it is equal set the grid to '@'
				bar[i]='@';
				hintGrid[currRow][i]='@';
				if(!usedlist.contains(guess.charAt(i))) 
					usedlist.add(guess.charAt(i));
			}else{
				boolean found=false;
				for(int j=0;j<letter;j++) {//check to see if the current letter appears in the randomized chosen word
					if(word.charAt(j)==guess.charAt(i)) {//if there is the letter in the word, add the letter to the used list and break the loop
						found=true;
						bar[i]='x';
						hintGrid[currRow][i]='x';
						if(!usedlist.contains(guess.charAt(i))) 
							usedlist.add(guess.charAt(i));
						break;
					}
				}
				if(!found) {//if did not found the letter, remove the letter from the alphabet
					alphabet.remove((Object)guess.charAt(i));
					bar[i]='-';
					hintGrid[currRow][i]='*';
				}
			}
		}
		currRow++;//the dynamic variable plus one to process the next row
		System.out.println("word left:"+alphabet);
		System.out.println("word appeared:"+usedlist);
		process(w);
		
	}
	
	//a binary search program to see if the user-input word is in the big word bank
	public static boolean find(String w) {
		if(w.equals(inputWordBank.get(inputWordBank.size()-2))||w.equals(inputWordBank.get(0))) return true;
		int start=0;
		int end=inputWordBank.size()-1;
		while(start<end) {
			int mid=(start+end)/2;
			if(mid>=inputWordBank.size()||mid<0) return false;
			if(inputWordBank.get(mid).equals(w)) {
				return true;
			}
			else if(inputWordBank.get(mid).compareTo(w)<0) {
				start=mid+1;
			}else {
				end=mid;
			}
		}
		return false;
	}

}
