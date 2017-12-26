package snakes;
// Zoltan Gercsak - Snakes and ladders game
import java.awt.*;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

// Set up the board and the display for the game
public class Board {

	// initialize the Swing components
	JFrame frame = new JFrame("Snakes & Ladders");
	JLabel diceLabel = new JLabel();
	JLabel boardPiece[] = new JLabel[101];
	JLabel miscellaneous[] = new JLabel[6];
	JLabel logo = new JLabel();
	JTextField tf1, tf2;
	JButton rollButton;
	JButton learnMore, reset;
	JTextField playerTurn;
	JLabel yourNumber = new JLabel(" ");
	Listener listener = new Listener(this);
	ImageIcon diceIcon = new ImageIcon();

	// function to create tiles for board, odd rows
	public void createOddRows(int front, int end, int pos) {
		int j = 0;
		for (int i = front; i < end; i++) {
			boardPiece[i] = new JLabel();
			boardPiece[i].setBounds((200 + j * 70), pos, 70, 70);
			URL url = Board.class.getResource("/Pictures/" + i + ".png");
			boardPiece[i].setIcon(new ImageIcon(url));
			frame.add(boardPiece[i]);
			j++;
		}
	}

	// function to create tiles for board, even rows
	public void createEvenRows(int front, int end, int pos) {
		int j = 0;
		for (int i = end; i >= front; i--) {
			boardPiece[i] = new JLabel();
			boardPiece[i].setBounds((200 + j * 70), pos, 70, 70);
			URL url = Board.class.getResource("/Pictures/" + i + ".png");
			boardPiece[i].setIcon(new ImageIcon(url));
			frame.add(boardPiece[i]);
			j++;
		}

	}


	// swing worker to run the dice animation in the background
	SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
		@Override
		protected Boolean doInBackground() throws Exception {

			diceLabel.setIcon(diceIcon);
			return true;
		}

