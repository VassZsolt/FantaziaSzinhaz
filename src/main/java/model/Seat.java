package model;

public class Seat {
    private int id;
    private boolean reserved;
    private int row;
    private int column;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", reserved=" + reserved +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
