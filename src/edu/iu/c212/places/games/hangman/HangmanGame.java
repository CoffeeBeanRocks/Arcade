package edu.iu.c212.places.games.hangman;

import edu.iu.c212.models.User;
import edu.iu.c212.places.games.Game;
import edu.iu.c212.utils.http.HttpUtils;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;


public class HangmanGame extends Game implements IHangmanGame
{
    //game variables
    private String word;
    private List<Character> guessedLetters;
    private List<Character> validLetters;
    private static int wrongAnswers;
    private static String ans = null;

    //GUI Variables
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
    }

    @Override
    public void onEnter(User user)
    {
        //Entry Fee
        user.subtractValueFromBalance(5);

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

        while(!gameOver())
        {
            info.setText("You've guessed "+wrongAnswers+" times incorrectly ("+guessedLetters.toString()+")");
            info2.setText("The current word is: "+getBlurredWord(guessedLetters,word)+". Please enter a lowercase letter in the following lexicon to guess: "+getValidLexicon().toString());

            if(ans != null) //waiting for user input
            {
                if(ans.length() == 1 && validLetters.contains(ans.charAt(0))) //Input validation
                {
                    char c = ans.charAt(0);
                    if(!containsChar(word,c))
                        wrongAnswers++;
                    guessedLetters.add(c);
                    validLetters.remove(validLetters.indexOf(c));
                }
                else
                    System.out.println("Bad input");
                ans = null; //looking for new user input
            }
        }

        if(wrongAnswers >= 6) //player lost
        {
            info.setText("You've guessed "+wrongAnswers+" times incorrectly, you lose!");
            info2.setText("The word was: " + word);
            //TODO: Close GUI
        }
        else //player won
        {
            info.setText("You've guessed the correct word, you win!");
            info2.setText("Congratulations on guessing the word "+word+". $15 has been added to your account!");
            user.addValueToBalance(15);
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        }
        catch (Exception ignored){}
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public boolean containsChar(String word, char character)
    {
        for(int i=0; i<word.length(); i++)
        {
            if(word.charAt(i) == character)
                return true;
        }
        return false;
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
            //All the labels and text field being added to the panel
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
                    if(textField.getText().length()==1)
                    {
                        ans = textField.getText();
                        textField.setText("");
                    }
                }
            }
        }
    }

    private boolean gameOver()
    {
        int count = 0;
        String blurredWord = getBlurredWord(guessedLetters,word);
        for(int i=0; i<blurredWord.length(); i++)
        {
            if(blurredWord.charAt(i) == '*')
                count++;
        }
        if(count == 0 || wrongAnswers >= 6)
            return true;
        return false;
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