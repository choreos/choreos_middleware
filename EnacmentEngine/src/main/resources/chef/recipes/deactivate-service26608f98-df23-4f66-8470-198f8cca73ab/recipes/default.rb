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

ruby_block "disable-service26608f98-df23-4f66-8470-198f8cca73ab" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service26608f98-df23-4f66-8470-198f8cca73ab::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['26608f98-df23-4f66-8470-198f8cca73ab']['InstallationDir']}/service-26608f98-df23-4f66-8470-198f8cca73ab.jar]", :immediately
end

ruby_block "remove-service26608f98-df23-4f66-8470-198f8cca73ab" do
  block do
  	node.run_list.remove("recipe[service26608f98-df23-4f66-8470-198f8cca73ab::jar]")
  end
  only_if { node.run_list.include?("recipe[service26608f98-df23-4f66-8470-198f8cca73ab::jar]") }
end

ruby_block "remove-deactivate-service26608f98-df23-4f66-8470-198f8cca73ab" do
  block do
    node.run_list.remove("recipe[deactivate-service26608f98-df23-4f66-8470-198f8cca73ab]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service26608f98-df23-4f66-8470-198f8cca73ab]") }
end
