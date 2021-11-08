package sample;

import java.util.*;
//import java.util.stream.Collectors;

public class ListGraph<T> implements Graph<T>{
//private Collection<T>nodes = new HashSet<>();
private Map<T,Set<Edge<T>>>connectinMap=new HashMap<>();

@Override
public void add(T node) {

    connectinMap.putIfAbsent(node, new HashSet<>());
}

@Override
public void connect(T node1, T node2, String name, int weight) {



    if(isNodeExist(node1)&&isNodeExist(node2))
    {
        Edge<T>edge = new Edge<>(name,weight,node1);
        Edge<T>edge2 = new Edge<>(name,weight,node2);

        if(getEdgeBetween(node1,node2)!= null){
            throw new IllegalStateException();

        }


        Set<Edge<T>> set = connectinMap.get(node1);
        Set<Edge<T>> set2 = connectinMap.get(node2);

        set.add(edge2);
        set2.add(edge);
    }




}

private boolean isConnectionNotExist(T node1,T node2)
{


    for(Edge<T> e1: connectinMap.get(node1))
    {
        if(e1.getDestination()==node2)
        {
            return false;
        }
    }



    return true;
}
private boolean isNodeExist(T node)
{
    if(connectinMap.containsKey(node))
    {
        return true;
    }

    throw new NoSuchElementException();
}
private void setConnections(T node1, T node2,Edge<T>edge2)
{
    if(connectinMap.containsKey(node1))
    {
        if (isConnectionNotExist(node1,node2))


            connectinMap.get(node1).add(edge2);

    }
    else
    {
        Set<Edge<T>>edges = new HashSet<>();

        edges.add(edge2);
        connectinMap.put(node1,edges);
    }
}

@Override
public void setConnectionWeight(T node1, T node2, int weight) {
    Edge<T> e1 = getEdgeBetween(node1,node2);
    if(e1!=null)
    {
        e1.setWeight(weight);
        Edge<T> e2 = getEdgeBetween(node2,node1);
        if(e2!=null)
        {
            e2.setWeight(weight);

        }
    }

}

@Override
public Set<T> getNodes() {
    return connectinMap.keySet();
}

@Override
public Collection<Edge<T>> getEdgesFrom(T node) {
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
public Edge<T> getEdgeBetween(T node1, T node2) {
 if(isNodeExist(node1)&& isNodeExist(node2))
   {
        if(connectinMap.containsKey(node1))//na dileo hoi
        {

            for(Edge<T> e1: connectinMap.get(node1))
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
public void disconnect(T node1, T node2) {
    if(!connectinMap.containsKey(node1) || !connectinMap.containsKey(node2)  ) {
        throw new NoSuchElementException();
    }
    if(getEdgeBetween(node1,node2)==null) {
        throw new IllegalStateException();
    }
    Edge<T> value1 = getEdgeBetween(node1,node2);

    Edge<T> value2 =getEdgeBetween(node2,node1);
   // connectinMap.get(node1);
    connectinMap.get(node1).remove( value1);
    connectinMap.get(node2).remove( value2);


}

@Override
public void remove(T node) {
    if(connectinMap.containsKey(node))
    {
        for(Edge<T> e: connectinMap.get(node))
        {
            for(Edge<T> e1: connectinMap.get(e.getDestination()))
            {
                if(e1.getDestination()==node)
                {
                    connectinMap.get(e.getDestination()).remove(e1);
                    break;
                }
            }

        }
        connectinMap.remove(node);




    }else
    {
        throw new NoSuchElementException();
    }




}

private void depthFirstSearch(T where, Set<T> visited){
    visited.add(where);

    for(Edge<T> edge:connectinMap.get(where)){
        if (!visited.contains(edge.getDestination()))
            depthFirstSearch(edge.getDestination(), visited);
    }
}

@Override
public boolean pathExists(T from, T to) {
    Set<T> visited = new HashSet<>();
    if(!connectinMap.containsKey(from))
        return false;
    depthFirstSearch(from,visited);
    return visited.contains(to);
}

private void depthFirstSearchForList(T where, T fromWhere, Set<T> visited, Map<T,T> via){
    visited.add(where);
    via.put(where,fromWhere);
    if(connectinMap.containsKey(where))
    for(Edge<T> edge:connectinMap.get(where)){
        if (!visited.contains(edge.getDestination()))
            depthFirstSearchForList(edge.getDestination(), where, visited,via);
    }
}


private List<Edge<T>> gatherPath(T from, T to, Map<T, T> via){
    List<Edge<T>> path = new ArrayList<>();
    T where = to;
    while(!where.equals(from)){
        T t = via.get(where);
        if(connectinMap.containsKey(t))
        {

            for(Edge<T> e1: connectinMap.get(t))
            {
                if(e1.getDestination().equals(where))
                {
                    path.add(e1);
                    where = t;
                    break;

                }
            }

        }

        else
            break;

    }
    Collections.reverse(path);
    return  path.isEmpty()?null:path;
}

@Override
public List<Edge<T>> getPath(T from, T to) {
   Set<T> visited = new HashSet<>();
   Map<T, T> via = new HashMap<>();
    if(!connectinMap.containsKey(from))
        return null;
    depthFirstSearchForList(from, null, visited, via);
   return gatherPath(from, to, via);

}








@Override
public String toString() {
    String s = "";
    for(T t: connectinMap.keySet()){
        s += t + ":";
        for(Edge<T> edge : connectinMap.get(t)) {
            s += edge;

        }
        s += "\n";
    }

    return s;
}
}
