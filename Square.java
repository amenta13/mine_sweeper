import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Square extends JButton{
		int row,col;
		Square[][] board;
		boolean mine = false;
		boolean flagged = false;
		boolean exposed = false;
		MineSweeper game;
		
		public Square(MineSweeper game, int r, int c, Square[][] bored) {
			this.game = game;
			row =r;
			col = c;
			board = bored;
			addMouseListener(new MyListener());
		}
		
		class MyListener implements MouseListener {
			public void mouseClicked(MouseEvent e) {
				if (game.gameover) return;
				if (exposed) return;
				if (e.getButton()==3) {
					if (flagged) { // Un-flag it
						setText("");
						setBackground(null);
						flagged = false;
						game.flagsremaining++;
						game.minesmarked.setText(""+game.flagsremaining);
						return;
					} else {
						if (game.flagsremaining == 0) return;
						setText("|>");
						setBackground(Color.yellow);
						flagged = true;
						game.flagsremaining--;
						game.minesmarked.setText(""+game.flagsremaining);
						checkWin();
						if (game.gameover) {
						    String[] options = { "YES", "NO" };
						    var selection = JOptionPane.showOptionDialog(null, "Game over, YOU WIN!\nPlay again?",null, 
						                                                      0, 2, null, options, options[0]);
						    if (selection == 0) {
						    	game.dispose();
						    	MineSweeper.MainCaller();
						    }
						    if (selection == 1) { 
						        System.exit(0);
						    }
							return;
						}
					}
				}				
				if (flagged) return;
				if (mine) {
					setBackground(Color.red);
					setText("*");
					game.gameover = true;
					for (int i=0; i<board.length; i++)
						for (int j=0; j<board.length; j++) {
							if (board[i][j].mine) {
								board[i][j].setBackground(Color.red);
								board[i][j].setText("*");
							}
						}
					if (game.gameover) {
					    String[] options = { "YES", "NO" };
					    var selection = JOptionPane.showOptionDialog(null, "Game over, YOU LOSE!\nPlay again?",null, 
					                                                      0, 2, null, options, options[0]);
					    if (selection == 0) {
					    	game.dispose();
					    	MineSweeper.MainCaller();
					    }
					    if (selection == 1) { 
					        System.exit(0);
					    }
					}
					return;
				}
				expose(row,col);
				checkWin();
				if (game.gameover) {
					if (game.gameover) {
					    String[] options = { "YES", "NO" };
					    var selection = JOptionPane.showOptionDialog(null, "Game over, YOU WIN!\nPlay again?",null, 
					                                                      0, 2, null, options, options[0]);
					    if (selection == 0) {
					    	game.dispose();
					    	MineSweeper.MainCaller();
					    }
					    if (selection == 1) {
					    	System.exit(0);
					    }
					}
				}
			}

			public void mousePressed(MouseEvent e) {
				//System.out.println("Mouse Pressed"+row+","+col);
				//System.out.println("    Button number: "+e.getButton());
				
			}

			public void mouseReleased(MouseEvent e) {
				//System.out.println("Mouse Released "+row+","+col);
				//System.out.println("    Button number: "+e.getButton());
				
			}

			public void mouseEntered(MouseEvent e) {
				//System.out.println("You are pointing at "+row+","+col);
				//System.out.println("    Button number: "+e.getButton());
				
			}

			public void mouseExited(MouseEvent e) {
				//System.out.println("You are not pointing at "+row+","+col);
				//System.out.println("    Button number: "+e.getButton());
				
			}
			
		}
		
		public void expose(int row, int col) {
			int mineCount = 0;
			if (board[row][col].exposed) return;
			board[row][col].setBackground(Color.white);
			board[row][col].exposed = true;
			// count the mines touching this square
			for (int i=-1; i<=1; i++)
				for (int j=-1; j<=1; j++)
					try {
						if (board[row+i][col+j].mine) mineCount++;
					} catch (ArrayIndexOutOfBoundsException e1) {}
			if (mineCount>0)
				board[row][col].setText(""+mineCount);
			else {
				for (int i=-1; i<=1; i++)
					for (int j=-1; j<=1; j++)
						try {
							expose(row+i,col+j);
						} catch (ArrayIndexOutOfBoundsException e1) {}
			}
			//System.out.println("Mouse Clicked "+row+","+col);
			//System.out.println("    Button number: "+e.getButton());
		}
		
		private boolean checkWin() {
			int counter = 0, squaretotal = 0;
			for (int r=0; r<board.length; r++)
				for (int c=0; c<board.length; c++)
						squaretotal++;
			System.out.println(squaretotal);
			for (int r=0; r<board.length; r++)
				for (int c=0; c<board.length; c++) {
						if (board[r][c].exposed)
							counter++;
						if (board[r][c].flagged && board[r][c].mine)
							counter++;
				}
			System.out.println(counter);
			if (counter == squaretotal)
				game.gameover = true;
						
			return (game.gameover);
		}
}