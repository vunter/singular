package com.mybusiness.myrequirementmodule.spring;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.opensingular.app.commons.spring.persistence.database.SingularPersistenceConfiguration;

import java.util.List;

public class PersistenceConfiguration implements SingularPersistenceConfiguration {

    @Override
    public Class<? extends Dialect> getHibernateDialect() {
        return Oracle10gDialect.class;
    }

    @Override
    public String getActorTableScript() {
        return "/db/ddl/create-myrequirement-actor.sql";
    }

    @Override
    public void configureInitSQLScripts(List<String> scripts) {
        scripts.add("/db/dml/insert-myrequirement-module.sql");
    }

    @Override
    public void configureHibernatePackagesToScan(List<String> list) {
        list.add("com.mybusiness");
    }

}
