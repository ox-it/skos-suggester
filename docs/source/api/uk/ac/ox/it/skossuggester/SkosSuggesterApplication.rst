.. java:import:: io.dropwizard Application

.. java:import:: io.dropwizard.setup Bootstrap

.. java:import:: io.dropwizard.setup Environment

.. java:import:: java.util EnumSet

.. java:import:: javax.servlet DispatcherType

.. java:import:: javax.servlet FilterRegistration

.. java:import:: org.apache.solr.client.solrj.impl HttpSolrServer

.. java:import:: org.eclipse.jetty.servlets CrossOriginFilter

.. java:import:: uk.ac.ox.it.skossuggester.configuration AppConfiguration

.. java:import:: uk.ac.ox.it.skossuggester.health SolrHealth

.. java:import:: uk.ac.ox.it.skossuggester.cli SkosFileImporter

.. java:import:: uk.ac.ox.it.skossuggester.cli TdbImporter

.. java:import:: uk.ac.ox.it.skossuggester.dao SkosConceptsDao

.. java:import:: uk.ac.ox.it.skossuggester.jerseyutils JsonIllegalArgumentExceptionMapper

.. java:import:: uk.ac.ox.it.skossuggester.resources Get

.. java:import:: uk.ac.ox.it.skossuggester.resources Search

.. java:import:: uk.ac.ox.it.skossuggester.resources Suggest

SkosSuggesterApplication
========================

.. java:package:: uk.ac.ox.it.skossuggester
   :noindex:

.. java:type:: public class SkosSuggesterApplication extends Application<AppConfiguration>

Methods
-------
initialize
^^^^^^^^^^

.. java:method:: @Override public void initialize(Bootstrap<AppConfiguration> bootstrap)
   :outertype: SkosSuggesterApplication

main
^^^^

.. java:method:: public static void main(String[] args) throws Exception
   :outertype: SkosSuggesterApplication

run
^^^

.. java:method:: @Override public void run(AppConfiguration configuration, Environment environment) throws Exception
   :outertype: SkosSuggesterApplication

