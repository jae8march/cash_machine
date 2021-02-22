package com.company.app.dao.interfaceDao;
//TODO WORK
import com.company.app.dao.entity.Check;
import java.util.List;

public interface ICheckDAO extends IDAO<Check> {
    List<Check> findAllChecksByUser(String userLogin);
    List<Check> findAll();
    List<Check> findAllNewChecks();
    boolean closedCheck(long id);
    boolean changeStatus(long id, String sql);
    List<Check> mapperList(String sql);
}
