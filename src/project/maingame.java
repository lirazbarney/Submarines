package project;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Color;
import java.util.Random;

public class maingame implements ActionListener
{
    JFrame frame;
    int x, y;
    static int i=-1, j=-1, dir=-1;
    int xx, yy;
    public maingame(int x, int y)
    {
        this.frame=ButtonGrid.frame;
        this.x=x;
        this.y=y;
    }

    public static int findsub(int x, int y, submarine[] subs)
    {
        for(int a=0;a<6;a++)
        {
            for(int i=subs[a].starti;i<=subs[a].lasti;i++)
            {
                for(int j=subs[a].startj;j<=subs[a].lastj;j++)
                {
                    System.out.println("y="+y+";  i="+i+";  y==i="+(y==i)+";  x="+x+";  j="+j+";  x==j="+(x==j));
                    if((y==i)&&(x==j))
                        return a;
                }
            }
            System.out.println();
        }
        System.out.println(":-(");
        return -1;
    }

    public void compturn()
    {
        if((i==-1)&&(j==-1))
        {
            Random rand = new Random();
            xx = 1 + rand.nextInt(14 - 1 + 1);
            yy = 1 + rand.nextInt(14 - 1 + 1);
            //1+rand.nextInt(14-1+1)
            //minimum + rn.nextInt(maxValue - minvalue + 1)
        }
        else
        {
            if(dir!=-1) {
                switch (dir) {
                    case 0: {
                            xx = j + 1;
                            yy = i;
                            if(xx>14)
                            {
                                xx=14;
                                dir=1;
                                while(ButtonGrid.myboard[yy][xx]>=10)
                                {
                                    xx--;
                                }
                            }
                            while(ButtonGrid.myboard[yy][xx]>=10)
                            {
                                xx--;
                                dir = 1;
                            }
                            break;
                    }
                    case 1: {
                        xx = j - 1;
                        yy = i;
                        if(xx<1)
                        {
                            xx=1;
                            dir=0;
                            while(ButtonGrid.myboard[yy][xx]>=10)
                            {
                                xx++;
                            }
                        }
                        while(ButtonGrid.myboard[yy][xx]>=10)
                        {
                            xx++;
                            dir=0;
                        }
                        break;
                    }
                    case 2: {
                        xx = j;
                        yy = i + 1;
                        if(yy>14)
                        {
                            yy=14;
                            dir=3;
                            while(ButtonGrid.myboard[yy][xx]>=10)
                            {
                                yy--;
                            }
                        }
                        while(ButtonGrid.myboard[yy][xx]>=10)
                        {
                            yy--;
                            dir=3;
                        }
                        break;
                    }
                    case 3: {
                        xx = j;
                        yy = i - 1;
                        if(yy<1)
                        {
                            yy=1;
                            dir=2;
                            while(ButtonGrid.myboard[yy][xx]>=10)
                            {
                                yy++;
                            }
                        }
                        while(ButtonGrid.myboard[yy][xx]>=10)
                        {
                            yy++;
                            dir=2;
                        }
                        break;
                    }
                }
            }
            else
            {
                //dir=-1
                Random rand = new Random();
                int num=rand.nextInt(3 - 0 + 1);
                switch(num)
                {
                    case 0:
                    {
                        xx=j+1;
                        yy=i;
                        if(ButtonGrid.myboard[yy][xx]==ButtonGrid.SUBMARINE)
                        {
                            dir=0;
                        }
                        break;
                    }
                    case 1:
                    {
                        xx=j-1;
                        yy=i;
                        if(ButtonGrid.myboard[yy][xx]==ButtonGrid.SUBMARINE)
                        {
                            dir=1;
                        }
                        break;
                    }
                    case 2:
                    {
                        xx=j;
                        yy=i+1;
                        if(ButtonGrid.myboard[yy][xx]==ButtonGrid.SUBMARINE)
                        {
                            dir=2;
                        }
                        break;
                    }
                    case 3:
                    {
                        xx=j;
                        yy=i-1;
                        if(ButtonGrid.myboard[yy][xx]==ButtonGrid.SUBMARINE)
                        {
                            dir=3;
                        }
                        break;
                    }
                }
            }
        }
        if((ButtonGrid.myboard[yy][xx]>=10)||((xx>14)||(xx<1)||(yy>14)||(yy<1)))
        {
            compturn();
            return;
        }
        //JOptionPane.showMessageDialog(frame, "i="+i+" j="+j+" x="+xx+" y="+yy);
        if (ButtonGrid.myboard[yy][xx] == ButtonGrid.SUBMARINE)
        {
            ButtonGrid.mytotsub--;
            i=yy;
            j=xx;
            int place = findsub(yy, xx, ButtonGrid.mysubs);
            try
            {
                if (ButtonGrid.mysubs[place].hit(ButtonGrid.myboard))
                {
                    i=j=dir=-1;
                    JOptionPane.showMessageDialog(frame, "the comp sinked your #" + (place + 1)+" sub");
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(frame, e);
            }
        }
        ButtonGrid.myboard[yy][xx]+=10;
        System.out.println("x="+xx+" (j="+j+");    y="+yy+" (i="+i+")");
        colormyboard();
        if(ButtonGrid.mytotsub==0)
            while (true)
                JOptionPane.showMessageDialog(frame, "the computer won, you are the greatest loser!");
    }

    public static void colormyboard()
    {
        for(int i=1;i<15;i++)
        {
            for (int j = 1; j < 15; j++)
            {
                if(ButtonGrid.myboard[i][j]==0)
                    ButtonGrid.mygrid[i-1][j-1].setBackground(Color.BLUE);
                if(ButtonGrid.myboard[i][j]==1)
                    ButtonGrid.mygrid[i-1][j-1].setBackground(Color.GRAY);
                if(ButtonGrid.myboard[i][j]==2)
                    if(ButtonGrid.gamestarted)
                        ButtonGrid.mygrid[i-1][j-1].setBackground(Color.BLUE);
                    else
                        ButtonGrid.mygrid[i-1][j-1].setBackground(Color.GREEN);
                if(ButtonGrid.myboard[i][j]==10||ButtonGrid.myboard[i][j]==12)
                    ButtonGrid.mygrid[i-1][j-1].setBackground(Color.YELLOW);
                if(ButtonGrid.myboard[i][j]==11)
                    ButtonGrid.mygrid[i-1][j-1].setBackground(Color.RED);
            }
        }
    }

    public void colorboard()
    {
        //ButtonGrid.printBoard(ButtonGrid.compboard);

        for(int i=1;i<15;i++)
        {
            for(int j=1;j<15;j++)
            {
                if(ButtonGrid.compboard[i][j]<10)
                    ButtonGrid.compgrid[i-1][j-1].setBackground(Color.CYAN);
                if(ButtonGrid.compboard[i][j]==11)
                    ButtonGrid.compgrid[i-1][j-1].setBackground(Color.RED);
                if((ButtonGrid.compboard[i][j]==10)||(ButtonGrid.compboard[i][j]==12))
                    ButtonGrid.compgrid[i-1][j-1].setBackground(Color.YELLOW);
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if(ButtonGrid.subsofmine<6||ButtonGrid.myTurn==false)
        {
            JOptionPane.showMessageDialog(frame, "It would seem that this is the computer's turn or perhaps you haven't chosen your submarines placement. \nIf none of those possibilities are correct, then by all means please DO tell the programmer about this so called 'bug' (this is not a bug, i just don't want you to play)");
        }
        else {
            ButtonGrid.gamestarted=true;
            if (ButtonGrid.compboard[x + 1][y + 1] >= 10) {
                JOptionPane.showMessageDialog(frame, "unpressable button; x = " + (x + 1) + "; y = " + (y + 1));

            } else {
                if ((ButtonGrid.compboard[x + 1][y + 1] == 0) || (ButtonGrid.compboard[x + 1][y + 1] == 2)) {
                    //JOptionPane.showMessageDialog(frame, "missed; x = " + (x + 1) + "; y = " + (y + 1));
                    ButtonGrid.compboard[x + 1][y + 1] += 10;
                }
                if (ButtonGrid.compboard[x + 1][y + 1] == 1) {
                    ButtonGrid.comptotsub--;
                    ButtonGrid.compboard[x + 1][y + 1] += 10;
                    int place = findsub(x + 1, y + 1, ButtonGrid.compsubs);
                    if (ButtonGrid.compsubs[place].hit(ButtonGrid.compboard)) {
                        JOptionPane.showMessageDialog(frame, "you sinked comp's #" + (place + 1)+ " sub");
                    } else {
                        //JOptionPane.showMessageDialog(frame, "hit; x = " + (x + 1) + "; y = " + (y + 1));
                    }
                }
                ButtonGrid.myTurn=false;
                colorboard();
                compturn();
                ButtonGrid.myTurn=true;
                System.out.println(ButtonGrid.comptotsub+" hello world! :)");
                if (ButtonGrid.comptotsub == 0) {
                    while(true)
                        JOptionPane.showMessageDialog(frame, "you won!");
                }
            }
        }
    }
}


