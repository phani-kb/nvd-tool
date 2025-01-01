
package com.github.phanikb.nvd.api2.cve;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "sourceIdentifier",
    "vulnStatus",
    "published",
    "lastModified",
    "evaluatorComment",
    "evaluatorSolution",
    "evaluatorImpact",
    "cisaExploitAdd",
    "cisaActionDue",
    "cisaRequiredAction",
    "cisaVulnerabilityName",
    "cveTags",
    "descriptions",
    "references",
    "metrics",
    "weaknesses",
    "configurations",
    "vendorComments"
})
public class CveItem {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    private String id;
    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;
    @JsonProperty("vulnStatus")
    private String vulnStatus;
    /**
     * 
     * (Required)
     * 
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]", timezone = "UTC")
    @JsonProperty("published")
    private LocalDateTime published;
    /**
     * 
     * (Required)
     * 
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]", timezone = "UTC")
    @JsonProperty("lastModified")
    private LocalDateTime lastModified;
    @JsonProperty("evaluatorComment")
    private String evaluatorComment;
    @JsonProperty("evaluatorSolution")
    private String evaluatorSolution;
    @JsonProperty("evaluatorImpact")
    private String evaluatorImpact;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("cisaExploitAdd")
    private LocalDate cisaExploitAdd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("cisaActionDue")
    private LocalDate cisaActionDue;
    @JsonProperty("cisaRequiredAction")
    private String cisaRequiredAction;
    @JsonProperty("cisaVulnerabilityName")
    private String cisaVulnerabilityName;
    @JsonProperty("cveTags")
    private List<CveTag> cveTags = new ArrayList<CveTag>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("descriptions")
    private List<LangString> descriptions = new ArrayList<LangString>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("references")
    private List<Reference> references = new ArrayList<Reference>();
    /**
     * Metric scores for a vulnerability as found on NVD.
     * 
     */
    @JsonProperty("metrics")
    @JsonPropertyDescription("Metric scores for a vulnerability as found on NVD.")
    private Metrics metrics;
    @JsonProperty("weaknesses")
    private List<Weakness> weaknesses = new ArrayList<Weakness>();
    @JsonProperty("configurations")
    private List<Config> configurations = new ArrayList<Config>();
    @JsonProperty("vendorComments")
    private List<VendorComment> vendorComments = new ArrayList<VendorComment>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("sourceIdentifier")
    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    @JsonProperty("sourceIdentifier")
    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    @JsonProperty("vulnStatus")
    public String getVulnStatus() {
        return vulnStatus;
    }

