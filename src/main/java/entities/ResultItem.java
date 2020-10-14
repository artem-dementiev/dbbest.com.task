package entities;
/**
 * Entity ResultItem which stores boolean variable which describes existing route between vertexes and min distance
 * @author Artem Dementiev
 */
public class ResultItem implements OutputFormat {
    private boolean isExist;
    private int distance =-1;

    public ResultItem(){}
    public ResultItem(boolean isExist, int distance) {
        this.isExist = isExist;
        this.distance = distance;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        if(distance==-1){
            return "ResultItem{" +
                    "isExist=" + isExist +
                    '}';
        }else{
            return "ResultItem{" +
                    "isExist=" + isExist +
                    ", distance=" + distance +
                    '}';
        }
    }

    @Override
    public String toConsole() {
        return this.toString()+"\n";
    }

    @Override
    public String toFile() {
        if(distance==-1){
            return String.format("%b;",isExist);
        }else{
            return String.format("%b;%d",isExist, distance);
        }

    }
}
