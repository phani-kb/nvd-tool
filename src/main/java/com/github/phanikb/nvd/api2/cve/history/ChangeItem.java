
package com.github.phanikb.nvd.api2.cve.history;

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
    "cveId",
    "eventName",
    "cveChangeId",
    "sourceIdentifier",
    "created",
    "details"
})
public class ChangeItem {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cveId")
    private String cveId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("eventName")
    private String eventName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cveChangeId")
    private UUID cveChangeId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]", timezone = "UTC")
    @JsonProperty("created")
    private LocalDateTime created;
    @JsonProperty("details")
    private List<Detail> details = new ArrayList<Detail>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cveId")
    public String getCveId() {
        return cveId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cveId")
    public void setCveId(String cveId) {
        this.cveId = cveId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("eventName")
    public String getEventName() {
        return eventName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("eventName")
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cveChangeId")
    public UUID getCveChangeId() {
        return cveChangeId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cveChangeId")
    public void setCveChangeId(UUID cveChangeId) {
        this.cveChangeId = cveChangeId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sourceIdentifier")
    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sourceIdentifier")
    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    @JsonProperty("created")
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @JsonProperty("details")
    public List<Detail> getDetails() {
        return details;
    }

    @JsonProperty("details")
    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ChangeItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cveId");
        sb.append('=');
        sb.append(((this.cveId == null)?"<null>":this.cveId));
        sb.append(',');
        sb.append("eventName");
        sb.append('=');
        sb.append(((this.eventName == null)?"<null>":this.eventName));
        sb.append(',');
        sb.append("cveChangeId");
        sb.append('=');
        sb.append(((this.cveChangeId == null)?"<null>":this.cveChangeId));
        sb.append(',');
        sb.append("sourceIdentifier");
        sb.append('=');
        sb.append(((this.sourceIdentifier == null)?"<null>":this.sourceIdentifier));
        sb.append(',');
        sb.append("created");
        sb.append('=');
        sb.append(((this.created == null)?"<null>":this.created));
        sb.append(',');
        sb.append("details");
        sb.append('=');
        sb.append(((this.details == null)?"<null>":this.details));
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
        result = ((result* 31)+((this.sourceIdentifier == null)? 0 :this.sourceIdentifier.hashCode()));
        result = ((result* 31)+((this.created == null)? 0 :this.created.hashCode()));
        result = ((result* 31)+((this.cveId == null)? 0 :this.cveId.hashCode()));
        result = ((result* 31)+((this.eventName == null)? 0 :this.eventName.hashCode()));
        result = ((result* 31)+((this.details == null)? 0 :this.details.hashCode()));
        result = ((result* 31)+((this.cveChangeId == null)? 0 :this.cveChangeId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ChangeItem) == false) {
            return false;
        }
        ChangeItem rhs = ((ChangeItem) other);
        return (((((((this.sourceIdentifier == rhs.sourceIdentifier)||((this.sourceIdentifier!= null)&&this.sourceIdentifier.equals(rhs.sourceIdentifier)))&&((this.created == rhs.created)||((this.created!= null)&&this.created.equals(rhs.created))))&&((this.cveId == rhs.cveId)||((this.cveId!= null)&&this.cveId.equals(rhs.cveId))))&&((this.eventName == rhs.eventName)||((this.eventName!= null)&&this.eventName.equals(rhs.eventName))))&&((this.details == rhs.details)||((this.details!= null)&&this.details.equals(rhs.details))))&&((this.cveChangeId == rhs.cveChangeId)||((this.cveChangeId!= null)&&this.cveChangeId.equals(rhs.cveChangeId))));
    }

}
