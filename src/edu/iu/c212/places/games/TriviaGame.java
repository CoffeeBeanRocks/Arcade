package edu.iu.c212.places.games;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import edu.iu.c212.Arcade;
import edu.iu.c212.models.User;
import edu.iu.c212.places.games.Game;
import edu.iu.c212.utils.ConsoleUtils;
import edu.iu.c212.utils.http.HttpUtils;
import edu.iu.c212.utils.http.TriviaQuestion;

public class TriviaGame extends Game{

	Arcade arcade;
	public TriviaGame(Arcade arcade) {
		super("Trivia", 0);
		this.arcade = arcade;
	}

	@Override
	public void onEnter(User user)
	{
		//Scanner keyboard = new Scanner(System.in);
		
		int questionsAsked = 0;
		int right = 0;
		List<TriviaQuestion> questions= new ArrayList<TriviaQuestion>();
		
		try {
			questions = HttpUtils.getTriviaQuestions(5);
		} 
		catch (IOException e) {
			System.out.println(e);
		}
		
		System.out.println("Welcome to C212 Trivia. You get $2 for every correct answer - there are 5 total questions in this trivia round");
		
		while (questionsAsked != 5)
		{
			//System.out.println("=========");
			System.out.println("You're on question #" + (questionsAsked + 1) + ". Ready?");
			//System.out.println(questions.get(questionsAsked).getQuestion());

			//Shuffle order of answers
			List<String> listQuestions = questions.get(questionsAsked).getIncorrectAnswers();
			listQuestions.add(questions.get(questionsAsked).getCorrectAnswer());
			Collections.shuffle(listQuestions);

			String ans = ConsoleUtils.printMenuToConsole(questions.get(questionsAsked).getQuestion(), listQuestions, true);

			if (ans.equals(questions.get(questionsAsked).getCorrectAnswer())) {

				System.out.println("You got it right! You got $2");
				user.addValueToBalance(2);
				arcade.saveUsersToFile();
				right ++;
			}
			else {
				System.out.println("You got it wrong :( The correct answer is: " + questions.get(questionsAsked).getCorrectAnswer());
			}
			questionsAsked ++;
			try {
				TimeUnit.SECONDS.sleep(1);
			}
			catch (Exception ignored){}
		}
		if (right < 3) {
			System.out.println("Aww, good try. You got " + right + " right");
		}
		else {
			System.out.println("Nice! You got " + right + " right");
		}
	}
}
