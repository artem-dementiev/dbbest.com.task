package entities;

/**
 * Entity Node which stores length from vertex X to vertex Y
 * @author Artem Dementiev
 */
public class Node implements OutputFormat {
    private int id;
    private int idX;
    private int idY;
    private int length;

    public Node(int idX, int idY, int length) {
        this.idX = idX;
        this.idY = idY;
        this.length = length;
    }

    public Node(int id, int idX, int idY, int length) {
        this.id = id;
        this.idX = idX;
        this.idY = idY;
        this.length = length;
    }
    public Node(){}

    @Override
    public String toString() {
        return "Node{" +
                "idX=" + idX +
                ", idY=" + idY +
                ", length=" + length +
                '}';
    }

    @Override
    public String toConsole(){
        return this.toString()+"\n";
    }

    @Override
    public String toFile() {
        return String.format("%d;%d;%d",idX, idY, length);
    }

    public int getIdX() {
        return idX;
    }

    public void setIdX(int idX) {
        this.idX = idX;
    }

    public int getIdY() {
        return idY;
    }

    public void setIdY(int idY) {
        this.idY = idY;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
