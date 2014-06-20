Suggest endpoint
================

Endpoint for suggesting SKOS concepts

Mainly used for type-ahead

.. http:get:: /suggest

    Suggest SKOS concepts

    **Example request**:

    .. sourcecode:: http

        GET /suggest?q=secu HTTP/1.1
        Host: 127.0.0.1
        Accept: application/json

    **Example response**:

    .. sourcecode:: http

        HTTP/1.1 200 OK
        Content-Type: application/json
        
        {"concepts":[
            {"uri":"http://id.worldcat.org/fast/872484","prefLabel":"Computer security","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/869774","prefLabel":"Common Data Security Architecture (Computer security standard)","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/1005142","prefLabel":"Macintosh (Computer)--Security measures","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/872590","prefLabel":"Computer software--Security measures","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/872437","prefLabel":"Computer programs--Security measures","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/872486","prefLabel":"Computer security--Computer programs","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/872485","prefLabel":"Computer security--Auditing","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/925719","prefLabel":"Firewalls (Computer security)","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/872487","prefLabel":"Computer security--Costs","altLabels":[],"related":[]},
            {"uri":"http://id.worldcat.org/fast/872488","prefLabel":"Computer security--Evaluation","altLabels":[],"related":[]}]
        }
        

    :query q: search query
    :type uri: string
    
    :statuscode 200: request done
    :statuscode 400: Bad request (if you don't pass the parameter)
    :statuscode 404: Not found
    :statuscode 500: an exception occured
