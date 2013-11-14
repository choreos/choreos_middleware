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

ruby_block "remove-activate-service385e614c-7cfb-442f-8bbc-eb181c536574" do
  block do
    node.run_list.remove("recipe[activate-service385e614c-7cfb-442f-8bbc-eb181c536574]")
  end
  only_if { node.run_list.include?("recipe[activate-service385e614c-7cfb-442f-8bbc-eb181c536574]") }
end


ruby_block "remove-deactivate-service385e614c-7cfb-442f-8bbc-eb181c536574" do
  block do
    node.run_list.remove("recipe[deactivate-service385e614c-7cfb-442f-8bbc-eb181c536574]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service385e614c-7cfb-442f-8bbc-eb181c536574]") }
  notifies :stop, "service[service_385e614c-7cfb-442f-8bbc-eb181c536574_jar]", :immediately
end
