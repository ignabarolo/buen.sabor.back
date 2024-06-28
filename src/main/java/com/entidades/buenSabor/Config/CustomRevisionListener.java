package com.entidades.buenSabor.Config;

import com.entidades.buenSabor.audit.AuditRevision;
import org.hibernate.envers.RevisionListener;
import org.springframework.data.history.Revision;

public class CustomRevisionListener implements RevisionListener {
    public void newRevision(Object revisionEntity){
        final AuditRevision revision=(AuditRevision) revisionEntity;//transformamos el objeto en una revision


    }
}
