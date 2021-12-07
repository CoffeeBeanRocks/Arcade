package edu.iu.c212.places.games;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;
import edu.iu.c212.places.games.Game;
import edu.iu.c212.utils.ConsoleUtils;

public class GuessTheNumberGame extends Game {

	public GuessTheNumberGame() {
		super("Guess the Number", 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEnter(User user) {
		// TODO Auto-generated method stub
		
		Scanner keyboard = new Scanner(System.in);
		
		int guessesRemain = 5;
		int guess = 0;
		int ans = (int)(Math.random() * 101);
		
		System.out.println(ans);
		
		System.out.println("Welcome to Guess the Number. You'll be guessing a number between 0 and 100");
		System.out.println("You'll get $10 if you correctly guess the number within 5 tries. Otherwise you get nothing");
		
		while (guess != ans && guessesRemain != 0) {
			
			System.out.println("Whats your guess?");
			guess = ConsoleUtils.readIntegerLineFromConsoleOrElseComplainAndRetry((num) -> (num>=0 && num<=100), "Number is not between 0 and 100");
			
			if (guess == ans) {
				
				System.out.println("Congratulations, you correctly guessed the number!");
				System.out.println("You guessed it within 5 tries, so you get $10");
			}
			else
			{
				if (guess > ans) {
					System.out.println("Your number was too high");
				}
				
				else {
					System.out.println("Your number was too low");
				}
			}
			guessesRemain --;
		}

		if (guess != ans) {

			System.out.println("You got it wrong :( The correct answer is: " + ans);
		}
	}

}
