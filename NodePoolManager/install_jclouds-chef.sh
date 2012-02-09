cd ../..
git clone https://github.com/jclouds/jclouds-chef.git
cd jclouds-chef
mvn install
cd ..
rm -R jclouds-chef
rmdir jclouds-chef
cd choreos_middleware/NodePoolManager
