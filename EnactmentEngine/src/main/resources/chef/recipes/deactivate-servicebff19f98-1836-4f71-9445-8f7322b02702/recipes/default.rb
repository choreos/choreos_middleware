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

#ruby_block "disable-servicebff19f98-1836-4f71-9445-8f7322b02702" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicebff19f98-1836-4f71-9445-8f7322b02702::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['bff19f98-1836-4f71-9445-8f7322b02702']['InstallationDir']}/service-bff19f98-1836-4f71-9445-8f7322b02702.jar]", :immediately
#end

ruby_block "remove-servicebff19f98-1836-4f71-9445-8f7322b02702" do
  block do
  	node.run_list.remove("recipe[servicebff19f98-1836-4f71-9445-8f7322b02702::jar]")
  end
  only_if { node.run_list.include?("recipe[servicebff19f98-1836-4f71-9445-8f7322b02702::jar]") }
end

ruby_block "remove-deactivate-servicebff19f98-1836-4f71-9445-8f7322b02702" do
  block do
    node.run_list.remove("recipe[deactivate-servicebff19f98-1836-4f71-9445-8f7322b02702]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicebff19f98-1836-4f71-9445-8f7322b02702]") }
end
