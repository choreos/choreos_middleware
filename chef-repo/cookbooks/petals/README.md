Description
====

This cookbook installs Petals Enterprise Service Bus. 

More information about Petals ESB at http://http://petals.ow2.org/

Requirements
====

Works on any platform with Java Sun SDK installed.

Usage
====

Most often this will be used to generate a secure password for an attribute.

    include Opscode::OpenSSL::Password

    set_unless[:my_password] = secure_password

License and Author
====

Daniel Cukier
CCSL - Centro de Competencia de Software Livre - IME/USP
University of Sao Paulo
http://ccsl.ime.usp.br
Apache 2.0 License

CHOReOS Project
http://choreos.eu/

