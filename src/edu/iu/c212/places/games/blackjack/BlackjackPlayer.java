package edu.iu.c212.places.games.blackjack;

import java.util.ArrayList;

public class BlackjackPlayer extends BlackjackParticipant {

	public BlackjackPlayer() {
		
		cards = new ArrayList<Integer>(52);
		
		for (int i = 0; i < 14; i ++) {
			
			for (int z = 0; z < 4; z ++) {
				
				cards.add(i + 1);
			}
		}
		
		super.handTotals = new int[2];
		super.hit();
		super.hit();
	}
	
	public String getCurrentTotalsString() {
		
		if (handTotals[0] != handTotals[1] && (handTotals[0] <= 21 || handTotals[1] <= 21)) {
			
			return handTotals[0] + " " + handTotals[1];
		}
			
		return String.valueOf(handTotals[0]);
	}
	
	@Override
	public int getBestTotal() {
		// TODO Auto-generated method stub

		//Implementation not stated in directions
		if (handTotals[0] <= 21 && handTotals[1] <= 21) {

			return handTotals[1];
		}

		else if (handTotals[0] <= 21 && handTotals[1] > 21){

			return handTotals[0];
		}

		else {

			return - 1;
		}
	}
}
