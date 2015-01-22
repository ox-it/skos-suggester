.. java:import:: com.hp.hpl.jena.rdf.model Model

.. java:import:: com.hp.hpl.jena.rdf.model ModelFactory

.. java:import:: com.hp.hpl.jena.rdf.model ResIterator

.. java:import:: com.hp.hpl.jena.rdf.model Resource

.. java:import:: com.hp.hpl.jena.vocabulary RDF

.. java:import:: io.dropwizard.cli ConfiguredCommand

.. java:import:: io.dropwizard.setup Bootstrap

.. java:import:: java.io File

.. java:import:: java.io FileInputStream

.. java:import:: java.io FileNotFoundException

.. java:import:: java.io InputStream

.. java:import:: java.util ArrayList

.. java:import:: java.util Collection

.. java:import:: net.sourceforge.argparse4j.impl Arguments

.. java:import:: net.sourceforge.argparse4j.inf Namespace

.. java:import:: net.sourceforge.argparse4j.inf Subparser

.. java:import:: org.apache.solr.client.solrj.impl HttpSolrServer

.. java:import:: org.apache.solr.common SolrInputDocument

.. java:import:: uk.ac.ox.it.skossuggester.configuration AppConfiguration

SkosFileImporter
================

.. java:package:: uk.ac.ox.it.skossuggester.cli
   :noindex:

.. java:type:: public class SkosFileImporter extends ConfiguredCommand<AppConfiguration>

   Import SKOS concepts from an RDF file Easier to use than TdbImporter as it does not need an intermediary Jena TDB store, but might reach memory limits

   :author: martinfilliau

Constructors
------------
SkosFileImporter
^^^^^^^^^^^^^^^^

.. java:constructor:: public SkosFileImporter()
   :outertype: SkosFileImporter

Methods
-------
configure
^^^^^^^^^

.. java:method:: @Override public void configure(Subparser subparser)
   :outertype: SkosFileImporter

getDocsFromFile
^^^^^^^^^^^^^^^

.. java:method:: protected Collection<SolrInputDocument> getDocsFromFile(File file, String lang) throws FileNotFoundException
   :outertype: SkosFileImporter

   Import a given RDF file to the search index

   :param file: RDF file
   :param lang: RDF format (RDF/XML, N-TRIPLE, TURTLE or N3)
   :throws java.io.FileNotFoundException:
   :return: collection of SolrInputDocument

getDocsFromModel
^^^^^^^^^^^^^^^^

.. java:method:: protected Collection<SolrInputDocument> getDocsFromModel(Model m)
   :outertype: SkosFileImporter

   Import a Model

   :param m: Jena Model
   :return: collection of SolrInputDocument

run
^^^

.. java:method:: @Override protected void run(Bootstrap<AppConfiguration> bootstrap, Namespace namespace, AppConfiguration configuration) throws Exception
   :outertype: SkosFileImporter

