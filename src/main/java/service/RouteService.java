package service;

import dao.RouteDAO;
import entities.Route;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connect.DBConnection.initializeDatabase;
/**
 * Implementation of RouteDAO level
 * @author Artem Dementiev
 */
public class RouteService implements RouteDAO {

    private static final Logger LOGGER = Logger.getLogger(NodeService.class);
    @Override
    public boolean addRoute(Route route) {
        try(Connection conn = initializeDatabase()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ROUTE(idX, idY) values (?, ?)");
            ps.setInt(1, route.getIdX());
            ps.setInt(2, route.getIdY());
            ps.executeUpdate();
            ps.close();
            LOGGER.info(String.format("Added %s to DB",route));
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }
    @Override
    public boolean addListOfRoutes(List<Route> routes){
        try(Connection conn = initializeDatabase()) {
            for(Route route : routes){
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Route(idX, idY) values (?, ?)");
                ps.setInt(1, route.getIdX());
                ps.setInt(2, route.getIdY());
                ps.executeUpdate();
                ps.close();
                LOGGER.info(String.format("Added %s to DB",route));
            }
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public List<Route> getAllRoute() {
        List<Route> l = new ArrayList<>();
        try(Connection conn = initializeDatabase()) {
            Statement st = conn.createStatement();
            boolean hasRS = st.execute("Select * FROM ROUTE");
            if(hasRS){
                ResultSet rs = st.getResultSet();
                while (rs.next()){
                    int id = rs.getInt("id");
                    int idx = rs.getInt("idX");
                    int idy = rs.getInt("idY");
                    l.add(new Route(id, idx,idy));
                }
                rs.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        LOGGER.info("Got list of routes from DB");
        return l;
    }

    @Override
    public List<Route> getRouteByID(int id) {
        return null;
    }

    @Override
    public void updateRoute(Route route) {

    }

    @Override
    public boolean removeRoute(int id) {
        String sql = "DELETE FROM Route where id=?";
        try (Connection conn = initializeDatabase();PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            LOGGER.info(String.format("Route with id=%d deleted in DB", id));
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean removeListOfRoutes(List<Route> routes, boolean isCallFromCleanUpMethod) {
        int count =0;
        String sql = !isCallFromCleanUpMethod ? "DELETE FROM Route WHERE idX=? AND idY=?" : "DELETE FROM Route where id=?";
        try (Connection conn = initializeDatabase();PreparedStatement ps = conn.prepareStatement(sql)) {
            for(Route item : routes){
                if(!isCallFromCleanUpMethod){
                    ps.setInt(1, item.getIdX());
                    ps.setInt(2, item.getIdY());
                    LOGGER.info(String.format("Route with idX=%d and idY=%d deleted in DB", item.getIdX(), item.getIdY()));
                }else {
                    ps.setInt(1, item.getId());
                    LOGGER.info(String.format("Route with id=%d deleted in DB", item.getId()));
                }
                count+=ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return count != 0;
    }

    @Override
    public boolean cleanUpRouteTable() {
        LOGGER.info("Method 'cleanUpRouteTable' started");
        RouteService routeService = new RouteService();
        return routeService.removeListOfRoutes(routeService.getAllRoute(), true);
    }
}
