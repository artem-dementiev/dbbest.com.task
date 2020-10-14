package Utils;

import entities.Node;
import entities.OutputFormat;
import entities.ResultItem;
import entities.Route;
import org.apache.log4j.Logger;
import service.NodeService;
import service.RouteService;

import java.io.*;
import java.util.*;

/**
 * Supporting functions
 */
public class Util {
    private static final Logger LOGGER = Logger.getLogger(Util.class);

    /**
     * Delete nodes in DB which are specified in the file 'pipeline_system_to_delete.csv'
     * @param csvNodesFileToDelete path to file which consists nodes which must be deleted
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean deletePipelineSystemToDB(String csvNodesFileToDelete) {
        List<Node> list = readNodeDataFromFile(csvNodesFileToDelete);
        NodeService dao = new NodeService();
        return dao.removeListOfNodes(list);
    }

    /**
     * Delete routes in DB which are specified in the file 'set_of_points_to_delete.csv'
     * @param csvRouteFileToDelete path to file which consists routes which must be deleted
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean deleteSetOfPointsToDB(String csvRouteFileToDelete) {
        List<Route> list = readRouteDataFromFile(csvRouteFileToDelete);
        RouteService dao = new RouteService();
        return dao.removeListOfRoutes(list);
    }

    /**
     * get SetOfPoints.csv From DB And Display it
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean getSetOfPointsFromDBAndDisplay() {
        RouteService routeService = new RouteService();
        List<Route> routes = new LinkedList<>();
        routes = routeService.getAllRoute();
        return displayData(routes);
    }

    /**
     * get PipelineSystem.csv From DB And Display it
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean getPipelineSystemFromDBAndDisplay() {
        NodeService nodeService = new NodeService();
        List<Node> nodes = new LinkedList<>();
        nodes = nodeService.getAllNode();
        return displayData(nodes);
    }

    /**
     * formatted output to console
     * @param list list of objects which implements OutputFormat interface
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    private boolean displayData(List<? extends OutputFormat> list){
        if (list.isEmpty()){
            System.out.println("No data available.");
            return false;
        }else {
            System.out.println("Choose from these choices");
            System.out.println("1 - display on screen");
            System.out.println("2 - write to file");
            System.out.println("3 - return to main menu");
            System.out.print("Choose:");
            int choice;
            Scanner in = new Scanner(System.in);
            while (true) {
                if (in.hasNextInt()) {
                    choice = in.nextInt();
                    if (choice == 1) {
                        StringBuilder output = new StringBuilder("Data:\n");
                        for (OutputFormat item : list) {
                            output.append(item.toConsole());
                        }
                        System.out.println(output.toString());
                        break;
                    } else if (choice == 2) {
                        System.out.print("Enter the name of file: ");
                        String destinationPath = "src/main/resources/userFiles/";
                        String fileName=in.next();
                        try (PrintWriter pw = new PrintWriter(new FileWriter(destinationPath+fileName, false))) {
                            for (OutputFormat item : list) {
                                pw.println(item.toFile());
                                LOGGER.info("Successfully wrote data to the file.");
                            }
                        } catch (IOException e) {
                            LOGGER.error("An error occurred while writing source data.");
                            e.printStackTrace();
                        }
                        break;
                    } else if(choice == 3){
                        break;
                    } else {
                        System.out.print("Entered incorrect option!");
                    }
                } else {
                    in.next();
                    System.out.print("Entered incorrect option! Choose again:");
                }
            }
            return true;
        }
    }

    /**
     * Upload the file that describes the water pipeline system into an H2 database
     * @param csvNodesFile path to the file that describes the water pipeline system
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean uploadPipelineSystemToDB(String csvNodesFile) {
        List<Node> nodes = readNodeDataFromFile(csvNodesFile);
        NodeService nodeService = new NodeService();
        return nodeService.addListOfNodes(nodes);
    }

    /**
     * Upload the file with a set of points into an H2 database
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean uploadSetOfPointsToDB(String csvRouteFile) {
        List<Route> routes = readRouteDataFromFile(csvRouteFile);
        RouteService routeService = new RouteService();
        return routeService.addListOfRoutes(routes);
    }

    /**
     * Getting data from DB and creating result
     * @param csvResultFile path to the file with the result
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean getFromDBAndFindResult(String csvResultFile) {
        NodeService nodeService = new NodeService();
        RouteService routeService = new RouteService();
        //reading files from DB
        List<Node> nodes = nodeService.getAllNode();
        List<Route> routes = routeService.getAllRoute();
//      System.out.println(routes);
//      System.out.println(nodes);
        return createPriceMatrixAndGetResult(nodes,routes, csvResultFile);
    }

    /**
     * Getting data from local files and creating result
     * @param csvNodesFile path to the file that describes the water pipeline system
     * @param csvRouteFile path to the file  with a set of points, between which you
     * need to find the route
     * @param csvResultFile path to the file with the result
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean getFromLocalFilesAndFindResult(String csvNodesFile,String csvRouteFile, String csvResultFile) {
        //reading files from local storage
        List<Node> nodes = readNodeDataFromFile(csvNodesFile);
        List<Route> routes = readRouteDataFromFile(csvRouteFile);
        return createPriceMatrixAndGetResult(nodes,routes, csvResultFile);
    }

    /**
     * Main function which prepare result
     * @param nodes list of nodes
     * @param routes list of routes
     * @param csvResultFile path to the file with the result
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    private boolean createPriceMatrixAndGetResult(List<Node> nodes, List<Route> routes, String csvResultFile){
        if(nodes ==null || routes == null || routes.isEmpty()||nodes.isEmpty()){
            System.out.println("No data available. Check for data in file or database!");
            LOGGER.info("No data available. Check for data in file or database!");
            return false;
        } else {
            //find amount of nodes
            int amountOfXatNodes = Collections.max(nodes, Comparator.comparing(Node::getIdX)).getIdX();
            int amountOfYatNodes = Collections.max(nodes, Comparator.comparing(Node::getIdY)).getIdY();
            int amountOfXatRoutes = Collections.max(routes, Comparator.comparing(Route::getIdX)).getIdX();
            int amountOfYatRoutes = Collections.max(routes, Comparator.comparing(Route::getIdY)).getIdY();
            int nodeLargest = Math.max(amountOfXatNodes, amountOfYatNodes);
            int routeLargest = Math.max(amountOfXatRoutes, amountOfYatRoutes);
            int n = Math.max(nodeLargest, routeLargest) + 1; // number of nodes
            int[][] cost = new int[n][n]; //price matrix
            //initialization of the price matrix
            for (Node item : nodes) {
                cost[item.getIdX()][item.getIdY()] = item.getLength();
                cost[item.getIdY()][item.getIdX()] = item.getLength();
//            System.out.printf("[%d][%d]",item.getIdX(),item.getIdY());
//            System.out.println(cost[item.getIdX()][item.getIdY()]);
            }

            //The price matrix must be symmetrical
            if (!isSymmetric(cost)) throw new RuntimeException("The array of costs must be symmetric!");

            //writing result to file
            // false - re-write file
            try (PrintWriter pw = new PrintWriter(new FileWriter(csvResultFile, false))) {
                for (Route item : routes) {
                    //The algorithm itself
                    int minDistance = Dijkstra(cost, n, item.getIdX(), item.getIdY());
                    //writing to file
                    if (minDistance <= 0) {
                        pw.println("FALSE;");
                    } else {
                        pw.printf("TRUE;%d\n", minDistance);
                    }
                    LOGGER.info("Successfully wrote to the file.");
                }
            } catch (IOException e) {
                LOGGER.error("An error occurred while writing data to 'result.csv'.");
                e.printStackTrace();
            }
            return true;
        }
    }

    /**
     * Reading a CSV file that describes the water pipeline system.
     * @param csvNodesFile path to the file that describes the water pipeline system
     * @return List of nodes
     * @author Artem Dementiev
     */
    private static List<Node> readNodeDataFromFile(String csvNodesFile){
        LinkedList<Node> l = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(csvNodesFile))){
            String line;
            while ((line = br.readLine()) != null) {
                //if the line variable has no data then re-iterate the loop to move on the next line
                if(line.length()<=0)continue;
                // use semicolon as separator
                String[] data = line.split(";");
                //.replaceAll("[^\\d.]", ""); if file has value 'asda232asds' it converts to '232'
                if(data.length==3){
                    int idx = Integer.parseInt(data[0].replaceAll("[^\\d.]", ""));
                    int idy = Integer.parseInt(data[1].replaceAll("[^\\d.]", ""));
                    int length = Integer.parseInt(data[2].replaceAll("[^\\d.]", ""));
                    l.add(new Node(idx,idy,length));
                }else {
                    LOGGER.error(String.format("File '%s' has incorrect data format",csvNodesFile));
                    throw new IllegalArgumentException("Incorrect data format in file!");
                }

            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return l;
    }

    /**
     * Reading a CSV file with a set of points, between which you need to find the route.
     * @param csvRouteFile path to the file  with a set of points, between which you
     * @return list of routes
     * @author Artem Dementiev
     */
    private static List<Route> readRouteDataFromFile(String csvRouteFile){
        LinkedList<Route> l = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(csvRouteFile))){
            String line;
            while ((line = br.readLine()) != null) {
                //if the line variable has no data then re-iterate the loop to move on the next line
                if(line.length()<=0)continue;
                // use comma as separator
                String[] data = line.split(";");
                int idx = Integer.parseInt(data[0]);
                int idy = Integer.parseInt(data[1]);
                l.add(new Route(0, idx,idy));
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return l;
    }

    /**
     * A function that checks if the array is symmetric about the main diagonal.
     * @param a checked array
     * @return comparison result
     * @author Artem Dementiev
     */
    private boolean isSymmetric(int[][] a){
        for (int i = 0; i < a.length; i++){
            for (int j = 0; j < a.length; j++){
                if (a[i][j] != a[j][i]) return false;
            }
        }
        return true;
    }

    /**
     * Dijkstra's algorithm
     * @param graphMatrix price matrix
     * @param V number of vertices
     * @param start start from this top
     * @param end end of route
     * @return shortest distances from the starting vertex to end vertex
     * @author Artem Dementiev
     */
    private int Dijkstra(int[][] graphMatrix, int V, int start, int end){
        int[] distance;
        boolean[] visited;
        int count, index=0;
        int i, u;
        distance = new int [V];
        visited = new boolean [V];
        for (i = 0; i < V; i++){
            distance[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        distance[start] = 0;
        for (count = 0; count < V; count++){
            int min = Integer.MAX_VALUE;
            while(true){
                for (i = 0; i < V; i++){
                    if (!visited[i] && distance[i] <= min) //if we have not been at this vertex before and the cost is less than infinity
                    {
                        min = distance[i]; //the cost value for the vertex changes
                        index = i; //indicates the index of the vertex from which we came
                    }
                }
                u = index;
                visited[u] = true; //have visited vertex U
                for (i = 0; i < V; i++){
                    // if we have not been on current vertex and the cost from vertex to next vertex exists
                    // and it doesnt equal infinity
                    // and the previous edge + the cost of the current edge is less than the cost of the vertex,
                    // then we define a new vertex.
                    if (!visited[i] && graphMatrix[u][i] != 0 && distance[u] != Integer.MAX_VALUE && distance[u] + graphMatrix[u][i] < distance[i]){
                        distance[i] = distance[u] + graphMatrix[u][i];
                    }
                }
                break;
            }
        }
        return distance[end]==Integer.MAX_VALUE ? -1 : distance[end];
    }

    /**
     * get result data from file and display in console
     * @param csvResultFile path to the file with the result
     * @return boolean value indicating the result of the function execution (success, failure)
     * @author Artem Dementiev
     */
    public boolean getResultFromFileAndDisplay(String csvResultFile) {
        LinkedList<ResultItem> l = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(csvResultFile))){
            String line;
            while ((line = br.readLine()) != null) {
                //if the line variable has no data then re-iterate the loop to move on the next line
                if(line.length()<=0)continue;
                // use comma as separator
                String[] data = line.split(";");
//                System.out.println(Arrays.toString(data));
                boolean isExist = Boolean.parseBoolean(data[0]);
                if(!isExist){
                    l.add(new ResultItem(isExist,-1));
                }else{
                    int distance = Integer.parseInt(data[1]);
                    l.add(new ResultItem(isExist,distance));
                }
            }
            displayData(l);
            return true;
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return false;
    }
}
