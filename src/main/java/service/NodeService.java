package service;

import dao.NodeDAO;
import entities.Node;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connect.DBConnection.initializeDatabase;

/**
 * Implementation of NodeDAO level
 * @author Artem Dementiev
 */
public class NodeService implements NodeDAO {

    private static final Logger LOGGER = Logger.getLogger(NodeService.class);
    private static final String PREPARED_SQL = "INSERT INTO Node(idX, idY, length) values (?, ?, ?)";
    @Override
    public boolean addNode(Node node) {
        try(Connection conn = initializeDatabase()) {
            PreparedStatement ps = conn.prepareStatement(PREPARED_SQL);
            ps.setInt(1, node.getIdX());
            ps.setInt(2, node.getIdY());
            ps.setInt(3, node.getLength());
            ps.executeUpdate();
            ps.close();
            LOGGER.info(String.format("Added %s to DB",node));
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }
    @Override
    public boolean addListOfNodes(List<Node> nodes){
        try(Connection conn = initializeDatabase()) {
            for(Node node : nodes){
                PreparedStatement ps = conn.prepareStatement(PREPARED_SQL);
                ps.setInt(1, node.getIdX());
                ps.setInt(2, node.getIdY());
                ps.setInt(3, node.getLength());
                ps.executeUpdate();
                ps.close();
                LOGGER.info(String.format("Added %s to DB",node));
            }
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public List<Node> getAllNode() {
        List<Node> l = new ArrayList<>();
        try(Connection conn = initializeDatabase()) {
            Statement st = conn.createStatement();
            boolean hasRS = st.execute("Select * FROM NODE");
            if(hasRS){
                ResultSet rs = st.getResultSet();
                while (rs.next()){
                    int id = rs.getInt("id");
                    int idx = rs.getInt("idX");
                    int idy = rs.getInt("idY");
                    int length = rs.getInt("length");
                    l.add(new Node(id, idx,idy,length));
                }
                rs.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        LOGGER.info("Got list of nodes from DB");
        return l;
    }

    @Override
    public List<Node> getNodeByID(int id) {
        return null;
    }

    @Override
    public void updateNode(Node node) {

    }

    @Override
    public boolean removeNode(int id) {
        String sql = "DELETE FROM Node where id=?";
        try (Connection conn = initializeDatabase();PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            LOGGER.info(String.format("Node with id=%d deleted in DB", id));
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean removeListOfNodes(List<Node> nodes, boolean isCallFromCleanUpMethod) {
        int count =0;
        String sql = !isCallFromCleanUpMethod ? "DELETE FROM Node WHERE idX=? AND idY=? AND length=?" : "DELETE FROM Node where id=?";
        try (Connection conn = initializeDatabase();PreparedStatement ps = conn.prepareStatement(sql)) {
            for(Node item : nodes){
                if(!isCallFromCleanUpMethod){
                    ps.setInt(1, item.getIdX());
                    ps.setInt(2, item.getIdY());
                    ps.setInt(3, item.getLength());
                    LOGGER.info(String.format("Node with idX=%d and idY=%d and length=%d deleted in DB", item.getIdX(), item.getIdY(), item.getLength()));
                }else {
                    ps.setInt(1, item.getId());
                    LOGGER.info(String.format("Node with id=%d deleted in DB", item.getId()));
                }
                count+=ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return count != 0;
    }

    @Override
    public boolean cleanUpNodeTable() {
        LOGGER.info("Method 'cleanUpNodeTable' started");
        NodeService nodeService = new NodeService();
        return nodeService.removeListOfNodes(nodeService.getAllNode(), true);
    }
}
