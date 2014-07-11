Get endpoint
============

Endpoint for retrieving SKOS concepts by their unique identifier.

.. http:get:: /get

    Get SKOS concepts by their URI

    **Example request**:

    .. sourcecode:: http

        GET /get?uri=http://id.worldcat.org/fast/869764 HTTP/1.1
        Host: 127.0.0.1
        Accept: application/json

    **Example request with multiple URIs**:

    .. sourcecode:: http

        GET /get?uri=http://id.worldcat.org/fast/1902995&uri=http://id.worldcat.org/fast/869764
        Host: 127.0.0.1
        Accept: application/json

    **Example response**:

    .. sourcecode:: http

        HTTP/1.1 200 OK
        Content-Type: application/json

        {
            "_links":
                {"self":
                    {"href":"/search?uri=http://id.worldcat.org/fast/1902995&uri=http://id.worldcat.org/fast/869764"}},
            "_embedded":
                {"concepts":
                    [{"uri":"http://id.worldcat.org/fast/1902995",
                    "prefLabel":"Depressions in motion pictures",
                    "altLabels":[],"related":[]},
                    {"uri":"http://id.worldcat.org/fast/869764",
                    "prefLabel":"Commodore 64 (Computer)",
                    "altLabels":[],
                    "related":[{"label":"Electronic digital computers","uri":"http://id.worldcat.org/fast/907122"},{"label":"Commodore computers","uri":"http://id.worldcat.org/fast/869771"}]}
        ]}}

    Expect the same response if only one concept is requested (i.e. a list of one concept).

    :query uri: unique identifier of a SKOS concept
    :type uri: string

    :statuscode 200: request done
    :statuscode 400: Bad request (if you don't pass the parameter)
    :statuscode 500: an exception occured
