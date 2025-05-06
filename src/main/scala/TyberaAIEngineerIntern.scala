/**
 * This Scala 3 snippet demonstrates core data modeling for legal documents and
 * foundational functions for processing them, including classification-based filtering,
 * metadata extraction, simple keyword search, and basic PII redaction.
 * It is intended to showcase Scala proficiency and an understanding of concepts
 * relevant to an AI-enabled document management system.
 */
// Create an object to hold all your definitions
object DocumentProcessing {

// Define possible document types using Scala 3 enums
enum DocumentType:
  case Pleading, Motion, Order, Exhibit, Unknown

// Represent a document with basic metadata, potential classification, and optional PII
case class LegalDocument(
  id: String,
  contentSnippet: String,         // Representing the document text
  potentialType: DocumentType,    // Simulates holding the result of classification
  metadata: Map[String, String],  // Simulates holding results of 'Metadata extraction'
  ssn: Option[String] = None      // Optional field for PII demonstration
)

/**
 * Filters documents by type (using classification result) and extracts specific metadata (using extraction result).
 * Demonstrates functional list processing in Scala. Relevant to 'Contribute Scala code' and using AI feature outputs.
 */
def findCaseNumbersForMotions(documents: List[LegalDocument]): List[String] =
  documents
    .filter(_.potentialType == DocumentType.Motion) // Select only motions
    .flatMap(_.metadata.get("caseNumber"))         // Safely extract "caseNumber" if present, flatten Options

/**
 * Simple keyword search functionality. Relevant to 'Document ... search' AI feature.
 * In a real system, this would use more sophisticated indexing/search techniques.
 */
def simpleSearchByKeyword(documents: List[LegalDocument], keyword: String): List[String] =
  documents
    .filter(_.contentSnippet.toLowerCase.contains(keyword.toLowerCase)) // Case-insensitive search in snippet
    .map(_.id) // Return IDs of matching documents

/**
 * Simple PII redaction example. Relevant to 'Collaborate on privacy-conscious AI features'.
 * Demonstrates modifying data potentially based on privacy rules. Real redaction is more complex.
 * Ensures the SSN value is actually present in the content snippet before attempting replacement for clarity.
 */
def redactSSN(doc: LegalDocument): LegalDocument =
  doc.ssn match {
    // Only attempt to replace if ssnValue exists and is found in the content
    case Some(ssnValue) if ssnValue.nonEmpty && doc.contentSnippet.contains(ssnValue) =>
      // Create a copy with SSN removed
      doc.copy(
        contentSnippet = doc.contentSnippet.replace(ssnValue, "[REDACTED_SSN]"), // Replace the actual SSN string
        ssn = None // Remove the sensitive data from the dedicated field
      )
      // If SSN is present but not in content, just remove it from the ssn field
    case Some(_) =>
        doc.copy(ssn = None)
    case _ => doc // No SSN present or empty, return original
  }
}

// The @main method serves as an entry point for demonstration purposes.
@main def runDocumentProcessingDemo(): Unit =
  import DocumentProcessing._

  // Sample data setup
  val docs = List(
    LegalDocument(
    id = "doc1",
    contentSnippet = "Argument supporting motion... sensitive info: 123-456-7890. More text.",
    potentialType = DocumentType.Motion,
    metadata = Map("caseNumber" -> "CV-2025-123"),
    ssn = Some("123-456-7890")
  ),
  LegalDocument(
    id = "doc2",
    contentSnippet = "List of exhibit items...",
    potentialType = DocumentType.Exhibit,
    metadata = Map("exhibitNum" -> "A")
  ),
  LegalDocument(
    id = "doc3",
    contentSnippet = "Order granting relief...",
    potentialType = DocumentType.Order,
    metadata = Map("caseNumber" -> "CV-2025-123")
  ),
  LegalDocument(
    id = "doc4",
    contentSnippet = "Further argument on motion...",
    potentialType = DocumentType.Motion,
    metadata = Map("caseNumber" -> "CV-2025-456")
  )
  )

  val motionCaseNumbers = findCaseNumbersForMotions(docs)
  println(s"Found Case Numbers for Motions: ${motionCaseNumbers.mkString(", ")}")

  val searchKeyword = "argument"
  val searchResults = simpleSearchByKeyword(docs, searchKeyword)
  println(s"Docs containing '$searchKeyword': ${searchResults.mkString(", ")}")

  val redactedDocs = docs.map(redactSSN)
  println("\nRedacted Documents (showing content and SSN presence):")
  redactedDocs.foreach { doc =>
    println(f" - ID: ${doc.id}%-5s | Has SSN: ${doc.ssn.isDefined}%-5s | Content Snippet: ${doc.contentSnippet}")
  }
  println("\nDemo completed.")