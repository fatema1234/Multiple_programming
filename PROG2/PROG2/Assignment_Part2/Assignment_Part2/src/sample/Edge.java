package sample;

public class Edge<T> {
    private String name;
    private int weight;
    private T destination;

    public Edge(String name, int weight, T destination) {
        this.name = name;
       setWeight(weight);
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public T getDestination() {
        return destination;
    }

    public void setWeight(int weight) {
        if(weight<0)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            this.weight = weight;
        }

    }

    @Override
    public String toString() {

        return "till "+destination+" med "+name+" tar "+weight;
    }
}

