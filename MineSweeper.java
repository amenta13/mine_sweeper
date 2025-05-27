import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class MineSweeper extends JFrame{
	int size;
	int flagsremaining = 0;
	Square[][] board;
	JPanel boardpanel = new JPanel();
	boolean gameover = false;
	JPanel panel = new JPanel(new BorderLayout());
    JLabel markedmineslabel = new JLabel("Number of Flags Remaining: ");
    JTextField minesmarked = new JTextField();

	public MineSweeper(int size) {
		int r,c;
		int mineCount = 0;
		this.size = size;
		board = new Square[size][size];
		System.out.println(size);
		setTitle("Mine Sweeper");
		setSize(size*50,size*50+30);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		boardpanel.setLayout(new GridLayout(size,size));
		for (r=0; r<size; r++)
			for (c=0; c<size; c++) {
				Square square = new Square(this,r,c,board);
				board[r][c] = square;
				boardpanel.add(square);
				if (Math.random()<0.1) {
					square.mine = true;
					mineCount++;
				}	

			}
		flagsremaining = mineCount;
		add(boardpanel, BorderLayout.CENTER);
	    markedmineslabel.setLabelFor(minesmarked);
	    minesmarked.setText(""+flagsremaining);
	    minesmarked.setEditable(false);
	    panel.add(markedmineslabel, BorderLayout.WEST);
	    panel.add(minesmarked, BorderLayout.CENTER);
	    add(panel, BorderLayout.NORTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		String answer;
		int size = 0;
		answer = JOptionPane.showInputDialog("Mine Sweeper\nEnter the board size");
		while (size==0) {
			try {
				size = Integer.parseInt(answer);
			}
			catch(NumberFormatException e) {
				answer = JOptionPane.showInputDialog("Invlaid number, Enter size");
			}
		}
		
		new MineSweeper(size);

	}
	
	public static void MainCaller() {
        main(null);
	}

}
