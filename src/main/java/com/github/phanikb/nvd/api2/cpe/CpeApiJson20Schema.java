
package com.github.phanikb.nvd.api2.cpe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * JSON Schema for NVD Common Product Enumeration (CPE) API version 2.0
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resultsPerPage",
    "startIndex",
    "totalResults",
    "format",
    "version",
    "timestamp",
    "products"
})
public class CpeApiJson20Schema {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultsPerPage")
    private Integer resultsPerPage;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("startIndex")
    private Integer startIndex;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("totalResults")
    private Integer totalResults;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("format")
    private String format;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    private String version;
    /**
     * 
     * (Required)
     * 
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]", timezone = "UTC")
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    /**
     * NVD feed array of CPE
     * (Required)
     * 
     */
    @JsonProperty("products")
    @JsonPropertyDescription("NVD feed array of CPE")
    private List<DefCpe> products = new ArrayList<DefCpe>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultsPerPage")
    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultsPerPage")
    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("startIndex")
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("startIndex")
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("totalResults")
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("totalResults")
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * NVD feed array of CPE
     * (Required)
     * 
     */
    @JsonProperty("products")
    public List<DefCpe> getProducts() {
        return products;
    }

    /**
     * NVD feed array of CPE
     * (Required)
     * 
     */
    @JsonProperty("products")
    public void setProducts(List<DefCpe> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CpeApiJson20Schema.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("resultsPerPage");
        sb.append('=');
        sb.append(((this.resultsPerPage == null)?"<null>":this.resultsPerPage));
        sb.append(',');
        sb.append("startIndex");
        sb.append('=');
        sb.append(((this.startIndex == null)?"<null>":this.startIndex));
        sb.append(',');
        sb.append("totalResults");
        sb.append('=');
        sb.append(((this.totalResults == null)?"<null>":this.totalResults));
        sb.append(',');
        sb.append("format");
        sb.append('=');
        sb.append(((this.format == null)?"<null>":this.format));
        sb.append(',');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null)?"<null>":this.version));
        sb.append(',');
        sb.append("timestamp");
        sb.append('=');
        sb.append(((this.timestamp == null)?"<null>":this.timestamp));
        sb.append(',');
        sb.append("products");
        sb.append('=');
        sb.append(((this.products == null)?"<null>":this.products));
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
        result = ((result* 31)+((this.startIndex == null)? 0 :this.startIndex.hashCode()));
        result = ((result* 31)+((this.totalResults == null)? 0 :this.totalResults.hashCode()));
        result = ((result* 31)+((this.resultsPerPage == null)? 0 :this.resultsPerPage.hashCode()));
        result = ((result* 31)+((this.format == null)? 0 :this.format.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.timestamp == null)? 0 :this.timestamp.hashCode()));
        result = ((result* 31)+((this.products == null)? 0 :this.products.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CpeApiJson20Schema) == false) {
            return false;
        }
        CpeApiJson20Schema rhs = ((CpeApiJson20Schema) other);
        return ((((((((this.startIndex == rhs.startIndex)||((this.startIndex!= null)&&this.startIndex.equals(rhs.startIndex)))&&((this.totalResults == rhs.totalResults)||((this.totalResults!= null)&&this.totalResults.equals(rhs.totalResults))))&&((this.resultsPerPage == rhs.resultsPerPage)||((this.resultsPerPage!= null)&&this.resultsPerPage.equals(rhs.resultsPerPage))))&&((this.format == rhs.format)||((this.format!= null)&&this.format.equals(rhs.format))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.timestamp == rhs.timestamp)||((this.timestamp!= null)&&this.timestamp.equals(rhs.timestamp))))&&((this.products == rhs.products)||((this.products!= null)&&this.products.equals(rhs.products))));
    }

}
