.. java:import:: com.google.common.base Optional

.. java:import:: java.util List

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

.. java:import:: org.apache.solr.client.solrj SolrQuery

.. java:import:: org.apache.solr.client.solrj SolrServer

.. java:import:: org.apache.solr.client.solrj SolrServerException

.. java:import:: org.apache.solr.client.solrj.impl BinaryResponseParser

.. java:import:: org.apache.solr.client.solrj.request QueryRequest

.. java:import:: org.apache.solr.client.solrj.response QueryResponse

.. java:import:: org.apache.solr.common SolrDocument

.. java:import:: org.apache.solr.common SolrDocumentList

.. java:import:: uk.ac.ox.it.skossuggester.representations SkosConcept

.. java:import:: uk.ac.ox.it.skossuggester.representations SkosConcepts

.. java:import:: uk.ac.ox.it.skossuggester.resources Get

SkosConceptsDao
===============

.. java:package:: uk.ac.ox.it.skossuggester.dao
   :noindex:

.. java:type:: public class SkosConceptsDao

   Encapsulates all queries to Solr

   :author: martinfilliau

Constructors
------------
SkosConceptsDao
^^^^^^^^^^^^^^^

.. java:constructor:: public SkosConceptsDao(SolrServer solr)
   :outertype: SkosConceptsDao

   Constructor for SkosConceptsDao

   :param solr: Instance of SolrServer (e.g. HttpSolrServer)

Methods
-------
get
^^^

.. java:method:: public Optional<SkosConcepts> get(List<String> uris)
   :outertype: SkosConceptsDao

   Get documents by their unique IDs

   :param uris: list of URIs
   :return: SkosConcepts or absent

search
^^^^^^

.. java:method:: public Optional<SkosConcepts> search(String query, Integer start, Integer count)
   :outertype: SkosConceptsDao

   Search for documents by a query string

   :param query: string to search
   :param start: first document to retrieve
   :param count: number of documents to retrieve
   :return: SkosConcepts or absent

suggest
^^^^^^^

.. java:method:: public Optional<SkosConcepts> suggest(String query, Integer start, Integer count)
   :outertype: SkosConceptsDao

   Search for documents by a query string Use the "suggest" handler which provides a light response

   :param query: string to search
   :param start: first document to retrieve
   :param count: number of documents to retrieve
   :return: SkosConcepts or absent

