git clone git://github.com/duns/cpptasks-parallel.git
git clone https://github.com/duns/maven-nar-plugin.git

cd cpptasks-parallel
mvn install

cd ..

cd maven-nar-plugin
mvn install

cd ..
rm -rf cpptasks-parallel
rm -rf  maven-nar-plugin

