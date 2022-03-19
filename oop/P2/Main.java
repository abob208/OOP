import java.io.*;
import java.util.*;
import java.util.ArrayList;
class GameHelper {

	  private static final String alphabet = "abcdefg";
	  private int gridLength = 7;
	  private int gridSize = 49;
	  private int [] grid = new int[gridSize];
	  private int comCount = 0;


	  public String getUserInput(String prompt) {
	     String inputLine = null;
	     System.out.print(prompt + "  ");
	     try {
	       BufferedReader is = new BufferedReader(
		 new InputStreamReader(System.in));
	       inputLine = is.readLine();
	       if (inputLine.length() == 0 )  return null; 
	     } catch (IOException e) {
	       System.out.println("IOException: " + e);
	     }
	     return inputLine.toLowerCase();
	  }

	 
	 public void removeCom( ArrayList<String> loc, int size ) {
		   int[] temp = new int[size] ;
		   int count = 0 ;
		   int num = 0 ;
		   for( int i = 0 ; i < size ; i++ ) {
			   for( int j = 0 ; j < alphabet.length() ; j++ ) {
				   if( loc.get(i).charAt(0) == alphabet.charAt(j)) {
					   num = loc.get(i).charAt(1) - '0' ;
					   temp[count] = num * 7 + j ;
					   grid[temp[count]] = 0 ;
					   count++ ;
				   }
			   }
		   }
		   
	 }
	  
	 public ArrayList<String> placeDotCom(int comSize) {                 // line 19
	    ArrayList<String> alphaCells = new ArrayList<String>();
	    String temp = null;                                // temporary String for concat
	    int [] coords = new int[comSize];                  // current candidate coords
	    int attempts = 0;                                  // current attempts counter
	    boolean success = false;                           // flag = found a good location ?
	    int location = 0;                                  // current starting location
	    
	    comCount++;                                        // nth dot com to place
	    int incr = 1;                                      // set horizontal increment
	    if ((comCount % 2) == 1) {                         // if odd dot com  (place vertically)
	      incr = gridLength;                               // set vertical increment
	    }

	    while ( !success & attempts++ < 200 ) {             // main search loop  (32)
		location = (int) (Math.random() * gridSize);      // get random starting point
	        //System.out.print(" try " + location);
		int x = 0;                                        // nth position in dotcom to place
	        success = true;                                 // assume success
	        while (success && x < comSize) {                // look for adjacent unused spots
	          if (grid[location] == 0) {                    // if not already used
	             coords[x++] = location;                    // save location
	             location += incr;                          // try 'next' adjacent
	             if (location >= gridSize){                 // out of bounds - 'bottom'
	               success = false;                         // failure
	             }
	             if (x>0 && (location % gridLength == 0)) {  // out of bounds - right edge
	               success = false;                         // failure
	             }
	          } else {                                      // found already used location
	              // System.out.print(" used " + location);  
	              success = false;                          // failure
	          }
	        }
	    }                                                   // end while

	    int x = 0;                                          // turn good location into alpha coords
	    int row = 0;
	    int column = 0;
	    // System.out.println("\n");
	    while (x < comSize) {
	      grid[coords[x]] = 1;                              // mark master grid pts. as 'used'
	      row = (int) (coords[x] / gridLength);             // get row value
	      column = coords[x] % gridLength;                  // get numeric column value
	      temp = String.valueOf(alphabet.charAt(column));   // convert to alpha
	      
	      alphaCells.add(temp.concat(Integer.toString(row)));
	      x++;

	       System.out.print("  coord "+x+" = " + alphaCells.get(x-1));
	      
	    }
	    // System.out.println("\n");
	    
	    return alphaCells;
	   }
	 
