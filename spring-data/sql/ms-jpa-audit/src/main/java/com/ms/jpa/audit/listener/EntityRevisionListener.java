package com.ms.jpa.audit.listener;


import org.hibernate.envers.RevisionListener;


/**
 * @author MS
 *
 */
public class EntityRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object o) {
        System.out.println("New revision is created: " + o);
        
    }
}
