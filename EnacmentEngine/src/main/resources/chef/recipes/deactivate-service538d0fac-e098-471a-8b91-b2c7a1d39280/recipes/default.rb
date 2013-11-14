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

ruby_block "disable-service538d0fac-e098-471a-8b91-b2c7a1d39280" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service538d0fac-e098-471a-8b91-b2c7a1d39280::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['538d0fac-e098-471a-8b91-b2c7a1d39280']['InstallationDir']}/service-538d0fac-e098-471a-8b91-b2c7a1d39280.jar]", :immediately
end

ruby_block "remove-service538d0fac-e098-471a-8b91-b2c7a1d39280" do
  block do
  	node.run_list.remove("recipe[service538d0fac-e098-471a-8b91-b2c7a1d39280::jar]")
  end
  only_if { node.run_list.include?("recipe[service538d0fac-e098-471a-8b91-b2c7a1d39280::jar]") }
end

ruby_block "remove-deactivate-service538d0fac-e098-471a-8b91-b2c7a1d39280" do
  block do
    node.run_list.remove("recipe[deactivate-service538d0fac-e098-471a-8b91-b2c7a1d39280]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service538d0fac-e098-471a-8b91-b2c7a1d39280]") }
end
