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

ruby_block "remove-activate-service9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0" do
  block do
    node.run_list.remove("recipe[activate-service9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0]")
  end
  only_if { node.run_list.include?("recipe[activate-service9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0]") }
end


ruby_block "remove-deactivate-service9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0" do
  block do
    node.run_list.remove("recipe[deactivate-service9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0]") }
  notifies :stop, "service[service_9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0_jar]", :immediately
end
