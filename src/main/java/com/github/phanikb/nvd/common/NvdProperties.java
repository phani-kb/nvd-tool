package com.github.phanikb.nvd.common;

import java.io.InputStream;
import java.util.Optional;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import static com.github.phanikb.nvd.common.Constants.DEFAULT_RETRY_INTERVAL_SECS;

@Getter
@Setter
@ToString
public class NvdProperties {
    private static NvdProperties properties;
    private Nvd nvd;

    private NvdProperties() {
        // prevent instantiation
    }

    public static String getApiEndpoint(ApiEndpointType type) {
        ApiEndpointVersion version = properties.getNvd().getApi().getVersion();
        return switch (type) {
            case CVE -> switch (version) {
                case V2 -> properties.getNvd().getCve().getApiV2().getEp();
            };
            case CPE -> switch (version) {
                case V2 -> properties.getNvd().getCpe().getApiV2().getEp();
            };
            case CPE_MATCH -> switch (version) {
                case V2 -> properties.getNvd().getCpeMatch().getApiV2().getEp();
            };
            case CVE_HISTORY -> switch (version) {
                case V2 -> properties.getNvd().getCveHistory().getApiV2().getEp();
            };
        };
    }

    private static NvdProperties loadNvdProperties() {
        Optional<InputStream> optionalInputStream = Util.loadFileFromClasspath(Constants.NVD_PROPERTIES_FILE);
        return optionalInputStream.map(NvdProperties::getProperties).orElseGet(NvdProperties::createDefaultProperties);
    }

    private static NvdProperties createDefaultProperties() {
        NvdProperties defaultProps = new NvdProperties();
        Nvd nvd = new Nvd();

        Proxy proxy = new Proxy();
        proxy.setHost(null);
        proxy.setPort(null);
        nvd.setProxy(proxy);

        Api api = new Api();
        api.setVersion(ApiEndpointVersion.V2);
        api.setKey(null);
        api.setKeyUrl("https://nvd.nist.gov/developers/request-an-api-key");
        nvd.setApi(api);

        Endpoint cveEndpoint = new Endpoint();
        cveEndpoint.setEp("https://services.nvd.nist.gov/rest/json/cves/2.0");
        EndpointAndUrl cve = new EndpointAndUrl();
        cve.setApiV2(cveEndpoint);
        Url cveUrl = new Url();
        cveUrl.setValue("https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-{download-type}.json.{archive-type}");
        cve.setMainUrl(cveUrl.getValue());
        nvd.setCve(cve);

        Endpoint cpeEndpoint = new Endpoint();
        cpeEndpoint.setEp("https://services.nvd.nist.gov/rest/json/cpes/2.0");
        EndpointAndUrl cpe = new EndpointAndUrl();
        cpe.setApiV2(cpeEndpoint);
        Url cpeUrl = new Url();
        cpeUrl.setValue(
                "https://nvd.nist.gov/feeds/xml/cpe/dictionary/official-cpe-dictionary_v2.3.xml.{archive-type}");
        cpe.setMainUrl(cpeUrl.getValue());
        nvd.setCpe(cpe);

        Endpoint cpeMatchEndpoint = new Endpoint();
        cpeMatchEndpoint.setEp("https://services.nvd.nist.gov/rest/json/cpematch/2.0");
        EndpointAndUrl cpeMatch = new EndpointAndUrl();
        cpeMatch.setApiV2(cpeMatchEndpoint);
        Url cpeMatchUrl = new Url();
        cpeMatchUrl.setValue("https://nvd.nist.gov/feeds/json/cpematch/1.0/nvdcpematch-1.0.json.{archive-type}");
        cpeMatch.setMainUrl(cpeMatchUrl.getValue());
        nvd.setCpeMatch(cpeMatch);

        CveHistory cveHistory = new CveHistory();
        Endpoint cveHistoryEndpoint = new Endpoint();
        cveHistoryEndpoint.setEp("https://services.nvd.nist.gov/rest/json/cvehistory/2.0");
        cveHistory.setApiV2(cveHistoryEndpoint);
        nvd.setCveHistory(cveHistory);

        Url cweUrl = new Url();
        cweUrl.setValue("https://cwe.mitre.org/data/xml/cwec_latest.xml.zip");
        nvd.setCwe(cweUrl);

        Download download = getDownload();
        nvd.setDownload(download);

        Merge merge = new Merge();
        merge.setSkipInvalidCollection(true);
        nvd.setMerge(merge);

        defaultProps.setNvd(nvd);
        return defaultProps;
    }

