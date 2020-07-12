package com.ng.emts.eng.vas.morecreditrouter.model.request;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MCCreditConSettings_CSMS")
public class MoreCreditConstantsSettings implements Serializable {
    private static final long serialVersionUID = 8542578024621456301L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String settingValue;
    private String settingDescription;
    private String settingName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getSettingDescription() {
        return settingDescription;
    }

    public void setSettingDescription(String settingDescription) {
        this.settingDescription = settingDescription;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }
    @Override
    public String toString() {
        return "MoreCreditConstantsSettings{" + "id=" + id + ", name=" + settingName + ", value=" + settingValue + '}';
    }
}
