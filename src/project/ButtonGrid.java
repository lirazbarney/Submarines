package project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ButtonGrid
{
	public static final int SEA=0;
	public static final int SUBMARINE=1;
	public static final int CANNOT=2;

	static JFrame frame=new JFrame();
	static JButton[][] compgrid;
	static JButton[][] mygrid;
	static JPanel compp=new JPanel();
	static JPanel myp = new JPanel();
	static JPanel buttonp = new JPanel();
	static JPanel text1p = new JPanel();
	static JPanel text2p = new JPanel();
	static JPanel externalp = new JPanel();

	static int[][] compboard=new int[16][16];
	static int[][] myboard=new int[16][16];

	static int comptotsub=29;
	static int mytotsub=29;
	static int subsofmine=0;
	static submarine[] compsubs;
	static submarine[] mysubs;
	static boolean myTurn=true;
	static boolean gamestarted = false;
	static int[] nums=new int[] {1, 7, 1, 6, 1, 5, 1, 4, 1, 4, 1, 3};


	public ButtonGrid(int width, int length)
	{
		compp.setBorder(BorderFactory.createTitledBorder("computer board"));
		compp.setLayout(new GridLayout(width,length));
		compgrid=new JButton[width][length];

		for(int y=0; y<length; y++)
		{
			for(int x=0; x<width; x++)
			{
				//compgrid[x][y]=new JButton("("+(y+1)+","+(x+1)+")");
				compgrid[x][y]=new JButton("");
				compgrid[x][y].setBackground(Color.CYAN);
				compgrid[x][y].setPreferredSize(new Dimension(72, 25));
				compgrid[x][y].addActionListener(new maingame(x, y));
				compp.add(compgrid[x][y]);
			}
		}
		/*String str="cyan = sea, there is a chance it has subs in it; yellow = sea but you know there isn't subs there; red = it a sub!!";
		JLabel lbcomp = new JLabel(str);
		compp.add(lbcomp);*/

		myp.setBorder(BorderFactory.createTitledBorder("my board"));
		myp.setLayout(new GridLayout(width,length));
		mygrid=new JButton[width][length];
		for(int y=0; y<length; y++)
		{
			for(int x=0; x<width; x++)
			{
				//mygrid[x][y]=new JButton("("+(y+1)+","+(x+1)+")");
				mygrid[x][y]=new JButton("");
				mygrid[x][y].setBackground(Color.BLUE);
				mygrid[x][y].setPreferredSize(new Dimension(72, 25));
				mygrid[x][y].addActionListener(new fillMyBoard(x, y));
				myp.add(mygrid[x][y]);
			}
		}

		JButton resetMyBoardBtn, randomMyBoardBtn, undoMySubBtn;
		resetMyBoardBtn = new JButton("RESET MY BOARD");
		resetMyBoardBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gamestarted)
				{
					zero(myboard);
					maingame.colormyboard();
					fillMyBoard.zero();
					subsofmine = 0;
					fillMyBoard.lasti=-1;
					fillMyBoard.lastj=-1;
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "stop cheating! the game already started");

				}
			}
		});
		randomMyBoardBtn = new JButton("RANDOM MY BOARD");
		randomMyBoardBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gamestarted)
				{
					fill(myboard, mysubs);
					maingame.colormyboard();
					subsofmine = 6;
					myTurn = true;
					fillMyBoard.lasti=-1;
					fillMyBoard.lastj=-1;
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "stop cheating! the game already started");

				}
			}
		});
		undoMySubBtn = new JButton("undo submarine");
		undoMySubBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!gamestarted) {
					if (subsofmine > 0) {
						int si = mysubs[subsofmine - 1].starti, sj = mysubs[subsofmine - 1].startj;
						int li = mysubs[subsofmine - 1].lasti, lj = mysubs[subsofmine - 1].lastj;
						int len = mysubs[subsofmine - 1].numOfLines, wid = mysubs[subsofmine - 1].numOfColomns;
						//JOptionPane.showMessageDialog(frame, "si="+si+"; sj="+sj+"; li="+li+"; lj="+lj+"; nol="+len+"; noc="+wid);
						for (int i = si - 1; i <= li + 1; i++) {
							for (int j = sj - 1; j <= lj + 1; j++) {
								myboard[j][i] = SEA;
								//JOptionPane.showMessageDialog(frame, "si="+si+"; sj="+sj+"; li="+li+"; lj="+lj+"; i="+i+"; j="+j);
							}
						}
						printBoard(myboard);
						for (int i = 0; i < 16; i++) {
							myboard[0][i] = CANNOT;
							myboard[myboard.length - 1][i] = CANNOT;
							myboard[i][0] = CANNOT;
							myboard[i][myboard.length - 1] = CANNOT;
							//JOptionPane.showMessageDialog(frame, "(0, "+i+"), ("+(myboard.length - 1)+", "+i+"), ("+i+", 0), ("+i+", "+(myboard.length - 1)+")");

						}
						subsofmine--;
						//JOptionPane.showMessageDialog(frame, "subsofmine="+subsofmine);
						for (int i = 0; i < subsofmine; i++)
							circle(myboard, mysubs[i].startj - 1, mysubs[i].starti - 1, mysubs[i].numOfColomns, mysubs[i].numOfLines);
						maingame.colormyboard();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "stop cheating! the game already started");
				}
			}
		});
		String str="AT THE COMP BOARD: cyan = sea, there is a chance it has subs in it; yellow = sea but you know there isn't subs there; red = it a sub!!";
		JLabel lbcomp = new JLabel(str);
		str= "AT MY BOARD: blue = sea; grey = it's a sub; green = it's sea but you cant put subs in there; yellow = the comp tried to hit there but missed; red = he hit you sub!";
		JLabel lbmy = new JLabel(str);
		text1p.add(lbcomp);
		text2p.add(lbmy);

		buttonp.add(resetMyBoardBtn);
		buttonp.add(randomMyBoardBtn);
		buttonp.add(undoMySubBtn);

		externalp.add(compp, BorderLayout.PAGE_END);
		externalp.add(myp, BorderLayout.PAGE_END);
		externalp.add(buttonp, BorderLayout.PAGE_END);
		externalp.add(text1p, BorderLayout.PAGE_END);
		externalp.add(text2p, BorderLayout.PAGE_END);

		compp.setMinimumSize(new Dimension(1200, 200));
		myp.setMinimumSize(new Dimension(1200, 200));
		buttonp.setMinimumSize(new Dimension(1200, 200));

		frame.setVisible(true);
		frame.add(externalp);
		frame.setResizable(false);
		externalp.setMinimumSize(new Dimension(1200, 900));
		frame.setPreferredSize(new Dimension(1050, 900));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
	public static void main(String[] args)
	{
		compsubs=new submarine[6];
		mysubs=new submarine[6];
		zero(myboard);
		fill(compboard, compsubs);
		//printBoard(myboard);
		for(int i=0;i<6;i++)
			System.out.println(i+": "+compsubs[i].toString());
		System.out.println();
		new ButtonGrid(14,14);
	}
	public static void printBoard(int[][] board)
	{
		for(int i=0;i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
				System.out.print("board["+i+"]["+j+"]="+board[j][i]+"	");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static void zero(int[][] b)
	{
		//פעולה שמאפסת את הלוח
		for(int i=0;i<b.length;i++)
		{
			b[0][i]=CANNOT;
			b[b.length-1][i]=CANNOT;
			b[i][0]=CANNOT;
			b[i][b.length-1]=CANNOT;
		}
		for(int i=1;i<b.length-1;i++)
		{
			for(int j=1;j<b[i].length-1;j++)
			{
				b[i][j]=SEA;
			}
		}
	}
	public static void circle(int[][] b, int y, int x, int len, int wid)
	{
		//System.out.println(y + " " + x + " " + len + " " + wid);
		for(int k=0;k<wid+2;k++)
		{
			//System.out.println("i="+i+". j="+j+". len="+len+". wid="+wid+". k="+k+". i+wid-1="+(i+wid-1)+". j+k="+(j+k));
			b[y][x+k]=CANNOT;
			b[y+len+1][x+k]=CANNOT;

			//System.out.println(y + " " + (x + k));
			//System.out.println((y + len + 1) + " " + (x + k));
		}
		for(int k=0;k<len+2;k++)
		{
			//System.out.println("i="+i+". j="+j+". len="+len+". wid="+wid+". k="+k+". j+len-1="+(j+len-1)+". i+k="+(i+k));
			b[y+k][x]=CANNOT;
			b[y+k][x+wid+1]=CANNOT;
		}
	}
	public static void fill(int[][] board, submarine[] subs)
	{
		zero(board); //לאפס את הלוח כך שלא יהיו בו צוללות
		Random rand = new Random();
		int dir, k, m; //dir קובע את כיוון הצוללת בצורה רנדומלית
		boolean b=false;

		int n1, n2;
		for(int a=0;a<12;a+=2)
		{
			int place=0;
			b=false;
			n1=nums[a];
			n2=nums[a+1];
			dir=rand.nextInt(2);
			for(int i=0;i<3;i++) //הלולאה תנסה 9 פעמים לשים את הצוללות, במידה והיא לא מצליחה היא תקרא לפונקציה מחדש ותציב מחדש את הצוללות
			{
				for(int j=0;j<3;j++)
				{
					if(!b)
					{
						k=rand.nextInt(14)+1;
						m=rand.nextInt(14)+1;
						if(dir==0)
						{
							b=addSub(board, n1, n2, m, k);
							if(b)
							{
								subs[a/2]=new submarine(k, m, n1, n2);
								//System.out.println((a/2)+": "+subs[a/2].toString());
							}
						}
						if(dir==1)
						{
							b=addSub(board, n2, n1, m, k);
							if(b)
							{
								subs[a/2]=new submarine(k, m, n2, n1);
								//System.out.println((a/2)+": "+subs[a/2].toString());
							}
						}
					}
				}
				if(!b)
				{
					fill(board, subs);
					return;
				}
			}
		}
	}

	public static boolean addSub(int[][] b, int cols, int lines, int i, int j)
	{
		int temp[][]=new int[16][16]; //temp הוא מערך דו-מימדי נוסף שיכיל זמנית את המערך הדו-מימדי ויבדוק האם ניתן להציב את הצוללת במקום abh,i
		int k, m;

		for(k=0;k<b.length;k++)
		{
			for(m=0;m<b[k].length;m++)
			{
				temp[k][m]=b[k][m];
			}
		}

		for(int w=0;w<lines;w++)
		{
			k=i+w;
			for(int p=0;p<cols;p++)
			{
				m=j+p;
				if(k>14||k<1||m>14||m<1||(temp[k][m]==SUBMARINE)||(temp[k][m]==CANNOT)) //במידה ולא ניתן מסיבה כזאת או אחרת להציב את הצוללת הפעולה תחזיר שקר
					return false;
				temp[k][m]=SUBMARINE;
			}
		}
		//ניתן היה להציב את הצוללת והמערך יועתק למערך הדו-מימדי שהפעולה קיבלה
		for(k=0;k<b.length;k++)
		{
			for(m=0;m<b[k].length;m++)
			{
				b[k][m]=temp[k][m];
			}
		}
		//printBoard(b);
		//System.out.println("i="+i+". j="+j+". cols="+cols+". lines="+lines);
		circle(b, i-1, j-1, lines, cols);
		return true;
	}

}


