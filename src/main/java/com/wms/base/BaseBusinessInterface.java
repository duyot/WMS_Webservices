package com.wms.base;

import com.wms.dto.Condition;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by duyot on 8/29/2016.
 */
public interface BaseBusinessInterface<T extends BaseDTO> {

    Long getSequence(String sequenceName);

    String getSysDate(String pattern);
    String getSysDate();

    String deleteByObject(T obj);
    String deleteById(long id);

    String saveOrUpdate(T obj);
    String save(T obj);
    String save(List<T> lstObj);
    String saveBySession(T obj, Session session);


    String update(T obj);

    List<T> getAll();
    List<T> getList(int count);
    List<T> getAllByPage(int pageNum, int countPerPage);

    T findById(long id);
    List<T> findByProperty(String property, String value);
    List<T> findByCondition(List<Condition> lstCondition);
    String deleteByCondition(List<Condition> lstCondition);
    Long countByCondition(List<Condition> lstCondition);

    String updateByProperties(T sourceObject, Long id, String[] copiedProperties);
}
