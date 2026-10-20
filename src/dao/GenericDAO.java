
package dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

    void add(T object) throws SQLException;

    List<T> getAll() throws SQLException;
}
