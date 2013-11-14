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

#ruby_block "disable-service92450c26-011a-4ea1-80d7-3bac47d8868a" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service92450c26-011a-4ea1-80d7-3bac47d8868a::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['92450c26-011a-4ea1-80d7-3bac47d8868a']['InstallationDir']}/service-92450c26-011a-4ea1-80d7-3bac47d8868a.jar]", :immediately
#end

ruby_block "remove-service92450c26-011a-4ea1-80d7-3bac47d8868a" do
  block do
  	node.run_list.remove("recipe[service92450c26-011a-4ea1-80d7-3bac47d8868a::jar]")
  end
  only_if { node.run_list.include?("recipe[service92450c26-011a-4ea1-80d7-3bac47d8868a::jar]") }
end

ruby_block "remove-deactivate-service92450c26-011a-4ea1-80d7-3bac47d8868a" do
  block do
    node.run_list.remove("recipe[deactivate-service92450c26-011a-4ea1-80d7-3bac47d8868a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service92450c26-011a-4ea1-80d7-3bac47d8868a]") }
end
