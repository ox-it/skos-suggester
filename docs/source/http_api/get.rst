Get endpoint
============

Endpoint for retrieving a SKOS concept by its unique identifier.

.. http:get:: /get

    Get a SKOS concept by its URI

    **Example request**:

    .. sourcecode:: http

        GET /get?uri=http://id.worldcat.org/fast/869764 HTTP/1.1
        Host: 127.0.0.1
        Accept: application/json

    **Example response**:

    .. sourcecode:: http

        HTTP/1.1 200 OK
        Content-Type: application/json
        {
            "uri": "http://id.worldcat.org/fast/869764",
            "prefLabel":"Commodore 64 (Computer)",
            "altLabels":null,
            "related":[
                {"label":"Electronic digital computers",
                "uri":"http://id.worldcat.org/fast/907122"}
            ]
        }

    :query uri: unique identifier of a SKOS concept
    :type uri: string
    
    :statuscode 200: request done
    :statuscode 400: Bad request (if you don't pass the parameter)
    :statuscode 404: Not found
    :statuscode 500: an exception occured
