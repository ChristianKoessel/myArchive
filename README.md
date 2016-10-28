# myArchive
A simple personal document archive for home use, based on CouchDB and HTML5

The archive is intended to store images of documents (scanned or taken by a camera) or documents itself (e.g. PDF).
The files are stored in a CouchDB database as documents with attachments.
Every document contains a title, keywords/tags, a reference date and a thumbnail image.

The user interface is a HTML5 web interface which talks directly to the CouchDB via RESTful HTTP, 
the projects intends to avoid the need for an extra application tier/server.
If possible, the CouchDB will be run on a small server like the Rasperry Pi.

For batch imports and configuration issues an extra command line interface, written in Java is provided.

Another option is a simple Android app for taking pictures and upload them to the archive.
