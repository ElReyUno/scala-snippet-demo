/**
 * This file contains unit tests for the functionalities defined in the
 * DocumentProcessing object, ensuring the correctness of document filtering,
 * keyword search, and PII redaction logic.
 * Tests are written using ScalaTest.
 */
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

// Import the definitions from DocumentProcessing object
import DocumentProcessing._               // Ensure DocumentType is an object or package
import DocumentProcessing.DocumentType._  // Import the enum

class DocumentProcessingSuite extends AnyFunSuite with Matchers {

  test("findCaseNumbersForMotions: should return empty list if input is empty") {
    findCaseNumbersForMotions(List.empty) should be (empty)
  }
  test("findCaseNumbersForMotions: should return case numbers only for motions") {
     val docs = List(
        LegalDocument("doc1", "...", Motion, Map("caseNumber" -> "CV-123")),
        LegalDocument("doc2", "...", Exhibit, Map("exhibitNum" -> "A")),
        LegalDocument("doc4", "...", Motion, Map("caseNumber" -> "CV-456")),
        LegalDocument("doc5", "...", Motion, Map()) // No caseNumber
      )
      findCaseNumbersForMotions(docs) should contain theSameElementsAs List("CV-123", "CV-456")
  }

   test("simpleSearchByKeyword: should find matching document IDs") {
     val docs = List(
        LegalDocument("doc1", "Argument supporting motion...", Motion, Map()),
        LegalDocument("doc2", "List of exhibit items...", Exhibit, Map()),
        LegalDocument("doc4", "Further argument on motion...", Motion, Map())
     )
     simpleSearchByKeyword(docs, "argument") should contain theSameElementsAs List("doc1", "doc4")
   }
   test("simpleSearchByKeyword: should be case-insensitive") {
     val docs = List(LegalDocument("doc1", "Argument...", Motion, Map()))
     simpleSearchByKeyword(docs, "ARGUMENT") should contain theSameElementsAs List("doc1")
   }
   test("simpleSearchByKeyword: should return empty list if no match") {
     val docs = List(LegalDocument("doc1", "Argument...", Motion, Map()))
     simpleSearchByKeyword(docs, "keyword") should be (empty)
   }

   test("redactSSN: should redact present SSN and update content") {
     val doc = LegalDocument("doc1", "Info ssn:123-456-7890.", Motion, Map(), Some("123-456-7890"))
     val redacted = redactSSN(doc)
     redacted.ssn should be (None)
     redacted.contentSnippet should include ("[REDACTED_SSN]")
     redacted.contentSnippet should not include ("123-456-7890")
   }
   test("redactSSN: should not change doc if SSN is None") {
     val doc = LegalDocument("doc2", "No ssn here.", Exhibit, Map(), None)
     val redacted = redactSSN(doc)
     redacted should be theSameInstanceAs (doc)
     redacted.ssn should be (None)
   }
   test("redactSSN: should not change doc if SSN is Some but empty") {
     val doc = LegalDocument("doc3", "Empty ssn.", Exhibit, Map(), Some(""))
     val redacted = redactSSN(doc)
     redacted should be theSameInstanceAs (doc)
   }
}