    private static Download getDownload() {
        Download download = new Download();
        UsingUri usingUri = new UsingUri();
        usingUri.setMaxConcurrentDownloads(1);
        download.setUsingUri(usingUri);

        UsingApi usingApi = new UsingApi();
        usingApi.setMaxRetries(3);
        usingApi.setMaxDownloadAttempts(10);
        usingApi.setRetryIntervalInSecs(30);
        usingApi.setDelayBetweenRequestsInMs(6000);
        LastModifiedDates lastModifiedDates = new LastModifiedDates();
        lastModifiedDates.setMaxRangeInDays(120);
        usingApi.setLastModifiedDates(lastModifiedDates);
        ChangeDates changeDates = new ChangeDates();
        changeDates.setMaxRangeInDays(120);
        usingApi.setChangeDates(changeDates);

        Processor processor = new Processor();
        processor.setMaxThreads(4);
        processor.setRateLimitWithKey(50);
        processor.setRateLimitWithoutKey(5);
        processor.setRollingWindowInSecs(30);
        processor.setLogEveryNProcessedElements(20);
        processor.setProducerWaitTimeToFinishInMinutes(30);
        processor.setConsumerWaitTimeToFinishInMinutes(180);
        usingApi.setProcessor(processor);

        download.setUsingApi(usingApi);
        return download;
    }

    private static NvdProperties getProperties(InputStream inputStream) {
        Yaml yaml = new Yaml(new Constructor(NvdProperties.class, new LoaderOptions()));
        return yaml.load(inputStream);
    }

    public static NvdProperties getInstance() {
        if (properties == null) {
            properties = loadNvdProperties();
        }
        return properties;
    }

    public enum ApiEndpointVersion {
        V2
    }

    public enum ApiEndpointType {
        CVE,
        CPE,
        CPE_MATCH,
        CVE_HISTORY
    }

    @Getter
    @Setter
    @ToString
    public static class Nvd {
        private Proxy proxy;
        private Api api;
        private EndpointAndUrl cve;
        private EndpointAndUrl cpe;
        private EndpointAndUrl cpeMatch;
        private CveHistory cveHistory;
        private Url cwe;
        private String notice;
        private Download download;
        private Merge merge;
    }

    @Getter
    @Setter
    @ToString
    public static class Api {
        private String key;

        @NonNull private String keyUrl;

        @NonNull private ApiEndpointVersion version;
    }

    @Getter
    @Setter
    @ToString
    public static class CveHistory {
        @NonNull private Endpoint apiV2;
    }

    @Getter
    @Setter
    @ToString
    public static class Endpoint {
        @NonNull private String ep;
    }

    @Getter
    @Setter
    @ToString
    public static class Url {
        @NonNull private String value;
    }

    @Getter
    @Setter
    @ToString
    public static class EndpointAndUrl {
        @NonNull private String mainUrl;

        @NonNull private Endpoint apiV2;
    }

    @Getter
    @Setter
    @ToString
    public static class Proxy {
        private String host;
        private Integer port;
    }

    @Getter
    @Setter
    @ToString
    public static class Download {
        private UsingUri usingUri;
        private UsingApi usingApi;
    }

    @Getter
    @Setter
    @ToString
    public static class UsingUri {
        private int maxConcurrentDownloads = 1;
    }

    @Getter
    @Setter
    @ToString
    public static class UsingApi {
        private LastModifiedDates lastModifiedDates;
        private ChangeDates changeDates;
        private Processor processor;
        private long delayBetweenRequestsInMs = Constants.DEFAULT_DELAY_BETWEEN_REQUESTS_IN_MS;
        private int maxRetries = Constants.DEFAULT_MAX_RETRIES;
        private int maxDownloadAttempts = Constants.DEFAULT_MAX_DOWNLOAD_ATTEMPTS;
        private int retryIntervalInSecs = DEFAULT_RETRY_INTERVAL_SECS;
    }

    @Getter
    @Setter
    @ToString
    public static class LastModifiedDates {
        private int maxRangeInDays = Constants.DEFAULT_MAX_RANGE_IN_DAYS;
    }

    @Getter
    @Setter
    @ToString
    public static class ChangeDates {
        private int maxRangeInDays = Constants.DEFAULT_MAX_RANGE_IN_DAYS;
    }

    @Getter
    @Setter
    @ToString
    public static class Processor {
        private int maxThreads = 1;

        @Required
        private int rateLimitWithKey;

        @Required
        private int rateLimitWithoutKey;

        @Required
        private int rollingWindowInSecs;

        private int logEveryNProcessedElements = Constants.LOG_EVERY_N_PROCESSED_ELEMENTS;
        private int producerWaitTimeToFinishInMinutes = Constants.DEFAULT_PRODUCER_TIMEOUT_IN_MINUTES;
        private int consumerWaitTimeToFinishInMinutes = Constants.DEFAULT_CONSUMER_TIMEOUT_IN_MINUTES;
    }

    @Getter
    @Setter
    @ToString
    public static class Merge {
        private boolean skipInvalidCollection = true;
    }
}
