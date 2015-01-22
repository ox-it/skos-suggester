.. java:import:: com.hp.hpl.jena.query Dataset

.. java:import:: com.hp.hpl.jena.rdf.model Model

.. java:import:: com.hp.hpl.jena.rdf.model ResIterator

.. java:import:: com.hp.hpl.jena.rdf.model Resource

.. java:import:: com.hp.hpl.jena.tdb TDBFactory

.. java:import:: com.hp.hpl.jena.vocabulary RDF

.. java:import:: io.dropwizard.cli ConfiguredCommand

.. java:import:: io.dropwizard.setup Bootstrap

.. java:import:: java.io File

.. java:import:: net.sourceforge.argparse4j.impl Arguments

.. java:import:: net.sourceforge.argparse4j.inf Namespace

.. java:import:: net.sourceforge.argparse4j.inf Subparser

.. java:import:: org.apache.solr.client.solrj.impl ConcurrentUpdateSolrServer

.. java:import:: uk.ac.ox.it.skossuggester.configuration AppConfiguration

TdbImporter
===========

.. java:package:: uk.ac.ox.it.skossuggester.cli
   :noindex:

.. java:type:: public class TdbImporter extends ConfiguredCommand<AppConfiguration>

   Import SKOS concepts from a Jena TDB store More robust than SkosFileImporter for big data sets See http://jena.apache.org/documentation/tdb/

   :author: martinfilliau

Constructors
------------
TdbImporter
^^^^^^^^^^^

.. java:constructor:: public TdbImporter()
   :outertype: TdbImporter

Methods
-------
configure
^^^^^^^^^

.. java:method:: @Override public void configure(Subparser subparser)
   :outertype: TdbImporter

run
^^^

.. java:method:: @Override protected void run(Bootstrap<AppConfiguration> btstrp, Namespace namespace, AppConfiguration configuration) throws Exception
   :outertype: TdbImporter

