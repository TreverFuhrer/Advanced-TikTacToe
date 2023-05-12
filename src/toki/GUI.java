package toki;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GUI implements ActionListener{

	private JFrame frame;
	private JButton[][] buttons;
	private int[][] board;
	private int playerSymbol;
	private JPanel panel;
	
	public GUI() {
		initialize();
	}
	
	public void initialize() {
		frame = new JFrame();
		panel = new JPanel();
		this.frame.setTitle("Tik Tac Toe !!");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(600, 600);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setLayout(new BorderLayout());
		
		ImageIcon image = new ImageIcon("src\\logo.png");
		this.frame.setIconImage(image.getImage());
		
		this.panel.setLayout(new GridLayout(3,3));
		this.panel.setBackground(new Color(150,150,150));
		this.frame.add(panel);
		
		this.board = new int[3][3];
		this.buttons = createButtons();
		this.playerSymbol = whoStarts();
		
		this.frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		
		for(int j = 0; j < this.buttons.length; j++) {
			for(int k = 0; k < this.buttons.length; k++) {
				if(this.buttons[j][k]==button) {
					
					this.board[j][k] = 1;
					
					button.setText(this.playerSymbol == 1 ? "X" : "O");
					button.setEnabled(false);
					if(checkIfWon()) {
						JOptionPane.showMessageDialog(this.frame, "You Win!!", "", JOptionPane.PLAIN_MESSAGE);
						resetGame();
					}
					else if(checkIfTie()) {
						JOptionPane.showMessageDialog(this.frame, "ooo, its a tie...", " ", JOptionPane.PLAIN_MESSAGE);
						resetGame();
					}
					else 
						computerMove();
				}
			}
		}
	}
	
	private JButton[][] createButtons() {
		JButton[][] buttons = new JButton[3][3];
		for(int j = 0; j < 3; j++) {
			for(int k = 0; k < 3; k++) {
				buttons[j][k] = new JButton();
				this.panel.add(buttons[j][k]);
				buttons[j][k].setBounds(j*195, k*195, 195, 195);
				buttons[j][k].setBackground(Color.WHITE);
				buttons[j][k].addActionListener(this);
				buttons[j][k].setFont(new Font("Default",Font.BOLD,120));
			}
		}
		return buttons;
	}
	
	
	
	
	/**
	 * Gets random value between 1 and max
	 * @param Max
	 * @return random number
	 */
	public int random(int Max, int min) {
		return (int)(Math.random()*(Max-min+1))+min;
	}
	
	
	
	
	
	private int getPlayerSymbol() {
		int option = JOptionPane.showConfirmDialog(this.frame, "You get to pick!!\nYes for X - No for O", " ", JOptionPane.YES_NO_OPTION);
		return option == 0 ? 1 : 2;
	}
	
	
	
	
	
	private void computerMove() {
		boolean done = true;
		do {
			int row = random(2, 0);
			int col = random(2, 0);
			if(this.board[row][col] == 0) {
				this.board[row][col] = 2;
				this.buttons[row][col].setText(this.playerSymbol == 2 ? "X" : "O");
				this.buttons[row][col].setEnabled(false);
				if(checkIfWon()) {
					JOptionPane.showMessageDialog(this.frame, "You Lose... :(", "", JOptionPane.PLAIN_MESSAGE);
					resetGame();
				}
				else if(checkIfTie()) {
					JOptionPane.showMessageDialog(this.frame, "oooooo, its a tie...", " ", JOptionPane.PLAIN_MESSAGE);
					resetGame();
				}
				done = false;
			}
		}while(done);
	}

	
	
	
	
	
	
	private int whoStarts() {
		int coinFlip = random(2, 1);
		if(coinFlip == 1) {
			JOptionPane.showConfirmDialog(this.frame, "Flipping a coin!!\nYou get to go first!!!", "", JOptionPane.PLAIN_MESSAGE);
			return getPlayerSymbol();
		}
		JOptionPane.showConfirmDialog(this.frame, "Flipping a coin!!\nThe Computer goes first", "", JOptionPane.PLAIN_MESSAGE);
		this.playerSymbol = random(2, 1);
		computerMove();
		return this.playerSymbol;
	}
	
	
	
	
	
	
	private void resetGame() {
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				this.board[row][col] = 0;
				this.buttons[row][col].setText("");
				this.buttons[row][col].setBackground(Color.WHITE);
				this.buttons[row][col].setEnabled(true);
			}
		}
		this.playerSymbol = whoStarts();
	}
	
	
	
	
	/**
	 * Checks if someone won the game
	 * @param board deep array
	 */
	private boolean checkIfWon() {
		for(int row = 0; row < 3; row++) {
			if(this.board[row][0] != 0 && this.board[row][0] == this.board[row][1] && this.board[row][1] == this.board[row][2]) {
				this.buttons[row][0].setBackground(Color.GREEN);
				this.buttons[row][1].setBackground(Color.GREEN);
				this.buttons[row][2].setBackground(Color.GREEN);
				return true;
			}
		}
		for(int col = 0; col < 3; col++) {
			if(this.board[0][col] != 0 && this.board[0][col] == this.board[1][col] && this.board[1][col] == this.board[2][col]) {
				this.buttons[0][col].setBackground(Color.GREEN);
				this.buttons[1][col].setBackground(Color.GREEN);
				this.buttons[2][col].setBackground(Color.GREEN);
				return true;
			}
		}
		if(this.board[0][0] != 0 && this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2]) {
			this.buttons[0][0].setBackground(Color.GREEN);
			this.buttons[1][1].setBackground(Color.GREEN);
			this.buttons[2][2].setBackground(Color.GREEN);
			return true;
		}
		if(this.board[0][2] != 0 && this.board[0][2] == this.board[1][1] && this.board[1][1] == this.board[2][0]) {
			this.buttons[0][2].setBackground(Color.GREEN);
			this.buttons[1][1].setBackground(Color.GREEN);
			this.buttons[2][0].setBackground(Color.GREEN);
			return true;
		}
		return false;
	}
	
	
	
	
	
	/**
	 * Checks if the game resulted in a tie
	 * @param board deep array
	 */
	public boolean checkIfTie() {
		for(int j = 0; j < 3; j++) 
			for(int k = 0; k < 3; k++) 
				if(this.board[j][k] == 0)
					return false;
		for(int j = 0; j < 3; j++) 
			for(int k = 0; k < 3; k++) 
				this.buttons[j][k].setBackground(Color.RED);
		return true;
	}
	
}
