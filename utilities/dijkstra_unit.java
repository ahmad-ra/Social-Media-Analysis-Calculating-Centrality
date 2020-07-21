package socialmediaanalysis.utilities;

import java.util.ArrayList;

import datastructure.Graph;



public class dijkstra_unit {

    public int parentId ;
    public int nodeId ;
    public float distance ;

    public dijkstra_unit(int parentId, int nodeId, float distance) {
        this.parentId = parentId;
        this.nodeId = nodeId;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "{" +
            " parentId='" + parentId+ "'" +
            ", nodeId='" + nodeId + "'" +
            ", distance='" +distance + "'" +
            "}";
    }


}

