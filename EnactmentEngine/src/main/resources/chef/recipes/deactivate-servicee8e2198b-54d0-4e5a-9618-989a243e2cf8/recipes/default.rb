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

#ruby_block "disable-servicee8e2198b-54d0-4e5a-9618-989a243e2cf8" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicee8e2198b-54d0-4e5a-9618-989a243e2cf8::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['e8e2198b-54d0-4e5a-9618-989a243e2cf8']['InstallationDir']}/service-e8e2198b-54d0-4e5a-9618-989a243e2cf8.jar]", :immediately
#end

ruby_block "remove-servicee8e2198b-54d0-4e5a-9618-989a243e2cf8" do
  block do
  	node.run_list.remove("recipe[servicee8e2198b-54d0-4e5a-9618-989a243e2cf8::jar]")
  end
  only_if { node.run_list.include?("recipe[servicee8e2198b-54d0-4e5a-9618-989a243e2cf8::jar]") }
end

ruby_block "remove-deactivate-servicee8e2198b-54d0-4e5a-9618-989a243e2cf8" do
  block do
    node.run_list.remove("recipe[deactivate-servicee8e2198b-54d0-4e5a-9618-989a243e2cf8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee8e2198b-54d0-4e5a-9618-989a243e2cf8]") }
end
