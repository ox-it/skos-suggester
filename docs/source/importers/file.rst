RDF file importer
=================

Imports SKOS concepts from an RDF file. This is the recommended method if you have a small
dataset as it is easier to use.

Importing the file into SKOS suggester
--------------------------------------

Run the following command, where `[path to file]` is the path of the RDF file,
and `[configuration file]` is the Dropwizard configuration file.

    `java -jar target/skos-suggester-1.0-SNAPSHOT.jar skosimport -f [path to file] [configuration file]`
