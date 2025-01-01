
package com.github.phanikb.nvd.api2.cpe;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cpe"
})
public class DefCpe {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpe")
    private Cpe cpe;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpe")
    public Cpe getCpe() {
        return cpe;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpe")
    public void setCpe(Cpe cpe) {
        this.cpe = cpe;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DefCpe.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cpe");
        sb.append('=');
        sb.append(((this.cpe == null)?"<null>":this.cpe));
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
        result = ((result* 31)+((this.cpe == null)? 0 :this.cpe.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DefCpe) == false) {
            return false;
        }
        DefCpe rhs = ((DefCpe) other);
        return ((this.cpe == rhs.cpe)||((this.cpe!= null)&&this.cpe.equals(rhs.cpe)));
    }

}
