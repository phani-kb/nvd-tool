
package com.github.phanikb.nvd.api2.cve;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * CPE match string or range
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "vulnerable",
    "criteria",
    "matchCriteriaId",
    "versionStartExcluding",
    "versionStartIncluding",
    "versionEndExcluding",
    "versionEndIncluding"
})
public class CpeMatch {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vulnerable")
    private Boolean vulnerable;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("criteria")
    private String criteria;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("matchCriteriaId")
    private UUID matchCriteriaId;
    @JsonProperty("versionStartExcluding")
    private String versionStartExcluding;
    @JsonProperty("versionStartIncluding")
    private String versionStartIncluding;
    @JsonProperty("versionEndExcluding")
    private String versionEndExcluding;
    @JsonProperty("versionEndIncluding")
    private String versionEndIncluding;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vulnerable")
    public Boolean getVulnerable() {
        return vulnerable;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vulnerable")
    public void setVulnerable(Boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("criteria")
    public String getCriteria() {
        return criteria;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("criteria")
    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("matchCriteriaId")
    public UUID getMatchCriteriaId() {
        return matchCriteriaId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("matchCriteriaId")
    public void setMatchCriteriaId(UUID matchCriteriaId) {
        this.matchCriteriaId = matchCriteriaId;
    }

    @JsonProperty("versionStartExcluding")
    public String getVersionStartExcluding() {
        return versionStartExcluding;
    }

    @JsonProperty("versionStartExcluding")
    public void setVersionStartExcluding(String versionStartExcluding) {
        this.versionStartExcluding = versionStartExcluding;
    }

    @JsonProperty("versionStartIncluding")
    public String getVersionStartIncluding() {
        return versionStartIncluding;
    }

    @JsonProperty("versionStartIncluding")
    public void setVersionStartIncluding(String versionStartIncluding) {
        this.versionStartIncluding = versionStartIncluding;
    }

    @JsonProperty("versionEndExcluding")
    public String getVersionEndExcluding() {
        return versionEndExcluding;
    }

    @JsonProperty("versionEndExcluding")
    public void setVersionEndExcluding(String versionEndExcluding) {
        this.versionEndExcluding = versionEndExcluding;
    }

    @JsonProperty("versionEndIncluding")
    public String getVersionEndIncluding() {
        return versionEndIncluding;
    }

    @JsonProperty("versionEndIncluding")
    public void setVersionEndIncluding(String versionEndIncluding) {
        this.versionEndIncluding = versionEndIncluding;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CpeMatch.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vulnerable");
        sb.append('=');
        sb.append(((this.vulnerable == null)?"<null>":this.vulnerable));
        sb.append(',');
        sb.append("criteria");
        sb.append('=');
        sb.append(((this.criteria == null)?"<null>":this.criteria));
        sb.append(',');
        sb.append("matchCriteriaId");
        sb.append('=');
        sb.append(((this.matchCriteriaId == null)?"<null>":this.matchCriteriaId));
        sb.append(',');
        sb.append("versionStartExcluding");
        sb.append('=');
        sb.append(((this.versionStartExcluding == null)?"<null>":this.versionStartExcluding));
        sb.append(',');
        sb.append("versionStartIncluding");
        sb.append('=');
        sb.append(((this.versionStartIncluding == null)?"<null>":this.versionStartIncluding));
        sb.append(',');
        sb.append("versionEndExcluding");
        sb.append('=');
        sb.append(((this.versionEndExcluding == null)?"<null>":this.versionEndExcluding));
        sb.append(',');
        sb.append("versionEndIncluding");
        sb.append('=');
        sb.append(((this.versionEndIncluding == null)?"<null>":this.versionEndIncluding));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
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
        result = ((result* 31)+((this.vulnerable == null)? 0 :this.vulnerable.hashCode()));
        result = ((result* 31)+((this.versionStartExcluding == null)? 0 :this.versionStartExcluding.hashCode()));
        result = ((result* 31)+((this.criteria == null)? 0 :this.criteria.hashCode()));
        result = ((result* 31)+((this.versionEndExcluding == null)? 0 :this.versionEndExcluding.hashCode()));
        result = ((result* 31)+((this.versionEndIncluding == null)? 0 :this.versionEndIncluding.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.matchCriteriaId == null)? 0 :this.matchCriteriaId.hashCode()));
        result = ((result* 31)+((this.versionStartIncluding == null)? 0 :this.versionStartIncluding.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CpeMatch) == false) {
            return false;
        }
        CpeMatch rhs = ((CpeMatch) other);
        return (((((((((this.vulnerable == rhs.vulnerable)||((this.vulnerable!= null)&&this.vulnerable.equals(rhs.vulnerable)))&&((this.versionStartExcluding == rhs.versionStartExcluding)||((this.versionStartExcluding!= null)&&this.versionStartExcluding.equals(rhs.versionStartExcluding))))&&((this.criteria == rhs.criteria)||((this.criteria!= null)&&this.criteria.equals(rhs.criteria))))&&((this.versionEndExcluding == rhs.versionEndExcluding)||((this.versionEndExcluding!= null)&&this.versionEndExcluding.equals(rhs.versionEndExcluding))))&&((this.versionEndIncluding == rhs.versionEndIncluding)||((this.versionEndIncluding!= null)&&this.versionEndIncluding.equals(rhs.versionEndIncluding))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.matchCriteriaId == rhs.matchCriteriaId)||((this.matchCriteriaId!= null)&&this.matchCriteriaId.equals(rhs.matchCriteriaId))))&&((this.versionStartIncluding == rhs.versionStartIncluding)||((this.versionStartIncluding!= null)&&this.versionStartIncluding.equals(rhs.versionStartIncluding))));
    }

}
