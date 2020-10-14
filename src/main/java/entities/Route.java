package entities;
/**
 * Entity Route which stores route from vertex X to vertex Y, the distance between which must be found
 * @author Artem Dementiev
 */
public class Route implements OutputFormat {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private int idX;
    private int idY;

    public Route(int id, int idX, int idY) {
        this.id = id;
        this.idX = idX;
        this.idY = idY;
    }
    @Override
    public String toConsole(){
        return this.toString()+"\n";
    }

    @Override
    public String toFile() {
        return String.format("%d;%d",idX, idY);
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

    @Override
    public String toString() {
        return "Route{" +
                "idX=" + idX +
                ", idY=" + idY +
                '}';
    }
}
