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

ruby_block "disable-service5c40b23a-fc44-49c7-aeb3-be3d586827de" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service5c40b23a-fc44-49c7-aeb3-be3d586827de::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['InstallationDir']}/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.jar]", :immediately
end

ruby_block "remove-service5c40b23a-fc44-49c7-aeb3-be3d586827de" do
  block do
  	node.run_list.remove("recipe[service5c40b23a-fc44-49c7-aeb3-be3d586827de::jar]")
  end
  only_if { node.run_list.include?("recipe[service5c40b23a-fc44-49c7-aeb3-be3d586827de::jar]") }
end

ruby_block "remove-deactivate-service5c40b23a-fc44-49c7-aeb3-be3d586827de" do
  block do
    node.run_list.remove("recipe[deactivate-service5c40b23a-fc44-49c7-aeb3-be3d586827de]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service5c40b23a-fc44-49c7-aeb3-be3d586827de]") }
end
