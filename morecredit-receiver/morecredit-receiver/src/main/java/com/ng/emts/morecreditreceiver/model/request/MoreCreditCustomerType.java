package com.ng.emts.morecreditreceiver.model.request;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="MoreCreditCustomerType_CSMS")
public class MoreCreditCustomerType implements Serializable {

    private static final long serialVersionUID = 6993859443254176673L;

    public MoreCreditCustomerType() {
    }

    public MoreCreditCustomerType(String name) {
        this.name = name;
    }

    @Id
    private Long id;
    private String name;
    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final com.ng.emts.sercom.more.credit.receiver.entities.MoreCreditCustomerType other = (com.ng.emts.sercom.more.credit.receiver.entities.MoreCreditCustomerType) obj;
//        if (this.id != other.id) {
//            return false;
//        }
//        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        return "EasyCreditCustomerType{" + "id=" + id + ", name=" + name + ", enabled=" + enabled + '}';
    }
}
