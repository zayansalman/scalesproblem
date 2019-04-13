import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.*;

class Lab9 {
	
	public static void main(String args[])
	{

		ArrayList<Double> weightList = new ArrayList<Double>(); //just some double arraylist
		weightList.add(1.0); 
		weightList.add(2.0); 
		weightList.add(3.0); 
		weightList.add(4.0); 
		weightList.add(10.0);
		
		ScalesSoln aRun = new ScalesSoln(weightList.size()); // a scalessoln that will take a size input and generate solutions 
		
		aRun = RMHC(weightList, weightList.size(), 500); // solution, solution size, iterations

	} //main class bracket end 
	
	
	//takes weights in arraylist, size of weightlist, and the num of iterations to do
	public static ScalesSoln RMHC(ArrayList<Double> weightList,int solnSize,int iterations)
	{
		
		ScalesSoln soln = new ScalesSoln(solnSize);  //gets a string solution 
		
		Double currentFitness = soln.ScalesFitness(weightList); //gets a fitness
		
		if (weightList.size() == 0) //if solution size is 0 
		{
			throw new NullPointerException("weights list is empty");
		}
		else if (iterations < 1) {
			throw new NullPointerException("invalid number of iterations");  //if runs less than 1 
		}
		
		else
		//initial printing
		System.out.println("Initial Soln:"); 
		soln.print(); //prints first random solution
		System.out.println(); 
		System.out.println("Initial Fitness:" + currentFitness); //prints the fitness of the first solution 
		
		for (int i = 0; i < iterations; i++) {  //runs the number of iterations
			ScalesSoln newSoln = new ScalesSoln(soln.GetSol());  //gets a solution 
			
			newSoln.smallChange(); //small change to the string 
			Double newFitness = newSoln.ScalesFitness(weightList); //the fitness of the string
			
			//comparing fitness 
			if (currentFitness>newFitness) {
				soln = newSoln; //if currentfitness is greater than new fitness (ideal fitness is 0) then the soln becomes the new soln that we made
				currentFitness = newFitness; //and the current fitness becomes the new fitness 
			}
		}
		
		//printing 
		System.out.println();
		System.out.println("Best Soln");
		soln.println();
		System.out.println("Best Fitness" + currentFitness + " in" + iterations + "runs"); 
		return(soln);
	}

	
	public static ScalesSoln arraylistRMHC (ArrayList <Double> weights, int solnSize, int iterations) {
		
		
		// generate a solution / list of weights
		ArrayList<Integer> intSoln = new ArrayList <Integer>();
		 
		for (int i = 0; i < solnSize; i++ ) {
			int val = CS2004.UI(0,1); 
			intSoln.add(val); 
		} //solution generated into an integer arraylist
		
		ScalesSoln soln = new ScalesSoln(intSoln); 
		ScalesSoln oldSoln = new ScalesSoln(soln.getIntSol());	
		
		return null; 
 	}
	static class ScalesSoln {
		
	    private static String scalesSoln; //global variable the string solution 
	    private static ArrayList<Double> arraylistWeights; 
	    private static ArrayList<Integer> intWeights; 
	    

	    /**Creates a new scales solution based on a string parameter
	    The string parameter is checked to see if it contains all zeros and ones
	    Otherwise the random binary string generator is used (n = length of parameter) **/
	    public ScalesSoln(String inputString) {
	        boolean tF = true;
	        int stringLength = inputString.length();
	        for (int i = 0; i < stringLength; ++i) {  //for the length of the string generate a solution by putting random 0 and 1 
	            char atPosition = inputString.charAt(i);
	            if (atPosition != '0' && atPosition != '1') tF = false; //checks if weight is not 0 or 1 
	            
	        }
	        if (tF) { //if tF is true it continues
	            scalesSoln = inputString; //the solution is the given string
	        } else {
	            scalesSoln = RandomBinaryString(stringLength); //creates a new solution string of same length
	        }
	    }
	/** generates string of 0 and 1 of given length **/
	    private static String RandomBinaryString(int numLength) 
	    {
	    	String weight = new String();  
			for(int i = 0; i < numLength; i++){
				int randomNum = CS2004.UI(0,1);
				if(randomNum == 0)
				{
					weight += '0';
				}
				else if(randomNum == 1)
				{
					weight += '1';
				}
			}
			return(weight);
		}

	    public ScalesSoln(int numLength) {
	        scalesSoln = RandomBinaryString(numLength); //the string solution is now the random string 
	    }

	    /**This is the fitness function for the Scales problem
	    This function returns -1 if the length of weight string is less than
	    the size of the current solution
	    THIS IS A FITNESS METHOD FOR A STRING SOLUTION */
	    public static double ScalesFitness(ArrayList<Double> weights)
		{
			int weightLength = scalesSoln.length(); //the number of 0 and 1 in the string
			double leftSide = 0.0, rightSide = 0.0; 
			if (weightLength > weights.size()) return(-1); 
			//^checks if there is length difference between input weight list and solution

			for(int i = 0; i < weightLength; i++){
				if(scalesSoln.charAt(i) == '0'){
					leftSide += weights.get(i);
				}
				else{        	
					rightSide += weights.get(i);
				}
			}       
			return(Math.abs(leftSide-rightSide));
		}
	    
	    
	    /** Display the string without a new line **/
	    public void print() {
	        System.out.print(scalesSoln);
	    }

