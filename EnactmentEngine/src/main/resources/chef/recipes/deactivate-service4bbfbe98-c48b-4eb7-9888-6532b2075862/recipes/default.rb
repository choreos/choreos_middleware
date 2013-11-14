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

ruby_block "remove-service4bbfbe98-c48b-4eb7-9888-6532b2075862" do
  block do
  	node.run_list.remove("recipe[service4bbfbe98-c48b-4eb7-9888-6532b2075862::jar]")
  end
  only_if { node.run_list.include?("recipe[service4bbfbe98-c48b-4eb7-9888-6532b2075862::jar]") }
end

ruby_block "remove-deactivate-service4bbfbe98-c48b-4eb7-9888-6532b2075862" do
  block do
    node.run_list.remove("recipe[deactivate-service4bbfbe98-c48b-4eb7-9888-6532b2075862]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4bbfbe98-c48b-4eb7-9888-6532b2075862]") }
  notifies :stop, "service[service_4bbfbe98-c48b-4eb7-9888-6532b2075862_jar]", :immediately
end
