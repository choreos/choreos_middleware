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

ruby_block "disable-service5270a2cf-30c7-48df-9629-81f25829d37e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service5270a2cf-30c7-48df-9629-81f25829d37e::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['InstallationDir']}/service-5270a2cf-30c7-48df-9629-81f25829d37e.jar]", :immediately
end

ruby_block "remove-service5270a2cf-30c7-48df-9629-81f25829d37e" do
  block do
  	node.run_list.remove("recipe[service5270a2cf-30c7-48df-9629-81f25829d37e::jar]")
  end
  only_if { node.run_list.include?("recipe[service5270a2cf-30c7-48df-9629-81f25829d37e::jar]") }
end

ruby_block "remove-deactivate-service5270a2cf-30c7-48df-9629-81f25829d37e" do
  block do
    node.run_list.remove("recipe[deactivate-service5270a2cf-30c7-48df-9629-81f25829d37e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service5270a2cf-30c7-48df-9629-81f25829d37e]") }
end
