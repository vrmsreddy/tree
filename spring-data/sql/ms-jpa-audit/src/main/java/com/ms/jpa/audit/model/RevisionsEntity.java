package com.ms.jpa.audit.model;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.ms.jpa.audit.listener.EntityRevisionListener;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * Created by MS
 */

@Entity
@RevisionEntity(value = EntityRevisionListener.class)
@Table(name = "city_audit_revision")
public class RevisionsEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @RevisionNumber
    private Long revisionId;

    @RevisionTimestamp
    private Date revisionDate;

    public RevisionsEntity(Long revisionId, Date revisionDate) {
        this.revisionId = revisionId;
        this.revisionDate = revisionDate;
    }

    public RevisionsEntity() { }

    public Long getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Long revisionId) {
        this.revisionId = revisionId;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    @Override
    public String toString() {
        return "RevisionsEntity{" +
                "revisionId=" + revisionId +
                ", revisionDate=" + revisionDate +
                '}';
    }
}
