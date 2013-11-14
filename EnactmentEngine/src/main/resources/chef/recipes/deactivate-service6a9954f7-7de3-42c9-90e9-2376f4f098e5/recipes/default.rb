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

ruby_block "disable-service6a9954f7-7de3-42c9-90e9-2376f4f098e5" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service6a9954f7-7de3-42c9-90e9-2376f4f098e5::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['InstallationDir']}/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.jar]", :immediately
end

ruby_block "remove-service6a9954f7-7de3-42c9-90e9-2376f4f098e5" do
  block do
  	node.run_list.remove("recipe[service6a9954f7-7de3-42c9-90e9-2376f4f098e5::jar]")
  end
  only_if { node.run_list.include?("recipe[service6a9954f7-7de3-42c9-90e9-2376f4f098e5::jar]") }
end

ruby_block "remove-deactivate-service6a9954f7-7de3-42c9-90e9-2376f4f098e5" do
  block do
    node.run_list.remove("recipe[deactivate-service6a9954f7-7de3-42c9-90e9-2376f4f098e5]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6a9954f7-7de3-42c9-90e9-2376f4f098e5]") }
end
