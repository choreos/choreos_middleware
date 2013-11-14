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

ruby_block "remove-service8d60fbd3-7b28-4eda-a059-a0fcab79aa89" do
  block do
  	node.run_list.remove("recipe[service8d60fbd3-7b28-4eda-a059-a0fcab79aa89::jar]")
  end
  only_if { node.run_list.include?("recipe[service8d60fbd3-7b28-4eda-a059-a0fcab79aa89::jar]") }
end

ruby_block "remove-deactivate-service8d60fbd3-7b28-4eda-a059-a0fcab79aa89" do
  block do
    node.run_list.remove("recipe[deactivate-service8d60fbd3-7b28-4eda-a059-a0fcab79aa89]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8d60fbd3-7b28-4eda-a059-a0fcab79aa89]") }
  notifies :stop, "service[service_8d60fbd3-7b28-4eda-a059-a0fcab79aa89_jar]", :immediately
end
