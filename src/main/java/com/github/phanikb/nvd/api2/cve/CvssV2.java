
package com.github.phanikb.nvd.api2.cve;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "source",
    "type",
    "cvssData",
    "baseSeverity",
    "exploitabilityScore",
    "impactScore",
    "acInsufInfo",
    "obtainAllPrivilege",
    "obtainUserPrivilege",
    "obtainOtherPrivilege",
    "userInteractionRequired"
})
public class CvssV2 {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    private String source;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    private CvssV2 .Type type;
    /**
     * JSON Schema for Common Vulnerability Scoring System version 2.0
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("cvssData")
    private CvssV20 cvssData;
    @JsonProperty("baseSeverity")
    private String baseSeverity;
    /**
     * CVSS subscore.
     * 
     */
    @JsonProperty("exploitabilityScore")
    @JsonPropertyDescription("CVSS subscore.")
    private Double exploitabilityScore;
    /**
     * CVSS subscore.
     * 
     */
    @JsonProperty("impactScore")
    @JsonPropertyDescription("CVSS subscore.")
    private Double impactScore;
    @JsonProperty("acInsufInfo")
    private Boolean acInsufInfo;
    @JsonProperty("obtainAllPrivilege")
    private Boolean obtainAllPrivilege;
    @JsonProperty("obtainUserPrivilege")
    private Boolean obtainUserPrivilege;
    @JsonProperty("obtainOtherPrivilege")
    private Boolean obtainOtherPrivilege;
    @JsonProperty("userInteractionRequired")
    private Boolean userInteractionRequired;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    public CvssV2 .Type getType() {
        return type;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(CvssV2 .Type type) {
        this.type = type;
    }

    /**
     * JSON Schema for Common Vulnerability Scoring System version 2.0
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("cvssData")
    public CvssV20 getCvssData() {
        return cvssData;
    }

    /**
     * JSON Schema for Common Vulnerability Scoring System version 2.0
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("cvssData")
    public void setCvssData(CvssV20 cvssData) {
        this.cvssData = cvssData;
    }

    @JsonProperty("baseSeverity")
    public String getBaseSeverity() {
        return baseSeverity;
    }

    @JsonProperty("baseSeverity")
    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

    /**
     * CVSS subscore.
     * 
     */
    @JsonProperty("exploitabilityScore")
    public Double getExploitabilityScore() {
        return exploitabilityScore;
    }

    /**
     * CVSS subscore.
     * 
     */
    @JsonProperty("exploitabilityScore")
    public void setExploitabilityScore(Double exploitabilityScore) {
        this.exploitabilityScore = exploitabilityScore;
    }

    /**
     * CVSS subscore.
     * 
     */
    @JsonProperty("impactScore")
    public Double getImpactScore() {
        return impactScore;
    }

    /**
     * CVSS subscore.
     * 
     */
    @JsonProperty("impactScore")
    public void setImpactScore(Double impactScore) {
        this.impactScore = impactScore;
    }

    @JsonProperty("acInsufInfo")
    public Boolean getAcInsufInfo() {
        return acInsufInfo;
    }

    @JsonProperty("acInsufInfo")
    public void setAcInsufInfo(Boolean acInsufInfo) {
        this.acInsufInfo = acInsufInfo;
    }

    @JsonProperty("obtainAllPrivilege")
    public Boolean getObtainAllPrivilege() {
        return obtainAllPrivilege;
    }

    @JsonProperty("obtainAllPrivilege")
    public void setObtainAllPrivilege(Boolean obtainAllPrivilege) {
        this.obtainAllPrivilege = obtainAllPrivilege;
    }

    @JsonProperty("obtainUserPrivilege")
    public Boolean getObtainUserPrivilege() {
        return obtainUserPrivilege;
    }

    @JsonProperty("obtainUserPrivilege")
    public void setObtainUserPrivilege(Boolean obtainUserPrivilege) {
        this.obtainUserPrivilege = obtainUserPrivilege;
    }

    @JsonProperty("obtainOtherPrivilege")
    public Boolean getObtainOtherPrivilege() {
        return obtainOtherPrivilege;
    }

    @JsonProperty("obtainOtherPrivilege")
    public void setObtainOtherPrivilege(Boolean obtainOtherPrivilege) {
        this.obtainOtherPrivilege = obtainOtherPrivilege;
    }

    @JsonProperty("userInteractionRequired")
    public Boolean getUserInteractionRequired() {
        return userInteractionRequired;
    }

    @JsonProperty("userInteractionRequired")
    public void setUserInteractionRequired(Boolean userInteractionRequired) {
        this.userInteractionRequired = userInteractionRequired;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CvssV2 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("source");
        sb.append('=');
        sb.append(((this.source == null)?"<null>":this.source));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("cvssData");
        sb.append('=');
        sb.append(((this.cvssData == null)?"<null>":this.cvssData));
        sb.append(',');
        sb.append("baseSeverity");
        sb.append('=');
        sb.append(((this.baseSeverity == null)?"<null>":this.baseSeverity));
        sb.append(',');
        sb.append("exploitabilityScore");
        sb.append('=');
        sb.append(((this.exploitabilityScore == null)?"<null>":this.exploitabilityScore));
        sb.append(',');
        sb.append("impactScore");
        sb.append('=');
        sb.append(((this.impactScore == null)?"<null>":this.impactScore));
        sb.append(',');
        sb.append("acInsufInfo");
        sb.append('=');
        sb.append(((this.acInsufInfo == null)?"<null>":this.acInsufInfo));
        sb.append(',');
        sb.append("obtainAllPrivilege");
        sb.append('=');
        sb.append(((this.obtainAllPrivilege == null)?"<null>":this.obtainAllPrivilege));
        sb.append(',');
        sb.append("obtainUserPrivilege");
        sb.append('=');
        sb.append(((this.obtainUserPrivilege == null)?"<null>":this.obtainUserPrivilege));
        sb.append(',');
        sb.append("obtainOtherPrivilege");
        sb.append('=');
        sb.append(((this.obtainOtherPrivilege == null)?"<null>":this.obtainOtherPrivilege));
        sb.append(',');
        sb.append("userInteractionRequired");
        sb.append('=');
        sb.append(((this.userInteractionRequired == null)?"<null>":this.userInteractionRequired));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.baseSeverity == null)? 0 :this.baseSeverity.hashCode()));
        result = ((result* 31)+((this.exploitabilityScore == null)? 0 :this.exploitabilityScore.hashCode()));
        result = ((result* 31)+((this.obtainAllPrivilege == null)? 0 :this.obtainAllPrivilege.hashCode()));
        result = ((result* 31)+((this.userInteractionRequired == null)? 0 :this.userInteractionRequired.hashCode()));
        result = ((result* 31)+((this.cvssData == null)? 0 :this.cvssData.hashCode()));
        result = ((result* 31)+((this.obtainOtherPrivilege == null)? 0 :this.obtainOtherPrivilege.hashCode()));
        result = ((result* 31)+((this.source == null)? 0 :this.source.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.impactScore == null)? 0 :this.impactScore.hashCode()));
        result = ((result* 31)+((this.acInsufInfo == null)? 0 :this.acInsufInfo.hashCode()));
        result = ((result* 31)+((this.obtainUserPrivilege == null)? 0 :this.obtainUserPrivilege.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CvssV2) == false) {
            return false;
        }
        CvssV2 rhs = ((CvssV2) other);
        return ((((((((((((this.baseSeverity == rhs.baseSeverity)||((this.baseSeverity!= null)&&this.baseSeverity.equals(rhs.baseSeverity)))&&((this.exploitabilityScore == rhs.exploitabilityScore)||((this.exploitabilityScore!= null)&&this.exploitabilityScore.equals(rhs.exploitabilityScore))))&&((this.obtainAllPrivilege == rhs.obtainAllPrivilege)||((this.obtainAllPrivilege!= null)&&this.obtainAllPrivilege.equals(rhs.obtainAllPrivilege))))&&((this.userInteractionRequired == rhs.userInteractionRequired)||((this.userInteractionRequired!= null)&&this.userInteractionRequired.equals(rhs.userInteractionRequired))))&&((this.cvssData == rhs.cvssData)||((this.cvssData!= null)&&this.cvssData.equals(rhs.cvssData))))&&((this.obtainOtherPrivilege == rhs.obtainOtherPrivilege)||((this.obtainOtherPrivilege!= null)&&this.obtainOtherPrivilege.equals(rhs.obtainOtherPrivilege))))&&((this.source == rhs.source)||((this.source!= null)&&this.source.equals(rhs.source))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.impactScore == rhs.impactScore)||((this.impactScore!= null)&&this.impactScore.equals(rhs.impactScore))))&&((this.acInsufInfo == rhs.acInsufInfo)||((this.acInsufInfo!= null)&&this.acInsufInfo.equals(rhs.acInsufInfo))))&&((this.obtainUserPrivilege == rhs.obtainUserPrivilege)||((this.obtainUserPrivilege!= null)&&this.obtainUserPrivilege.equals(rhs.obtainUserPrivilege))));
    }

    public enum Type {

        PRIMARY("Primary"),
        SECONDARY("Secondary");
        private final String value;
        private final static Map<String, CvssV2 .Type> CONSTANTS = new HashMap<String, CvssV2 .Type>();

        static {
            for (CvssV2 .Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV2 .Type fromValue(String value) {
            CvssV2 .Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
