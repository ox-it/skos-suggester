Search endpoint
===============

Endpoint for searching SKOS concepts

.. http:get:: /search

    Search for SKOS concepts by labels

    **Example request**:

    .. sourcecode:: http

        GET /search?q=http HTTP/1.1
        Host: 127.0.0.1
        Accept: application/json

    **Example response**:

    .. sourcecode:: http

        HTTP/1.1 200 OK
        Content-Type: application/json
        {"concepts":[
            {"uri":"http://id.worldcat.org/fast/950001",
            "prefLabel":"HTTP (Computer network protocol)",
            "altLabels":["HyperText Transfer Protocol (Computer network protocol)"],
            "related":[
                {"label":"Computer network protocols",
                "uri":"http://id.worldcat.org/fast/872279"}
            ]},
            {"uri":"http://id.worldcat.org/fast/878049",
            "prefLabel":"Cookies (Computer science)",
            "altLabels":["Persistent cookies (Computer science)","HTTP cookies (Computer science)","Magic cookies (Computer science)"],
            "related":[
                {"label":"Data structures (Computer science)",
                "uri":"http://id.worldcat.org/fast/887978"}]}
            ]
        }

    :query q: search query
    :type uri: string
    :query count: number of results
    :type count: int
    :query page: page (pagination)
    :type page: int
    
    :statuscode 200: request done
    :statuscode 400: Bad request (if you don't pass the parameter)
    :statuscode 404: Not found
    :statuscode 500: an exception occured
