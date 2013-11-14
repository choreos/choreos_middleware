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

ruby_block "remove-service2322b728-ecdc-4035-aa6b-1265298fc9bd" do
  block do
  	node.run_list.remove("recipe[service2322b728-ecdc-4035-aa6b-1265298fc9bd::jar]")
  end
  only_if { node.run_list.include?("recipe[service2322b728-ecdc-4035-aa6b-1265298fc9bd::jar]") }
end

ruby_block "remove-deactivate-service2322b728-ecdc-4035-aa6b-1265298fc9bd" do
  block do
    node.run_list.remove("recipe[deactivate-service2322b728-ecdc-4035-aa6b-1265298fc9bd]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2322b728-ecdc-4035-aa6b-1265298fc9bd]") }
  notifies :stop, "service[service_2322b728-ecdc-4035-aa6b-1265298fc9bd_jar]", :immediately
end
