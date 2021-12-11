package edu.iu.c212.places.games.blackjack;

import java.util.Collections;

public class BlackjackDealer extends BlackjackParticipant {

	private int shownCard;
	private int dealerBest;
	
	public BlackjackDealer() {
		
		handTotals = new int[2];
		
		hit();
		hit();
		
	}

	@Override public void hit() {
		
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
		
		if (shownCard == 0) {
			
			shownCard = num;
			
		}
		
	}
	
	public String getPartialHand() {
		
		if (shownCard <= 10 && shownCard > 1) {
			
			return shownCard + " + ???";
			
		}
		
		else if (shownCard == 11) {
			
			return "J + ???";
			
		}
		
		else if (shownCard == 12) {
			
			return "Q + ???";
			
		}
		
		else if (shownCard == 13) {
			
			return "K + ???";
			
		}
		
		return "A + ???";
		
	}
	
	//TODO Create 
	public void play() {
		
		while (handTotals[0] < 17 ) {
			
			hit();
			
		}
		
		if ((handTotals[1] >= 17 && handTotals[1] <= 21) || (handTotals[0] >= 17 && handTotals[0] <= 21)) {
			
			if (handTotals[1] > handTotals[0] && handTotals[1] <= 21) {
				
				dealerBest = handTotals[1];
				
			}
			
			else {
				
				dealerBest = handTotals[0];
				
			}
			
		}
		
		else {
			
			dealerBest = -1;
			
		}
		
	}
	
	@Override
	public int getBestTotal() {
		// TODO Auto-generated method stub

		//Implementation not stated, assumed getter for instance variable
		return dealerBest;
	}
	
	
}
