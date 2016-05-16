package com.mysimplework.model.generic;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dzhao on 19/08/2015.
 */
@MappedSuperclass
public abstract class AbstractDomain extends AbstractBaseDomain {

    @Version
    protected Long version;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    protected Date creationDate;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "modified_datetime")
    protected Date lastModificationDate;

    protected Long getVersion() {
        return version;
    }

    /**
     * Set to protected as it's not supposed to be called externally
     * @param version
     */
    protected void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Set to protected as it's not supposed to be called externally
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    /**
     * Set to protected as it's not supposed to be called externally
     * @param lastModificationDate
     */
    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}
