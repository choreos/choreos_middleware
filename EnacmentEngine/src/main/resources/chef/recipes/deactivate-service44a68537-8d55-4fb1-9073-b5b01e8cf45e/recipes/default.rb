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

#ruby_block "disable-service44a68537-8d55-4fb1-9073-b5b01e8cf45e" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service44a68537-8d55-4fb1-9073-b5b01e8cf45e::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['44a68537-8d55-4fb1-9073-b5b01e8cf45e']['InstallationDir']}/service-44a68537-8d55-4fb1-9073-b5b01e8cf45e.jar]", :immediately
#end

ruby_block "remove-service44a68537-8d55-4fb1-9073-b5b01e8cf45e" do
  block do
  	node.run_list.remove("recipe[service44a68537-8d55-4fb1-9073-b5b01e8cf45e::jar]")
  end
  only_if { node.run_list.include?("recipe[service44a68537-8d55-4fb1-9073-b5b01e8cf45e::jar]") }
end

ruby_block "remove-deactivate-service44a68537-8d55-4fb1-9073-b5b01e8cf45e" do
  block do
    node.run_list.remove("recipe[deactivate-service44a68537-8d55-4fb1-9073-b5b01e8cf45e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service44a68537-8d55-4fb1-9073-b5b01e8cf45e]") }
end
