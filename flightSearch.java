/**Nico Dennis
 * Last edited May 6th, 2019
 * 
 * This class takes in the city file and flight file and initializes a digraph with the given input
 * Then it takes input from the user interface and finds a flight route to get to the desired destination
 * 
*/
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
public class flightSearch
{
    private static linkedList[] mainTable;
    public static void main(String[] args)
    throws ArrayIndexOutOfBoundsException
    {
        try{
            String cityFile = args[0];
            String flightFile = args[1];
            int tableSize = 0;
            BufferedReader reader = new BufferedReader(new FileReader(cityFile));
            while (reader.readLine() != null) tableSize++;
            reader.close();
            boolean check = true;
            while(check)
            {
                mainTable = initializeTable(tableSize,cityFile,flightFile);
                Scanner userInput = new Scanner(System.in);
                System.out.println("from what city would you like to depart");
                String departure = userInput.nextLine();
                if(searchDepartures(mainTable, departure))
                {
                    
                    System.out.println("Which city would you like to go to");
                    String arrival = userInput.nextLine();
                    System.out.println("Searching for flights from: " + departure + " to: " + arrival + "...");
                    searchDestinations(mainTable,departure,arrival);
                    searchMultiple(mainTable, departure, arrival);
                    Scanner cont = new Scanner(System.in);
                    System.out.println("Would you like to run another search? (y/n)");
                    String mayContinue = cont.nextLine();
                    if(mayContinue.charAt(0) == 'n')
                    {
                        check = false;
                    }
                }
                else
                {
                    System.out.println("Sorry, that departure is not serviced.");
                }
            }
        }
        catch(IOException e)
            {
                System.out.println("sorry error whoops");
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Please run this program with the following format: java flightSearch cityFile flightFile");
                System.exit(0);
            }
    }
    public static linkedList[] initializeTable(int tableSize, String cityFile, String flightFile)
    {
        
        linkedList[] allFlights = new linkedList[tableSize];
        try{
            //initialize array linked list with cities from cityFile
             Scanner br = new Scanner(new File(cityFile));
             br.useDelimiter("\n");
             int j = 0;
             while(br.hasNext())
             {
                 linkedList newList = new linkedList();
                 newList.DepartInsert(br.next());
                 allFlights[j] = newList;
                 j++;
             }
             br.close();
     
     
             //read through flightFile and print out line by line
             Scanner bz = new Scanner(new File(flightFile));
             bz.useDelimiter("\n");
             
             String flight;
             while(bz.hasNext())
             {
                 int i = 0;
                 flight = bz.next();
                 StringTokenizer stringTokens = new StringTokenizer(flight, ",");
                 String temp = stringTokens.nextToken();
                 String tempDest = stringTokens.nextToken();
                 int tempCost = Integer.parseInt(stringTokens.nextToken());
                 for(int z = 0; z < allFlights.length; z++)
                 {
                     if(allFlights[z].RetrieveDeparture().compareTo(temp) == 0)
                     {
                         allFlights[z].DestInsert(tempDest, tempCost);
                     }
                 }
             }
             bz.close(); 
         }
         catch(FileNotFoundException e)
         {
             System.out.println("file not found");
         }
         return allFlights;
    }

        //search algorithm to find given departure
    public static boolean searchDepartures(linkedList[] allFlights, String departure)
    {
        for(int i=0; i<allFlights.length; i++)
        {
            if(allFlights[i].RetrieveDeparture().compareTo(departure) == 0)
            {
                return true;
            }
        }
        return false;
    }
    //searches destinations to see if there is a flight from the given departure to the destination
    public static Node searchDestinations(linkedList[] allFlights, String departure, String destination) throws NullPointerException
    {
        try{
        String temp;
        int otherTemp = 0;
        for(int i=0; i<allFlights.length; i++)
        {
            if(allFlights[i].RetrieveDeparture().compareTo(departure) == 0)
            {
                temp = allFlights[i].RetrieveDeparture();
                otherTemp=i;
            }
            
        }
        Node curr = allFlights[otherTemp].getHead();
        while(curr.getNext().getDestination().compareTo(destination) != 0)
        {
            curr = curr.getNext();    
        }
        if(curr.getNext() == null)
        {
            System.exit(0);
        }
        curr = curr.getNext();
        return curr;
    }
    catch(NullPointerException e)
    {
        System.out.println("No Direct Flights... ");
    }
    return null;
    }

    //sets the node of the given city to visited
    public static void setVisited(linkedList[] allFlights, String city)
    {
        for(int i = 0; i < allFlights.length; i++)
        {
            if(allFlights[i].RetrieveDeparture().compareTo(city) == 0)
            {
                allFlights[i].getHead().setChecked(true);
                break;
            }
        }
    }
    //gets the next unvisited destination
    public static Node getUnvisited(linkedList[] allFlights, StackRefBased stack)
    {
        Node top = stack.peek();
        int i = 0;
        while(allFlights[i].getHead().getDeparture().compareTo(top.getDeparture()) != 0 )
        {
            i++;
        }
        Node curr = allFlights[i].getHead();
        while (curr.getNext() != null)
        {
            curr=curr.getNext();
            if(curr.getChecked() == false)
            {
                return curr;
            }
        }
        
        return null;
    }
    //core method that runs the backtracking algorithm to find a flight path between the two given cities.
    public static void searchMultiple(linkedList[] allFlights, String departure, String destination) 
    throws EmptyStackException
    {
        try{
        StackRefBased path = new StackRefBased();
        Node depart = new Node(departure);
        setVisited(allFlights, departure);
        path.pushInitial(depart);
        Node temp;
        int h = 1;
        while(path.peek().getDeparture().compareTo(destination) != 0)
        {
            if(getUnvisited(allFlights, path) == null)
            {
                temp = path.pop();
                h++;
            }
            else{
                //get next reachable city from top of stack
                Node nextVisit = new Node(getUnvisited(allFlights, path).getDestination());
                getUnvisited(allFlights, path).setChecked(true);
                path.pushDepart(nextVisit);
                setVisited(allFlights,nextVisit.getDeparture());

            }
        }
        StackRefBased tempStack = new StackRefBased();
        while(!path.StackIsEmpty())
        {
            tempStack.pushDepart(path.pop());
        }
        int totalCost = 0;
        while(tempStack.peek().getDeparture().compareTo(destination) != 0)
        {
            Node prevNode = tempStack.peek().getNext();
            Node tempNode = tempStack.pop();
            Node nico = searchDestinations(mainTable,tempNode.getDeparture(),prevNode.getDeparture());
            System.out.println("The cost to fly from: " + tempNode.getDeparture() + " to: " + nico.getDestination() + " is: " + nico.getCost());
            totalCost = totalCost + nico.getCost();
        }
        System.out.println("The total cost of the trip is: " + totalCost + " dollars");
    }
        catch(EmptyStackException e)
        {
            System.out.println("sorry we don't serve that route");
        }
        catch(NullPointerException e)
        {
            System.out.println("sorry we don't serve that route");
        }
    }

}