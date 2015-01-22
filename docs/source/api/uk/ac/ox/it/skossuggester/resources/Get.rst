.. java:import:: com.codahale.metrics.annotation Timed

.. java:import:: com.google.common.base Optional

.. java:import:: com.google.common.base Preconditions

.. java:import:: java.util List

.. java:import:: io.dropwizard.jersey.caching CacheControl

.. java:import:: java.util.concurrent TimeUnit

.. java:import:: javax.ws.rs GET

.. java:import:: javax.ws.rs Path

.. java:import:: javax.ws.rs Produces

.. java:import:: javax.ws.rs QueryParam

.. java:import:: javax.ws.rs.core MediaType

.. java:import:: javax.ws.rs.core UriBuilder

.. java:import:: uk.ac.ox.it.skossuggester.dao SkosConceptsDao

.. java:import:: uk.ac.ox.it.skossuggester.representations SkosConcepts

.. java:import:: uk.ac.ox.it.skossuggester.representations.hal HalLink

.. java:import:: uk.ac.ox.it.skossuggester.representations.hal HalRepresentation

Get
===

.. java:package:: uk.ac.ox.it.skossuggester.resources
   :noindex:

.. java:type:: @Path @Produces public class Get

Constructors
------------
Get
^^^

.. java:constructor:: public Get(SkosConceptsDao dao)
   :outertype: Get

Methods
-------
get
^^^

.. java:method:: @GET @CacheControl @Timed public HalRepresentation get(List<String> uris)
   :outertype: Get

