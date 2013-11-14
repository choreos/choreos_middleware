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

ruby_block "remove-service14a0b696-fa81-4c14-925b-43a57e3d92c0" do
  block do
  	node.run_list.remove("recipe[service14a0b696-fa81-4c14-925b-43a57e3d92c0::jar]")
  end
  only_if { node.run_list.include?("recipe[service14a0b696-fa81-4c14-925b-43a57e3d92c0::jar]") }
end

ruby_block "remove-deactivate-service14a0b696-fa81-4c14-925b-43a57e3d92c0" do
  block do
    node.run_list.remove("recipe[deactivate-service14a0b696-fa81-4c14-925b-43a57e3d92c0]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service14a0b696-fa81-4c14-925b-43a57e3d92c0]") }
  notifies :stop, "service[service_14a0b696-fa81-4c14-925b-43a57e3d92c0_jar]", :immediately
end
