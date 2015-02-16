/***************
	The first Program Assignment of CSE 11, winter 2015
	Written by Mingxuan Wang
***************/
/**************
	Name : Mingxuan Wang
	Email: miw072@eng.ucsd.edu
	ID: A53077257
**************/

import java.util.Random;
import java.util.Scanner;

public class Triple{
	public static void main(String[] args){
		final int MAX_DICE = 8;
		int seedVal = 0;
		boolean isLucky = false;
		boolean[] isPair = new boolean[2];
		boolean[] isTriple = new boolean[2];
		int[] rollResult = new int[3];
		int[] diceCount = new int[MAX_DICE];
		Random rand = new Random();
		Scanner scnr = new Scanner(System.in);
		
		for (int i = 0; i < diceCount.length; i++){
			diceCount[i] = 0;
		}
		
		//Enter seed and set seed
		System.out.print("Enter Seed: ");
		seedVal = scnr.nextInt();
		rand.setSeed(seedVal);
		
		//Roll for the first time
		for (int i = 0; i < 3; i++){
			rollResult[i] = rand.nextInt(MAX_DICE) + 1;
			for (int j = 0; j < diceCount.length; j++){
				if (rollResult[i] == j + 1){
					diceCount[j]++;
				}
			}
		}

		//Print the first result
		System.out.println("rolls: " + rollResult[0] + " " + rollResult[1] + " " + rollResult[2]);
		
		for (int i = 0; i < diceCount.length; i++){
			if (diceCount[i]  == 2){
				isPair[0] = true;
				break;
			}else if (diceCount[i]  == 3){
				isTriple[0] = true;
				break;
			}else{
				isPair[0] = false;
				isTriple[0] = false;
			}
		}
		
		if (isPair[0]){
			System.out.println("You rolled a pair");
		}else if (isTriple[0]){
			System.out.println("You rolled 3 of a kind");
		}else{
			System.out.println("You rolled nothing");
		}
		
		for (int i = 0; i < diceCount.length; i++){
			diceCount[i] = 0;
		}
		
		//Roll for the second time
		for (int i = 0; i < 3; i++){
			rollResult[i] = rand.nextInt(MAX_DICE) + 1;
			for (int j = 0; j < diceCount.length; j++){
				if (rollResult[i] == j + 1){
					diceCount[j]++;
				}
			}
		}
		
		//Print the second result
		System.out.println("rolls: " + rollResult[0] + " " + rollResult[1] + " " + rollResult[2]);
		
		for (int i = 0; i < diceCount.length; i++){
			if (diceCount[i] == 2){
				isPair[1] = true;
				break;
			}else if (diceCount[i] == 3){
				isTriple[1] = true;
				break;
			}else{
				isPair[1] = false;
				isTriple[1] = false;
			}
		}
		
		if (isPair[1]){
			System.out.println("You rolled a pair");
		}else if (isTriple[1]){
			System.out.println("You rolled 3 of a kind");
		}else{
			System.out.println("You rolled nothing");
		}
		
		//Determine isLucky
		if ((isPair[0] && isPair[1]) || (isTriple[0] && isTriple[1]) || (isPair[0] && isTriple[1]) || (isTriple[0] && isPair[1])){
			isLucky = true;
		}else{
			isLucky = false;
		}
		if (isLucky){
			System.out.println("You are lucky");
		}else{
			System.out.println("You are NOT lucky");
		}		
	}
}


