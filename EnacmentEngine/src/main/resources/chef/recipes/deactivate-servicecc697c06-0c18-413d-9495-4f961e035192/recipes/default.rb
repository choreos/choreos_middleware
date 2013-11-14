#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

#ruby_block "disable-servicecc697c06-0c18-413d-9495-4f961e035192" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicecc697c06-0c18-413d-9495-4f961e035192::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['cc697c06-0c18-413d-9495-4f961e035192']['InstallationDir']}/service-cc697c06-0c18-413d-9495-4f961e035192.jar]", :immediately
#end

ruby_block "remove-servicecc697c06-0c18-413d-9495-4f961e035192" do
  block do
  	node.run_list.remove("recipe[servicecc697c06-0c18-413d-9495-4f961e035192::jar]")
  end
  only_if { node.run_list.include?("recipe[servicecc697c06-0c18-413d-9495-4f961e035192::jar]") }
end

ruby_block "remove-deactivate-servicecc697c06-0c18-413d-9495-4f961e035192" do
  block do
    node.run_list.remove("recipe[deactivate-servicecc697c06-0c18-413d-9495-4f961e035192]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicecc697c06-0c18-413d-9495-4f961e035192]") }
end
