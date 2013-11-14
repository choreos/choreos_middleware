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

ruby_block "disable-serviceb727367d-eb90-4bf1-951c-beadd0890a0e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb727367d-eb90-4bf1-951c-beadd0890a0e::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['InstallationDir']}/service-b727367d-eb90-4bf1-951c-beadd0890a0e.jar]", :immediately
end

ruby_block "remove-serviceb727367d-eb90-4bf1-951c-beadd0890a0e" do
  block do
  	node.run_list.remove("recipe[serviceb727367d-eb90-4bf1-951c-beadd0890a0e::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb727367d-eb90-4bf1-951c-beadd0890a0e::jar]") }
end

ruby_block "remove-deactivate-serviceb727367d-eb90-4bf1-951c-beadd0890a0e" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb727367d-eb90-4bf1-951c-beadd0890a0e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb727367d-eb90-4bf1-951c-beadd0890a0e]") }
end
