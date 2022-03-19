import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

class Player{
 public void roll( Dice dice ) {
  int x = 0 ;
  Random random = new Random() ;
  x = random.nextInt(6) + 1 ;
  dice.value = x ;
  System.out.println(dice.name + ":" + x );
 
 }
  
 boolean isstraight( Vector<Dice> dicelist ) {
	 int[] temp ;
	 temp = new int[99] ;
	 int temp2 = 0 ;
	 for( int i = 0 ; i < dicelist.size() ; i++) {
		 temp[i] = dicelist.get(i).value ;
	 }
	 
	 for( int i = 0 ; i < dicelist.size() ; i++ ) {
		 for( int j = 0 ; j < dicelist.size() - i - 1 ; j++ ) {
			 if( temp[j] > temp[j+1]) {
				 temp2 = temp[j+1] ;
				 temp[j+1] = temp[j] ;
				 temp[j] = temp2 ;
			 } 
		 } 
	 }

	 for( int i = 0 ; i < dicelist.size() - 1 ; i++ ) {
		 if( temp[i] + 1 != temp[i+1])
			 return false ;
		 
	 }
	 return true ;
 }
 public void compare( Vector<Dice> dicelist ) {
  int total = 0 ;
  for( int i =  0 ; i < dicelist.size() ; i++ ) {
	  total = total + dicelist.get(i).value ;
  }
  if( dicelist.get(0).value == dicelist.get(1).value && dicelist.get(1).value == dicelist.get(2).value )
   System.out.println("three of a kind") ;
  else if( isstraight( dicelist ) ){
   System.out.println( "straight" ) ;
  }
  else if( dicelist.get(0).value == dicelist.get(1).value || dicelist.get(0).value == dicelist.get(2).value || dicelist.get(1).value == dicelist.get(2).value )
   System.out.println( "pair" ) ;
  else 
   System.out.println( "sum : " + total );
  
 }
 
}
class Dice{
 String name ;
 int value ;
 public Dice( String temp ) {
  value = 0 ;
  name = temp ;
 }
 
 
}
public class Main {

 public static void main(String[] args) {
  Scanner scanner = new Scanner( System.in ) ;
  int command = 0 ;
  int num = 0 ;
  System.out.print("請輸入要執行的動作 :\n" + "1 : 擲骰子\n" + "2 : 測試(testcode)\n" + "0 : 離開遊戲\n");
  command = scanner.nextInt() ;
  while( command != 0 ) {
   if( command == 1 ) {
    Player play1 = new Player() ;
    Vector<Dice> dicelist = new Vector<Dice>() ;
    for( int i = 1 ; i <= 3 ; i++ ) {
    	dicelist.add(new Dice( "dice" + i )) ;
    }
    for( int i = 0 ; i < dicelist.size() ; i++ ) {
        play1.roll( dicelist.elementAt(i) ) ;
    }
    
    play1.compare( dicelist ) ;
   }
   else if( command == 2 ) {
	    Player play1 = new Player() ;
	    Vector<Dice> dicelist = new Vector<Dice>() ;
	    for( int i = 1 ; i <= 3 ; i++ ) {
		    System.out.println( "請輸入第" + i + "個數字\n" ) ;
		    num = scanner.nextInt() ;
	    	dicelist.add(new Dice( "dice" + i )) ;
	    	dicelist.elementAt(i - 1).value = num ;
	    }

	    play1.compare(dicelist);
	   
   }
   else 
    System.out.println( "This is a wrong instruction" ) ;
   
   System.out.print( "1 : 繼續遊戲\n" + "2 : 測試(testcode)\n" + "0 : 離開遊戲\n");
   command = scanner.nextInt() ;
  }
  
  scanner.close();
 }

}