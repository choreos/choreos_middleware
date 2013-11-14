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

ruby_block "disable-servicefdd71959-a719-47ab-bb9b-c6c9220d8d79" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicefdd71959-a719-47ab-bb9b-c6c9220d8d79::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['fdd71959-a719-47ab-bb9b-c6c9220d8d79']['InstallationDir']}/service-fdd71959-a719-47ab-bb9b-c6c9220d8d79.jar]", :immediately
end

ruby_block "remove-servicefdd71959-a719-47ab-bb9b-c6c9220d8d79" do
  block do
  	node.run_list.remove("recipe[servicefdd71959-a719-47ab-bb9b-c6c9220d8d79::jar]")
  end
  only_if { node.run_list.include?("recipe[servicefdd71959-a719-47ab-bb9b-c6c9220d8d79::jar]") }
end

ruby_block "remove-deactivate-servicefdd71959-a719-47ab-bb9b-c6c9220d8d79" do
  block do
    node.run_list.remove("recipe[deactivate-servicefdd71959-a719-47ab-bb9b-c6c9220d8d79]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicefdd71959-a719-47ab-bb9b-c6c9220d8d79]") }
end
