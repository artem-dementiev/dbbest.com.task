package dao;

import entities.Route;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface which define methods for deal with Route t.e. set of points, between which you
 * need to find the route
 * @author Artem Dementiev
 */
public interface RouteDAO {
    //create
    boolean addRoute(Route route) throws SQLException;
    boolean addListOfRoutes(List<Route> routes) throws SQLException;
    //read
    List<Route> getAllRoute() throws SQLException;

    List<Route> getRouteByID(int id) throws SQLException;
    //update
    void updateRoute(Route route) throws SQLException;

    //delete
    boolean removeRoute(int id) throws SQLException;
    boolean removeListOfRoutes(List<Route> routes) throws SQLException;
}
