
package com.github.phanikb.nvd.api2.cve.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "change"
})
public class DefChange {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("change")
    private ChangeItem change;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("change")
    public ChangeItem getChange() {
        return change;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("change")
    public void setChange(ChangeItem change) {
        this.change = change;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DefChange.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("change");
        sb.append('=');
        sb.append(((this.change == null)?"<null>":this.change));
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
        result = ((result* 31)+((this.change == null)? 0 :this.change.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DefChange) == false) {
            return false;
        }
        DefChange rhs = ((DefChange) other);
        return ((this.change == rhs.change)||((this.change!= null)&&this.change.equals(rhs.change)));
    }

}
