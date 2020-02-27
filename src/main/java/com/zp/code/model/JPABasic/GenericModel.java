package com.zp.code.model.JPABasic;

import javax.persistence.MappedSuperclass;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
@MappedSuperclass
public class GenericModel {
    public GenericModel() {
    }

    public static class JPAQuery {
        public Query query;
        public String sq;

        public JPAQuery(String sq, Query query) {
            this.query = query;
            this.sq = sq;
        }

        public JPAQuery(Query query) {
            this.query = query;
            this.sq = query.toString();
        }

        public <T> T first() {
            try {
                List<T> results = this.query.setMaxResults(1).getResultList();
                return results.isEmpty() ? null : results.get(0);
            } catch (Exception var2) {
                throw new RuntimeException("Error while executing query <strong>" + this.sq + "</strong>", var2);
            }
        }

        public GenericModel.JPAQuery bind(String name, Object param) {
            if (param.getClass().isArray()) {
                param = Arrays.asList((Object[])((Object[])param));
            }

            if (param instanceof Integer) {
                param = ((Integer)param).longValue();
            }

            this.query.setParameter(name, param);
            return this;
        }

        public GenericModel.JPAQuery setParameter(String name, Object param) {
            this.query.setParameter(name, param);
            return this;
        }

        public <T> List<T> fetch() {
            try {
                return this.query.getResultList();
            } catch (Exception var2) {
                throw new RuntimeException("Error while executing query <strong>" + this.sq + "</strong>", var2);
            }
        }

        public <T> List<T> fetch(int max) {
            try {
                this.query.setMaxResults(max);
                return this.query.getResultList();
            } catch (Exception var3) {
                throw new RuntimeException("Error while executing query <strong>" + this.sq + "</strong>", var3);
            }
        }

        public <T> GenericModel.JPAQuery from(int position) {
            this.query.setFirstResult(position);
            return this;
        }

        public <T> List<T> fetch(int page, int length) {
            if (page < 1) {
                page = 1;
            }

            this.query.setFirstResult((page - 1) * length);
            this.query.setMaxResults(length);

            try {
                return this.query.getResultList();
            } catch (Exception var4) {
                throw new RuntimeException("Error while executing query <strong>" + this.sq + "</strong>", var4);
            }
        }
    }
}
