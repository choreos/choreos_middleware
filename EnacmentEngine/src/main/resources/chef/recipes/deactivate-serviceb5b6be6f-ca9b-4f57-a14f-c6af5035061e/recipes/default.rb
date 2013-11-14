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

#ruby_block "disable-serviceb5b6be6f-ca9b-4f57-a14f-c6af5035061e" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceb5b6be6f-ca9b-4f57-a14f-c6af5035061e::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['b5b6be6f-ca9b-4f57-a14f-c6af5035061e']['InstallationDir']}/service-b5b6be6f-ca9b-4f57-a14f-c6af5035061e.jar]", :immediately
#end

ruby_block "remove-serviceb5b6be6f-ca9b-4f57-a14f-c6af5035061e" do
  block do
  	node.run_list.remove("recipe[serviceb5b6be6f-ca9b-4f57-a14f-c6af5035061e::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb5b6be6f-ca9b-4f57-a14f-c6af5035061e::jar]") }
end

ruby_block "remove-deactivate-serviceb5b6be6f-ca9b-4f57-a14f-c6af5035061e" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb5b6be6f-ca9b-4f57-a14f-c6af5035061e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb5b6be6f-ca9b-4f57-a14f-c6af5035061e]") }
end
