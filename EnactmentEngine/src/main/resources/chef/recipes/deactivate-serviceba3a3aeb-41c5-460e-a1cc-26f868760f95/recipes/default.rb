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

ruby_block "disable-serviceba3a3aeb-41c5-460e-a1cc-26f868760f95" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceba3a3aeb-41c5-460e-a1cc-26f868760f95::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ba3a3aeb-41c5-460e-a1cc-26f868760f95']['InstallationDir']}/service-ba3a3aeb-41c5-460e-a1cc-26f868760f95.jar]", :immediately
end

ruby_block "remove-serviceba3a3aeb-41c5-460e-a1cc-26f868760f95" do
  block do
  	node.run_list.remove("recipe[serviceba3a3aeb-41c5-460e-a1cc-26f868760f95::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceba3a3aeb-41c5-460e-a1cc-26f868760f95::jar]") }
end

ruby_block "remove-deactivate-serviceba3a3aeb-41c5-460e-a1cc-26f868760f95" do
  block do
    node.run_list.remove("recipe[deactivate-serviceba3a3aeb-41c5-460e-a1cc-26f868760f95]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceba3a3aeb-41c5-460e-a1cc-26f868760f95]") }
end
