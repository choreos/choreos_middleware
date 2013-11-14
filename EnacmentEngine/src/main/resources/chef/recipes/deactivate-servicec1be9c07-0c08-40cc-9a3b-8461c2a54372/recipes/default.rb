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

ruby_block "disable-servicec1be9c07-0c08-40cc-9a3b-8461c2a54372" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec1be9c07-0c08-40cc-9a3b-8461c2a54372::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['c1be9c07-0c08-40cc-9a3b-8461c2a54372']['InstallationDir']}/service-c1be9c07-0c08-40cc-9a3b-8461c2a54372.jar]", :immediately
end

ruby_block "remove-servicec1be9c07-0c08-40cc-9a3b-8461c2a54372" do
  block do
  	node.run_list.remove("recipe[servicec1be9c07-0c08-40cc-9a3b-8461c2a54372::jar]")
  end
  only_if { node.run_list.include?("recipe[servicec1be9c07-0c08-40cc-9a3b-8461c2a54372::jar]") }
end

ruby_block "remove-deactivate-servicec1be9c07-0c08-40cc-9a3b-8461c2a54372" do
  block do
    node.run_list.remove("recipe[deactivate-servicec1be9c07-0c08-40cc-9a3b-8461c2a54372]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec1be9c07-0c08-40cc-9a3b-8461c2a54372]") }
end
