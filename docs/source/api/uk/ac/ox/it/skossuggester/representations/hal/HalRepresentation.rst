.. java:import:: com.fasterxml.jackson.annotation JsonProperty

.. java:import:: uk.ac.ox.it.skossuggester.representations SkosConcepts

HalRepresentation
=================

.. java:package:: uk.ac.ox.it.skossuggester.representations.hal
   :noindex:

.. java:type:: public class HalRepresentation

   Represents a HAL representation

   :author: martinfilliau

Constructors
------------
HalRepresentation
^^^^^^^^^^^^^^^^^

.. java:constructor:: public HalRepresentation()
   :outertype: HalRepresentation

Methods
-------
getEmbedded
^^^^^^^^^^^

.. java:method:: @JsonProperty public SkosConcepts getEmbedded()
   :outertype: HalRepresentation

getLinks
^^^^^^^^

.. java:method:: @JsonProperty public HalLinks getLinks()
   :outertype: HalRepresentation

setEmbedded
^^^^^^^^^^^

.. java:method:: public void setEmbedded(SkosConcepts embedded)
   :outertype: HalRepresentation

setLinks
^^^^^^^^

.. java:method:: public void setLinks(HalLinks links)
   :outertype: HalRepresentation

setSelfLink
^^^^^^^^^^^

.. java:method:: public void setSelfLink(HalLink link)
   :outertype: HalRepresentation

