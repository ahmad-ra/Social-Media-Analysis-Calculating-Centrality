
package socialmediaanalysis.utilities;



public class modifiedDijkstraUnit {

    

    public modifiedDijkstraUnit(int nodeId, float distance, boolean passThrough) {
        this.nodeId = nodeId;
        this.distance = distance;
        this.passThrough = passThrough;
    }

    @Override
    public String toString() {
        return "{" +
            " nodeId='" + nodeId + "'" +
            ", distance='" + distance + "'" +
            ", passThrough='" + passThrough + "'" +
            "}";
    }

 
    public int nodeId ;
    public float distance ;
    public boolean passThrough ;




}
