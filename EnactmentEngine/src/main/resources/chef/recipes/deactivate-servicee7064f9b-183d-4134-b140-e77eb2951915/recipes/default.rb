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

ruby_block "disable-servicee7064f9b-183d-4134-b140-e77eb2951915" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee7064f9b-183d-4134-b140-e77eb2951915::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['e7064f9b-183d-4134-b140-e77eb2951915']['InstallationDir']}/service-e7064f9b-183d-4134-b140-e77eb2951915.jar]", :immediately
end

ruby_block "remove-servicee7064f9b-183d-4134-b140-e77eb2951915" do
  block do
  	node.run_list.remove("recipe[servicee7064f9b-183d-4134-b140-e77eb2951915::jar]")
  end
  only_if { node.run_list.include?("recipe[servicee7064f9b-183d-4134-b140-e77eb2951915::jar]") }
end

ruby_block "remove-deactivate-servicee7064f9b-183d-4134-b140-e77eb2951915" do
  block do
    node.run_list.remove("recipe[deactivate-servicee7064f9b-183d-4134-b140-e77eb2951915]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee7064f9b-183d-4134-b140-e77eb2951915]") }
end
