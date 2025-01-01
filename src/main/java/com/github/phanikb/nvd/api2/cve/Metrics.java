
package com.github.phanikb.nvd.api2.cve;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Metric scores for a vulnerability as found on NVD.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cvssMetricV40",
    "cvssMetricV31",
    "cvssMetricV30",
    "cvssMetricV2"
})
public class Metrics {

    /**
     * CVSS V4.0 score.
     * 
     */
    @JsonProperty("cvssMetricV40")
    @JsonPropertyDescription("CVSS V4.0 score.")
    private List<CvssV40> cvssMetricV40 = new ArrayList<CvssV40>();
    /**
     * CVSS V3.1 score.
     * 
     */
    @JsonProperty("cvssMetricV31")
    @JsonPropertyDescription("CVSS V3.1 score.")
    private List<CvssV31> cvssMetricV31 = new ArrayList<CvssV31>();
    /**
     * CVSS V3.0 score.
     * 
     */
    @JsonProperty("cvssMetricV30")
    @JsonPropertyDescription("CVSS V3.0 score.")
    private List<CvssV30> cvssMetricV30 = new ArrayList<CvssV30>();
    /**
     * CVSS V2.0 score.
     * 
     */
    @JsonProperty("cvssMetricV2")
    @JsonPropertyDescription("CVSS V2.0 score.")
    private List<CvssV2> cvssMetricV2 = new ArrayList<CvssV2>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * CVSS V4.0 score.
     * 
     */
    @JsonProperty("cvssMetricV40")
    public List<CvssV40> getCvssMetricV40() {
        return cvssMetricV40;
    }

    /**
     * CVSS V4.0 score.
     * 
     */
    @JsonProperty("cvssMetricV40")
    public void setCvssMetricV40(List<CvssV40> cvssMetricV40) {
        this.cvssMetricV40 = cvssMetricV40;
    }

    /**
     * CVSS V3.1 score.
     * 
     */
    @JsonProperty("cvssMetricV31")
    public List<CvssV31> getCvssMetricV31() {
        return cvssMetricV31;
    }

    /**
     * CVSS V3.1 score.
     * 
     */
    @JsonProperty("cvssMetricV31")
    public void setCvssMetricV31(List<CvssV31> cvssMetricV31) {
        this.cvssMetricV31 = cvssMetricV31;
    }

    /**
     * CVSS V3.0 score.
     * 
     */
    @JsonProperty("cvssMetricV30")
    public List<CvssV30> getCvssMetricV30() {
        return cvssMetricV30;
    }

    /**
     * CVSS V3.0 score.
     * 
     */
    @JsonProperty("cvssMetricV30")
    public void setCvssMetricV30(List<CvssV30> cvssMetricV30) {
        this.cvssMetricV30 = cvssMetricV30;
    }

    /**
     * CVSS V2.0 score.
     * 
     */
    @JsonProperty("cvssMetricV2")
    public List<CvssV2> getCvssMetricV2() {
        return cvssMetricV2;
    }

    /**
     * CVSS V2.0 score.
     * 
     */
    @JsonProperty("cvssMetricV2")
    public void setCvssMetricV2(List<CvssV2> cvssMetricV2) {
        this.cvssMetricV2 = cvssMetricV2;
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
        sb.append(Metrics.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cvssMetricV40");
        sb.append('=');
        sb.append(((this.cvssMetricV40 == null)?"<null>":this.cvssMetricV40));
        sb.append(',');
        sb.append("cvssMetricV31");
        sb.append('=');
        sb.append(((this.cvssMetricV31 == null)?"<null>":this.cvssMetricV31));
        sb.append(',');
        sb.append("cvssMetricV30");
        sb.append('=');
        sb.append(((this.cvssMetricV30 == null)?"<null>":this.cvssMetricV30));
        sb.append(',');
        sb.append("cvssMetricV2");
        sb.append('=');
        sb.append(((this.cvssMetricV2 == null)?"<null>":this.cvssMetricV2));
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
        result = ((result* 31)+((this.cvssMetricV40 == null)? 0 :this.cvssMetricV40 .hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.cvssMetricV31 == null)? 0 :this.cvssMetricV31 .hashCode()));
        result = ((result* 31)+((this.cvssMetricV30 == null)? 0 :this.cvssMetricV30 .hashCode()));
        result = ((result* 31)+((this.cvssMetricV2 == null)? 0 :this.cvssMetricV2 .hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Metrics) == false) {
            return false;
        }
        Metrics rhs = ((Metrics) other);
        return ((((((this.cvssMetricV40 == rhs.cvssMetricV40)||((this.cvssMetricV40 != null)&&this.cvssMetricV40 .equals(rhs.cvssMetricV40)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.cvssMetricV31 == rhs.cvssMetricV31)||((this.cvssMetricV31 != null)&&this.cvssMetricV31 .equals(rhs.cvssMetricV31))))&&((this.cvssMetricV30 == rhs.cvssMetricV30)||((this.cvssMetricV30 != null)&&this.cvssMetricV30 .equals(rhs.cvssMetricV30))))&&((this.cvssMetricV2 == rhs.cvssMetricV2)||((this.cvssMetricV2 != null)&&this.cvssMetricV2 .equals(rhs.cvssMetricV2))));
    }

}
