
package com.github.phanikb.nvd.api2.cpe.match;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cpeName",
    "cpeNameId"
})
public class DefCpeName {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DefCpeName.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cpeName");
        sb.append('=');
        sb.append(((this.cpeName == null)?"<null>":this.cpeName));
        sb.append(',');
        sb.append("cpeNameId");
        sb.append('=');
        sb.append(((this.cpeNameId == null)?"<null>":this.cpeNameId));
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
        result = ((result* 31)+((this.cpeName == null)? 0 :this.cpeName.hashCode()));
        result = ((result* 31)+((this.cpeNameId == null)? 0 :this.cpeNameId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DefCpeName) == false) {
            return false;
        }
        DefCpeName rhs = ((DefCpeName) other);
        return (((this.cpeName == rhs.cpeName)||((this.cpeName!= null)&&this.cpeName.equals(rhs.cpeName)))&&((this.cpeNameId == rhs.cpeNameId)||((this.cpeNameId!= null)&&this.cpeNameId.equals(rhs.cpeNameId))));
    }

}
