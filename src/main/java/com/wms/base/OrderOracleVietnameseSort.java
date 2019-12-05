package com.wms.base;

import java.sql.Types;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

public class OrderOracleVietnameseSort extends Order {
    private static final long serialVersionUID = 1L;
    private boolean ignoreCase = false;
    private boolean ascending;
    private String propertyName;

    public OrderOracleVietnameseSort(String propertyName) {
        super(propertyName, true);
        this.ascending = true;
        this.propertyName = propertyName;
    }

    public OrderOracleVietnameseSort(String propertyName, boolean ascending) {
        super(propertyName, ascending);
        this.ascending = ascending;
        this.propertyName = propertyName;
    }

    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
            throws HibernateException {

        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
        Type type = criteriaQuery.getTypeUsingProjection(criteria, propertyName);
        StringBuffer fragment = new StringBuffer();
        for (int i = 0; i < columns.length; i++) {
            fragment.append("NLSSORT(");
            SessionFactoryImplementor factory = criteriaQuery.getFactory();
            boolean lower = ignoreCase && type.sqlTypes(factory)[i] == Types.VARCHAR;
            if (lower) {
                fragment.append(factory.getDialect().getLowercaseFunction()).append('(');
            }
            fragment.append(columns[i]);
            if (lower) {
                fragment.append(')');
            }

            fragment.append(", 'NLS_SORT = vietnamese')");

            fragment.append(ascending ? " asc" : " desc");
            if (i < columns.length - 1) {
                fragment.append(",");
            }
        }
        return fragment.toString();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static Order asc(String propertyName) {
        return new OrderOracleVietnameseSort(propertyName);
    }

    public static Order desc(String propertyName) {
        return new OrderOracleVietnameseSort(propertyName, false);
    }
}
