package TuringMachine;


public class Tape {
    private Cell currentCell;

    Tape () {
        this.currentCell = new Cell();
        this.currentCell.content = ' ';
    }

    /** returns the pointer that points to the current cell */
    public Cell getCurrentCell() {
        return this.currentCell;
    }

    /** returns the char from the current cell */
    public char getContent() {
        return this.currentCell.content;
    }

    /** changes the char in the current cell to the specified value */
    public void setContent(char ch) {
        this.currentCell.content = ch;
    }

    /** moves the current cell one position to the left along the tape. Note that if the current cell is the leftmost
     * cell that exists, then a new cell must be created and added to the tape at the left of the current cell, and
     * then the current cell pointer can be moved to point to the new cell. The content of the new cell should be a
     * blank space. (Remember that the Turing machine's tape is conceptually infinite, so your linked list must
     * be prepared to expand on demand, when the machine wants to move past the current end of the list. */
    public void moveLeft() {
        if (this.currentCell.prev != null) {
            this.currentCell = this.currentCell.prev;
        } else {
            Cell newCurrentCell = new Cell();
            newCurrentCell.next = this.currentCell;
            newCurrentCell.content = ' ';

            this.currentCell.prev = newCurrentCell;
            this.currentCell = newCurrentCell;
        }
    }

    /** moves the current cell one position to the right along the tape. Note that if the current cell is the rightmost
     * cell that exists, then a new cell must be created and added to the tape at the right of the current cell, and
     * then the current cell pointer can be moved to point to the new cell. The content of the new cell should be a
     * blank space */
    public void moveRight() {
        if (this.currentCell.next != null) {
            this.currentCell = this.currentCell.next;
        } else {
            Cell newCurrentCell = new Cell();
            newCurrentCell.prev = this.currentCell;
            newCurrentCell.content = ' ';

            this.currentCell.next = newCurrentCell;
            this.currentCell = newCurrentCell;
        }
    }
    /** returns a String consisting of the chars from all the cells on the tape, read from left to right, except that
     * leading or trailing blank characters should be discarded. The current cell pointer should not be moved by this
     * method; it should point to the same cell after the method is called as it did before. You can create a different
     * pointer to move along the tape and get the full contents. (This method is the hardest one to implement. */
    public String getTapeContents() {
        StringBuilder tapeContents = new StringBuilder();
        Cell start = this.getCurrentCell();

        while (this.currentCell.prev != null) {
            this.moveLeft();
        }

        tapeContents.append(this.getContent());
        while (this.currentCell.next != null) {
            this.moveRight();
            tapeContents.append(this.getContent());
        }

        this.currentCell = start;
        return tapeContents.toString().strip();
    }
}