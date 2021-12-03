package edu.iu.c212.places.games.blackjack;

import java.util.ArrayList;
import java.util.Collections;

//TODO DO EXTRA CREDIT
public abstract class BlackjackParticipant  {

	protected static ArrayList<Integer> cards;
	protected int[] handTotals;
	
	public void hit() {
		
		Collections.shuffle(cards);
		
		int num = cards.get(0);
		
		cards.remove(0);
		
		if (num >= 11 && num < 14) {
				
			handTotals[0] += 10;
			handTotals[1] += 10;
				
		}
			
		else if (num == 14 || num == 1){
				
			handTotals[0] += 1;
			handTotals[1] += 11;
				
		}
			
		else {
				
			handTotals[0] += num;
			handTotals[1] += num;
				
		}
		
	}
	
	public abstract int getBestTotal();
	
	
}
