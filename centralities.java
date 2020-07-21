package socialmediaanalysis;

import java.util.ArrayList;
import java.util.*;
import java.util.Comparator;
import java.util.PriorityQueue;

import datastructure.*;
import socialmediaanalysis.utilities.*;




public class centralities {


     /**
     *
     * @param Graph G : the needed graph
     * @return Arraylist<int> result : the degree centrality of each node sorted by the node index
     */

    public static ArrayList<Integer> degree ( Graph G) {

        ArrayList<Integer> result = new ArrayList<Integer>() ;


        for ( int i = 0 ; i< G.getNoVertices() ; i++) {


            result.add(G.getNode(i).getNoChildren()) ;
        }


        return result ;
    } 



     /**
     *
     * @param  G : the graph
     * @param  src : source node
     * @return Arraylist<dijkstra_unit> 
     * the shortest path from node src to every node in the graph formated as 
     * : parent ; node ; distance for each unit in the Arraylist 
     */


     public static dijkstra_unit[] dijkstra ( Graph G , Node src ){
       
        
        PriorityQueue <dijkstra_unit>table = new PriorityQueue<dijkstra_unit>(new Comparator<dijkstra_unit>() {

            @Override
            public int compare(dijkstra_unit u1, dijkstra_unit u2) {
                if ( u1.distance > u2.distance) return 1 ;
                else return -1 ;
            }
        });


dijkstra_unit  result [] = new dijkstra_unit [G.getNoVertices() ];

//initialization //
for( int i =0 ; i<G.getNoVertices() ; i++){
     result[i]=new dijkstra_unit(0, 0, -1) ;
     }


table.add(new dijkstra_unit(src.getID() , src.getID() , 0 ) ) ;
////////////////////////

while (! table.isEmpty() ) {
dijkstra_unit temp = table.poll() ; 

if(result[temp.nodeId].distance ==-1 )
{
    result[temp.nodeId]= temp;
}

for (Edge  E : G.getNode(temp.nodeId).getChildren() ) {
   
   if(result[E.getChild().getID()].distance ==-1  ){
   
    dijkstra_unit child = new dijkstra_unit(temp.nodeId, E.getChild().getID() , (float) ( temp.distance + E.getWeight())  );
    table.add(child ) ;
   }
}




}





return result ;

     }



     public static float[]  closeness(Graph G) {

        int numNodes = G.getNoVertices() ;
       float res[] = new float [G.getNoVertices() ] ; 
        for ( int i =0 ; i < numNodes ; i++) {
            float sumOfPathes = sumShortestPathesFrom (G , G.getNode(i) ) ;

            res[i]= (float)((numNodes-1) / sumOfPathes) ;
        }

        return res ;

     }

     /**
 * 
 * helper function for closeness
 * @param G
 * @param node
 * @return
 */
     private static int sumShortestPathesFrom(Graph G ,Node node) {

        dijkstra_unit all [] = dijkstra(G, node) ;
        int res = 0 ;
        for ( dijkstra_unit D : all )
        {
            if (D.distance!= -1)
            {
                res+=D.distance ;
            }

        }
        return res;
    }




  
  
  public static ArrayList<betweennessTableEntry> modifiedDijkstra (Graph G , Node src , Node between) {

    
    PriorityQueue <modifiedDijkstraUnit>table = new PriorityQueue<modifiedDijkstraUnit>(new Comparator<modifiedDijkstraUnit>() {

        @Override
        public int compare(modifiedDijkstraUnit u1, modifiedDijkstraUnit u2) {
            if ( u1.distance > u2.distance) return 1 ;
            else return -1 ;
        }
    });


ArrayList <betweennessTableEntry> result = new ArrayList<betweennessTableEntry>(G.getNoVertices()) ;

//initialization //
for( int i =0 ; i<G.getNoVertices() ; i++){
 result.add (new betweennessTableEntry(0 , 0 , -1 , 0 , 0) );
 }


table.add(new modifiedDijkstraUnit(src.getID(), 0, false) ) ;
////////////////////////



while (! table.isEmpty() ) {
modifiedDijkstraUnit temp = table.poll() ; 

///////////modification from dijkstra here //////////////////
if(result.get(temp.nodeId).distance ==-1 )
{
result.set(temp.nodeId, new betweennessTableEntry(temp.nodeId, src.getID() ,temp.distance, 1, temp.passThrough? 1:0 ));
}
else if (result.get(temp.nodeId).distance == temp.distance){

 result.get(temp.nodeId).noOfPathes ++ ;

 result.get(temp.nodeId).noOfPasses += temp.passThrough? 1 : 0 ; 


}

////////////////////end modif1 /////////////////////////////////

///////////modif 2//////////////////////////////
if (temp.nodeId == between.getID()) { temp.passThrough=true ;}

////////////end modif2/////////////////////////////



for (Edge  E : G.getNode(temp.nodeId).getChildren() ) {
////////////////modif 3 /////////////////////////
if( result.get(  E.getChild().getID()  ).distance  ==-1   ||
    result.get(  
    E.getChild().getID()  ).distance >= (float) ( temp.distance + E.getWeight()) )
{

modifiedDijkstraUnit child = new modifiedDijkstraUnit(E.getChild().getID(), (float) ( temp.distance + E.getWeight()), temp.passThrough) ;
/////////////////end modif 3 ////////////////////////////

table.add(child ) ;
}

}
}



return result ;
  }
  


  public static  ArrayList<betweennessTableEntry>  fillBetweennessTableForOneNode ( Graph G , Node n )
  {

    ArrayList <betweennessTableEntry> result = new ArrayList<betweennessTableEntry>(G.getNoVertices()) ;



    for ( int i = 0 ; i < G.getNoVertices() ; i ++ ) {

       
        ArrayList <betweennessTableEntry> temp = modifiedDijkstra(G, G.getNode(i), n) ;
       
        for ( int k = 0 ; k < temp.size() ; k ++ ) {
            if ( ! result.contains(temp.get(k) )  ) {
                result.add(temp.get(k)) ;

            } 
    
        }



    
}

    
return result ;
  }

  public static float betweennessCentralityForOneNode ( Graph G , Node n) {

    ArrayList<betweennessTableEntry> res = fillBetweennessTableForOneNode (G , n) ;
    float result = (float) 0.0;

    for ( betweennessTableEntry E  : res) {

        if ( E.nodeId != n.getID() && E.srcId != n.getID()  ) 
        {

            result += (float)((E.noOfPasses)/((E.noOfPathes) * 1.0) ) ;

        }


    }

    return result ;
  }


  public static ArrayList<Float> betweenness ( Graph G) {

    ArrayList <Float> res = new ArrayList<Float>(G.getNoVertices()) ;
    for ( Node n : G.getVertices()) {
        res.add (betweennessCentralityForOneNode(G, n)) ;


    }
 return res ;
  }






  ////////test/////////////
//   public static void main(String[] args) {
         
//         Graph g = new Graph(5) ;

//         g.addUndirectedEdge(0, 1, 1) ;
//         g.addUndirectedEdge(0, 2, 1) ;
//         g.addUndirectedEdge(1, 3, 1) ;
//         g.addUndirectedEdge(2, 3, 1) ;
//         g.addUndirectedEdge(3, 4, 1) ;
//         g.addUndirectedEdge(2, 4, 3) ;


//       //  System.out.print(betweenness(g).toString());
   

//      }


}



