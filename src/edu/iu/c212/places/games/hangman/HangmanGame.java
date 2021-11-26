package edu.iu.c212.places.games.hangman;

import edu.iu.c212.models.User;
import edu.iu.c212.places.games.Game;
import edu.iu.c212.utils.http.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class HangmanGame extends Game implements IHangmanGame
{
    //game variables
    private String word;
    private List<Character> guessedLetters;
    private List<Character> validLetters;
    private static int wrongAnswers;
    private static String ans = null;

    //GUI Variables
    private static JLabel lettersRemaining;
    private static JTextField textField;
    private static JLabel info;
    private static JLabel info2;

    public HangmanGame()
    {
        super("Hangman",5);

        ans = null;
        String letters = "abcdefghijklmnopqrstuvwxyz";
        validLetters = new ArrayList<>();
        for(int i=0; i<letters.length(); i++)
            validLetters.add(letters.charAt(i));
        List<Character> guessedLetters = new ArrayList<>();
    }

    @Override
    public void onEnter(User user)
    {
        //add GUI
        JFrame frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new mainPanel());
        frame.pack();
        frame.setVisible(true);

        //initialize variables
        wrongAnswers = 0; //used to determine how many incorrect guesses
        guessedLetters = new ArrayList<>();
        try
        {
            word = HttpUtils.getRandomHangmanWord();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        //game loop
        System.out.println("You've entered hangman! Here are the rules: ");
        System.out.println("1) A random word has been chosen, you need to guess the word letter by letter");
        System.out.println("2) If you guess 6 incorrect letters you lose!");

        while(!gameOver())  //TODO: Game loops does not exit when guesses have reached 6
        {
            info.setText("You've guessed "+wrongAnswers+" times incorrectly ("+guessedLetters.toString()+")");
            info2.setText("The current word is: "+getBlurredWord(guessedLetters,word)+". Please enter a lowercase letter in the following lexicon to guess: "+getValidLexicon().toString());

            //TODO: Fix user submission of data

            if(ans != null) //waiting for user input
            {
                if(!containsChar(word,ans.charAt(0)))
                    wrongAnswers++;
                guessedLetters.add(ans.charAt(0));
                ans = null;
            }
        }

        info.setText("Game Over!");
        info.setText("Game Over!");
        System.exit(0);

        if(wrongAnswers >= 6) //player lost
        {

        }
        else //player won
        {

        }
    }

    private boolean containsChar(String word, char character)
    {
        for(int i=0; i<word.length(); i++)
        {
            if(word.charAt(i) == character)
                return true;
        }
        return false;
    }

    public static void setAns(String val)
    {
        ans = val;
    }

    private static class mainPanel extends JPanel
    {
        public mainPanel()
        {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(1000,500));
            this.setFocusable(true);
            this.requestFocusInWindow();
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); //creates vertical layout
            add(new drawInfo());
            add(new drawInfo2());
            add(new textGuess());
        }

        public class drawInfo extends JPanel
        {
            public drawInfo()
            {
                info = new JLabel();
                add(info);
            }
        }

        public class drawInfo2 extends JPanel
        {
            public drawInfo2()
            {
                info2 = new JLabel();
                add(info2);
            }
        }

        public class textGuess extends JPanel
        {
            public textGuess()
            {
                textField = new JTextField();
                textField.setPreferredSize(new Dimension(100,50));
                add(textField);
                textField.addActionListener(new enterPressedListener());
            }

            private class enterPressedListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(textField.getText().length()==1) //TODO: only accept valid answers
                    {
                        ans = textField.getText();
                        textField.setText("");
                    }
                }
            }
        }
    }

    public int getWrongAnswers()
    {
        return wrongAnswers;
    }

    private boolean gameOver()
    {
        for(int i=0; i<word.length(); i++)
        {
            if(!guessedLetters.contains(word.charAt(i)) || getWrongAnswers() >= 6)
                return false;
        }
        return true;
    }

    public List<Character> getValidLexicon()
    {
        return validLetters;
    }

    public String getBlurredWord(List<Character> guesses, String word)
    {
        String blurredWord = "";
        for(int i=0; i<word.length(); i++)
        {
            if(guesses.contains(word.charAt(i)))
                blurredWord += word.charAt(i);
            else
                blurredWord += "*";
        }
        return  blurredWord;
    }
}
