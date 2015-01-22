.. java:import:: javax.ws.rs.core Response

.. java:import:: javax.ws.rs.ext ExceptionMapper

.. java:import:: javax.ws.rs.ext Provider

.. java:import:: org.slf4j Logger

.. java:import:: org.slf4j LoggerFactory

JsonIllegalArgumentExceptionMapper
==================================

.. java:package:: uk.ac.ox.it.skossuggester.jerseyutils
   :noindex:

.. java:type:: @Provider public class JsonIllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException>

Methods
-------
toResponse
^^^^^^^^^^

.. java:method:: @Override public Response toResponse(IllegalArgumentException error)
   :outertype: JsonIllegalArgumentExceptionMapper

