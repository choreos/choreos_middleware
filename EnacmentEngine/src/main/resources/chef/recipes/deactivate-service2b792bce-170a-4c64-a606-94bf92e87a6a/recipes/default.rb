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

#ruby_block "disable-service2b792bce-170a-4c64-a606-94bf92e87a6a" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service2b792bce-170a-4c64-a606-94bf92e87a6a::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['2b792bce-170a-4c64-a606-94bf92e87a6a']['InstallationDir']}/service-2b792bce-170a-4c64-a606-94bf92e87a6a.jar]", :immediately
#end

ruby_block "remove-service2b792bce-170a-4c64-a606-94bf92e87a6a" do
  block do
  	node.run_list.remove("recipe[service2b792bce-170a-4c64-a606-94bf92e87a6a::jar]")
  end
  only_if { node.run_list.include?("recipe[service2b792bce-170a-4c64-a606-94bf92e87a6a::jar]") }
end

ruby_block "remove-deactivate-service2b792bce-170a-4c64-a606-94bf92e87a6a" do
  block do
    node.run_list.remove("recipe[deactivate-service2b792bce-170a-4c64-a606-94bf92e87a6a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2b792bce-170a-4c64-a606-94bf92e87a6a]") }
end
