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

ruby_block "disable-serviceea4c70c8-ed94-4ba2-931c-f73102da4cd1" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceea4c70c8-ed94-4ba2-931c-f73102da4cd1::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ea4c70c8-ed94-4ba2-931c-f73102da4cd1']['InstallationDir']}/service-ea4c70c8-ed94-4ba2-931c-f73102da4cd1.jar]", :immediately
end

ruby_block "remove-serviceea4c70c8-ed94-4ba2-931c-f73102da4cd1" do
  block do
  	node.run_list.remove("recipe[serviceea4c70c8-ed94-4ba2-931c-f73102da4cd1::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceea4c70c8-ed94-4ba2-931c-f73102da4cd1::jar]") }
end

ruby_block "remove-deactivate-serviceea4c70c8-ed94-4ba2-931c-f73102da4cd1" do
  block do
    node.run_list.remove("recipe[deactivate-serviceea4c70c8-ed94-4ba2-931c-f73102da4cd1]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceea4c70c8-ed94-4ba2-931c-f73102da4cd1]") }
end
