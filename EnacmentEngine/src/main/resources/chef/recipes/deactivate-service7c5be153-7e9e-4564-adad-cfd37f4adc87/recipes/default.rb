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

ruby_block "remove-service7c5be153-7e9e-4564-adad-cfd37f4adc87" do
  block do
  	node.run_list.remove("recipe[service7c5be153-7e9e-4564-adad-cfd37f4adc87::jar]")
  end
  only_if { node.run_list.include?("recipe[service7c5be153-7e9e-4564-adad-cfd37f4adc87::jar]") }
end

ruby_block "remove-deactivate-service7c5be153-7e9e-4564-adad-cfd37f4adc87" do
  block do
    node.run_list.remove("recipe[deactivate-service7c5be153-7e9e-4564-adad-cfd37f4adc87]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7c5be153-7e9e-4564-adad-cfd37f4adc87]") }
  notifies :stop, "service[service_7c5be153-7e9e-4564-adad-cfd37f4adc87_jar]", :immediately
end
