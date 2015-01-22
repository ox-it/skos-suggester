.. java:import:: java.util ArrayList

.. java:import:: java.util Iterator

.. java:import:: java.util List

.. java:import:: com.google.common.base Objects

.. java:import:: org.apache.solr.common SolrDocument

.. java:import:: org.apache.solr.common SolrDocumentList

SkosConcepts
============

.. java:package:: uk.ac.ox.it.skossuggester.representations
   :noindex:

.. java:type:: public class SkosConcepts

   Represents a list of SkosConcept

   :author: martinfilliau

Constructors
------------
SkosConcepts
^^^^^^^^^^^^

.. java:constructor:: public SkosConcepts()
   :outertype: SkosConcepts

SkosConcepts
^^^^^^^^^^^^

.. java:constructor:: public SkosConcepts(List<SkosConcept> concepts)
   :outertype: SkosConcepts

Methods
-------
addConcept
^^^^^^^^^^

.. java:method:: public void addConcept(SkosConcept concept)
   :outertype: SkosConcepts

   Add a SkosConcept to the list of concepts

   :param concept: SkosConcept

equals
^^^^^^

.. java:method:: @Override public boolean equals(Object obj)
   :outertype: SkosConcepts

fromSolr
^^^^^^^^

.. java:method:: public static SkosConcepts fromSolr(SolrDocumentList docs)
   :outertype: SkosConcepts

   Get SkosConcepts from a SolrDocumentList

   :param docs: SolrDocumentList
   :return: SkosConcepts

getConcepts
^^^^^^^^^^^

.. java:method:: public List<SkosConcept> getConcepts()
   :outertype: SkosConcepts

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: SkosConcepts

setConcepts
^^^^^^^^^^^

.. java:method:: public void setConcepts(List<SkosConcept> concepts)
   :outertype: SkosConcepts

