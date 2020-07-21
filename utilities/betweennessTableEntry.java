
package socialmediaanalysis.utilities;

import java.util.Objects;

public class betweennessTableEntry {

public int nodeId ;
public int srcId ;
public float distance ;
public int noOfPathes ;
public int noOfPasses ;




    public betweennessTableEntry(int nodeId, int srcId, float distance, int noOfPathes, int noOfPasses) {
        this.nodeId = nodeId;
        this.srcId = srcId;
        this.distance = distance;
        this.noOfPathes = noOfPathes;
        this.noOfPasses = noOfPasses;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof betweennessTableEntry)) {
            return false;
        }
        betweennessTableEntry betweennessTableEntry = (betweennessTableEntry) o;
        return nodeId == betweennessTableEntry.srcId && srcId == betweennessTableEntry.nodeId && distance == betweennessTableEntry.distance && noOfPathes == betweennessTableEntry.noOfPathes ;
    }

    @Override

    public String toString() {
        return "{" +
            " nodeId='" + nodeId + "'" +
            ", srcId='" + srcId + "'" +
            ", distance='" + distance + "'" +
            ", noOfPathes='" + noOfPathes + "'" +
            ", noOfPasses='" + noOfPasses + "'" +
            "}";
    }




}

    