		protected void done() {

			boolean status;
			try {
				// Retrieve the return value of doInBackground.
				status = get();
			} catch (InterruptedException e) {
				// This is thrown if the thread's interrupted.
			} catch (ExecutionException e) {
				// This is thrown if we throw an exception
				// from doInBackground.
			}
		}
	};

	Board() {

		// add player pictures
		miscellaneous[1] = new JLabel();
		miscellaneous[1].setBounds(140, 650, 20, 52);
		URL url = Board.class.getResource("/Pictures/redpawn.png");
		miscellaneous[1].setIcon(new ImageIcon(url));
		frame.add(miscellaneous[1]);

		miscellaneous[2] = new JLabel();
		miscellaneous[2].setBounds(70, 650, 20, 52);
		url = Board.class.getResource("/Pictures/bluepawn.png");
		miscellaneous[2].setIcon(new ImageIcon(url));
		frame.add(miscellaneous[2]);

		// add start place for players
		miscellaneous[3] = new JLabel();
		miscellaneous[3].setBounds(50, 550, 150, 150);
		url = Board.class.getResource("/Pictures/starthere.png");
		miscellaneous[3].setIcon(new ImageIcon(url));
		frame.add(miscellaneous[3]);

		miscellaneous[4] = new JLabel();
		miscellaneous[4].setBounds(5, 290, 20, 52);
		url = Board.class.getResource("/Pictures/redpawn.png");
		miscellaneous[4].setIcon(new ImageIcon(url));
		frame.add(miscellaneous[4]);

		miscellaneous[5] = new JLabel();
		miscellaneous[5].setBounds(5, 340, 20, 52);
		url = Board.class.getResource("/Pictures/bluepawn.png");
		miscellaneous[5].setIcon(new ImageIcon(url));
		frame.add(miscellaneous[5]);

		// make the calls for the even rows of the board
		createEvenRows(91, 100, 0);

		createEvenRows(71, 80, 140);

		createEvenRows(51, 60, 280);

		createEvenRows(31, 40, 420);

		createEvenRows(11, 20, 560);

		// make the calls to create the odd rows of the board

		createOddRows(81, 91, 70);

		createOddRows(61, 71, 210);

		createOddRows(41, 51, 350);

		createOddRows(21, 31, 490);

		createOddRows(1, 11, 630);

		// adding game logo to the screen
		logo = new JLabel();
		logo.setBounds(0, 0, 200, 200);
		url = Board.class.getResource("/Pictures/gamelogo.png");
		logo.setIcon(new ImageIcon(url));
		frame.add(logo);

		// adding game logo to the screen
		logo = new JLabel();
		logo.setBounds(900, 535, 200, 165);
		url = Board.class.getResource("/Pictures/gamelogo.png");
		logo.setIcon(new ImageIcon(url));
		frame.add(logo);

		// text field that displays who's turn it is currently
		playerTurn = new JTextField("Roll to start!");
		playerTurn.setBounds(25, 250, 150, 40);
		playerTurn.setBackground(Color.YELLOW);
		playerTurn.setForeground(Color.BLACK);
		playerTurn.setEditable(false);
		frame.add(playerTurn);

		// text field that shows which pawn is player 1
		tf1 = new JTextField();
		tf1.setBounds(25, 310, 150, 35);
		tf1.setText("Player 1 ");
		tf1.setEditable(false);
		frame.add(tf1);
		tf1.setBackground(Color.YELLOW);

		// text field that shows which pawn is player 2
		tf2 = new JTextField();
		tf2.setBounds(25, 350, 150, 35);
		tf2.setText("Computer ");
		tf2.setEditable(false);
		frame.add(tf2);
		tf2.setBackground(Color.YELLOW);

		diceLabel = new JLabel();
		diceLabel.setBounds(930, 260, 100, 100);
		diceLabel.setText("");
		url = Board.class.getResource("/Pictures/dice.gif");
		diceIcon = new ImageIcon(url);

		// execute the swing worker initialized above
		worker.execute();

		frame.add(diceLabel);

		// set up the roll dice button
		rollButton = new JButton();
		rollButton.setBounds(930, 350, 120, 40);
		rollButton.setText("");
		url = Board.class.getResource("/Pictures/rolldice.png");
		rollButton.setIcon(new ImageIcon(url));
		rollButton.addActionListener(listener);
		frame.add(rollButton);

		yourNumber = new JLabel("0");
		yourNumber.setForeground(Color.BLACK);
		yourNumber.setBounds(1020, 270, 100, 100);
		frame.add(yourNumber);

		Font font = new Font("Times New Roman", Font.BOLD, 20);
		Font font1 = new Font("Times New Roman", Font.BOLD, 26);
		Font font2 = new Font("Times New Roman", Font.BOLD, 18);
		Font font3 = new Font("Times New Roman", Font.BOLD, 30);

		// set the fonts
		tf1.setFont(font);
		playerTurn.setFont(font2);
		tf2.setFont(font);
		yourNumber.setFont(font3);

		diceLabel.setFont(font1);
		frame.setLayout(null);

		// set up the learn more button
		learnMore = new JButton();
		url = Board.class.getResource("/Pictures/learnmore.png");
		learnMore.setIcon(new ImageIcon(url));
		learnMore.addActionListener(listener);
		learnMore.setBounds(930, 20, 120, 40);
		frame.add(learnMore);

		// set up the reset board button
		reset = new JButton("");
		url = Board.class.getResource("/Pictures/resetboard.png");
		reset.setIcon(new ImageIcon(url));
		reset.addActionListener(listener);
		reset.setBounds(930, 70, 120, 40);
		reset.setBackground(Color.BLUE);
		frame.add(reset);

		// set the layout on the JFrame
		frame.setLayout(null);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);

		frame.setSize(1100, 735);

		frame.getContentPane().setBackground(new Color(167, 173, 186));

	}

}
