/**
 * Nico Dennis
 * last edited May 6th, 2019
 * Node class will need to store the departure in a departure node or destination and the cost within the destination node
 */
public class Node
{
    private String destination;
    private String departure;
    private int cost;
    private Node next;
    private boolean checked;

    public Node(String flightDestination, int flightCost)
    {
        cost = flightCost;
        destination = flightDestination;
    }
    public Node(String flightDeparture)
    {
        departure = flightDeparture;
        checked = false;
    }
    public String getDestination()
    {
        return destination;
    }
    public String getDeparture()
    {
        return departure;
    }
    public int getCost()
    {
        return cost;
    }
    public Node getNext()
    {
        return next;
    }
    public void setNext(Node nextNode)
    {
        next = nextNode;
    }
    public void setDestination(String dest)
    {
        destination = dest;
    }
    public void setDeparture(String dest)
    {
        departure = dest;
    }
    public boolean getChecked()
    {
        return checked;
    }
    public void setChecked(boolean tf)
    {
        checked = tf;
    }

}