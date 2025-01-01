
package com.github.phanikb.nvd.api2.cve;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "source",
    "type",
    "cvssData"
})
public class CvssV40 {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    private String source;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    private CvssV40 .Type type;
    /**
     * JSON Schema for Common Vulnerability Scoring System version 4.0
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("cvssData")
    private CvssV40__1 cvssData;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    public CvssV40 .Type getType() {
        return type;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(CvssV40 .Type type) {
        this.type = type;
    }

    /**
     * JSON Schema for Common Vulnerability Scoring System version 4.0
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("cvssData")
    public CvssV40__1 getCvssData() {
        return cvssData;
    }

    /**
     * JSON Schema for Common Vulnerability Scoring System version 4.0
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("cvssData")
    public void setCvssData(CvssV40__1 cvssData) {
        this.cvssData = cvssData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CvssV40 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("source");
        sb.append('=');
        sb.append(((this.source == null)?"<null>":this.source));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("cvssData");
        sb.append('=');
        sb.append(((this.cvssData == null)?"<null>":this.cvssData));
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
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.cvssData == null)? 0 :this.cvssData.hashCode()));
        result = ((result* 31)+((this.source == null)? 0 :this.source.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CvssV40) == false) {
            return false;
        }
        CvssV40 rhs = ((CvssV40) other);
        return ((((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type)))&&((this.cvssData == rhs.cvssData)||((this.cvssData!= null)&&this.cvssData.equals(rhs.cvssData))))&&((this.source == rhs.source)||((this.source!= null)&&this.source.equals(rhs.source))));
    }

    public enum Type {

        PRIMARY("Primary"),
        SECONDARY("Secondary");
        private final String value;
        private final static Map<String, CvssV40 .Type> CONSTANTS = new HashMap<String, CvssV40 .Type>();

        static {
            for (CvssV40 .Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
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
        public static CvssV40 .Type fromValue(String value) {
            CvssV40 .Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
