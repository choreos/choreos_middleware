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

ruby_block "disable-serviceee9bdeee-ed9c-44ca-8d4c-3bb632248509" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceee9bdeee-ed9c-44ca-8d4c-3bb632248509::jar]") }
  notifies :stop, "service[service_ee9bdeee-ed9c-44ca-8d4c-3bb632248509_jar]", :immediately
end

ruby_block "remove-serviceee9bdeee-ed9c-44ca-8d4c-3bb632248509" do
  block do
  	node.run_list.remove("recipe[serviceee9bdeee-ed9c-44ca-8d4c-3bb632248509::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceee9bdeee-ed9c-44ca-8d4c-3bb632248509::jar]") }
end

ruby_block "remove-deactivate-serviceee9bdeee-ed9c-44ca-8d4c-3bb632248509" do
  block do
    node.run_list.remove("recipe[deactivate-serviceee9bdeee-ed9c-44ca-8d4c-3bb632248509]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceee9bdeee-ed9c-44ca-8d4c-3bb632248509]") }
end
