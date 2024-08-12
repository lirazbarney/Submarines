package project;

import java.awt.Color;
public class submarine
{
	int starti;
	int startj;
	int lasti;
	int lastj;
	int numOfLines;
	int numOfColomns;
	int lefts;
	public submarine(int starti, int startj, int numOfLines, int numOfColomns)
	{
		this.starti=starti;
		this.startj=startj;
		this.numOfLines=numOfLines;
		this.numOfColomns=numOfColomns;
		this.lasti=this.starti+this.numOfLines-1;
		this.lastj=this.startj+this.numOfColomns-1;
		this.lefts=this.numOfColomns*this.numOfLines;
	}
	public boolean hit(int[][] b)
	{
		//true if the sub sinked, false if  JUST hit
		this.lefts--;
		if(this.lefts==0)
		{
			for(int k=0;k<this.numOfColomns+2;k++)
			{
				b[this.startj+k-1][this.starti-1]=12;
				b[this.startj+k-1][this.starti+this.numOfLines]=12;
				//System.out.println("k = "+k+"; nol = "+this.numOfLines+"; starti = "+this.starti+"; starti-1 = "+(this.starti-1)+"; startj+k-1 = "+(this.startj+k-1)+"; starti+numOfColomns = "+(this.starti+this.numOfColomns)+"; startj+k-1 = "+(this.startj+k-1));
				/*ButtonGrid.compboard[this.startj+k-1][this.starti-1]=12;
				ButtonGrid.compboard[this.startj+k-1][this.starti+this.numOfLines]=12;*/
			}
			for(int k=0;k<this.numOfLines;k++)
			{
				b[this.startj-1][this.starti+k]=12;
				b[this.lastj+1][this.starti+k]=12;
				/*ButtonGrid.compboard[this.startj-1][this.starti+k]=12;
				ButtonGrid.compboard[this.lastj+1][this.starti+k]=12;*/
			}
			return true;
		}
		return false;
	}

	public String toString()
	{
		return "starti="+this.starti+"; startj="+this.startj+"; lasti="+this.lasti+"; lastj="+this.lastj+"; nol="+this.numOfLines+"; noc="+this.numOfColomns+"; letfts="+this.lefts;
	}

	public int getLestfs()
	{
		return this.lefts;
	}
}
