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

ruby_block "remove-activate-servicef7ae5cba-6c05-4a5b-97b7-69de91df1acc" do
  block do
    node.run_list.remove("recipe[activate-servicef7ae5cba-6c05-4a5b-97b7-69de91df1acc]")
  end
  only_if { node.run_list.include?("recipe[activate-servicef7ae5cba-6c05-4a5b-97b7-69de91df1acc]") }
end


ruby_block "remove-deactivate-servicef7ae5cba-6c05-4a5b-97b7-69de91df1acc" do
  block do
    node.run_list.remove("recipe[deactivate-servicef7ae5cba-6c05-4a5b-97b7-69de91df1acc]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef7ae5cba-6c05-4a5b-97b7-69de91df1acc]") }
  notifies :stop, "service[service_f7ae5cba-6c05-4a5b-97b7-69de91df1acc_jar]", :immediately
end