	   public ArrayList<String> move(ArrayList<String> loc, int size){
		   ArrayList<String> alphaCells = new ArrayList<String>();
		   Random random = new Random() ;
		   int[] step = { 1, -1 } ;
		   int[] step2 = { 1, -1, 2, -2 } ;
		   int[] temp = new int[size] ;
		   int count = 0 ;
		   int num = 0 ;
		   boolean success = true ;
		   String temp2 = null ;
		   for( int i = 0 ; i < size ; i++ ) {
			   for( int j = 0 ; j < alphabet.length() ; j++ ) {
				   if( loc.get(i).charAt(0) == alphabet.charAt(j)) {
					   num = loc.get(i).charAt(1) - '0' ;
					   temp[count] = num * 7 + j ;
					   count++ ;
				   }
			   }
		   }
		   
		   int[] copy = new int[size] ;
		   for( int i = 0 ; i < size ; i++ ) {
			   copy[i] = temp[i] ;
		   }
		   for( int i = 0 ; i < size ; i++ ) {
			   grid[temp[i]] = 0 ;
		   }
		   if( temp[0] + 1 == temp[1]) {
			   if( size > 2 ) {
			     num = random.nextInt(2) ;
			     for( int i = 0 ; i < size ; i++ ) {
			    	 temp[i] = temp[i] + step[num] ;  	 
			     }
			   }
			   else if( size == 2 ) {
				     num = random.nextInt(4) ;
				     for( int i = 0 ; i < size ; i++ ) {
				    	 temp[i] = temp[i] + step2[num] ;  	 
				     }
			   }
			   
		   }
			   
		   else if( temp[0] + 7 == temp[1]) {
			   if( size > 2 ) {
				     num = random.nextInt(2) ;
				     for( int i = 0 ; i < size ; i++ ) {
				    	 temp[i] = temp[i] + step[num] * 7 ;  	 
				     }
				   }
			   else if( size == 2 ) {
				   num = random.nextInt(4) ;
				   for( int i = 0 ; i < size ; i++ ) {
					   temp[i] = temp[i] + step2[num] * 7;  	 
				   }
			   }
			   
		   }
		   
		   
		   for( int i = 0 ; i < size ; i++ ) {
			   if( temp[i] >= 0 && temp[i] <= 48 ) {
				   if( grid[temp[i]] != 0 )
					   success = false ;
			   }
	           if ( temp[i] >= gridSize || temp[i] < 0 )               
		               success = false;                         
		       if( i > 0 && temp[i] % gridLength == 0)
		    	   success = false ;
		   }
		   
		   if( success ) {
			   for( int i = 0 ; i < size ; i++ ) {
				  grid[temp[i]] = 1 ;
			   }
		   }
		   else if( !success ){
			   for( int i = 0 ; i < size ; i++ ) {
				  grid[copy[i]] = 1 ;
			   }
			   return loc ;
		   }
		   
		   int row = 0 ;
		   int column = 0 ;
		   int x = 0 ;
		    while (x < size) {
			      row = (int) (temp[x] / gridLength);             // get row value
			      column = temp[x] % gridLength;                  // get numeric column value
			      temp2 = String.valueOf(alphabet.charAt(column));   // convert to alpha
			      
			      alphaCells.add(temp2.concat(Integer.toString(row)));
			      x++;

			       System.out.print("  coord "+x+" = " + alphaCells.get(x-1));
			      
			    }
			     System.out.println("\n");
			    
			    return alphaCells;
		   
	   }
	   
	   
	   
	   
	   
	   
	}

abstract class DotCom {
     protected ArrayList<String> locationCells;
     protected ArrayList<String> destroy = new ArrayList<String>();
     protected void setLocationCells(ArrayList<String> loc)
    {
        locationCells = loc;
    }

     protected ArrayList<String> getloc(){
    	return locationCells ;
    }
    

    protected String checkYourself(String userInput)
    {
        String result = "miss";
        
        //用indexOf來檢查是否打中
        int index = locationCells.indexOf(userInput); 
        if( index >= 0 && destroy.indexOf(Integer.toString(index)) >= 0 )
        	System.out.print( "repeat DotCombody, so ");
        if (index >= 0 && destroy.indexOf(Integer.toString(index)) < 0) {
        	destroy.add( Integer.toString(index) ) ;
            if ( destroy.size() == comSize ) {
                result = "kill";
                if( comSize == 2 )
                  System.out.print("HIT!");
                else if( comSize == 3 )
                  System.out.print("WHACK!");
                else if( comSize == 4 )
                  System.out.print("SMASH!");
            }
            else
            {
                result = "hit";
                if( comSize == 2 )
                    System.out.print("HIT!\n");
                else if( comSize == 3 )
                    System.out.print("WHACK!\n");
                else if( comSize == 4 )
                    System.out.print("SMASH!\n");
                
            }
        }
        return result;
    }

