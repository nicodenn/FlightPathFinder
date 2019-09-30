/**Nico Dennis
 * Last edited May 6th, 2019
 * The linkedList class creates a linkedList using the methods below
 */

public class linkedList
{
    private Node head;
    private int numItems;

    public linkedList()
    {
        head = null;
        numItems = 0;
    }

    public Node getHead() {
        return this.head;
    }

    public boolean ListIsEmpty()
    {
        return this.numItems == 0;
    }

    public int ListLength()
    {
        return this.numItems;
    }

    private Node Locate(int position)
    {
        Node curr = head;
        while (position > 1)
        {
            curr = curr.getNext();
            position--;
        }
        return curr;
    }
    //Same as above but for departure
    public boolean ContainsDeparture(String departure)
    {
            if (RetrieveDeparture() == departure) return true;
        return false;
    }
    //returns the head nodes departure
    public String RetrieveDeparture()
    {
            Node curr = head;
            return curr.getDeparture();
    }
    //inserts a destination node into the linked list
    public void DestInsert(String destination, int flightCost)
    {
        Node newNode = new Node(destination,flightCost);
        Node curr = head;
        while(curr.getNext()!=null)
        {
            curr = curr.getNext();
        }
        curr.setNext(newNode);
        numItems++;
        
    }
    //inserts a departure node into the linked list
    public void DepartInsert(String departure)
    {
        Node newNode = new Node(departure);
        head = newNode;
        numItems++;
    }
}
