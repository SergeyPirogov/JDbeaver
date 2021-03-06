package com.qaguild.dbeaver.processors.jdbi;

import com.qaguild.dbeaver.processors.AbstractProcessor;
import com.qaguild.dbeaver.processors.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;

public class FindOneByIdProcessor extends AbstractProcessor<Jdbi> {

    public FindOneByIdProcessor(Jdbi executor) {
        super(executor);
    }

    @Override
    public String getName() {
        return "findOne";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String table = Utils.getTableName(clazz);
        String fieldName = Utils.getIdFieldName(clazz);
        String query = String.format("SELECT * FROM %s WHERE %s = ?", table, fieldName);
        return executor.withHandle(h -> h.select(query, args[0]).mapToBean(clazz).findOne())
                .orElse(null);
    }
}
