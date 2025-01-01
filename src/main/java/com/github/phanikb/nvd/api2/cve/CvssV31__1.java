
package com.github.phanikb.nvd.api2.cve;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * JSON Schema for Common Vulnerability Scoring System version 3.1
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "version",
    "vectorString",
    "attackVector",
    "attackComplexity",
    "privilegesRequired",
    "userInteraction",
    "scope",
    "confidentialityImpact",
    "integrityImpact",
    "availabilityImpact",
    "baseScore",
    "baseSeverity",
    "exploitCodeMaturity",
    "remediationLevel",
    "reportConfidence",
    "temporalScore",
    "temporalSeverity",
    "confidentialityRequirement",
    "integrityRequirement",
    "availabilityRequirement",
    "modifiedAttackVector",
    "modifiedAttackComplexity",
    "modifiedPrivilegesRequired",
    "modifiedUserInteraction",
    "modifiedScope",
    "modifiedConfidentialityImpact",
    "modifiedIntegrityImpact",
    "modifiedAvailabilityImpact",
    "environmentalScore",
    "environmentalSeverity"
})
public class CvssV31__1 {

    /**
     * CVSS Version
     * (Required)
     * 
     */
    @JsonProperty("version")
    @JsonPropertyDescription("CVSS Version")
    private CvssV31__1 .Version version;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vectorString")
    private String vectorString;
    @JsonProperty("attackVector")
    private CvssV31__1 .AttackVectorType attackVector;
    @JsonProperty("attackComplexity")
    private CvssV31__1 .AttackComplexityType attackComplexity;
    @JsonProperty("privilegesRequired")
    private CvssV31__1 .PrivilegesRequiredType privilegesRequired;
    @JsonProperty("userInteraction")
    private CvssV31__1 .UserInteractionType userInteraction;
    @JsonProperty("scope")
    private CvssV31__1 .ScopeType scope;
    @JsonProperty("confidentialityImpact")
    private CvssV31__1 .CiaType confidentialityImpact;
    @JsonProperty("integrityImpact")
    private CvssV31__1 .CiaType integrityImpact;
    @JsonProperty("availabilityImpact")
    private CvssV31__1 .CiaType availabilityImpact;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("baseScore")
    private Double baseScore;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("baseSeverity")
    private CvssV31__1 .SeverityType baseSeverity;
    @JsonProperty("exploitCodeMaturity")
    private CvssV31__1 .ExploitCodeMaturityType exploitCodeMaturity;
    @JsonProperty("remediationLevel")
    private CvssV31__1 .RemediationLevelType remediationLevel;
    @JsonProperty("reportConfidence")
    private CvssV31__1 .ConfidenceType reportConfidence;
    @JsonProperty("temporalScore")
    private Double temporalScore;
    @JsonProperty("temporalSeverity")
    private CvssV31__1 .SeverityType temporalSeverity;
    @JsonProperty("confidentialityRequirement")
    private CvssV31__1 .CiaRequirementType confidentialityRequirement;
    @JsonProperty("integrityRequirement")
    private CvssV31__1 .CiaRequirementType integrityRequirement;
    @JsonProperty("availabilityRequirement")
    private CvssV31__1 .CiaRequirementType availabilityRequirement;
    @JsonProperty("modifiedAttackVector")
    private CvssV31__1 .ModifiedAttackVectorType modifiedAttackVector;
    @JsonProperty("modifiedAttackComplexity")
    private CvssV31__1 .ModifiedAttackComplexityType modifiedAttackComplexity;
    @JsonProperty("modifiedPrivilegesRequired")
    private CvssV31__1 .ModifiedPrivilegesRequiredType modifiedPrivilegesRequired;
    @JsonProperty("modifiedUserInteraction")
    private CvssV31__1 .ModifiedUserInteractionType modifiedUserInteraction;
    @JsonProperty("modifiedScope")
    private CvssV31__1 .ModifiedScopeType modifiedScope;
    @JsonProperty("modifiedConfidentialityImpact")
    private CvssV31__1 .ModifiedCiaType modifiedConfidentialityImpact;
    @JsonProperty("modifiedIntegrityImpact")
    private CvssV31__1 .ModifiedCiaType modifiedIntegrityImpact;
    @JsonProperty("modifiedAvailabilityImpact")
    private CvssV31__1 .ModifiedCiaType modifiedAvailabilityImpact;
    @JsonProperty("environmentalScore")
    private Double environmentalScore;
    @JsonProperty("environmentalSeverity")
    private CvssV31__1 .SeverityType environmentalSeverity;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * CVSS Version
     * (Required)
     * 
     */
    @JsonProperty("version")
    public CvssV31__1 .Version getVersion() {
        return version;
    }

