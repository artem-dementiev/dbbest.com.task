import Main.Main;
import Utils.Util;
import entities.Node;
import entities.Route;
import org.junit.Test;
import service.NodeService;
import service.RouteService;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class UtilTest {
    //path to correct files
    private static final String csvNodesFile = Main.getCsvNodesFile();
    private static final String csvRouteFile = Main.getCsvRouteFile();
    private static final String csvResultFile = Main.getCsvResultFile();

    //file paths to dirty data
    private static final String csvNodesFileIncorrectTestData1 = "src/test/resources/pipeline_system_incorrect_test_data1.csv";
    private static final String csvNodesFileIncorrectTestData2 = "src/test/resources/pipeline_system_incorrect_test_data2.csv";
    private static final String csvNodesFileIncorrectTestData3 = "src/test/resources/pipeline_system_incorrect_test_data3.csv";

    //file paths for tests without errors in files
    private static final String csvSetOfPointsEmptyToDelete = "src/test/resources/SetOfPointsEmptyToDelete.csv";
    private static final String csvSetOfPointsNonEmptyToDelete = "src/test/resources/SetOfPointsNonEmptyToDelete.csv";

    private static final String csvSetOfPointsUploadEmpty="src/test/resources/SetOfPointsUploadEmpty.csv";
    private static final String csvSetOfPointsUploadNonEmpty="src/test/resources/SetOfPointsUploadNonEmpty.csv";

    private static final String csvPipelineSystemUploadEmpty = "src/test/resources/PipelineSystemUploadEmpty.csv";
    private static final String csvPipelineSystemUploadNonEmpty = "src/test/resources/PipelineSystemUploadNonEmpty.csv";

    private static final String csvPipelineSystemEmptyToDelete = "src/test/resources/PipelineSystemEmptyToDelete.csv";
    private static final String csvPipelineSystemNonEmptyToDelete = "src/test/resources/PipelineSystemNonEmptyToDelete.csv";


    private final Util util = new Util();

    @Test
    public void util(){
        Util util = new Util();
        assertNotNull(util);
    }
    //testing function util.getFromDBAndFindResult(csvResultFile)
    @Test
    public void getFromCorrectLocalFilesAndFindResultTest(){
        boolean actual= util.getFromLocalFilesAndFindResult(csvNodesFile, csvRouteFile, csvResultFile);
        assertTrue("getting correct data from local files and find min distance", actual);
    }
    @Test(expected = IllegalArgumentException.class)
    public void getFromIncorrect1LocalFilesAndFindResultTest(){
        boolean actual= util.getFromLocalFilesAndFindResult(csvNodesFileIncorrectTestData1, csvRouteFile, csvResultFile);
        assertFalse("getting incorrect data from local files and find min distance", actual);
    }
    @Test
    public void getFromIncorrect2LocalFilesAndFindResultTest(){
        boolean actual= util.getFromLocalFilesAndFindResult(csvNodesFileIncorrectTestData2, csvRouteFile, csvResultFile);
        assertTrue("getting incorrect data from local files and find min distance", actual);
    }
    @Test(expected = IllegalArgumentException.class)
    public void getFromIncorrect3LocalFilesAndFindResultTest(){
        boolean actual= util.getFromLocalFilesAndFindResult(csvNodesFileIncorrectTestData3, csvRouteFile, csvResultFile);
        assertFalse("getting incorrect data from local files and find min distance", actual);
    }

    //testing function util.getFromDBAndFindResultTest(csvResultFile);
    @Test
    public void getFromDBAndFindResultTest(){
        boolean actual=util.getFromDBAndFindResult(csvResultFile);
        assertTrue("Getting data from local files and creating result",actual);
    }

    ////////////////////////////////////////
                                            //testing function util.getPipelineSystemFromDBAndDisplay();
    //don't have ideas how to test these      //testing function util.getSetOfPointsFromDBAndDisplay();
                                            //testing function util.getResultFromFileAndDisplay(csvResultFile);
    ////////////////////////////////////////

    //testing function util.uploadPipelineSystemToDB(csvNodesFile);
    @Test
    public void uploadPipelineSystemEmptyToDBTest(){
        boolean actual=util.uploadPipelineSystemToDB(csvPipelineSystemUploadEmpty);
        assertTrue(actual);
    }
    @Test
    public void uploadPipelineSystemNonEmptyToDBTest(){
        boolean actual=util.uploadPipelineSystemToDB(csvPipelineSystemUploadNonEmpty);
        assertTrue(actual);
    }
    //testing function util.util.uploadSetOfPointsToDB(csvRouteFile);
    @Test
    public void uploadSetOfPointsEmptyToDBTest(){
        boolean actual=util.uploadSetOfPointsToDB(csvSetOfPointsUploadEmpty);
        assertTrue(actual);
    }
    @Test
    public void uploadSetOfPointsNonEmptyToDBTest(){
        boolean actual=util.uploadSetOfPointsToDB(csvSetOfPointsUploadNonEmpty);
        assertTrue(actual);
    }
    //testing function util.deletePipelineSystemToDB(csvNodesFileToDelete);
    @Test
    public void deletePipelineSystemEmptyToDBTest(){
        boolean actual=util.deletePipelineSystemToDB(csvPipelineSystemEmptyToDelete);
        assertFalse(actual);
    }
    @Test
    public void deletePipelineSystemNonEmptyToDBTest(){
        boolean actual=util.deletePipelineSystemToDB(csvPipelineSystemNonEmptyToDelete);
        assertFalse(actual);
    }
    //testing function util.deleteSetOfPointsToDB(csvRouteFileToDelete);
    @Test
    public void deleteSetOfPointsEmptyToDBTest(){
        boolean actual=util.deleteSetOfPointsToDB(csvSetOfPointsEmptyToDelete);
        assertFalse(actual);
    }
    @Test
    public void deleteSetOfPointsNonEmptyToDBTest(){
        boolean actual=util.deleteSetOfPointsToDB(csvSetOfPointsNonEmptyToDelete);
        assertFalse(actual);
    }

    //testing function util.deleteAllNodesInDB();
    @Test
    public void deleteAllNodesInDBTest(){
        boolean actual=util.deleteAllNodesInDB();
        assertTrue(actual);
    }

    //testing function util.deleteAllRoutesInDB();
    @Test
    public void deleteAllRoutesInDBTest(){
        boolean actual=util.deleteAllRoutesInDB();
        assertTrue(actual);
    }

    //testing function nodeService.addNode(Node node)
    @Test
    public void addNodeTest(){
        assertTrue(new NodeService().addNode(new Node(1000000, 1, 1, 10)));
    }
    //testing function nodeService.removeNode(int id)
    @Test
    public void removeNodeTest(){
        assertTrue(new NodeService().removeNode(1000000));
    }

    //testing function routeService.addRoute(Node node)
    @Test
    public void addRouteTest(){
        assertTrue(new RouteService().addRoute(new Route(1000000, 1, 1)));
    }
    //testing function routeService.removeRoute(int id)
    @Test
    public void removeRouteTest(){
        assertTrue(new RouteService().removeRoute(1000000));
    }


}
