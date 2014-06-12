skos-suggester
==============

Objectives
----------

Help applications to get concepts from an existing SKOS taxonomy.

* To provide a suggestion/auto-completion from a list of concepts indexed
* To search for concepts from their names
* To get a concept by its identifier

Quick installation and running
------------------------------

Requires Java (version 6+) and Maven (version 2+).

Run `mvn clean install` at the root of the project. This will build a "fat" JAR (shaded JAR containing all its dependencies).

You can run the application by `java -jar target/skos-suggester-1.0-SNAPSHOT.jar server src/main/resources/configuration.yml` at the root of the project.
(This assumes that you have set up your configuration file at `src/main/resources/configuration.yml`, see `src/main/resources/configuration.yml.example` for a sample configuration.)

Tests
-----

Run `mvn test` at the root of the project.

Examples
--------

`GET /suggest?q=on` would return e.g. `ontology, oncology` (representation needs to be discussed)

`GET /search?q=ontology`

`GET /concept?uri=http://concept-uri`
