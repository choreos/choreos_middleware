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

ruby_block "disable-serviceac9aa197-abce-4bf8-ac18-ca3e0cf38ddb" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceac9aa197-abce-4bf8-ac18-ca3e0cf38ddb::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['InstallationDir']}/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.jar]", :immediately
end

ruby_block "remove-serviceac9aa197-abce-4bf8-ac18-ca3e0cf38ddb" do
  block do
  	node.run_list.remove("recipe[serviceac9aa197-abce-4bf8-ac18-ca3e0cf38ddb::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceac9aa197-abce-4bf8-ac18-ca3e0cf38ddb::jar]") }
end

ruby_block "remove-deactivate-serviceac9aa197-abce-4bf8-ac18-ca3e0cf38ddb" do
  block do
    node.run_list.remove("recipe[deactivate-serviceac9aa197-abce-4bf8-ac18-ca3e0cf38ddb]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceac9aa197-abce-4bf8-ac18-ca3e0cf38ddb]") }
end
