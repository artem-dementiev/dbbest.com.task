package Dijkstra;

import Utils.Util;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Scanner;

/**
 * Task
 * Dijkstra's algorithm
 * @author Artem Dementiev
 */
public class Main {

    //logger
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    //file paths
    static String csvNodesFile = "src/main/resources/pipeline_system.csv";
    static String csvRouteFile = "src/main/resources/set_of_points.csv";

    static String csvNodesFileToDelete = "src/main/resources/pipeline_system_to_delete.csv";
    static String csvRouteFileToDelete = "src/main/resources/set_of_points_to_delete.csv";

    static String csvResultFile = "src/main/resources/result.csv";
    static String log4jProperties = "src/main/resources/log4j.properties";

    public static String getCsvNodesFile() {
        return csvNodesFile;
    }

    public static String getCsvRouteFile() {
        return csvRouteFile;
    }

    public static String getCsvResultFile() {
        return csvResultFile;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        PropertyConfigurator.configure(log4jProperties);
        Util util = new Util();
        //menu
        Scanner in = new Scanner(System.in);
        for (;;) {
            switch (menu(in)) {
                case 1:
                    LOGGER.info("Getting data from DB and creating result");
                    util.getFromDBAndFindResult(csvResultFile);
                    break;
                case 2:
                    LOGGER.info("Getting data from local files and creating result");
                    util.getFromLocalFilesAndFindResult(csvNodesFile, csvRouteFile, csvResultFile);
                    break;
                case 3:
                    LOGGER.info("Getting 'pipeline_system.csv' from DB and displaying it on the screen");
                    util.getPipelineSystemFromDBAndDisplay();
                    break;
                case 4:
                    LOGGER.info("Getting 'set_of_points.csv' from DB and displaying it on the screen");
                    util.getSetOfPointsFromDBAndDisplay();
                    break;
                case 5:
                    LOGGER.info("Uploading 'pipeline_system.csv' to DB...");
                    util.uploadPipelineSystemToDB(csvNodesFile);
                    break;
                case 6:
                    LOGGER.info("Uploading 'set_of_points.csv' to DB...");
                    util.uploadSetOfPointsToDB(csvRouteFile);
                    break;
                case 7:
                    LOGGER.info("Delete nodes in DB which are specified in the file 'pipeline_system_to_delete.csv'");
                    util.deletePipelineSystemToDB(csvNodesFileToDelete);
                    break;
                case 8:
                    LOGGER.info("Delete routes in DB which are specified in the file 'set_of_points_to_delete.csv'");
                    util.deleteSetOfPointsToDB(csvRouteFileToDelete);
                    break;
                case 9:
                    LOGGER.info("Getting result file and display or re-write data to another file");
                    util.getResultFromFileAndDisplay(csvResultFile);
                    break;
                case 10:
                    LOGGER.info("You are exited from the application");
                    in.close();
                    System.exit(0);
                    break;
                case 0:
                    LOGGER.info("Wrong choice!");
                    break;
                default:
                    LOGGER.info("Wrong input! Try again!");
                    break;
            }
        }
    }

    /**
     * Application menu
     * @param in input stream
     * @return index of the selected action
     * @author Artem Dementiev
     */
    public static int menu(Scanner in)
    {
        System.out.println("-------------------------\nChoose from these choices\n-------------------------");
        System.out.println("1 - get data from DB and create result");
        System.out.println("2 - get data from local files and create result");
        System.out.println("3 - get 'pipeline_system.csv' from the database and display it on the screen or write to file");
        System.out.println("4 - get 'set_of_points.csv' from the database and display it on the screen or write to file");
        System.out.println("5 - upload file 'pipeline_system.csv' to DB");
        System.out.println("6 - upload file 'set_of_points.csv' to DB");
        System.out.println("7 - delete nodes in DB which are specified in the file 'pipeline_system_to_delete.csv'");
        System.out.println("8 - delete routes in DB which are specified in the file 'set_of_points_to_delete.csv'");
        System.out.println("9 - get result file and display or re-write data to another file");
        System.out.println("10 - exit");
        System.out.print("Choose: ");
        int choice=0;
        if(in.hasNextInt()){
            choice = in.nextInt();
        } else {
            in.next();
            System.out.println("Wrong input! Try again!");
        }

        return (choice > 0 && choice < 11) ? choice: 0;
    }

}
