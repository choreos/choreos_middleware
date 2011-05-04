Requirements
============

- Maven 3. Basically download, unpack and add its bin to your $PATH.


Petals Studio
=============

1. Copy the file settings.xml (root folder) to your maven repository (usually ~/.m2). If you don't have that folder, create it.
2. In this folder (where README.txt resides), run:
   a. mvn install
   b. mvn eclipse:eclipse
3. Import all projects into petals studio.
4. Add M2_REPO variable in Eclipse (default is ~/.m2/repository)
