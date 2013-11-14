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

#ruby_block "disable-serviceaf9b2971-994c-4184-95d0-7c284563e31c" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceaf9b2971-994c-4184-95d0-7c284563e31c::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['af9b2971-994c-4184-95d0-7c284563e31c']['InstallationDir']}/service-af9b2971-994c-4184-95d0-7c284563e31c.jar]", :immediately
#end

ruby_block "remove-serviceaf9b2971-994c-4184-95d0-7c284563e31c" do
  block do
  	node.run_list.remove("recipe[serviceaf9b2971-994c-4184-95d0-7c284563e31c::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceaf9b2971-994c-4184-95d0-7c284563e31c::jar]") }
end

ruby_block "remove-deactivate-serviceaf9b2971-994c-4184-95d0-7c284563e31c" do
  block do
    node.run_list.remove("recipe[deactivate-serviceaf9b2971-994c-4184-95d0-7c284563e31c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceaf9b2971-994c-4184-95d0-7c284563e31c]") }
end
