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

ruby_block "disable-serviceda66e508-c56a-402d-9f6c-1ddc2a26d0e8" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceda66e508-c56a-402d-9f6c-1ddc2a26d0e8::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['InstallationDir']}/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.jar]", :immediately
end

ruby_block "remove-serviceda66e508-c56a-402d-9f6c-1ddc2a26d0e8" do
  block do
  	node.run_list.remove("recipe[serviceda66e508-c56a-402d-9f6c-1ddc2a26d0e8::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceda66e508-c56a-402d-9f6c-1ddc2a26d0e8::jar]") }
end

ruby_block "remove-deactivate-serviceda66e508-c56a-402d-9f6c-1ddc2a26d0e8" do
  block do
    node.run_list.remove("recipe[deactivate-serviceda66e508-c56a-402d-9f6c-1ddc2a26d0e8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceda66e508-c56a-402d-9f6c-1ddc2a26d0e8]") }
end
