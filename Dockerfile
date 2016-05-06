FROM makuk66/docker-solr:4.10.4

ADD ./src/main/resources/solr/schema.xml /opt/solr/example/solr/collection1/conf/schema.xml
ADD ./src/main/resources/solr/solrconfig.xml /opt/solr/example/solr/collection1/conf/solrconfig.xml

# CMD ["/bin/bash", "-c", "cd /opt/solr/example; java -jar start.jar"]

EXPOSE 8983
