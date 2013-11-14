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

#ruby_block "disable-serviceaacc49f1-dd4c-456d-a10d-cde4be062dca" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceaacc49f1-dd4c-456d-a10d-cde4be062dca::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['aacc49f1-dd4c-456d-a10d-cde4be062dca']['InstallationDir']}/service-aacc49f1-dd4c-456d-a10d-cde4be062dca.jar]", :immediately
#end

ruby_block "remove-serviceaacc49f1-dd4c-456d-a10d-cde4be062dca" do
  block do
  	node.run_list.remove("recipe[serviceaacc49f1-dd4c-456d-a10d-cde4be062dca::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceaacc49f1-dd4c-456d-a10d-cde4be062dca::jar]") }
end

ruby_block "remove-deactivate-serviceaacc49f1-dd4c-456d-a10d-cde4be062dca" do
  block do
    node.run_list.remove("recipe[deactivate-serviceaacc49f1-dd4c-456d-a10d-cde4be062dca]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceaacc49f1-dd4c-456d-a10d-cde4be062dca]") }
end
