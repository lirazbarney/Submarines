package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fillMyBoard implements ActionListener {
    JFrame frame;
    int x, y;
    static int lasti=-1, lastj=-1;
    public static int n1=0, n2=1;
    public fillMyBoard(int x, int y)
    {
        this.frame=ButtonGrid.frame;
        this.x=x;
        this.y=y;
    }

    public boolean addsub(int[][] b)
    {
        //System.out.println("lasti="+this.lasti+"; last j="+this.lastj+"; x="+this.x+"; y="+this.y+"; n1="+this.n1+"; n2="+this.n2+"; nums[n1]="+ButtonGrid.nums[this.n1]+"; nums[n2]="+ButtonGrid.nums[this.n2]+"; lasti-y="+(Math.abs((this.y) - (this.lasti))+1)+"; lastj-x="+(Math.abs((this.x) - (this.lastj))+1));

        for(int tempi=Math.min(this.lasti, this.y);tempi <= Math.max(this.lasti, this.y);tempi++)
        {
            for(int tempj=Math.min(this.lastj, this.x);tempj <= Math.max(this.lastj, this.x);tempj++)
            {
                //System.out.println("b["+(tempj+1)+"]["+(tempi+1)+"]="+b[tempj+1][tempi+1]);
                if(b[tempj+1][tempi+1] != ButtonGrid.SEA)
                    return false;
            }
        }
        for(int itemp=Math.min(this.lasti, this.y);itemp <= Math.max(this.lasti, this.y);itemp++)
        {
            for(int jtemp=Math.min(this.lastj, this.x);jtemp <= Math.max(this.lastj, this.x);jtemp++)
            {
                b[jtemp+1][itemp+1]=ButtonGrid.SUBMARINE;
            }
        }
        //ButtonGrid.printBoard(b);
        ButtonGrid.mysubs[n1/2]=new submarine(Math.min(this.lasti, this.y)+1, Math.min(this.lastj, this.x)+1, Math.max(this.lasti, this.y)-Math.min(this.lasti, this.y)+1, Math.max(this.lastj, this.x)-Math.min(this.lastj, this.x)+1);
        System.out.println((n1/2)+": "+ButtonGrid.mysubs[n1/2].toString());
        ButtonGrid.circle(b, Math.min(this.lastj, this.x), Math.min(this.lasti, this.y) , Math.abs(this.x - this.lastj)+1, Math.abs(this.y-this.lasti)+1);
        colorMyBoard(b);
        ButtonGrid.subsofmine++;
        this.n1+=2;
        this.n2+=2;
        return true;
    }

    public static void zero()
    {
        n1=0;
        n2=1;
        lasti=-1;
        lastj=-1;
    }

    public void colorMyBoard(int[][] b)
    {
        for(int i=0;i<14;i++)
        {
            for(int j=0;j<14;j++)
            {
                if(b[i+1][j+1]==ButtonGrid.SEA)
                {
                    ButtonGrid.mygrid[i][j].setBackground(Color.CYAN);
                }
                if(b[i+1][j+1]==ButtonGrid.SUBMARINE)
                {
                    ButtonGrid.mygrid[i][j].setBackground(Color.GRAY);
                }
                if(b[i+1][j+1]==ButtonGrid.CANNOT)
                {
                    ButtonGrid.mygrid[i][j].setBackground(Color.GREEN);
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {

        if(ButtonGrid.subsofmine<6)
        {
            n1=ButtonGrid.subsofmine*2;
            n2=n1+1;
            //JOptionPane.showMessageDialog(frame, "n1="+n1+"; n2="+n2);
            if(ButtonGrid.myboard[this.x+1][this.y+1] != ButtonGrid.SEA)
            {
                JOptionPane.showMessageDialog(frame, "unpressable button");
                return;
            }
            //System.out.println("last i="+this.lasti+"; last j="+this.lastj+"; x="+this.x+"; y="+this.y);
            if (this.lasti == -1 || this.lastj == -1)
            {
                this.lasti = y;
                this.lastj = x;

                ButtonGrid.mygrid[x][y].setBackground(Color.MAGENTA);
                if (ButtonGrid.nums[n1] == 1)
                {
                   // System.out.println((this.x - ButtonGrid.nums[n2] + 1) + " " + (this.y - ButtonGrid.nums[n2] + 1));
                    if (this.x - ButtonGrid.nums[n2] + 1 > -1 && this.x - ButtonGrid.nums[n2] + 1 < 14)
                    {
                        if(ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y].getBackground() != Color.GRAY && ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y].setBackground(Color.YELLOW);
                    }
                    if (this.x + ButtonGrid.nums[n2] + 1 > -1 && this.x + ButtonGrid.nums[n2] - 1 < 14)
                    {
                        if(ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y].getBackground() != Color.GRAY && ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y].setBackground(Color.YELLOW);
                    }
                    if (this.y - ButtonGrid.nums[n2] + 1 > -1 && this.y - ButtonGrid.nums[n2] + 1 < 14)
                    {
                        if(ButtonGrid.mygrid[x][y - ButtonGrid.nums[n2] + 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x][y - ButtonGrid.nums[n2] + 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x][y - ButtonGrid.nums[n2] + 1].setBackground(Color.YELLOW);
                    }
                    if (this.y + ButtonGrid.nums[n2] + 1 > -1 && this.y + ButtonGrid.nums[n2] - 1 < 14)
                    {
                        if(ButtonGrid.mygrid[x][y + ButtonGrid.nums[n2] - 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x][y + ButtonGrid.nums[n2] - 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x][y + ButtonGrid.nums[n2] - 1].setBackground(Color.YELLOW);
                    }
                }
                else
                {
                    if (this.x - ButtonGrid.nums[n2] + 1 > -1 && this.y - 1 > -1)
                    {
                        if(ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y - 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y - 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y - 1].setBackground(Color.YELLOW);
                    }
                    if (this.x - ButtonGrid.nums[n2] + 1 > -1 && this.y + 1 < 14)
                    {
                        if(ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y + 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y + 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x - ButtonGrid.nums[n2] + 1][y + 1].setBackground(Color.YELLOW);
                    }
                    if (this.x + ButtonGrid.nums[n2] - 1 < 14 && this.y - 1 > -1)
                    {
                        if(ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y - 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y - 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y - 1].setBackground(Color.YELLOW);
                    }
                    if (this.x + ButtonGrid.nums[n2] - 1 < 14 && this.y + 1 < 14)
                    {
                        if(ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y + 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y + 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x + ButtonGrid.nums[n2] - 1][y + 1].setBackground(Color.YELLOW);
                    }
                    if (this.y - ButtonGrid.nums[n2] + 1 > -1 && this.x - 1 > -1)
                    {
                        if(ButtonGrid.mygrid[x - 1][y - ButtonGrid.nums[n2] + 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x - 1][y - ButtonGrid.nums[n2] + 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x - 1][y - ButtonGrid.nums[n2] + 1].setBackground(Color.YELLOW);
                    }
                    if (this.y - ButtonGrid.nums[n2] + 1 > -1 && this.x + 1 < 14)
                    {
                        if(ButtonGrid.mygrid[x + 1][y - ButtonGrid.nums[n2] + 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x + 1][y - ButtonGrid.nums[n2] + 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x + 1][y - ButtonGrid.nums[n2] + 1].setBackground(Color.YELLOW);
                    }
                    if (this.y + ButtonGrid.nums[n2] - 1 < 14 && this.x - 1 > -1)
                    {
                        if(ButtonGrid.mygrid[x - 1][y + ButtonGrid.nums[n2] - 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x - 1][y + ButtonGrid.nums[n2] - 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x - 1][y + ButtonGrid.nums[n2] - 1].setBackground(Color.YELLOW);
                    }
                    if (this.y + ButtonGrid.nums[n2] - 1 < 14 && this.x + 1 < 14)
                    {
                        if(ButtonGrid.mygrid[x + 1][y + ButtonGrid.nums[n2] - 1].getBackground() != Color.GRAY && ButtonGrid.mygrid[x + 1][y + ButtonGrid.nums[n2] - 1].getBackground() != Color.GREEN)
                            ButtonGrid.mygrid[x + 1][y + ButtonGrid.nums[n2] - 1].setBackground(Color.YELLOW);
                    }
                }
            }
            else
            {
                if (this.lasti == y && this.lastj == x)
                {
                    this.lasti = -1;
                    this.lastj = -1;
                    colorMyBoard(ButtonGrid.myboard);
                }
                else
                {
                    if (ButtonGrid.mygrid[x][y].getBackground() == Color.YELLOW)
                    {
                        if (addsub(ButtonGrid.myboard))
                        {
                            this.lasti = -1;
                            this.lastj = -1;
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "not in range");
                    }
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "stop pressing your board and start playing at THE COMPUTER'S BOARD. the upper board, not the bottom one. (this is your board if you can't read...)");
        }
    }
}
