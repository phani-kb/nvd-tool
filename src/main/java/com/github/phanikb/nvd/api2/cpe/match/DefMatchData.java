
package com.github.phanikb.nvd.api2.cpe.match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * CPE match string or range
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "criteria",
    "matchCriteriaId",
    "versionStartExcluding",
    "versionStartIncluding",
    "versionEndExcluding",
    "versionEndIncluding",
    "created",
    "lastModified",
    "cpeLastModified",
    "status",
    "matches"
})
public class DefMatchData {

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
    /**
     * 
     * (Required)
     * 
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]", timezone = "UTC")
    @JsonProperty("created")
    private LocalDateTime created;
    /**
     * 
     * (Required)
     * 
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]", timezone = "UTC")
    @JsonProperty("lastModified")
    private LocalDateTime lastModified;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]", timezone = "UTC")
    @JsonProperty("cpeLastModified")
    private LocalDateTime cpeLastModified;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    private String status;
    @JsonProperty("matches")
    private List<DefCpeName> matches = new ArrayList<DefCpeName>();

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

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("created")
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("created")
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastModified")
    public LocalDateTime getLastModified() {
        return lastModified;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastModified")
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @JsonProperty("cpeLastModified")
    public LocalDateTime getCpeLastModified() {
        return cpeLastModified;
    }

    @JsonProperty("cpeLastModified")
    public void setCpeLastModified(LocalDateTime cpeLastModified) {
        this.cpeLastModified = cpeLastModified;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("matches")
    public List<DefCpeName> getMatches() {
        return matches;
    }

    @JsonProperty("matches")
    public void setMatches(List<DefCpeName> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DefMatchData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("created");
        sb.append('=');
        sb.append(((this.created == null)?"<null>":this.created));
        sb.append(',');
        sb.append("lastModified");
        sb.append('=');
        sb.append(((this.lastModified == null)?"<null>":this.lastModified));
        sb.append(',');
        sb.append("cpeLastModified");
        sb.append('=');
        sb.append(((this.cpeLastModified == null)?"<null>":this.cpeLastModified));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("matches");
        sb.append('=');
        sb.append(((this.matches == null)?"<null>":this.matches));
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
        result = ((result* 31)+((this.versionStartExcluding == null)? 0 :this.versionStartExcluding.hashCode()));
        result = ((result* 31)+((this.cpeLastModified == null)? 0 :this.cpeLastModified.hashCode()));
        result = ((result* 31)+((this.criteria == null)? 0 :this.criteria.hashCode()));
        result = ((result* 31)+((this.versionEndExcluding == null)? 0 :this.versionEndExcluding.hashCode()));
        result = ((result* 31)+((this.created == null)? 0 :this.created.hashCode()));
        result = ((result* 31)+((this.versionEndIncluding == null)? 0 :this.versionEndIncluding.hashCode()));
        result = ((result* 31)+((this.lastModified == null)? 0 :this.lastModified.hashCode()));
        result = ((result* 31)+((this.matches == null)? 0 :this.matches.hashCode()));
        result = ((result* 31)+((this.matchCriteriaId == null)? 0 :this.matchCriteriaId.hashCode()));
        result = ((result* 31)+((this.versionStartIncluding == null)? 0 :this.versionStartIncluding.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DefMatchData) == false) {
            return false;
        }
        DefMatchData rhs = ((DefMatchData) other);
        return ((((((((((((this.versionStartExcluding == rhs.versionStartExcluding)||((this.versionStartExcluding!= null)&&this.versionStartExcluding.equals(rhs.versionStartExcluding)))&&((this.cpeLastModified == rhs.cpeLastModified)||((this.cpeLastModified!= null)&&this.cpeLastModified.equals(rhs.cpeLastModified))))&&((this.criteria == rhs.criteria)||((this.criteria!= null)&&this.criteria.equals(rhs.criteria))))&&((this.versionEndExcluding == rhs.versionEndExcluding)||((this.versionEndExcluding!= null)&&this.versionEndExcluding.equals(rhs.versionEndExcluding))))&&((this.created == rhs.created)||((this.created!= null)&&this.created.equals(rhs.created))))&&((this.versionEndIncluding == rhs.versionEndIncluding)||((this.versionEndIncluding!= null)&&this.versionEndIncluding.equals(rhs.versionEndIncluding))))&&((this.lastModified == rhs.lastModified)||((this.lastModified!= null)&&this.lastModified.equals(rhs.lastModified))))&&((this.matches == rhs.matches)||((this.matches!= null)&&this.matches.equals(rhs.matches))))&&((this.matchCriteriaId == rhs.matchCriteriaId)||((this.matchCriteriaId!= null)&&this.matchCriteriaId.equals(rhs.matchCriteriaId))))&&((this.versionStartIncluding == rhs.versionStartIncluding)||((this.versionStartIncluding!= null)&&this.versionStartIncluding.equals(rhs.versionStartIncluding))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))));
    }

}
