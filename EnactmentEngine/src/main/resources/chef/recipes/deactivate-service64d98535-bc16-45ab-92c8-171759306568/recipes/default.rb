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

#ruby_block "disable-service64d98535-bc16-45ab-92c8-171759306568" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service64d98535-bc16-45ab-92c8-171759306568::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['64d98535-bc16-45ab-92c8-171759306568']['InstallationDir']}/service-64d98535-bc16-45ab-92c8-171759306568.jar]", :immediately
#end

ruby_block "remove-service64d98535-bc16-45ab-92c8-171759306568" do
  block do
  	node.run_list.remove("recipe[service64d98535-bc16-45ab-92c8-171759306568::jar]")
  end
  only_if { node.run_list.include?("recipe[service64d98535-bc16-45ab-92c8-171759306568::jar]") }
end

ruby_block "remove-deactivate-service64d98535-bc16-45ab-92c8-171759306568" do
  block do
    node.run_list.remove("recipe[deactivate-service64d98535-bc16-45ab-92c8-171759306568]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service64d98535-bc16-45ab-92c8-171759306568]") }
end
