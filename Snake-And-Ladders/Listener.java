package snakes;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.concurrent.ThreadLocalRandom;

public class Listener implements ActionListener {
	Board snakeBoard;
	int randomNum;
	double y, z;
	static int player1Pos, player2Pos;
	static int w;
	int x1, y1;
	Rectangle r = new Rectangle();

	// play sound effects on a new thread
	public synchronized void playSound(String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(getClass().getResource(url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	// random number generator for dice rolls
	void genRandomNum() {
		randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);

		snakeBoard.yourNumber.setText(String.valueOf(randomNum));
	}
	
	// move the computer piece on another thread so animation doesn't stop
	public synchronized void moveComputerSynchronized() {
		new Thread(new Runnable() {
			public void run() {
				try {
					
					moveComputerPlayer();

				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	// move computer's piece 
	public void moveComputerPlayer() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		genRandomNum();
		snakeBoard.playerTurn.setText("Player 1's turn!");

		w++;

		if ((player2Pos + randomNum) <= 100) {

			player2Pos = player2Pos + randomNum;
			r = snakeBoard.boardPiece[player2Pos].getBounds();
			y = r.getX();
			z = r.getY();
			x1 = (int) Math.round(y);
			y1 = (int) Math.round(z);
			snakeBoard.miscellaneous[2].setBounds((x1 + 30), (y1 + 20), 20, 52);

			// check for snakes on the tiles
			int a[] = new int[] { 8, 18, 26, 39, 51, 54, 56, 60, 75, 83, 85, 90, 92, 97, 99 };
			int b[] = new int[] { 4, 1, 10, 5, 6, 36, 1, 23, 28, 45, 59, 48, 25, 87, 63 };

			for (int i = 0; i < 15; i++) {
				if (player2Pos == a[i]) {
					playSound("/Sounds/sadtrombone.wav");
					r = snakeBoard.boardPiece[b[i]].getBounds();
					y = r.getX();
					z = r.getY();
					x1 = (int) Math.round(y);
					y1 = (int) Math.round(z);
					snakeBoard.miscellaneous[2].setBounds((x1 + 30), (y1 + 17), 20, 52);
					player2Pos = b[i];

				}
			}
			// check for ladders on the tiles
			int c[] = new int[] { 3, 6, 11, 15, 17, 22, 38, 49, 57, 61, 73, 81, 88 };
			int f[] = new int[] { 20, 14, 28, 34, 74, 37, 59, 67, 76, 78, 86, 98, 91 };
			for (int i = 0; i < 13; i++) {
				if (player2Pos == c[i]) {
					playSound("/Sounds/cheering.wav");
					r = snakeBoard.boardPiece[f[i]].getBounds();
					y = r.getX();
					z = r.getY();
					x1 = (int) Math.round(y);
					y1 = (int) Math.round(z);
					snakeBoard.miscellaneous[2].setBounds((x1 + 30), (y1 + 16), 20, 52);
					player2Pos = f[i];

				}
			}

		}
	}

	public Listener(Board b) {
		// TODO Auto-generated constructor stub
		this.snakeBoard = b;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == snakeBoard.reset) {
			snakeBoard.miscellaneous[1].setBounds(140, 650, 20, 52);
			snakeBoard.miscellaneous[2].setBounds(70, 650, 20, 52);
			// roll dice button enabled
			snakeBoard.rollButton.setEnabled(true);
			snakeBoard.yourNumber.setText("0");
			player1Pos = 0;
			w = 0;
			player2Pos = 0;

		} else if (e.getSource() == snakeBoard.learnMore) {
			JFrame aboutFrame = new JFrame("About Game");
			aboutFrame.setBounds(20, 20, 900, 600);
			aboutFrame.setSize(900, 700);
			aboutFrame.setVisible(true);

			JLabel a4 = new JLabel();
			a4.setBounds(0, 0, 900, 600);
			a4.setText("");
			URL url = Board.class.getResource("/Pictures/gamerules.png");
			a4.setIcon(new ImageIcon(url));
			aboutFrame.add(a4);
		} else if (e.getSource() == snakeBoard.rollButton) {

			genRandomNum();

			if (w % 2 == 0) {
				snakeBoard.playerTurn.setText("Computer's turn!");

				w++;

				if ((player1Pos + randomNum) <= 100) {

					player1Pos = player1Pos + randomNum;
					r = snakeBoard.boardPiece[player1Pos].getBounds();
					y = r.getX();
					z = r.getY();
					x1 = (int) Math.round(y);
					y1 = (int) Math.round(z);

					snakeBoard.miscellaneous[1].setBounds((x1 + 10), (y1 + 20), 20, 52);

					// check for snake heads on the tile
					int a[] = new int[] { 8, 18, 26, 39, 51, 54, 56, 60, 75, 83, 85, 90, 92, 97, 99 };
					int b[] = new int[] { 4, 1, 10, 5, 6, 36, 1, 23, 28, 45, 59, 48, 25, 87, 63 };

					for (int i = 0; i < 15; i++) {
						if (player1Pos == a[i]) {
							playSound("/Sounds/sadtrombone.wav");
							r = snakeBoard.boardPiece[b[i]].getBounds();
							y = r.getX();
							z = r.getY();
							x1 = (int) Math.round(y);
							y1 = (int) Math.round(z);
							snakeBoard.miscellaneous[1].setBounds((x1 + 10), (y1 + 17), 20, 52);
							player1Pos = b[i];

						}
					}

					// check for ladders on the tile
					int c[] = new int[] { 3, 6, 11, 15, 17, 22, 38, 49, 57, 61, 73, 81, 88 };
					int f[] = new int[] { 20, 14, 28, 34, 74, 37, 59, 67, 76, 78, 86, 98, 91 };
					for (int i = 0; i < 13; i++) {
						if (player1Pos == c[i]) {
							playSound("/Sounds/cheering.wav");
							r = snakeBoard.boardPiece[f[i]].getBounds();
							y = r.getX();
							z = r.getY();
							x1 = (int) Math.round(y);
							y1 = (int) Math.round(z);
							snakeBoard.miscellaneous[1].setBounds((x1 + 10), (y1 + 16), 20, 52);
							player1Pos = f[i];

						}
					}

				}
				moveComputerSynchronized();

			}
		}
		if (player1Pos == 100) {
			snakeBoard.playerTurn.setText("Winner: P1");
			playSound("/Sounds/tada.wav");
			// if a player wins no more dice rolls available
			snakeBoard.rollButton.setEnabled(false);
		} else if (player2Pos == 100) {
			snakeBoard.playerTurn.setText("Winner: P2");
			playSound("/Sounds/tada.wav");
			// if a player wins no more dice rolls available
			snakeBoard.rollButton.setEnabled(false);
		}

	}
}
