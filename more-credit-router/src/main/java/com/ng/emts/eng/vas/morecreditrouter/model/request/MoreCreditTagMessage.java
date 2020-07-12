package com.ng.emts.eng.vas.morecreditrouter.model.request;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "MoreCreditTagMessage_CSMS")
public class MoreCreditTagMessage implements Serializable {
    private static final long serialVersionUID = 3702625876543413173L;

    public MoreCreditTagMessage() {
    }

    public MoreCreditTagMessage(String msg) {
        this.message = msg;
    }

    @Id
    private Long id;
    private String message;
    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    @Override
    public String toString() {
        return "EasyCreditTagMessage{" + "id=" + id + ", message=" + message + ", enabled=" + enabled + '}';
    }
}
