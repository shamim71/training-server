package com.versacomllc.training.dao.impl;

import java.io.Serializable;
import java.util.Date;



import com.versacomllc.training.domain.AbstractEntity;

public abstract class GenericEntityDaoImpl<T extends AbstractEntity, ID extends Serializable>
    extends GenericDaoImpl<T, Long> {

  @Override
  public void persist(T entity) {
    entity.setRecordCreated(new Date());
    entity.setLastModified(new Date());

    super.persist(entity);
  }

  @Override
  public void update(T entity) {
    entity.setLastModified(new Date());

    super.update(entity);
  }

}
