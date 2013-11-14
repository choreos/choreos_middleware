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

ruby_block "disable-servicee0a19a83-dae1-45bb-8db1-c4fd8ae562ba" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee0a19a83-dae1-45bb-8db1-c4fd8ae562ba::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['InstallationDir']}/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.jar]", :immediately
end

ruby_block "remove-servicee0a19a83-dae1-45bb-8db1-c4fd8ae562ba" do
  block do
  	node.run_list.remove("recipe[servicee0a19a83-dae1-45bb-8db1-c4fd8ae562ba::jar]")
  end
  only_if { node.run_list.include?("recipe[servicee0a19a83-dae1-45bb-8db1-c4fd8ae562ba::jar]") }
end

ruby_block "remove-deactivate-servicee0a19a83-dae1-45bb-8db1-c4fd8ae562ba" do
  block do
    node.run_list.remove("recipe[deactivate-servicee0a19a83-dae1-45bb-8db1-c4fd8ae562ba]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee0a19a83-dae1-45bb-8db1-c4fd8ae562ba]") }
end