    //TODO:  all the following code was added and should have been included in the book
    protected String name;
    protected int comSize ;
    
    protected void setName(String string) {
        name = string;
    }
    
    protected void setSize( int size ) {
    	
    	comSize = size ;
    }
    
    protected int getSize() {
    	return comSize ;
    }
}

class SmallDotCom extends DotCom{
	private int comSize ;
    private String name;
    private ArrayList<String> locationCells;
    private ArrayList<String> destroy = new ArrayList<String>();
	public SmallDotCom( String str ) {
		setSize( 2 ) ;
		setName( str ) ;
	}
	
}
class MedDotCom extends DotCom{
	private int comSize ;
    private String name;
    private ArrayList<String> locationCells;
    private ArrayList<String> destroy = new ArrayList<String>();
	public MedDotCom( String str ) {
		setSize( 3 ) ;
		setName( str ) ;
	}
	
}
class BigDotCom extends DotCom{
	private int comSize ;
    private String name;
    private ArrayList<String> locationCells;
    private ArrayList<String> destroy = new ArrayList<String>();
	public BigDotCom( String str ) {
		setSize( 4 ) ;
		setName( str ) ;
	}
	
}

public class Main {
    private GameHelper helper = new GameHelper();
    private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
    private int numOfGuesses = 0;
    
    
    private void setUpGame() {
        SmallDotCom one = new SmallDotCom( "smallCom");
        MedDotCom two = new MedDotCom("medCom");
        BigDotCom three = new BigDotCom( "bigCom");
        dotComsList.add(one);
        dotComsList.add(two);
        dotComsList.add(three);
        System.out.println("Your goal is to sink three dot coms.");
        System.out.println( "smallCom, medCom, bigCom " );
        System.out.println("Try to sink them all in the fewest number of guesses");
        
        for ( int i = 0 ; i < dotComsList.size() ; i++ ) {
            ArrayList<String> newLocation = helper.placeDotCom( dotComsList.get(i).getSize());
            dotComsList.get(i).setLocationCells(newLocation);  
        }
        
    }
    
    private void startPlaying() {
        while (!dotComsList.isEmpty()) {
            String userGuess = helper.getUserInput("Enter a guess");
            checkUserGuess(userGuess);
        }
        finishGame();
    }
    
    private void checkUserGuess(String userGuess)
    {
        numOfGuesses++;
        String result = "miss";
        for ( DotCom dotComToTest : dotComsList )
        {
            result = dotComToTest.checkYourself(userGuess);
            
            if (result.equals("hit"))
            {
            	dotComToTest.setLocationCells(helper.move( dotComToTest.getloc(), dotComToTest.getSize()));
                break;
            }
            if (result.equals("kill"))
            {
            	helper.removeCom( dotComToTest.getloc(), dotComToTest.getSize());
                dotComsList.remove(dotComToTest);
                System.out.print( "and kill!!\n") ;
                break;
            }
        }
        
        if( result.equals( "miss") )
        	System.out.print("miss\n");
    }
    
    private void finishGame() {
        System.out.println("All Dot Coms are dead!  Your stock is now worthless");
        if (numOfGuesses <= 18) {
            System.out.println("It only took you " + numOfGuesses + " guesses");
            System.out.println("You got out before your options sank.");
        }
        else
        {
            System.out.println("Took you long enough. " + numOfGuesses + " guesses.");
            System.out.println("Fish are dancing with your options.");
        }
    }
    
	public static void main(String[] args) {
		Main game = new Main();
        game.setUpGame();
        game.startPlaying();

	}

}