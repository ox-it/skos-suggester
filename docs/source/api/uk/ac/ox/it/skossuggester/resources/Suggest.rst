.. java:import:: com.codahale.metrics.annotation Timed

.. java:import:: com.google.common.base Optional

.. java:import:: com.google.common.base Preconditions

.. java:import:: io.dropwizard.jersey.caching CacheControl

.. java:import:: io.dropwizard.jersey.params IntParam

.. java:import:: java.util.concurrent TimeUnit

.. java:import:: javax.ws.rs DefaultValue

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

Suggest
=======

.. java:package:: uk.ac.ox.it.skossuggester.resources
   :noindex:

.. java:type:: @Path @Produces public class Suggest

Constructors
------------
Suggest
^^^^^^^

.. java:constructor:: public Suggest(SkosConceptsDao dao)
   :outertype: Suggest

Methods
-------
suggest
^^^^^^^

.. java:method:: @GET @CacheControl @Timed public HalRepresentation suggest(String query, IntParam page, IntParam count)
   :outertype: Suggest

