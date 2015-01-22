.. java:import:: com.codahale.metrics.health HealthCheck

.. java:import:: org.apache.solr.client.solrj SolrServer

.. java:import:: org.apache.solr.client.solrj.response SolrPingResponse

SolrHealth
==========

.. java:package:: uk.ac.ox.it.skossuggester.health
   :noindex:

.. java:type:: public class SolrHealth extends HealthCheck

   Health check

   :author: martinfilliau

Constructors
------------
SolrHealth
^^^^^^^^^^

.. java:constructor:: public SolrHealth(SolrServer solr)
   :outertype: SolrHealth

Methods
-------
check
^^^^^

.. java:method:: @Override protected Result check() throws Exception
   :outertype: SolrHealth

