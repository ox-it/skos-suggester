Jena TDB importer
=================

Imports SKOS concepts from a Jena TDB dataset. This is the recommended method if you have a fairly large dataset
(e.g. `FAST (Faceted Application of Subject Terminology) topics <http://fast.oclc.org>`_).

Requirements
------------

You need to install `Jena <http://jena.apache.org/>`_ to use the `tdbloader` command-line tool.

Importing RDF files into Jena TDB
---------------------------------

Run the following command, where `[path to store]` is the path of a directory,
and `[path to RDF file]` is the RDF file to be imported.

    `tdbloader2 --loc [path to store] [path to RDF file]`

See `TDB commands documentation <http://jena.apache.org/documentation/tdb/commands.html>`_ for more information.

Importing TDB into SKOS suggester
---------------------------------

Run the following command, where `[path to store]` is the path of the TDB directory,
and `[configuration file]` is the Dropwizard configuration file.

    `java -jar target/skos-suggester-1.0-SNAPSHOT.jar tdbimport -d [path to store] [configuration file]`
