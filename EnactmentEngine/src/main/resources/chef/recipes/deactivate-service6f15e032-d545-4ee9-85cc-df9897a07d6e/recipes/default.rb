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

ruby_block "disable-service6f15e032-d545-4ee9-85cc-df9897a07d6e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service6f15e032-d545-4ee9-85cc-df9897a07d6e::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['InstallationDir']}/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.jar]", :immediately
end

ruby_block "remove-service6f15e032-d545-4ee9-85cc-df9897a07d6e" do
  block do
  	node.run_list.remove("recipe[service6f15e032-d545-4ee9-85cc-df9897a07d6e::jar]")
  end
  only_if { node.run_list.include?("recipe[service6f15e032-d545-4ee9-85cc-df9897a07d6e::jar]") }
end

ruby_block "remove-deactivate-service6f15e032-d545-4ee9-85cc-df9897a07d6e" do
  block do
    node.run_list.remove("recipe[deactivate-service6f15e032-d545-4ee9-85cc-df9897a07d6e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6f15e032-d545-4ee9-85cc-df9897a07d6e]") }
end
