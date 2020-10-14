package dao;

import entities.Node;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface which define methods for deal with Node t.e. pipeline system
 * @author Artem Dementiev
 */
public interface NodeDAO {
    //create
    boolean addNode(Node node) throws SQLException;
    boolean addListOfNodes(List<Node> nodes) throws SQLException;
    //read
    List<Node> getAllNode() throws SQLException;

    List<Node> getNodeByID(int id) throws SQLException;
    //update
    void updateNode(Node node) throws SQLException;

    //delete
    boolean removeNode(int id) throws SQLException;
    boolean removeListOfNodes(List<Node> nodes) throws SQLException;
}
