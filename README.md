skos-suggester
==============
[![Build Status](https://travis-ci.org/ox-it/skos-suggester.svg?branch=master)](https://travis-ci.org/ox-it/skos-suggester)

See [documentation](http://skos-suggester.readthedocs.org/en/latest/)

Objectives
----------

Provide a reusable web service to search for concepts from an existing SKOS taxonomy.

* Provide a suggestion/auto-completion from a list of concepts indexed
* Search for concepts from their names
* Get a concept by its identifier

Quick installation and running
------------------------------

Requires Java (version 6+) and Maven (version 2+).

Run `mvn clean package` at the root of the project. This will build a "fat" JAR (shaded JAR containing all its dependencies).

You can start the application by running the following command at the root of the project:

    java -jar target/skos-suggester-1.0-SNAPSHOT.jar server src/main/resources/configuration.yml

(This assumes that you have set up your configuration file at `src/main/resources/configuration.yml`, see `src/main/resources/configuration.yml.example` for a sample configuration.)

Importing a SKOS file
---------------------

After having packaged the application, run:

    java -jar target/skos-suggester-1.0-SNAPSHOT.jar skosimport -f [path to your file] [path to your configuration file]

* `-f` is the location of the SKOS RDF file to import
* Use `--format` to override the default format (N-TRIPLES).

Alternatively, if you have a Jena TDB store instead of a file:

    java -jar target/skos-suggester-1.0-SNAPSHOT.jar tdbimport -d [path to the store] [path to your configuration file]

Automatically deploy the latest FAST topics
-------------------------------------------

See `fabfile.py` (requires Fabric, see `requirements.txt`, install it using `pip install -r requirements.txt`).

To automatically import the latest FAST topics, use the following command:

    fab ENV update_topics

The script assumes that `unzip` and `tdbloader2` are installed.

Unit tests
----------

Run at the root of the project:

    mvn test

Load tests
----------

Basic load testing has been done using [Locust](http://locust.io).

To run the load test, first install it using `pip` and then run:

    locust --hostname "http://127.0.0.1:8080"

See `locustfile.py` at the root of the project for more information.

Docker
------

Build the image by running at the root of the repository (assuming you have docker.io installed):

    docker build -t skos-solr .

Start the container by running:

    docker run -d -p 8983:8983 skos-solr

Or alternatively, if you have `fig` installed, just run `fig up`.
