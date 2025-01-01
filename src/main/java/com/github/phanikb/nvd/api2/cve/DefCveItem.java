
package com.github.phanikb.nvd.api2.cve;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cve"
})
public class DefCveItem {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cve")
    private CveItem cve;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cve")
    public CveItem getCve() {
        return cve;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cve")
    public void setCve(CveItem cve) {
        this.cve = cve;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DefCveItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cve");
        sb.append('=');
        sb.append(((this.cve == null)?"<null>":this.cve));
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
        result = ((result* 31)+((this.cve == null)? 0 :this.cve.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DefCveItem) == false) {
            return false;
        }
        DefCveItem rhs = ((DefCveItem) other);
        return ((this.cve == rhs.cve)||((this.cve!= null)&&this.cve.equals(rhs.cve)));
    }

}
