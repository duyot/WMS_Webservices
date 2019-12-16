package com.wms.base;

import com.wms.dto.Condition;
import java.util.List;
import org.hibernate.Session;

/**
 * Created by duyot on 8/29/2016.
 */
public interface BaseBusinessInterface<T extends BaseDTO> {
    Long getSequence(String sequenceName);

    //------------------------------------------------------------------------------------------------------------------
    String getSysDate(String pattern);

    String getSysDate();

    //------------------------------------------------------------------------------------------------------------------
    String deleteByObject(T obj);

    String deleteById(long id);

    String deleteByObjectSession(T obj, Session session);

    String deleteByCondition(List<Condition> lstCondition);

    String deleteByIdSession(long id, Session session);

    //------------------------------------------------------------------------------------------------------------------
    String saveOrUpdate(T obj);

    String save(T obj);

    String save(List<T> lstObj);

    String saveBySession(T obj, Session session);

    String saveBySession(List<T> objs, Session session);

    //------------------------------------------------------------------------------------------------------------------
    String update(T obj);

    String updateBySession(T obj, Session session);

    String updateByProperties(T sourceObject, Long id, String[] copiedProperties);

    String updateByPropertiesBySession(T sourceObject, Long id, String[] copiedProperties, Session session);

    //------------------------------------------------------------------------------------------------------------------
    List<T> findByProperty(String property, Object value);

    List<T> findByCondition(List<Condition> lstCondition);

    T findById(long id);

    Long countByCondition(List<Condition> lstCondition);


    List<T> getAll();

    List<T> getList(int count);

    List<T> getAllByPage(int pageNum, int countPerPage);
    //------------------------------------------------------------------------------------------------------------------

}
