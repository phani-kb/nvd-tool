
package com.github.phanikb.nvd.api2.cve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Defines a configuration node in an NVD applicability statement.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "operator",
    "negate",
    "cpeMatch"
})
public class Node {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("operator")
    private Node.Operator operator;
    @JsonProperty("negate")
    private Boolean negate;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeMatch")
    private List<CpeMatch> cpeMatch = new ArrayList<CpeMatch>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("operator")
    public Node.Operator getOperator() {
        return operator;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("operator")
    public void setOperator(Node.Operator operator) {
        this.operator = operator;
    }

    @JsonProperty("negate")
    public Boolean getNegate() {
        return negate;
    }

    @JsonProperty("negate")
    public void setNegate(Boolean negate) {
        this.negate = negate;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeMatch")
    public List<CpeMatch> getCpeMatch() {
        return cpeMatch;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cpeMatch")
    public void setCpeMatch(List<CpeMatch> cpeMatch) {
        this.cpeMatch = cpeMatch;
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
        sb.append(Node.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("operator");
        sb.append('=');
        sb.append(((this.operator == null)?"<null>":this.operator));
        sb.append(',');
        sb.append("negate");
        sb.append('=');
        sb.append(((this.negate == null)?"<null>":this.negate));
        sb.append(',');
        sb.append("cpeMatch");
        sb.append('=');
        sb.append(((this.cpeMatch == null)?"<null>":this.cpeMatch));
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
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.operator == null)? 0 :this.operator.hashCode()));
        result = ((result* 31)+((this.negate == null)? 0 :this.negate.hashCode()));
        result = ((result* 31)+((this.cpeMatch == null)? 0 :this.cpeMatch.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Node) == false) {
            return false;
        }
        Node rhs = ((Node) other);
        return (((((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))&&((this.operator == rhs.operator)||((this.operator!= null)&&this.operator.equals(rhs.operator))))&&((this.negate == rhs.negate)||((this.negate!= null)&&this.negate.equals(rhs.negate))))&&((this.cpeMatch == rhs.cpeMatch)||((this.cpeMatch!= null)&&this.cpeMatch.equals(rhs.cpeMatch))));
    }

    public enum Operator {

        AND("AND"),
        OR("OR");
        private final String value;
        private final static Map<String, Node.Operator> CONSTANTS = new HashMap<String, Node.Operator>();

        static {
            for (Node.Operator c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Operator(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Node.Operator fromValue(String value) {
            Node.Operator constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
