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

ruby_block "remove-service06fd1a16-1198-4aae-9cc0-4dc261cfc166" do
  block do
  	node.run_list.remove("recipe[service06fd1a16-1198-4aae-9cc0-4dc261cfc166::jar]")
  end
  only_if { node.run_list.include?("recipe[service06fd1a16-1198-4aae-9cc0-4dc261cfc166::jar]") }
end

ruby_block "remove-deactivate-service06fd1a16-1198-4aae-9cc0-4dc261cfc166" do
  block do
    node.run_list.remove("recipe[deactivate-service06fd1a16-1198-4aae-9cc0-4dc261cfc166]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service06fd1a16-1198-4aae-9cc0-4dc261cfc166]") }
  notifies :stop, "service[service_06fd1a16-1198-4aae-9cc0-4dc261cfc166_jar]", :immediately
end
