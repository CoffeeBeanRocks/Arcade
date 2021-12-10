package edu.iu.c212.places.games.blackjack;

import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	public static JLabel totalsLabel = new JLabel();
	public static JLabel dealerLabel = new JLabel();
	public static JLabel winLose = new JLabel();
	public static JButton hit = new JButton("Hit");
	public static JButton stay = new JButton("Stay");

	public BlackjackGame() {

		super("Blackjack", 20);

	}

	@Override
	public void onEnter(User user) {
		// TODO Auto-generated method stub

		myPlayer = new BlackjackPlayer();

		myDealer = new BlackjackDealer();

		this.myUser = user;

		myUser.subtractValueFromBalance(20);

		JFrame frame = new JFrame("Blackjack");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainPanel = new JPanel();

		JPanel statusPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();

		dealerLabel = new JLabel("Dealer has " + myDealer.getPartialHand());

		if (myPlayer.handTotals[0] < 21 && myPlayer.handTotals[1] > 21) {

			totalsLabel.setText("Your hands: " + myPlayer.handTotals[0] + "bust");

		}

		else {

			totalsLabel.setText("Your hands: " + myPlayer.handTotals[0] + " | " + myPlayer.handTotals[1]);

		}

		totalsLabel.setText("Your hands: " + myPlayer.handTotals[0] + " | " + myPlayer.handTotals[1]);

		if (myPlayer.handTotals[0] == 21 || myPlayer.handTotals[1] == 21) {

			hit.setEnabled(false);
			stay.setEnabled(false);
			winLose.setText("You win");
			myUser.addValueToBalance(50);

		}

		else if (myDealer.handTotals[0] == 21 || myDealer.handTotals[1] == 21) {

			hit.setEnabled(false);
			stay.setEnabled(false);
			winLose.setText("You lose");

		}

		else if (myDealer.getBestTotal() == 21 && (myPlayer.handTotals[0] == 21 || myPlayer.handTotals[1] == 21)) {

			hit.setEnabled(false);
			stay.setEnabled(false);
			winLose.setText("Tie");
			myUser.addValueToBalance(20);

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

		buttonsPanel.add(hit);
		buttonsPanel.add(stay);

		mainPanel.add(buttonsPanel);
		mainPanel.add(statusPanel);
		mainPanel.add(winLose);

		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setVisible(true);

	}

	private class HitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			myPlayer.hit();

			if (myPlayer.handTotals[0] > 21 && myPlayer.handTotals[1] > 21) {

				bust = true;
				totalsLabel.setText("Your hands: bust | bust");

			}

			else if (myPlayer.handTotals[0] < 21 && myPlayer.handTotals[1] > 21) {

				totalsLabel.setText("Your hands: " + myPlayer.handTotals[0] + " | bust");

			}

			else {

				totalsLabel.setText("Your hands: " + myPlayer.handTotals[0] + " | " + myPlayer.handTotals[1]);

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

			System.out.println(myDealer.handTotals[0]);
			System.out.println(myDealer.handTotals[1]);

			if (bust == false) {

				if (myDealer.getBestTotal() > myPlayer.handTotals[0] && myDealer.getBestTotal() > myPlayer.handTotals[1] && myPlayer.handTotals[1] < 21) {

					winLose.setText("You lose");

				}

				else if (myDealer.getBestTotal() == myPlayer.handTotals[0] || myDealer.getBestTotal() == myPlayer.handTotals[1]) {

					winLose.setText("Tie");
					myUser.addValueToBalance(20);

				}

				else {

					winLose.setText("You win");
					myUser.addValueToBalance(50);

				}

			}

		}

	}

	public static void main(String[] args) {

		BlackjackGame view = new BlackjackGame();

		Item bear = Item.BEAR;

		ArrayList<Item> inventory = new ArrayList<Item>();

		inventory.add(bear);

		myUser = new User("Ayush", 50, inventory);

		//myUser.subtractValueFromBalance(20);
		//System.out.println(myUser.getBalance());
		view.onEnter(myUser);

	}

}

