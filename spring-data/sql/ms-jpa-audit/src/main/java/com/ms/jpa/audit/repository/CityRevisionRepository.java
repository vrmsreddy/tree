package com.ms.jpa.audit.repository;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ms.jpa.audit.dto.EntityWithRevisionDTO;
import com.ms.jpa.audit.model.City;
import com.ms.jpa.audit.model.RevisionsEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MS
 */

@Repository
@Transactional
public class CityRevisionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    public List<EntityWithRevisionDTO<City>> listCityRevisions(Integer cityCode) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        City cityObject = cityRepository.findOne(cityCode);

        List<Number> revisions = auditReader.getRevisions(City.class, cityCode);

        List<EntityWithRevisionDTO<City>> cityRevisions = new ArrayList<>();
        for (Number revision : revisions) {
            City cityRevision = auditReader.find(City.class, cityCode, revision);
            Date revisionDate = auditReader.getRevisionDate(revision);

            cityRevisions.add(
                    new EntityWithRevisionDTO(
                            new RevisionsEntity(revision.longValue(), revisionDate), cityRevision));
        }

        return cityRevisions;
    }
}
