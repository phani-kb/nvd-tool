
package com.github.phanikb.nvd.api2.cpe.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "matchString"
})
public class DefMatchstring {

    /**
     * CPE match string or range
     * (Required)
     * 
     */
    @JsonProperty("matchString")
    @JsonPropertyDescription("CPE match string or range")
    private DefMatchData matchString;

    /**
     * CPE match string or range
     * (Required)
     * 
     */
    @JsonProperty("matchString")
    public DefMatchData getMatchString() {
        return matchString;
    }

    /**
     * CPE match string or range
     * (Required)
     * 
     */
    @JsonProperty("matchString")
    public void setMatchString(DefMatchData matchString) {
        this.matchString = matchString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DefMatchstring.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("matchString");
        sb.append('=');
        sb.append(((this.matchString == null)?"<null>":this.matchString));
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
        result = ((result* 31)+((this.matchString == null)? 0 :this.matchString.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DefMatchstring) == false) {
            return false;
        }
        DefMatchstring rhs = ((DefMatchstring) other);
        return ((this.matchString == rhs.matchString)||((this.matchString!= null)&&this.matchString.equals(rhs.matchString)));
    }

}
