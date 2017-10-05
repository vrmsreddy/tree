package com.ms.jpa.audit.dto;

import com.ms.jpa.audit.model.RevisionsEntity;

/**
 * Created MS
 */
public class EntityWithRevisionDTO <T> {

    private RevisionsEntity revision;

    private T entity;

    public EntityWithRevisionDTO(RevisionsEntity revision, T entity) {
        this.revision = revision;
        this.entity = entity;
    }

    public RevisionsEntity getRevision() {
        return revision;
    }

    public T getEntity() {
        return entity;
    }
}
