
package com.github.phanikb.nvd.api2.cpe;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cpeName",
    "cpeNameId"
})
public class Deprecate {

    @JsonProperty("cpeName")
    private String cpeName;
    @JsonProperty("cpeNameId")
    private UUID cpeNameId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("cpeName")
    public String getCpeName() {
        return cpeName;
    }

    @JsonProperty("cpeName")
    public void setCpeName(String cpeName) {
        this.cpeName = cpeName;
    }

    @JsonProperty("cpeNameId")
    public UUID getCpeNameId() {
        return cpeNameId;
    }

    @JsonProperty("cpeNameId")
    public void setCpeNameId(UUID cpeNameId) {
        this.cpeNameId = cpeNameId;
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
        sb.append(Deprecate.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cpeName");
        sb.append('=');
        sb.append(((this.cpeName == null)?"<null>":this.cpeName));
        sb.append(',');
        sb.append("cpeNameId");
        sb.append('=');
        sb.append(((this.cpeNameId == null)?"<null>":this.cpeNameId));
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
        result = ((result* 31)+((this.cpeName == null)? 0 :this.cpeName.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.cpeNameId == null)? 0 :this.cpeNameId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Deprecate) == false) {
            return false;
        }
        Deprecate rhs = ((Deprecate) other);
        return ((((this.cpeName == rhs.cpeName)||((this.cpeName!= null)&&this.cpeName.equals(rhs.cpeName)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.cpeNameId == rhs.cpeNameId)||((this.cpeNameId!= null)&&this.cpeNameId.equals(rhs.cpeNameId))));
    }

}
