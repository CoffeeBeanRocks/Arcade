package edu.iu.c212.places.games.blackjack;

import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;
import edu.iu.c212.places.games.Game;

public class BlackjackGame extends Game
{

	public static User myUser;
	private BlackjackPlayer myPlayer;
	private BlackjackDealer myDealer;
	public static boolean bust;
	public static JLabel totalsLabel;
	public static JLabel dealerLabel;
	public static JLabel winLose;
	public static JButton hit;
	public static JButton stay;
	public static boolean readyToExit;

	public BlackjackGame() {

		super("Blackjack", 20);
		this.winLose=new JLabel("");
		totalsLabel = new JLabel();
		dealerLabel = new JLabel();
		winLose = new JLabel();
		hit = new JButton("Hit");
		stay = new JButton("Stay");
		bust = false;
		readyToExit = false;
	}

	@Override
	public void onEnter(User user) {
		// TODO Auto-generated method stub

		myPlayer = new BlackjackPlayer();

		myDealer = new BlackjackDealer();

		this.myUser = user;

		//System.out.println("Dealer: " + myDealer.handTotals[1] + " myPlayer: "+myPlayer.handTotals[1]);

		JFrame frame = new JFrame("Blackjack");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowClosedListener());

		JPanel mainPanel = new JPanel();

		JPanel statusPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();

		dealerLabel = new JLabel("Dealer has " + myDealer.getPartialHand());

		if (myPlayer.handTotals[0] < 21 && myPlayer.handTotals[1] > 21) {

			totalsLabel.setText("Your hands(A = 1 | A = 11): " + myPlayer.handTotals[0] + " | bust");

		}

		else {

			totalsLabel.setText("Your hands(A = 1 | A = 11): " + myPlayer.handTotals[0] + " | " + myPlayer.handTotals[1]);

		}

		if (myPlayer.handTotals[0] == 21 || myPlayer.handTotals[1] == 21 && myDealer.getBestTotal() != 21) {

			hit.setEnabled(false);
			stay.setEnabled(false);
			winLose.setText("You win");
			System.out.println(myPlayer.handTotals[0] + " | " + myPlayer.handTotals[1]);
			//myUser.addValueToBalance(50); //updates balance in window close method
		}

		else if (myDealer.getBestTotal() == 21) {

			hit.setEnabled(false);
			stay.setEnabled(false);
			winLose.setText("You lose");
			dealerLabel.setText("Dealer has Blackjack");
		}

		HitButtonListener toHit = new HitButtonListener();
		StayButtonListener toStay = new StayButtonListener();

		hit.setPreferredSize(new Dimension(100, 100));
		stay.setPreferredSize(new Dimension(100, 100));

		hit.addActionListener(toHit);
		stay.addActionListener(toStay);

		mainPanel.setPreferredSize(new Dimension(750, 750));

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		statusPanel.add(dealerLabel);
		statusPanel.add(totalsLabel);

		//Instructions state possible 3 event listeners, but example GUI only has 2 and this implementation was achieved with only 2
		buttonsPanel.add(hit);
		buttonsPanel.add(stay);

		mainPanel.add(buttonsPanel);
		mainPanel.add(statusPanel);
		mainPanel.add(winLose);

		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setVisible(true);

		while(!readyToExit) //Sudo thread locking cuz Ayush made this difficult, and I didn't have enough time to implement proper thread locking -Ethan :)
			//Was not difficult just stupid lol -Ayush
		{
			frame.requestFocus();
			if(readyToExit)
			{
				System.out.print("");
				break;
			}
		}
	}

	private class HitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			myPlayer.hit();

			if (myPlayer.handTotals[0] > 21 && myPlayer.handTotals[1] > 21) {

				bust = true;
				totalsLabel.setText("Your hands(A = 1 | A = 11): bust | bust");

			}

			else if (myPlayer.handTotals[0] < 21 && myPlayer.handTotals[1] > 21) {

				totalsLabel.setText("Your hands(A = 1 | A = 11): " + myPlayer.handTotals[0] + " | bust");

			}

			else if (myPlayer.handTotals[0] == 21 || myPlayer.handTotals[1] == 21) {

				totalsLabel.setText("Your hands(A = 1 | A = 11): " + myPlayer.handTotals[0] + " | " + myPlayer.handTotals[1]);
				hit.setEnabled(false);
				stay.setEnabled(false);
				winLose.setText("You win");
				//myUser.addValueToBalance(50); //updates balance in window close method

			}

			else {

				totalsLabel.setText("Your hands(A = 1 | A = 11): " + myPlayer.handTotals[0] + " | " + myPlayer.handTotals[1]);

			}

			if (bust == true) {

				hit.setEnabled(false);
				stay.setEnabled(false);

				winLose.setText("You Lose");
			}
		}
	}

	private class StayButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			hit.setEnabled(false);
			stay.setEnabled(false);

			myDealer.play();

			if (myDealer.getBestTotal() == -1) {

				dealerLabel.setText("Dealer has busted");

			}

			else {

				dealerLabel.setText("Dealer has " + myDealer.getBestTotal());

			}

			if (!bust) {


				//18

				if ((myPlayer.handTotals[1] <= 21 && myPlayer.handTotals[1] > myDealer.getBestTotal()) || myPlayer.handTotals[0] > myDealer.getBestTotal()) {

					winLose.setText("You win");

				}

				else {

					winLose.setText("You lose");

				}

			}

		}

	}

	private class WindowClosedListener implements java.awt.event.WindowListener
	{

		WindowClosedListener(){}

		@Override
		public void windowOpened(WindowEvent e) {

		}

		@Override
		public void windowClosing(WindowEvent e) {

		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			if(winLose.getText().equals(""))
			{
				System.out.println("You have forfeited the game by closing the window early!");
			}
			else
			{
				System.out.println(winLose.getText());
				if(winLose.getText().equals("You win"))
					myUser.addValueToBalance(50);
			}
			readyToExit = true;
		}

		@Override
		public void windowIconified(WindowEvent e) {

		}

		@Override
		public void windowDeiconified(WindowEvent e) {

		}

		@Override
		public void windowActivated(WindowEvent e) {

		}

		@Override
		public void windowDeactivated(WindowEvent e) {

		}
	}
}

