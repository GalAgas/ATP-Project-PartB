package algorithms.mazeGenerators;

public class Position
{
    private int row;
    private int col;
    private String posName;

    public Position(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRowIndex() { return row; }

    public int getColumnIndex()
    {
        return col;
    }

    public String getPosNameName()
    {
        return Integer.toString(row)+Integer.toString(col);
    }

    public String toString() { return ("{" + row + "," + col +"}"); }
}