    /**
     * CVSS Version
     * (Required)
     * 
     */
    @JsonProperty("version")
    public void setVersion(CvssV31__1 .Version version) {
        this.version = version;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vectorString")
    public String getVectorString() {
        return vectorString;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vectorString")
    public void setVectorString(String vectorString) {
        this.vectorString = vectorString;
    }

    @JsonProperty("attackVector")
    public CvssV31__1 .AttackVectorType getAttackVector() {
        return attackVector;
    }

    @JsonProperty("attackVector")
    public void setAttackVector(CvssV31__1 .AttackVectorType attackVector) {
        this.attackVector = attackVector;
    }

    @JsonProperty("attackComplexity")
    public CvssV31__1 .AttackComplexityType getAttackComplexity() {
        return attackComplexity;
    }

    @JsonProperty("attackComplexity")
    public void setAttackComplexity(CvssV31__1 .AttackComplexityType attackComplexity) {
        this.attackComplexity = attackComplexity;
    }

    @JsonProperty("privilegesRequired")
    public CvssV31__1 .PrivilegesRequiredType getPrivilegesRequired() {
        return privilegesRequired;
    }

    @JsonProperty("privilegesRequired")
    public void setPrivilegesRequired(CvssV31__1 .PrivilegesRequiredType privilegesRequired) {
        this.privilegesRequired = privilegesRequired;
    }

    @JsonProperty("userInteraction")
    public CvssV31__1 .UserInteractionType getUserInteraction() {
        return userInteraction;
    }

    @JsonProperty("userInteraction")
    public void setUserInteraction(CvssV31__1 .UserInteractionType userInteraction) {
        this.userInteraction = userInteraction;
    }

    @JsonProperty("scope")
    public CvssV31__1 .ScopeType getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(CvssV31__1 .ScopeType scope) {
        this.scope = scope;
    }

    @JsonProperty("confidentialityImpact")
    public CvssV31__1 .CiaType getConfidentialityImpact() {
        return confidentialityImpact;
    }

    @JsonProperty("confidentialityImpact")
    public void setConfidentialityImpact(CvssV31__1 .CiaType confidentialityImpact) {
        this.confidentialityImpact = confidentialityImpact;
    }

    @JsonProperty("integrityImpact")
    public CvssV31__1 .CiaType getIntegrityImpact() {
        return integrityImpact;
    }

    @JsonProperty("integrityImpact")
    public void setIntegrityImpact(CvssV31__1 .CiaType integrityImpact) {
        this.integrityImpact = integrityImpact;
    }

    @JsonProperty("availabilityImpact")
    public CvssV31__1 .CiaType getAvailabilityImpact() {
        return availabilityImpact;
    }

    @JsonProperty("availabilityImpact")
    public void setAvailabilityImpact(CvssV31__1 .CiaType availabilityImpact) {
        this.availabilityImpact = availabilityImpact;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("baseScore")
    public Double getBaseScore() {
        return baseScore;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("baseScore")
    public void setBaseScore(Double baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("baseSeverity")
    public CvssV31__1 .SeverityType getBaseSeverity() {
        return baseSeverity;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("baseSeverity")
    public void setBaseSeverity(CvssV31__1 .SeverityType baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

    @JsonProperty("exploitCodeMaturity")
    public CvssV31__1 .ExploitCodeMaturityType getExploitCodeMaturity() {
        return exploitCodeMaturity;
    }

    @JsonProperty("exploitCodeMaturity")
    public void setExploitCodeMaturity(CvssV31__1 .ExploitCodeMaturityType exploitCodeMaturity) {
        this.exploitCodeMaturity = exploitCodeMaturity;
    }

    @JsonProperty("remediationLevel")
    public CvssV31__1 .RemediationLevelType getRemediationLevel() {
        return remediationLevel;
    }

    @JsonProperty("remediationLevel")
    public void setRemediationLevel(CvssV31__1 .RemediationLevelType remediationLevel) {
        this.remediationLevel = remediationLevel;
    }

    @JsonProperty("reportConfidence")
    public CvssV31__1 .ConfidenceType getReportConfidence() {
        return reportConfidence;
    }

    @JsonProperty("reportConfidence")
    public void setReportConfidence(CvssV31__1 .ConfidenceType reportConfidence) {
        this.reportConfidence = reportConfidence;
    }

    @JsonProperty("temporalScore")
    public Double getTemporalScore() {
        return temporalScore;
    }

    @JsonProperty("temporalScore")
    public void setTemporalScore(Double temporalScore) {
        this.temporalScore = temporalScore;
    }

    @JsonProperty("temporalSeverity")
    public CvssV31__1 .SeverityType getTemporalSeverity() {
        return temporalSeverity;
    }

    @JsonProperty("temporalSeverity")
    public void setTemporalSeverity(CvssV31__1 .SeverityType temporalSeverity) {
        this.temporalSeverity = temporalSeverity;
    }

    @JsonProperty("confidentialityRequirement")
    public CvssV31__1 .CiaRequirementType getConfidentialityRequirement() {
        return confidentialityRequirement;
    }

    @JsonProperty("confidentialityRequirement")
    public void setConfidentialityRequirement(CvssV31__1 .CiaRequirementType confidentialityRequirement) {
        this.confidentialityRequirement = confidentialityRequirement;
    }

    @JsonProperty("integrityRequirement")
    public CvssV31__1 .CiaRequirementType getIntegrityRequirement() {
        return integrityRequirement;
    }

    @JsonProperty("integrityRequirement")
    public void setIntegrityRequirement(CvssV31__1 .CiaRequirementType integrityRequirement) {
        this.integrityRequirement = integrityRequirement;
    }

    @JsonProperty("availabilityRequirement")
    public CvssV31__1 .CiaRequirementType getAvailabilityRequirement() {
        return availabilityRequirement;
    }

    @JsonProperty("availabilityRequirement")
    public void setAvailabilityRequirement(CvssV31__1 .CiaRequirementType availabilityRequirement) {
        this.availabilityRequirement = availabilityRequirement;
    }

    @JsonProperty("modifiedAttackVector")
    public CvssV31__1 .ModifiedAttackVectorType getModifiedAttackVector() {
        return modifiedAttackVector;
    }

    @JsonProperty("modifiedAttackVector")
    public void setModifiedAttackVector(CvssV31__1 .ModifiedAttackVectorType modifiedAttackVector) {
        this.modifiedAttackVector = modifiedAttackVector;
    }

    @JsonProperty("modifiedAttackComplexity")
    public CvssV31__1 .ModifiedAttackComplexityType getModifiedAttackComplexity() {
        return modifiedAttackComplexity;
    }

    @JsonProperty("modifiedAttackComplexity")
    public void setModifiedAttackComplexity(CvssV31__1 .ModifiedAttackComplexityType modifiedAttackComplexity) {
        this.modifiedAttackComplexity = modifiedAttackComplexity;
    }

    @JsonProperty("modifiedPrivilegesRequired")
    public CvssV31__1 .ModifiedPrivilegesRequiredType getModifiedPrivilegesRequired() {
        return modifiedPrivilegesRequired;
    }

    @JsonProperty("modifiedPrivilegesRequired")
    public void setModifiedPrivilegesRequired(CvssV31__1 .ModifiedPrivilegesRequiredType modifiedPrivilegesRequired) {
        this.modifiedPrivilegesRequired = modifiedPrivilegesRequired;
    }

    @JsonProperty("modifiedUserInteraction")
    public CvssV31__1 .ModifiedUserInteractionType getModifiedUserInteraction() {
        return modifiedUserInteraction;
    }

    @JsonProperty("modifiedUserInteraction")
    public void setModifiedUserInteraction(CvssV31__1 .ModifiedUserInteractionType modifiedUserInteraction) {
        this.modifiedUserInteraction = modifiedUserInteraction;
    }

    @JsonProperty("modifiedScope")
    public CvssV31__1 .ModifiedScopeType getModifiedScope() {
        return modifiedScope;
    }

    @JsonProperty("modifiedScope")
    public void setModifiedScope(CvssV31__1 .ModifiedScopeType modifiedScope) {
        this.modifiedScope = modifiedScope;
    }

    @JsonProperty("modifiedConfidentialityImpact")
    public CvssV31__1 .ModifiedCiaType getModifiedConfidentialityImpact() {
        return modifiedConfidentialityImpact;
    }

    @JsonProperty("modifiedConfidentialityImpact")
    public void setModifiedConfidentialityImpact(CvssV31__1 .ModifiedCiaType modifiedConfidentialityImpact) {
        this.modifiedConfidentialityImpact = modifiedConfidentialityImpact;
    }

    @JsonProperty("modifiedIntegrityImpact")
    public CvssV31__1 .ModifiedCiaType getModifiedIntegrityImpact() {
        return modifiedIntegrityImpact;
    }

    @JsonProperty("modifiedIntegrityImpact")
    public void setModifiedIntegrityImpact(CvssV31__1 .ModifiedCiaType modifiedIntegrityImpact) {
        this.modifiedIntegrityImpact = modifiedIntegrityImpact;
    }

    @JsonProperty("modifiedAvailabilityImpact")
    public CvssV31__1 .ModifiedCiaType getModifiedAvailabilityImpact() {
        return modifiedAvailabilityImpact;
    }

    @JsonProperty("modifiedAvailabilityImpact")
    public void setModifiedAvailabilityImpact(CvssV31__1 .ModifiedCiaType modifiedAvailabilityImpact) {
        this.modifiedAvailabilityImpact = modifiedAvailabilityImpact;
    }

    @JsonProperty("environmentalScore")
    public Double getEnvironmentalScore() {
        return environmentalScore;
    }

    @JsonProperty("environmentalScore")
    public void setEnvironmentalScore(Double environmentalScore) {
        this.environmentalScore = environmentalScore;
    }

    @JsonProperty("environmentalSeverity")
    public CvssV31__1 .SeverityType getEnvironmentalSeverity() {
        return environmentalSeverity;
    }

    @JsonProperty("environmentalSeverity")
    public void setEnvironmentalSeverity(CvssV31__1 .SeverityType environmentalSeverity) {
        this.environmentalSeverity = environmentalSeverity;
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
        sb.append(CvssV31__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null)?"<null>":this.version));
        sb.append(',');
        sb.append("vectorString");
        sb.append('=');
        sb.append(((this.vectorString == null)?"<null>":this.vectorString));
        sb.append(',');
        sb.append("attackVector");
        sb.append('=');
        sb.append(((this.attackVector == null)?"<null>":this.attackVector));
        sb.append(',');
        sb.append("attackComplexity");
        sb.append('=');
        sb.append(((this.attackComplexity == null)?"<null>":this.attackComplexity));
        sb.append(',');
        sb.append("privilegesRequired");
        sb.append('=');
        sb.append(((this.privilegesRequired == null)?"<null>":this.privilegesRequired));
        sb.append(',');
        sb.append("userInteraction");
        sb.append('=');
        sb.append(((this.userInteraction == null)?"<null>":this.userInteraction));
        sb.append(',');
        sb.append("scope");
        sb.append('=');
        sb.append(((this.scope == null)?"<null>":this.scope));
        sb.append(',');
        sb.append("confidentialityImpact");
        sb.append('=');
        sb.append(((this.confidentialityImpact == null)?"<null>":this.confidentialityImpact));
        sb.append(',');
        sb.append("integrityImpact");
        sb.append('=');
        sb.append(((this.integrityImpact == null)?"<null>":this.integrityImpact));
        sb.append(',');
        sb.append("availabilityImpact");
        sb.append('=');
        sb.append(((this.availabilityImpact == null)?"<null>":this.availabilityImpact));
        sb.append(',');
        sb.append("baseScore");
        sb.append('=');
        sb.append(((this.baseScore == null)?"<null>":this.baseScore));
        sb.append(',');
        sb.append("baseSeverity");
        sb.append('=');
        sb.append(((this.baseSeverity == null)?"<null>":this.baseSeverity));
        sb.append(',');
        sb.append("exploitCodeMaturity");
        sb.append('=');
        sb.append(((this.exploitCodeMaturity == null)?"<null>":this.exploitCodeMaturity));
        sb.append(',');
        sb.append("remediationLevel");
        sb.append('=');
        sb.append(((this.remediationLevel == null)?"<null>":this.remediationLevel));
        sb.append(',');
        sb.append("reportConfidence");
        sb.append('=');
        sb.append(((this.reportConfidence == null)?"<null>":this.reportConfidence));
        sb.append(',');
        sb.append("temporalScore");
        sb.append('=');
        sb.append(((this.temporalScore == null)?"<null>":this.temporalScore));
        sb.append(',');
        sb.append("temporalSeverity");
        sb.append('=');
        sb.append(((this.temporalSeverity == null)?"<null>":this.temporalSeverity));
        sb.append(',');
        sb.append("confidentialityRequirement");
        sb.append('=');
        sb.append(((this.confidentialityRequirement == null)?"<null>":this.confidentialityRequirement));
        sb.append(',');
        sb.append("integrityRequirement");
        sb.append('=');
        sb.append(((this.integrityRequirement == null)?"<null>":this.integrityRequirement));
        sb.append(',');
        sb.append("availabilityRequirement");
        sb.append('=');
        sb.append(((this.availabilityRequirement == null)?"<null>":this.availabilityRequirement));
        sb.append(',');
        sb.append("modifiedAttackVector");
        sb.append('=');
        sb.append(((this.modifiedAttackVector == null)?"<null>":this.modifiedAttackVector));
        sb.append(',');
        sb.append("modifiedAttackComplexity");
        sb.append('=');
        sb.append(((this.modifiedAttackComplexity == null)?"<null>":this.modifiedAttackComplexity));
        sb.append(',');
        sb.append("modifiedPrivilegesRequired");
        sb.append('=');
        sb.append(((this.modifiedPrivilegesRequired == null)?"<null>":this.modifiedPrivilegesRequired));
        sb.append(',');
        sb.append("modifiedUserInteraction");
        sb.append('=');
        sb.append(((this.modifiedUserInteraction == null)?"<null>":this.modifiedUserInteraction));
        sb.append(',');
        sb.append("modifiedScope");
        sb.append('=');
        sb.append(((this.modifiedScope == null)?"<null>":this.modifiedScope));
        sb.append(',');
        sb.append("modifiedConfidentialityImpact");
        sb.append('=');
        sb.append(((this.modifiedConfidentialityImpact == null)?"<null>":this.modifiedConfidentialityImpact));
        sb.append(',');
        sb.append("modifiedIntegrityImpact");
        sb.append('=');
        sb.append(((this.modifiedIntegrityImpact == null)?"<null>":this.modifiedIntegrityImpact));
        sb.append(',');
        sb.append("modifiedAvailabilityImpact");
        sb.append('=');
        sb.append(((this.modifiedAvailabilityImpact == null)?"<null>":this.modifiedAvailabilityImpact));
        sb.append(',');
        sb.append("environmentalScore");
        sb.append('=');
        sb.append(((this.environmentalScore == null)?"<null>":this.environmentalScore));
        sb.append(',');
        sb.append("environmentalSeverity");
        sb.append('=');
        sb.append(((this.environmentalSeverity == null)?"<null>":this.environmentalSeverity));
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
        result = ((result* 31)+((this.modifiedPrivilegesRequired == null)? 0 :this.modifiedPrivilegesRequired.hashCode()));
        result = ((result* 31)+((this.reportConfidence == null)? 0 :this.reportConfidence.hashCode()));
        result = ((result* 31)+((this.modifiedConfidentialityImpact == null)? 0 :this.modifiedConfidentialityImpact.hashCode()));
        result = ((result* 31)+((this.availabilityImpact == null)? 0 :this.availabilityImpact.hashCode()));
        result = ((result* 31)+((this.privilegesRequired == null)? 0 :this.privilegesRequired.hashCode()));
        result = ((result* 31)+((this.baseScore == null)? 0 :this.baseScore.hashCode()));
        result = ((result* 31)+((this.temporalSeverity == null)? 0 :this.temporalSeverity.hashCode()));
        result = ((result* 31)+((this.userInteraction == null)? 0 :this.userInteraction.hashCode()));
        result = ((result* 31)+((this.integrityRequirement == null)? 0 :this.integrityRequirement.hashCode()));
        result = ((result* 31)+((this.modifiedAvailabilityImpact == null)? 0 :this.modifiedAvailabilityImpact.hashCode()));
        result = ((result* 31)+((this.availabilityRequirement == null)? 0 :this.availabilityRequirement.hashCode()));
        result = ((result* 31)+((this.modifiedScope == null)? 0 :this.modifiedScope.hashCode()));
        result = ((result* 31)+((this.attackComplexity == null)? 0 :this.attackComplexity.hashCode()));
        result = ((result* 31)+((this.scope == null)? 0 :this.scope.hashCode()));
        result = ((result* 31)+((this.attackVector == null)? 0 :this.attackVector.hashCode()));
        result = ((result* 31)+((this.integrityImpact == null)? 0 :this.integrityImpact.hashCode()));
        result = ((result* 31)+((this.modifiedIntegrityImpact == null)? 0 :this.modifiedIntegrityImpact.hashCode()));
        result = ((result* 31)+((this.vectorString == null)? 0 :this.vectorString.hashCode()));
        result = ((result* 31)+((this.exploitCodeMaturity == null)? 0 :this.exploitCodeMaturity.hashCode()));
        result = ((result* 31)+((this.temporalScore == null)? 0 :this.temporalScore.hashCode()));
        result = ((result* 31)+((this.modifiedAttackVector == null)? 0 :this.modifiedAttackVector.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.confidentialityRequirement == null)? 0 :this.confidentialityRequirement.hashCode()));
        result = ((result* 31)+((this.remediationLevel == null)? 0 :this.remediationLevel.hashCode()));
        result = ((result* 31)+((this.modifiedUserInteraction == null)? 0 :this.modifiedUserInteraction.hashCode()));
        result = ((result* 31)+((this.modifiedAttackComplexity == null)? 0 :this.modifiedAttackComplexity.hashCode()));
        result = ((result* 31)+((this.baseSeverity == null)? 0 :this.baseSeverity.hashCode()));
        result = ((result* 31)+((this.confidentialityImpact == null)? 0 :this.confidentialityImpact.hashCode()));
        result = ((result* 31)+((this.environmentalScore == null)? 0 :this.environmentalScore.hashCode()));
        result = ((result* 31)+((this.environmentalSeverity == null)? 0 :this.environmentalSeverity.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CvssV31__1) == false) {
            return false;
        }
        CvssV31__1 rhs = ((CvssV31__1) other);
        return ((((((((((((((((((((((((((((((((this.modifiedPrivilegesRequired == rhs.modifiedPrivilegesRequired)||((this.modifiedPrivilegesRequired!= null)&&this.modifiedPrivilegesRequired.equals(rhs.modifiedPrivilegesRequired)))&&((this.reportConfidence == rhs.reportConfidence)||((this.reportConfidence!= null)&&this.reportConfidence.equals(rhs.reportConfidence))))&&((this.modifiedConfidentialityImpact == rhs.modifiedConfidentialityImpact)||((this.modifiedConfidentialityImpact!= null)&&this.modifiedConfidentialityImpact.equals(rhs.modifiedConfidentialityImpact))))&&((this.availabilityImpact == rhs.availabilityImpact)||((this.availabilityImpact!= null)&&this.availabilityImpact.equals(rhs.availabilityImpact))))&&((this.privilegesRequired == rhs.privilegesRequired)||((this.privilegesRequired!= null)&&this.privilegesRequired.equals(rhs.privilegesRequired))))&&((this.baseScore == rhs.baseScore)||((this.baseScore!= null)&&this.baseScore.equals(rhs.baseScore))))&&((this.temporalSeverity == rhs.temporalSeverity)||((this.temporalSeverity!= null)&&this.temporalSeverity.equals(rhs.temporalSeverity))))&&((this.userInteraction == rhs.userInteraction)||((this.userInteraction!= null)&&this.userInteraction.equals(rhs.userInteraction))))&&((this.integrityRequirement == rhs.integrityRequirement)||((this.integrityRequirement!= null)&&this.integrityRequirement.equals(rhs.integrityRequirement))))&&((this.modifiedAvailabilityImpact == rhs.modifiedAvailabilityImpact)||((this.modifiedAvailabilityImpact!= null)&&this.modifiedAvailabilityImpact.equals(rhs.modifiedAvailabilityImpact))))&&((this.availabilityRequirement == rhs.availabilityRequirement)||((this.availabilityRequirement!= null)&&this.availabilityRequirement.equals(rhs.availabilityRequirement))))&&((this.modifiedScope == rhs.modifiedScope)||((this.modifiedScope!= null)&&this.modifiedScope.equals(rhs.modifiedScope))))&&((this.attackComplexity == rhs.attackComplexity)||((this.attackComplexity!= null)&&this.attackComplexity.equals(rhs.attackComplexity))))&&((this.scope == rhs.scope)||((this.scope!= null)&&this.scope.equals(rhs.scope))))&&((this.attackVector == rhs.attackVector)||((this.attackVector!= null)&&this.attackVector.equals(rhs.attackVector))))&&((this.integrityImpact == rhs.integrityImpact)||((this.integrityImpact!= null)&&this.integrityImpact.equals(rhs.integrityImpact))))&&((this.modifiedIntegrityImpact == rhs.modifiedIntegrityImpact)||((this.modifiedIntegrityImpact!= null)&&this.modifiedIntegrityImpact.equals(rhs.modifiedIntegrityImpact))))&&((this.vectorString == rhs.vectorString)||((this.vectorString!= null)&&this.vectorString.equals(rhs.vectorString))))&&((this.exploitCodeMaturity == rhs.exploitCodeMaturity)||((this.exploitCodeMaturity!= null)&&this.exploitCodeMaturity.equals(rhs.exploitCodeMaturity))))&&((this.temporalScore == rhs.temporalScore)||((this.temporalScore!= null)&&this.temporalScore.equals(rhs.temporalScore))))&&((this.modifiedAttackVector == rhs.modifiedAttackVector)||((this.modifiedAttackVector!= null)&&this.modifiedAttackVector.equals(rhs.modifiedAttackVector))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.confidentialityRequirement == rhs.confidentialityRequirement)||((this.confidentialityRequirement!= null)&&this.confidentialityRequirement.equals(rhs.confidentialityRequirement))))&&((this.remediationLevel == rhs.remediationLevel)||((this.remediationLevel!= null)&&this.remediationLevel.equals(rhs.remediationLevel))))&&((this.modifiedUserInteraction == rhs.modifiedUserInteraction)||((this.modifiedUserInteraction!= null)&&this.modifiedUserInteraction.equals(rhs.modifiedUserInteraction))))&&((this.modifiedAttackComplexity == rhs.modifiedAttackComplexity)||((this.modifiedAttackComplexity!= null)&&this.modifiedAttackComplexity.equals(rhs.modifiedAttackComplexity))))&&((this.baseSeverity == rhs.baseSeverity)||((this.baseSeverity!= null)&&this.baseSeverity.equals(rhs.baseSeverity))))&&((this.confidentialityImpact == rhs.confidentialityImpact)||((this.confidentialityImpact!= null)&&this.confidentialityImpact.equals(rhs.confidentialityImpact))))&&((this.environmentalScore == rhs.environmentalScore)||((this.environmentalScore!= null)&&this.environmentalScore.equals(rhs.environmentalScore))))&&((this.environmentalSeverity == rhs.environmentalSeverity)||((this.environmentalSeverity!= null)&&this.environmentalSeverity.equals(rhs.environmentalSeverity))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

    public enum AttackComplexityType {

        HIGH("HIGH"),
        LOW("LOW");
        private final String value;
        private final static Map<String, CvssV31__1 .AttackComplexityType> CONSTANTS = new HashMap<String, CvssV31__1 .AttackComplexityType>();

        static {
            for (CvssV31__1 .AttackComplexityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AttackComplexityType(String value) {
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
        public static CvssV31__1 .AttackComplexityType fromValue(String value) {
            CvssV31__1 .AttackComplexityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum AttackVectorType {

        NETWORK("NETWORK"),
        ADJACENT_NETWORK("ADJACENT_NETWORK"),
        LOCAL("LOCAL"),
        PHYSICAL("PHYSICAL");
        private final String value;
        private final static Map<String, CvssV31__1 .AttackVectorType> CONSTANTS = new HashMap<String, CvssV31__1 .AttackVectorType>();

        static {
            for (CvssV31__1 .AttackVectorType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AttackVectorType(String value) {
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
        public static CvssV31__1 .AttackVectorType fromValue(String value) {
            CvssV31__1 .AttackVectorType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum CiaRequirementType {

        LOW("LOW"),
        MEDIUM("MEDIUM"),
        HIGH("HIGH"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .CiaRequirementType> CONSTANTS = new HashMap<String, CvssV31__1 .CiaRequirementType>();

        static {
            for (CvssV31__1 .CiaRequirementType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        CiaRequirementType(String value) {
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
        public static CvssV31__1 .CiaRequirementType fromValue(String value) {
            CvssV31__1 .CiaRequirementType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum CiaType {

        NONE("NONE"),
        LOW("LOW"),
        HIGH("HIGH");
        private final String value;
        private final static Map<String, CvssV31__1 .CiaType> CONSTANTS = new HashMap<String, CvssV31__1 .CiaType>();

        static {
            for (CvssV31__1 .CiaType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        CiaType(String value) {
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
        public static CvssV31__1 .CiaType fromValue(String value) {
            CvssV31__1 .CiaType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ConfidenceType {

        UNKNOWN("UNKNOWN"),
        REASONABLE("REASONABLE"),
        CONFIRMED("CONFIRMED"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .ConfidenceType> CONSTANTS = new HashMap<String, CvssV31__1 .ConfidenceType>();

        static {
            for (CvssV31__1 .ConfidenceType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ConfidenceType(String value) {
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
        public static CvssV31__1 .ConfidenceType fromValue(String value) {
            CvssV31__1 .ConfidenceType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ExploitCodeMaturityType {

        UNPROVEN("UNPROVEN"),
        PROOF_OF_CONCEPT("PROOF_OF_CONCEPT"),
        FUNCTIONAL("FUNCTIONAL"),
        HIGH("HIGH"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .ExploitCodeMaturityType> CONSTANTS = new HashMap<String, CvssV31__1 .ExploitCodeMaturityType>();

        static {
            for (CvssV31__1 .ExploitCodeMaturityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ExploitCodeMaturityType(String value) {
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
        public static CvssV31__1 .ExploitCodeMaturityType fromValue(String value) {
            CvssV31__1 .ExploitCodeMaturityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedAttackComplexityType {

        HIGH("HIGH"),
        LOW("LOW"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .ModifiedAttackComplexityType> CONSTANTS = new HashMap<String, CvssV31__1 .ModifiedAttackComplexityType>();

        static {
            for (CvssV31__1 .ModifiedAttackComplexityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedAttackComplexityType(String value) {
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
        public static CvssV31__1 .ModifiedAttackComplexityType fromValue(String value) {
            CvssV31__1 .ModifiedAttackComplexityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedAttackVectorType {

        NETWORK("NETWORK"),
        ADJACENT_NETWORK("ADJACENT_NETWORK"),
        LOCAL("LOCAL"),
        PHYSICAL("PHYSICAL"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .ModifiedAttackVectorType> CONSTANTS = new HashMap<String, CvssV31__1 .ModifiedAttackVectorType>();

        static {
            for (CvssV31__1 .ModifiedAttackVectorType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedAttackVectorType(String value) {
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
        public static CvssV31__1 .ModifiedAttackVectorType fromValue(String value) {
            CvssV31__1 .ModifiedAttackVectorType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedCiaType {

        NONE("NONE"),
        LOW("LOW"),
        HIGH("HIGH"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .ModifiedCiaType> CONSTANTS = new HashMap<String, CvssV31__1 .ModifiedCiaType>();

        static {
            for (CvssV31__1 .ModifiedCiaType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedCiaType(String value) {
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
        public static CvssV31__1 .ModifiedCiaType fromValue(String value) {
            CvssV31__1 .ModifiedCiaType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedPrivilegesRequiredType {

        HIGH("HIGH"),
        LOW("LOW"),
        NONE("NONE"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .ModifiedPrivilegesRequiredType> CONSTANTS = new HashMap<String, CvssV31__1 .ModifiedPrivilegesRequiredType>();

        static {
            for (CvssV31__1 .ModifiedPrivilegesRequiredType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedPrivilegesRequiredType(String value) {
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
        public static CvssV31__1 .ModifiedPrivilegesRequiredType fromValue(String value) {
            CvssV31__1 .ModifiedPrivilegesRequiredType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedScopeType {

        UNCHANGED("UNCHANGED"),
        CHANGED("CHANGED"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .ModifiedScopeType> CONSTANTS = new HashMap<String, CvssV31__1 .ModifiedScopeType>();

        static {
            for (CvssV31__1 .ModifiedScopeType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedScopeType(String value) {
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
        public static CvssV31__1 .ModifiedScopeType fromValue(String value) {
            CvssV31__1 .ModifiedScopeType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedUserInteractionType {

        NONE("NONE"),
        REQUIRED("REQUIRED"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .ModifiedUserInteractionType> CONSTANTS = new HashMap<String, CvssV31__1 .ModifiedUserInteractionType>();

        static {
            for (CvssV31__1 .ModifiedUserInteractionType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedUserInteractionType(String value) {
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
        public static CvssV31__1 .ModifiedUserInteractionType fromValue(String value) {
            CvssV31__1 .ModifiedUserInteractionType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum PrivilegesRequiredType {

        HIGH("HIGH"),
        LOW("LOW"),
        NONE("NONE");
        private final String value;
        private final static Map<String, CvssV31__1 .PrivilegesRequiredType> CONSTANTS = new HashMap<String, CvssV31__1 .PrivilegesRequiredType>();

        static {
            for (CvssV31__1 .PrivilegesRequiredType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PrivilegesRequiredType(String value) {
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
        public static CvssV31__1 .PrivilegesRequiredType fromValue(String value) {
            CvssV31__1 .PrivilegesRequiredType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum RemediationLevelType {

        OFFICIAL_FIX("OFFICIAL_FIX"),
        TEMPORARY_FIX("TEMPORARY_FIX"),
        WORKAROUND("WORKAROUND"),
        UNAVAILABLE("UNAVAILABLE"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV31__1 .RemediationLevelType> CONSTANTS = new HashMap<String, CvssV31__1 .RemediationLevelType>();

        static {
            for (CvssV31__1 .RemediationLevelType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        RemediationLevelType(String value) {
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
        public static CvssV31__1 .RemediationLevelType fromValue(String value) {
            CvssV31__1 .RemediationLevelType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ScopeType {

        UNCHANGED("UNCHANGED"),
        CHANGED("CHANGED");
        private final String value;
        private final static Map<String, CvssV31__1 .ScopeType> CONSTANTS = new HashMap<String, CvssV31__1 .ScopeType>();

        static {
            for (CvssV31__1 .ScopeType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ScopeType(String value) {
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
        public static CvssV31__1 .ScopeType fromValue(String value) {
            CvssV31__1 .ScopeType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum SeverityType {

        NONE("NONE"),
        LOW("LOW"),
        MEDIUM("MEDIUM"),
        HIGH("HIGH"),
        CRITICAL("CRITICAL");
        private final String value;
        private final static Map<String, CvssV31__1 .SeverityType> CONSTANTS = new HashMap<String, CvssV31__1 .SeverityType>();

        static {
            for (CvssV31__1 .SeverityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        SeverityType(String value) {
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
        public static CvssV31__1 .SeverityType fromValue(String value) {
            CvssV31__1 .SeverityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum UserInteractionType {

        NONE("NONE"),
        REQUIRED("REQUIRED");
        private final String value;
        private final static Map<String, CvssV31__1 .UserInteractionType> CONSTANTS = new HashMap<String, CvssV31__1 .UserInteractionType>();

        static {
            for (CvssV31__1 .UserInteractionType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        UserInteractionType(String value) {
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
        public static CvssV31__1 .UserInteractionType fromValue(String value) {
            CvssV31__1 .UserInteractionType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * CVSS Version
     * 
     */
    public enum Version {

        _3_1("3.1");
        private final String value;
        private final static Map<String, CvssV31__1 .Version> CONSTANTS = new HashMap<String, CvssV31__1 .Version>();

        static {
            for (CvssV31__1 .Version c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Version(String value) {
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
        public static CvssV31__1 .Version fromValue(String value) {
            CvssV31__1 .Version constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