	    /** FITNESS METHOD FOR ARRAYLIST **/
	    public static double arraylistScalesFitness(ArrayList <Double> thisWeights ) {
	    	int weightLength = arraylistWeights.size(); 
	    	double leftSide = 0.0, rightSide = 0.0; 
	    	
	    	if (weightLength > thisWeights.size()) return (-1); //check
	    	
	    	for (int i = 0; i < weightLength; i++) {
	    		if(arraylistWeights.get(i) == 0) {
	    			leftSide += thisWeights.get(i); 
	    		}
	    		else {
	    			rightSide += thisWeights.get(i); 
	    		}
	    	}
	    	return(Math.abs(leftSide - rightSide)); 
	    }
	    
	    public void arraylistPrint() {
	    	for (int i = 0; i < arraylistWeights.size(); i++) {
	    		System.out.print(arraylistWeights.get(i)); //println
	    	}
	    }
	    /** Display the string with a new line **/
	    public void println() {
	        print();
	        System.out.println();
	    }
	    
	    /** makes small change to string solution **/
	    public void smallChange()  //to string
		{	
			Random rand = new Random();
			int solnLength = scalesSoln.length();
			int randomPosition = rand.nextInt(solnLength-1)+ 0; 
			String changedSoln; 
			
			changedSoln = scalesSoln.substring(0, randomPosition);
			if (scalesSoln.charAt(randomPosition)== '0'){
				changedSoln = changedSoln + '1';
			}
			else {
				changedSoln = changedSoln + '0';
			}
			changedSoln = changedSoln + scalesSoln.substring(randomPosition + 1, solnLength);
			scalesSoln = changedSoln;
		}
		
	    /** gets solution **/
	    public String GetSol()
	    {
	    	return(scalesSoln);
	    }
	    
	   /** gets arraylist solution **/
	    public ArrayList<Double> getSol(){
	    	return(arraylistWeights); 
	    }
	    
	    /** gets arraylist solution **/
	    public ArrayList<Integer> getIntSol(){
	    	return(intWeights); 
	    }
	 
	    /** converts the string solution aka weights to an arraylist **/
	     
	    public ArrayList<Float> stringToArrayList()  //CHANGE TO DOUBLE OR FLOAT
	    {
	    	ArrayList<Float> arraylistSoln=new ArrayList<Float>();  //CHANGE TO DOUBLE OR FLOAT 
	    	for (int i = 0; i <scalesSoln.length(); i++) 
	    	{
	    		char numAt = scalesSoln.charAt(i); //each character in the string  
	    		int numInt = Character.getNumericValue(numAt); //convert character to integer
	    		//double numDouble = numInt;  										//CHANGE DOUBLE TO FLOAT
	    		float numFloat = numInt; 
	    		//arraylistSoln.add(numDouble); //add the number to the arraylist  	 FOR DOUBLE or FLOAT
	    		arraylistSoln.add(numFloat); 
	    		//h[i] = num;
	    	}
	    	return arraylistSoln;
	    } //end of method
	    
	   /** converts the string solution (weights) to an array **/
	    public double[] stringToArray() { //to Float
	    	double[] weightArray = null; //to Float
	    	for (int i = 0; i <scalesSoln.length(); i++)
	    	{
	    		char numAt = scalesSoln.charAt(i); //get each char
	    		int numInt = Character.getNumericValue(numAt); 
	    		double numDouble = numInt;  //to float
	    		weightArray[i] = numDouble; // = numFloat
	    	}
	    	return weightArray; //ok cool
	    } 
	    
	    /** small change to arraylist **/
	   public void smallarraylistChange() {
		   int arraylistSize = arraylistWeights.size(); 
		   int randomPosition = CS2004.UI(0, arraylistSize - 1); 
		   double randomWeight = CS2004.UI(0, 1); 
		   arraylistWeights.set(randomPosition, randomWeight); 
	   }
	   
	   
	   //constructor, change this when doing rmhc
	    public ScalesSoln(ArrayList <Integer> weights) {
	    	this.intWeights = new ArrayList <Integer> (weights); 
	    }
	   
	    

	} // end of scales solution class
	
	static class CS2004 {
		//Shared random object
		static private Random rand;

		//Create a uniformly distributed random integer between aa and bb inclusive
		static public int UI(int aa,int bb)
		{
		    int a = Math.min(aa,bb);
		    int b = Math.max(aa,bb);
		    if (rand == null) 
		    {
		        rand = new Random();
		        rand.setSeed(System.nanoTime());
		    }
		    int d = b - a + 1;
		    int x = rand.nextInt(d) + a;
		    return(x);
		}

		//Create a uniformly distributed random double between a and b inclusive
		static public double UR(double a, double b) {
			if (rand == null) {
				rand = new Random();
				rand.setSeed(System.nanoTime());
			}
			return ((b - a) * rand.nextDouble() + a);
		}

		//This method reads in a text file and parses all of the numbers in it
		//This code is not very good and can be improved!
		//But it should work!!!
		//It takes in as input a string filename and returns an array list of Doubles
		static public ArrayList<Double> ReadNumberFile(String filename) {
			ArrayList<Double> res = new ArrayList<Double>();
			Reader r;
			try {
				r = new BufferedReader(new FileReader(filename));
				StreamTokenizer stok = new StreamTokenizer(r);
				stok.parseNumbers();
				stok.nextToken();
				while (stok.ttype != StreamTokenizer.TT_EOF) {
					if (stok.ttype == StreamTokenizer.TT_NUMBER) {
						res.add(stok.nval);
					}
					stok.nextToken();
				}
			} catch (Exception E) {
				System.out.println("+++ReadFile: " + E.getMessage());
			}
			return (res);
		}
	}
} //java class bracket end

