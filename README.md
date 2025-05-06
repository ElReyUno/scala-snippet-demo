# Scala Snippet Demo Project

This project serves as a demonstration of basic Scala 3 features, data modeling, and functional programming concepts, particularly in the context of document processing and potential AI feature integration. It was created as part of an exercise and includes examples for:

*   Document classification and metadata handling simulation.
*   Simple keyword-based document search.
*   Basic PII (Personally Identifiable Information) redaction.

**Table of Contents**

*   [Project Structure](#project-structure)
*   [Prerequisites](#prerequisites)
*   [Building and Running](#building-and-running)
*   [Running Tests](#running-tests)
*   [Development Environment (VS Code with Metals)](#development-environment-vs-code-with-metals)
*   [Purpose of Snippets](#purpose-of-snippets)
*   [License](#license)

## Project Structure

*   `src/main/scala/DocumentProcessingDemo.scala`: Contains the main Scala code, including data models (`LegalDocument`, `DocumentType`) and demonstration functions (`findCaseNumbersForMotions`, `simpleSearchByKeyword`, `redactSSN`). It also includes an `@main` method to run the demonstrations.
*   `src/test/scala/DocumentProcessingSuite.scala`: Contains unit tests for the functions in `DocumentProcessingDemo.scala`, written using ScalaTest.
*   `build.sbt`: The sbt (Scala Build Tool) definition file, specifying the Scala version and project dependencies (like ScalaTest).
*   `.gitignore`: Specifies intentionally untracked files that Git should ignore (e.g., build artifacts, IDE files).

## Prerequisites

*   **Java Development Kit (JDK):** Version 8, 11, 17, or 21 is recommended.
*   **sbt (Scala Build Tool):** Ensure `sbt` is installed and available on your system PATH. (See [sbt download](https://www.scala-sbt.org/download.html)).

## Building and Running

1.  **Clone the repository (if you haven't already):**
    ```bash
    git clone https://github.com/ElReyUno/scala-snippet-demo.git
    cd scala-snippet-demo
    ```
    
2.  **Compile the project:**
    Open a terminal in the project root and run:
    ```bash
    sbt compile
    ```

3.  **Run the main demonstration:**
    The `DocumentProcessingDemo.scala` file contains an `@main` method that demonstrates the features.
    ```bash
    sbt run
    ```
    You should see output in the console showing the results of the different processing functions.

## Running Tests

Unit tests are written using ScalaTest. To run them:

```bash
sbt test
```

## Development Environment (VS Code with Metals)

This project is set up to work well with Visual Studio Code and the [Metals](https://scalameta.org/metals/) Scala language server extension.

1.  Ensure you have VS Code and the Metals extension installed.
2.  Open the project folder in VS Code.
3.  When prompted, allow Metals to "Import build".
4.  You can then use Metals features to run the main method, run/debug tests, and get rich code assistance.

## Purpose of Snippets

*   **`findCaseNumbersForMotions`**: Demonstrates filtering a list of documents based on a simulated classification (`DocumentType`) and extracting data from a `metadata` map (simulating metadata extraction).
*   **`simpleSearchByKeyword`**: Shows a basic keyword search through document content.
*   **`redactSSN`**: Illustrates a simple approach to privacy-conscious data handling by redacting a piece of PII.

This project is intended to be a small, focused example.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
