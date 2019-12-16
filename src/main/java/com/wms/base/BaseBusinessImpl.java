package com.wms.base;

import com.google.common.collect.Lists;
import com.wms.dto.Condition;
import com.wms.utils.StringUtils;
import java.util.List;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 8/29/2016.
 */
@Transactional
public class BaseBusinessImpl<T extends BaseDTO, TDAO extends BaseDAOImpl> implements BaseBusinessInterface<T> {
    public TDAO tdao;
    public T tDTO;
    protected Class<T> entityClass;

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String getSysDate(String pattern) {
        return tdao.getSysDate(pattern);
    }

    @Override
    public String getSysDate() {
        return tdao.getSysDate();
    }

    @Override
    public Long getSequence(String sequenceName) {
        return tdao.getSequence(sequenceName);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String deleteById(long id) {
        return tdao.deleteById(id);
    }

    @Override
    public String deleteByObject(T obj) {
        return tdao.deleteByObject(obj.toModel());
    }

    @Override
    public String deleteByCondition(List<Condition> lstCondition) {
        return tdao.deleteByCondition(StringUtils.convertCondition(lstCondition));
    }

    @Override
    public String deleteByIdSession(long id, Session session) {
        return tdao.deleteByIdSession(id, session);
    }

    public String deleteByObjectSession(T obj, Session session) {
        return tdao.deleteByObjectSession(obj.toModel(), session);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String saveOrUpdate(T obj) {
        return tdao.saveOrUpdate(obj.toModel());
    }

    @Override
    public String save(T obj) {
        return tdao.save(obj.toModel());
    }

    @Override
    public String saveBySession(T obj, Session session) {
        return tdao.saveBySession(obj.toModel(), session);
    }

    @Override
    public String save(List<T> lstObj) {
        return tdao.save(listDTOToModel(lstObj));
    }

    public String saveBySession(List<T> objs, Session session) {
        return tdao.saveBySession(listDTOToModel(objs), session);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String update(T obj) {
        return tdao.update(obj.toModel());
    }

    public String updateByProperties(T sourceObject, Long id, String[] copiedProperties) {
        return tdao.updateByProperties(sourceObject.toModel(), id, copiedProperties);
    }

    @Override
    public String updateByPropertiesBySession(T sourceObject, Long id, String[] copiedProperties, Session session) {
        return tdao.updateByPropertiesBySession(sourceObject.toModel(), id, copiedProperties, session);
    }

    public String updateBySession(T obj, Session session) {
        return tdao.updateBySession(obj.toModel(), session);
    }

    //--------------------------------------------------------------------
    @Override
    public List<T> getAll() {
        return listModelToDTO(tdao.getAll());
    }

    @Override
    public List<T> getList(int count) {
        return listModelToDTO(tdao.getList(count));
    }

    @Override
    public List<T> getAllByPage(int pageNum, int countPerPage) {
        return tdao.getAllByPage(pageNum, countPerPage);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public T findById(long id) {
        BaseModel temp = tdao.findById(id);
        if (temp != null) {
            return (T) tdao.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<T> findByProperty(String property, Object value) {
        return listModelToDTO(tdao.findByProperty(property, value));
    }

    @Override
    public List<T> findByCondition(List<Condition> lstCondition) {
        return listModelToDTO(tdao.findByCondition(lstCondition));
    }

    @Override
    public Long countByCondition(List<Condition> lstCondition) {
        return tdao.countByCondition(lstCondition);
    }

    //------------------------------------------------------------------------------------------------------------------
    private List<T> listModelToDTO(List<BaseModel> lstModel) {
        List<T> lstResult = Lists.newArrayList();
        for (BaseModel i : lstModel) {
            lstResult.add((T) i.toDTO());
        }
        return lstResult;
    }

    private List<BaseModel> listDTOToModel(List<T> lstDTO) {
        List<BaseModel> lstResult = Lists.newArrayList();
        for (T i : lstDTO) {
            lstResult.add(i.toModel());
        }
        return lstResult;
    }
    //------------------------------------------------------------------------------------------------------------------

}
