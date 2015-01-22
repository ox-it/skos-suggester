.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: com.google.common.base Objects

.. java:import:: org.apache.solr.common SolrDocument

SkosConcept
===========

.. java:package:: uk.ac.ox.it.skossuggester.representations
   :noindex:

.. java:type:: public class SkosConcept

   Represents a skos:concept

   :author: martinfilliau

Constructors
------------
SkosConcept
^^^^^^^^^^^

.. java:constructor:: public SkosConcept()
   :outertype: SkosConcept

Methods
-------
addAltLabel
^^^^^^^^^^^

.. java:method:: public void addAltLabel(String label)
   :outertype: SkosConcept

   Add an alternative label to the concept

   :param label: String

addRelated
^^^^^^^^^^

.. java:method:: public void addRelated(Related related)
   :outertype: SkosConcept

   Add a Related concept to the concept

   :param related: Related

equals
^^^^^^

.. java:method:: @Override public boolean equals(Object obj)
   :outertype: SkosConcept

fromSolr
^^^^^^^^

.. java:method:: public static SkosConcept fromSolr(SolrDocument doc)
   :outertype: SkosConcept

   Get a SkosConcept from a SolrDocument

   :param doc: SolrDocument
   :return: SkosConcept

getAltLabels
^^^^^^^^^^^^

.. java:method:: public List<String> getAltLabels()
   :outertype: SkosConcept

getPrefLabel
^^^^^^^^^^^^

.. java:method:: public String getPrefLabel()
   :outertype: SkosConcept

getRelated
^^^^^^^^^^

.. java:method:: public List<Related> getRelated()
   :outertype: SkosConcept

getUri
^^^^^^

.. java:method:: public String getUri()
   :outertype: SkosConcept

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: SkosConcept

setAltLabels
^^^^^^^^^^^^

.. java:method:: public void setAltLabels(List<String> altLabels)
   :outertype: SkosConcept

setPrefLabel
^^^^^^^^^^^^

.. java:method:: public void setPrefLabel(String prefLabel)
   :outertype: SkosConcept

setRelated
^^^^^^^^^^

.. java:method:: public void setRelated(List<Related> related)
   :outertype: SkosConcept

setUri
^^^^^^

.. java:method:: public void setUri(String uri)
   :outertype: SkosConcept

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: SkosConcept

