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

ruby_block "disable-service97aaa8db-f1c2-4ba3-964b-bace8e71fd4b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service97aaa8db-f1c2-4ba3-964b-bace8e71fd4b::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['InstallationDir']}/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.jar]", :immediately
end

ruby_block "remove-service97aaa8db-f1c2-4ba3-964b-bace8e71fd4b" do
  block do
  	node.run_list.remove("recipe[service97aaa8db-f1c2-4ba3-964b-bace8e71fd4b::jar]")
  end
  only_if { node.run_list.include?("recipe[service97aaa8db-f1c2-4ba3-964b-bace8e71fd4b::jar]") }
end

ruby_block "remove-deactivate-service97aaa8db-f1c2-4ba3-964b-bace8e71fd4b" do
  block do
    node.run_list.remove("recipe[deactivate-service97aaa8db-f1c2-4ba3-964b-bace8e71fd4b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service97aaa8db-f1c2-4ba3-964b-bace8e71fd4b]") }
end
