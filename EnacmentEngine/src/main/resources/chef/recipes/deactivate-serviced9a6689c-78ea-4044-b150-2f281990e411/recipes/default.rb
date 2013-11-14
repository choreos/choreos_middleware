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

ruby_block "disable-serviced9a6689c-78ea-4044-b150-2f281990e411" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced9a6689c-78ea-4044-b150-2f281990e411::jar]") }
  notifies :stop, "service[service_d9a6689c-78ea-4044-b150-2f281990e411_jar]", :immediately
end

ruby_block "remove-serviced9a6689c-78ea-4044-b150-2f281990e411" do
  block do
  	node.run_list.remove("recipe[serviced9a6689c-78ea-4044-b150-2f281990e411::jar]")
  end
  only_if { node.run_list.include?("recipe[serviced9a6689c-78ea-4044-b150-2f281990e411::jar]") }
end

ruby_block "remove-deactivate-serviced9a6689c-78ea-4044-b150-2f281990e411" do
  block do
    node.run_list.remove("recipe[deactivate-serviced9a6689c-78ea-4044-b150-2f281990e411]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced9a6689c-78ea-4044-b150-2f281990e411]") }
end
