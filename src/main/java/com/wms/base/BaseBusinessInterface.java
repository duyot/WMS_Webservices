package com.wms.base;

import com.wms.dto.Condition;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by duyot on 8/29/2016.
 */
public interface BaseBusinessInterface<T extends BaseDTO> {

    public Long getSequence(String sequenceName);

    public String getSysDate(String pattern);
    public String getSysDate();

    public String deleteByObject(T obj);
    public String deleteById(long id);

    public String saveOrUpdate(T obj);
    public String save(T obj);
    public String saveBySession(T obj, Session session);


    public String update(T obj);

    public List<T> getAll();
    public List<T> getList(int count);
    public List<T> getAllByPage(int pageNum, int countPerPage);

    public T findById(long id);
    public List<T> findByProperty(String property, String value);
    public List<T> findByCondition(List<Condition> lstCondition);
    public Long countByCondition(List<Condition> lstCondition);
}
