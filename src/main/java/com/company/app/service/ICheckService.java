package com.company.app.service;
//TODO WORK
import com.company.app.dao.entity.Check;
import java.util.List;

/**
 * Interface for Check service.
 */
public interface ICheckService extends IService<Check>{
    List<Check> getAllNewChecks();

    List<Check> getAllFinishedChecks();

    List<Check> getAllChecksByUser(String userLogin);

//    Check getCheckById(int id);
}
