# NVD Tool

A command-line interface (CLI) tool to fetch CVE (Common Vulnerabilities and Exposures) and CPE (Common Platform Enumeration) data from NIST's National Vulnerability Database (NVD) API, along with CWE (Common Weakness Enumeration) data from MITRE.

## Features

- **Multi-format Data Download**: Fetch CVE, CPE, CPE Match, and CVE History data using NIST's NVD API 2.0
- **Legacy URI Support**: Download data using traditional URI-based methods
- **Data Merging**: Combine multiple data files into consolidated datasets
- **Concurrent Processing**: Multi-threaded download and processing for improved performance
- **Flexible Output**: Configurable output directories and file naming
- **Query Filtering**: Support for date ranges, modification filters, and pagination

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Building the Project

### Standard Build
```bash
mvn clean package
```

### Full Build (with schema regeneration)
```bash
mvn clean post-clean package
```

### Generate Build Configuration
```bash
mvn resources:copy-resources@generate-buildconfig
```

## Usage

The tool provides several commands for different operations:

### Main Command Structure
```bash
java -jar nvd-tool-1.0-SNAPSHOT.jar [COMMAND] [OPTIONS]
```

### Available Commands

#### Download Command
Download CVE/CPE data from NIST NVD repository and CWE data from MITRE.

```bash
java -jar nvd-tool-1.0-SNAPSHOT.jar download [SUBCOMMAND] [OPTIONS]
```

**Download Subcommands:**
- `api` - Download using NVD API 2.0
- `uri` - Download using traditional URI methods

#### API Download Subcommands
```bash
# Download CVE data
java -jar nvd-tool-1.0-SNAPSHOT.jar download api cve [OPTIONS]

# Download CPE data  
java -jar nvd-tool-1.0-SNAPSHOT.jar download api cpe [OPTIONS]

# Download CPE Match data
java -jar nvd-tool-1.0-SNAPSHOT.jar download api cpe-match [OPTIONS]

# Download CVE History data
java -jar nvd-tool-1.0-SNAPSHOT.jar download api cve-history [OPTIONS]
```

#### Merge Command
Combine multiple downloaded data files.

```bash
java -jar nvd-tool-1.0-SNAPSHOT.jar merge [OPTIONS]
```

### Common Options

- `-o, --output-dir DIR` - Specify output directory
- `--output-file FILE` - Specify output filename
- `-h, --help` - Show help information
- `-V, --version` - Show version information

### Examples

#### Download recent CVE data
```bash
java -jar nvd-tool-1.0-SNAPSHOT.jar download api cve \
  --output-dir ./data \
  --last-mod-start-date 2024-01-01T00:00:00 \
  --last-mod-end-date 2024-01-31T23:59:59
```

#### Download CPE data with specific results per page
```bash
java -jar nvd-tool-1.0-SNAPSHOT.jar download api cpe \
  --output-dir ./cpe-data \
  --results-per-page 500
```

#### Merge downloaded files
```bash
java -jar nvd-tool-1.0-SNAPSHOT.jar merge \
  --input-dir ./data \
  --output-file merged-cve-data.json
```

## Project Structure

```
nvd-tool/
├── src/
│   ├── main/
│   │   ├── java/com/github/phanikb/nvd/
│   │   │   ├── cli/                    # Command-line interface classes
│   │   │   ├── api2/                   # Generated API 2.0 schema classes
│   │   │   ├── common/                 # Common utilities and exceptions
│   │   │   ├── enums/                  # Enumeration types
│   │   │   └── utils/                  # Utility classes
│   │   └── resources/
│   │       ├── schema/                 # JSON schema files
│   │       ├── spotbugs/              # SpotBugs configuration
│   │       └── pmd/                   # PMD ruleset
│   └── test/
│       └── java/                      # Unit tests
├── target/                            # Build artifacts
├── logs/                             # Application logs
├── pom.xml                           # Maven configuration
└── README.md
```

## Configuration

### Schema Generation
The project uses `jsonschema2pojo-maven-plugin` to generate Java classes from JSON schemas for:
- CVE API 2.0 schema
- CPE API 2.0 schema  
- CPE Match API 2.0 schema
- CVE History API 2.0 schema

## Dependencies

Key dependencies include:
- **PicoCLI**: Command-line interface framework
- **Apache HttpClient 5**: HTTP client for API requests
- **Jackson**: JSON processing
- **Lombok**: Boilerplate code reduction
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework for tests
- **Log4j 2**: Logging framework

## Testing

Run the test suite:
```bash
mvn test
```

## Code Quality

Run code quality checks:
```bash
# Spotless formatting check
mvn spotless:check

# Apply Spotless formatting
mvn spotless:apply

# Run SpotBugs analysis
mvn spotbugs:check

# Run PMD analysis
mvn pmd:check
```

## Logging

The application uses Log4j 2 for logging. Log files are stored in the `logs/` directory with automatic archiving.

## Contributing

1. Ensure code follows the project's formatting standards (run `mvn spotless:apply`)
2. Add appropriate unit tests for new functionality
3. Run the full test suite before submitting changes
4. Follow the existing code structure and naming conventions

## License

This project is licensed under the terms specified in the [LICENSE](LICENSE) file.

## Notice

**This tool uses data from the NVD API but is not endorsed or certified by the NVD.**
