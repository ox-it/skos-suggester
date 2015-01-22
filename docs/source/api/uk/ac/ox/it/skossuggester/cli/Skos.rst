.. java:import:: com.hp.hpl.jena.rdf.model Model

.. java:import:: com.hp.hpl.jena.rdf.model ModelFactory

.. java:import:: com.hp.hpl.jena.rdf.model Property

.. java:import:: com.hp.hpl.jena.rdf.model Resource

.. java:import:: com.hp.hpl.jena.rdf.model Statement

.. java:import:: com.hp.hpl.jena.vocabulary RDFS

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: org.apache.solr.common SolrInputDocument

Skos
====

.. java:package:: uk.ac.ox.it.skossuggester.cli
   :noindex:

.. java:type:: public class Skos

   Utility methods used by importers RDF -> SolrDocument

   :author: martinfilliau

Fields
------
ALT_LABEL
^^^^^^^^^

.. java:field:: public static final String ALT_LABEL
   :outertype: Skos

NS
^^

.. java:field:: public static final String NS
   :outertype: Skos

PREF_LABEL
^^^^^^^^^^

.. java:field:: public static final String PREF_LABEL
   :outertype: Skos

RELATED
^^^^^^^

.. java:field:: public static final String RELATED
   :outertype: Skos

Methods
-------
getDocument
^^^^^^^^^^^

.. java:method:: protected static SolrInputDocument getDocument(Resource res)
   :outertype: Skos

   Get a SolrInputDocument from a Resource

   :param res: Resource to analyse
   :return: SolrInputDocument to be ingested by Solr

