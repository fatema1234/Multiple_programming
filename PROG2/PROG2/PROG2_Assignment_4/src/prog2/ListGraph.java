package prog2;
import java.util.*;
//import java.util.stream.Collectors;

public class ListGraph<N> implements Graph<N>{
  //private Collection<T>nodes = new HashSet<>();
  private Map<N,Set<Edge<N>>>connectinMap=new HashMap<>();

  @Override
  public void add(N node) {
      connectinMap.putIfAbsent(node, new HashSet<>());
  }

  @Override
  public void connect(N node1, N node2, String name, int weight) {



      if(isNodeExist(node1)&&isNodeExist(node2))
      {

          Edge<N>edge = new Edge<N>(node1,name,weight);
          Edge<N>edge2 = new Edge<N>(node2,name,weight);

          if(getEdgeBetween(node1,node2)!= null){
              throw new IllegalStateException();

          }


          Set<Edge<N>> set = connectinMap.get(node1);
          Set<Edge<N>> set2 = connectinMap.get(node2);


          set.add(edge2);
          set2.add(edge);
         // setConnections(node1,node2,edge2);
         // setConnections(node2,node1,edge);
      }




  }

  private boolean isConnectionNotExist(N node1,N node2)
  {


      for(Edge<N> e1: connectinMap.get(node1))
      {
          if(e1.getDestination()==node2)
          {
              return false;
          }
      }



      return true;
  }
  private boolean isNodeExist(N node)
  {
      if(connectinMap.containsKey(node))
      {
          return true;
      }

      throw new NoSuchElementException();
  }
  private void setConnections(N node1, N node2,Edge<N>edge2)
  {
      if(connectinMap.containsKey(node1))
      {
          if (isConnectionNotExist(node1,node2))


              connectinMap.get(node1).add(edge2);

      }
      else
      {
          Set<Edge<N>>edges = new HashSet<>();

          edges.add(edge2);
          connectinMap.put(node1,edges);
      }
  }

  @Override
  public void setConnectionWeight(N node1, N node2, int weight) {
      Edge<N> e1 = getEdgeBetween(node1,node2);
      if(e1!=null)
      {
          e1.setWeight(weight);
          Edge<N> e2 = getEdgeBetween(node2,node1);
          if(e2!=null)
          {
              e2.setWeight(weight);

          }
      }

  }

  @Override
  public Set<N> getNodes() {
      return connectinMap.keySet();
  }

  @Override
  public Collection<Edge<N>> getEdgesFrom(N node) {
      if (isNodeExist(node))
      {
          if(connectinMap.containsKey(node))
          {
              // return connectinMap.get(node).stream().collect(Collections.unmodifiableCollection());

              return Collections.unmodifiableCollection(connectinMap.get(node));
          }
      }
      return null;
  }

  @Override
  public Edge<N> getEdgeBetween(N node1, N node2) {
      if(isNodeExist(node1)&& isNodeExist(node2))
      {
          if(connectinMap.containsKey(node1))
          {

              for(Edge<N> e1: connectinMap.get(node1))
              {
                  if(e1.getDestination().equals(node2))
                  {
                      return e1;

                  }
              }

          }
      }

      return null;
  }

  @Override
  public void disconnect(N node1, N node2) {
      if(!connectinMap.containsKey(node1) || !connectinMap.containsKey(node2)  ) {
          throw new NoSuchElementException();
      }
      if(getEdgeBetween(node1,node2)==null) {
          throw new IllegalStateException();
      }
      Edge<N> value1 = getEdgeBetween(node1,node2);

      Edge<N> value2 =getEdgeBetween(node2,node1);
      connectinMap.get(node1);
      connectinMap.get(node1).remove( value1);
      connectinMap.get(node2).remove( value2);



      

  }

  @Override
  public void remove(N node) {
      if(connectinMap.containsKey(node))
      {
          //graph theke remove korlam
          //connectinMap.remove(node);

          // if(connectinMap.containsKey(node))
          //{


          for(Edge<N> e: connectinMap.get(node))
          {
              for(Edge<N> e1: connectinMap.get(e.getDestination()))
              {
                  if(e1.getDestination()==node)
                  {
                      connectinMap.get(e.getDestination()).remove(e1);
                      break;
                  }
              }

          }
          //connection/edge theke remove korlam
          connectinMap.remove(node);




      }else
      {
          throw new NoSuchElementException();
      }




  }

  private void depthFirstSearch(N where, Set<N> visited){
      visited.add(where);
      for(Edge<N> edge:connectinMap.get(where)){
          if (!visited.contains(edge.getDestination()))
              depthFirstSearch(edge.getDestination(), visited);
      }
  }

  @Override
  public boolean pathExists(N from, N to) {
      Set<N> visited = new HashSet<>();
      depthFirstSearch(from,visited);
      return visited.contains(to);
  }

  private void depthFirstSearchForList(N where, N fromWhere, Set<N> visited, Map<N,N> via){
      visited.add(where);
      via.put(where,fromWhere);
      for(Edge<N> edge:connectinMap.get(where)){
          if (!visited.contains(edge.getDestination()))
              depthFirstSearchForList(edge.getDestination(), where, visited,via);
      }
  }

  private List<Edge<N>> gatherPath(N from, N to, Map<N, N> via){
      List<Edge<N>> path = new ArrayList<>();
      N where = to;
      while(!where.equals(from)){
          N t = via.get(where);
          Edge<N> e = getEdgeBetween(t, where);
          path.add(e);
          where = t;
      }
      Collections.reverse(path);
      return  path;
  }

  @Override
  public List<Edge<N>> getPath(N from, N to) {
     Set<N> visited = new HashSet<>();
     Map<N, N> via = new HashMap<>();
      depthFirstSearchForList(from, null, visited, via);
     return gatherPath(from, to, via);

  }








  @Override
  public String toString() {
      String s = "";
      for(N t: connectinMap.keySet()){
          s += t + ":";
          for(Edge<N> edge : connectinMap.get(t)) {
              s += edge;

          }
          s += "\n";
      }

      return s;
  }
}
