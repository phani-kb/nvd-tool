
package com.github.phanikb.nvd.api2.cpe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "deprecated",
    "cpeName",
    "cpeNameId",
    "created",
    "lastModified",
    "titles",
    "refs",
    "deprecatedBy",
    "deprecates"
})
public class Cpe {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("deprecated")
    private Boolean deprecated;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeName")
    private String cpeName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeNameId")
    private UUID cpeNameId;
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
    @JsonProperty("titles")
    private List<DefTitle> titles = new ArrayList<DefTitle>();
    @JsonProperty("refs")
    private List<DefReference> refs = new ArrayList<DefReference>();
    @JsonProperty("deprecatedBy")
    private List<DeprecatedBy> deprecatedBy = new ArrayList<DeprecatedBy>();
    @JsonProperty("deprecates")
    private List<Deprecate> deprecates = new ArrayList<Deprecate>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("deprecated")
    public Boolean getDeprecated() {
        return deprecated;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("deprecated")
    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeName")
    public String getCpeName() {
        return cpeName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeName")
    public void setCpeName(String cpeName) {
        this.cpeName = cpeName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeNameId")
    public UUID getCpeNameId() {
        return cpeNameId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeNameId")
    public void setCpeNameId(UUID cpeNameId) {
        this.cpeNameId = cpeNameId;
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

    @JsonProperty("titles")
    public List<DefTitle> getTitles() {
        return titles;
    }

    @JsonProperty("titles")
    public void setTitles(List<DefTitle> titles) {
        this.titles = titles;
    }

    @JsonProperty("refs")
    public List<DefReference> getRefs() {
        return refs;
    }

    @JsonProperty("refs")
    public void setRefs(List<DefReference> refs) {
        this.refs = refs;
    }

    @JsonProperty("deprecatedBy")
    public List<DeprecatedBy> getDeprecatedBy() {
        return deprecatedBy;
    }

    @JsonProperty("deprecatedBy")
    public void setDeprecatedBy(List<DeprecatedBy> deprecatedBy) {
        this.deprecatedBy = deprecatedBy;
    }

    @JsonProperty("deprecates")
    public List<Deprecate> getDeprecates() {
        return deprecates;
    }

    @JsonProperty("deprecates")
    public void setDeprecates(List<Deprecate> deprecates) {
        this.deprecates = deprecates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Cpe.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("deprecated");
        sb.append('=');
        sb.append(((this.deprecated == null)?"<null>":this.deprecated));
        sb.append(',');
        sb.append("cpeName");
        sb.append('=');
        sb.append(((this.cpeName == null)?"<null>":this.cpeName));
        sb.append(',');
        sb.append("cpeNameId");
        sb.append('=');
        sb.append(((this.cpeNameId == null)?"<null>":this.cpeNameId));
        sb.append(',');
        sb.append("created");
        sb.append('=');
        sb.append(((this.created == null)?"<null>":this.created));
        sb.append(',');
        sb.append("lastModified");
        sb.append('=');
        sb.append(((this.lastModified == null)?"<null>":this.lastModified));
        sb.append(',');
        sb.append("titles");
        sb.append('=');
        sb.append(((this.titles == null)?"<null>":this.titles));
        sb.append(',');
        sb.append("refs");
        sb.append('=');
        sb.append(((this.refs == null)?"<null>":this.refs));
        sb.append(',');
        sb.append("deprecatedBy");
        sb.append('=');
        sb.append(((this.deprecatedBy == null)?"<null>":this.deprecatedBy));
        sb.append(',');
        sb.append("deprecates");
        sb.append('=');
        sb.append(((this.deprecates == null)?"<null>":this.deprecates));
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
        result = ((result* 31)+((this.refs == null)? 0 :this.refs.hashCode()));
        result = ((result* 31)+((this.created == null)? 0 :this.created.hashCode()));
        result = ((result* 31)+((this.deprecates == null)? 0 :this.deprecates.hashCode()));
        result = ((result* 31)+((this.deprecated == null)? 0 :this.deprecated.hashCode()));
        result = ((result* 31)+((this.cpeName == null)? 0 :this.cpeName.hashCode()));
        result = ((result* 31)+((this.lastModified == null)? 0 :this.lastModified.hashCode()));
        result = ((result* 31)+((this.titles == null)? 0 :this.titles.hashCode()));
        result = ((result* 31)+((this.deprecatedBy == null)? 0 :this.deprecatedBy.hashCode()));
        result = ((result* 31)+((this.cpeNameId == null)? 0 :this.cpeNameId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Cpe) == false) {
            return false;
        }
        Cpe rhs = ((Cpe) other);
        return ((((((((((this.refs == rhs.refs)||((this.refs!= null)&&this.refs.equals(rhs.refs)))&&((this.created == rhs.created)||((this.created!= null)&&this.created.equals(rhs.created))))&&((this.deprecates == rhs.deprecates)||((this.deprecates!= null)&&this.deprecates.equals(rhs.deprecates))))&&((this.deprecated == rhs.deprecated)||((this.deprecated!= null)&&this.deprecated.equals(rhs.deprecated))))&&((this.cpeName == rhs.cpeName)||((this.cpeName!= null)&&this.cpeName.equals(rhs.cpeName))))&&((this.lastModified == rhs.lastModified)||((this.lastModified!= null)&&this.lastModified.equals(rhs.lastModified))))&&((this.titles == rhs.titles)||((this.titles!= null)&&this.titles.equals(rhs.titles))))&&((this.deprecatedBy == rhs.deprecatedBy)||((this.deprecatedBy!= null)&&this.deprecatedBy.equals(rhs.deprecatedBy))))&&((this.cpeNameId == rhs.cpeNameId)||((this.cpeNameId!= null)&&this.cpeNameId.equals(rhs.cpeNameId))));
    }

}