    @JsonProperty("vulnStatus")
    public void setVulnStatus(String vulnStatus) {
        this.vulnStatus = vulnStatus;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("published")
    public LocalDateTime getPublished() {
        return published;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("published")
    public void setPublished(LocalDateTime published) {
        this.published = published;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastModified")
    public LocalDateTime getLastModified() {
        return lastModified;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastModified")
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @JsonProperty("evaluatorComment")
    public String getEvaluatorComment() {
        return evaluatorComment;
    }

    @JsonProperty("evaluatorComment")
    public void setEvaluatorComment(String evaluatorComment) {
        this.evaluatorComment = evaluatorComment;
    }

    @JsonProperty("evaluatorSolution")
    public String getEvaluatorSolution() {
        return evaluatorSolution;
    }

    @JsonProperty("evaluatorSolution")
    public void setEvaluatorSolution(String evaluatorSolution) {
        this.evaluatorSolution = evaluatorSolution;
    }

    @JsonProperty("evaluatorImpact")
    public String getEvaluatorImpact() {
        return evaluatorImpact;
    }

    @JsonProperty("evaluatorImpact")
    public void setEvaluatorImpact(String evaluatorImpact) {
        this.evaluatorImpact = evaluatorImpact;
    }

    @JsonProperty("cisaExploitAdd")
    public LocalDate getCisaExploitAdd() {
        return cisaExploitAdd;
    }

    @JsonProperty("cisaExploitAdd")
    public void setCisaExploitAdd(LocalDate cisaExploitAdd) {
        this.cisaExploitAdd = cisaExploitAdd;
    }

    @JsonProperty("cisaActionDue")
    public LocalDate getCisaActionDue() {
        return cisaActionDue;
    }

    @JsonProperty("cisaActionDue")
    public void setCisaActionDue(LocalDate cisaActionDue) {
        this.cisaActionDue = cisaActionDue;
    }

    @JsonProperty("cisaRequiredAction")
    public String getCisaRequiredAction() {
        return cisaRequiredAction;
    }

    @JsonProperty("cisaRequiredAction")
    public void setCisaRequiredAction(String cisaRequiredAction) {
        this.cisaRequiredAction = cisaRequiredAction;
    }

    @JsonProperty("cisaVulnerabilityName")
    public String getCisaVulnerabilityName() {
        return cisaVulnerabilityName;
    }

    @JsonProperty("cisaVulnerabilityName")
    public void setCisaVulnerabilityName(String cisaVulnerabilityName) {
        this.cisaVulnerabilityName = cisaVulnerabilityName;
    }

    @JsonProperty("cveTags")
    public List<CveTag> getCveTags() {
        return cveTags;
    }

    @JsonProperty("cveTags")
    public void setCveTags(List<CveTag> cveTags) {
        this.cveTags = cveTags;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("descriptions")
    public List<LangString> getDescriptions() {
        return descriptions;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("descriptions")
    public void setDescriptions(List<LangString> descriptions) {
        this.descriptions = descriptions;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("references")
    public List<Reference> getReferences() {
        return references;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("references")
    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    /**
     * Metric scores for a vulnerability as found on NVD.
     * 
     */
    @JsonProperty("metrics")
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * Metric scores for a vulnerability as found on NVD.
     * 
     */
    @JsonProperty("metrics")
    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    @JsonProperty("weaknesses")
    public List<Weakness> getWeaknesses() {
        return weaknesses;
    }

    @JsonProperty("weaknesses")
    public void setWeaknesses(List<Weakness> weaknesses) {
        this.weaknesses = weaknesses;
    }

    @JsonProperty("configurations")
    public List<Config> getConfigurations() {
        return configurations;
    }

    @JsonProperty("configurations")
    public void setConfigurations(List<Config> configurations) {
        this.configurations = configurations;
    }

    @JsonProperty("vendorComments")
    public List<VendorComment> getVendorComments() {
        return vendorComments;
    }

    @JsonProperty("vendorComments")
    public void setVendorComments(List<VendorComment> vendorComments) {
        this.vendorComments = vendorComments;
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
        sb.append(CveItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("sourceIdentifier");
        sb.append('=');
        sb.append(((this.sourceIdentifier == null)?"<null>":this.sourceIdentifier));
        sb.append(',');
        sb.append("vulnStatus");
        sb.append('=');
        sb.append(((this.vulnStatus == null)?"<null>":this.vulnStatus));
        sb.append(',');
        sb.append("published");
        sb.append('=');
        sb.append(((this.published == null)?"<null>":this.published));
        sb.append(',');
        sb.append("lastModified");
        sb.append('=');
        sb.append(((this.lastModified == null)?"<null>":this.lastModified));
        sb.append(',');
        sb.append("evaluatorComment");
        sb.append('=');
        sb.append(((this.evaluatorComment == null)?"<null>":this.evaluatorComment));
        sb.append(',');
        sb.append("evaluatorSolution");
        sb.append('=');
        sb.append(((this.evaluatorSolution == null)?"<null>":this.evaluatorSolution));
        sb.append(',');
        sb.append("evaluatorImpact");
        sb.append('=');
        sb.append(((this.evaluatorImpact == null)?"<null>":this.evaluatorImpact));
        sb.append(',');
        sb.append("cisaExploitAdd");
        sb.append('=');
        sb.append(((this.cisaExploitAdd == null)?"<null>":this.cisaExploitAdd));
        sb.append(',');
        sb.append("cisaActionDue");
        sb.append('=');
        sb.append(((this.cisaActionDue == null)?"<null>":this.cisaActionDue));
        sb.append(',');
        sb.append("cisaRequiredAction");
        sb.append('=');
        sb.append(((this.cisaRequiredAction == null)?"<null>":this.cisaRequiredAction));
        sb.append(',');
        sb.append("cisaVulnerabilityName");
        sb.append('=');
        sb.append(((this.cisaVulnerabilityName == null)?"<null>":this.cisaVulnerabilityName));
        sb.append(',');
        sb.append("cveTags");
        sb.append('=');
        sb.append(((this.cveTags == null)?"<null>":this.cveTags));
        sb.append(',');
        sb.append("descriptions");
        sb.append('=');
        sb.append(((this.descriptions == null)?"<null>":this.descriptions));
        sb.append(',');
        sb.append("references");
        sb.append('=');
        sb.append(((this.references == null)?"<null>":this.references));
        sb.append(',');
        sb.append("metrics");
        sb.append('=');
        sb.append(((this.metrics == null)?"<null>":this.metrics));
        sb.append(',');
        sb.append("weaknesses");
        sb.append('=');
        sb.append(((this.weaknesses == null)?"<null>":this.weaknesses));
        sb.append(',');
        sb.append("configurations");
        sb.append('=');
        sb.append(((this.configurations == null)?"<null>":this.configurations));
        sb.append(',');
        sb.append("vendorComments");
        sb.append('=');
        sb.append(((this.vendorComments == null)?"<null>":this.vendorComments));
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
        result = ((result* 31)+((this.sourceIdentifier == null)? 0 :this.sourceIdentifier.hashCode()));
        result = ((result* 31)+((this.references == null)? 0 :this.references.hashCode()));
        result = ((result* 31)+((this.configurations == null)? 0 :this.configurations.hashCode()));
        result = ((result* 31)+((this.vendorComments == null)? 0 :this.vendorComments.hashCode()));
        result = ((result* 31)+((this.evaluatorSolution == null)? 0 :this.evaluatorSolution.hashCode()));
        result = ((result* 31)+((this.cisaExploitAdd == null)? 0 :this.cisaExploitAdd.hashCode()));
        result = ((result* 31)+((this.published == null)? 0 :this.published.hashCode()));
        result = ((result* 31)+((this.vulnStatus == null)? 0 :this.vulnStatus.hashCode()));
        result = ((result* 31)+((this.descriptions == null)? 0 :this.descriptions.hashCode()));
        result = ((result* 31)+((this.cveTags == null)? 0 :this.cveTags.hashCode()));
        result = ((result* 31)+((this.cisaActionDue == null)? 0 :this.cisaActionDue.hashCode()));
        result = ((result* 31)+((this.cisaVulnerabilityName == null)? 0 :this.cisaVulnerabilityName.hashCode()));
        result = ((result* 31)+((this.cisaRequiredAction == null)? 0 :this.cisaRequiredAction.hashCode()));
        result = ((result* 31)+((this.weaknesses == null)? 0 :this.weaknesses.hashCode()));
        result = ((result* 31)+((this.evaluatorImpact == null)? 0 :this.evaluatorImpact.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.lastModified == null)? 0 :this.lastModified.hashCode()));
        result = ((result* 31)+((this.metrics == null)? 0 :this.metrics.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.evaluatorComment == null)? 0 :this.evaluatorComment.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CveItem) == false) {
            return false;
        }
        CveItem rhs = ((CveItem) other);
        return (((((((((((((((((((((this.sourceIdentifier == rhs.sourceIdentifier)||((this.sourceIdentifier!= null)&&this.sourceIdentifier.equals(rhs.sourceIdentifier)))&&((this.references == rhs.references)||((this.references!= null)&&this.references.equals(rhs.references))))&&((this.configurations == rhs.configurations)||((this.configurations!= null)&&this.configurations.equals(rhs.configurations))))&&((this.vendorComments == rhs.vendorComments)||((this.vendorComments!= null)&&this.vendorComments.equals(rhs.vendorComments))))&&((this.evaluatorSolution == rhs.evaluatorSolution)||((this.evaluatorSolution!= null)&&this.evaluatorSolution.equals(rhs.evaluatorSolution))))&&((this.cisaExploitAdd == rhs.cisaExploitAdd)||((this.cisaExploitAdd!= null)&&this.cisaExploitAdd.equals(rhs.cisaExploitAdd))))&&((this.published == rhs.published)||((this.published!= null)&&this.published.equals(rhs.published))))&&((this.vulnStatus == rhs.vulnStatus)||((this.vulnStatus!= null)&&this.vulnStatus.equals(rhs.vulnStatus))))&&((this.descriptions == rhs.descriptions)||((this.descriptions!= null)&&this.descriptions.equals(rhs.descriptions))))&&((this.cveTags == rhs.cveTags)||((this.cveTags!= null)&&this.cveTags.equals(rhs.cveTags))))&&((this.cisaActionDue == rhs.cisaActionDue)||((this.cisaActionDue!= null)&&this.cisaActionDue.equals(rhs.cisaActionDue))))&&((this.cisaVulnerabilityName == rhs.cisaVulnerabilityName)||((this.cisaVulnerabilityName!= null)&&this.cisaVulnerabilityName.equals(rhs.cisaVulnerabilityName))))&&((this.cisaRequiredAction == rhs.cisaRequiredAction)||((this.cisaRequiredAction!= null)&&this.cisaRequiredAction.equals(rhs.cisaRequiredAction))))&&((this.weaknesses == rhs.weaknesses)||((this.weaknesses!= null)&&this.weaknesses.equals(rhs.weaknesses))))&&((this.evaluatorImpact == rhs.evaluatorImpact)||((this.evaluatorImpact!= null)&&this.evaluatorImpact.equals(rhs.evaluatorImpact))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.lastModified == rhs.lastModified)||((this.lastModified!= null)&&this.lastModified.equals(rhs.lastModified))))&&((this.metrics == rhs.metrics)||((this.metrics!= null)&&this.metrics.equals(rhs.metrics))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.evaluatorComment == rhs.evaluatorComment)||((this.evaluatorComment!= null)&&this.evaluatorComment.equals(rhs.evaluatorComment))));
    }

}
