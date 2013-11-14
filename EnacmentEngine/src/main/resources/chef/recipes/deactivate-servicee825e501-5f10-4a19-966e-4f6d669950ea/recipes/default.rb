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

ruby_block "remove-servicee825e501-5f10-4a19-966e-4f6d669950ea" do
  block do
  	node.run_list.remove("recipe[servicee825e501-5f10-4a19-966e-4f6d669950ea::jar]")
  end
  only_if { node.run_list.include?("recipe[servicee825e501-5f10-4a19-966e-4f6d669950ea::jar]") }
end

ruby_block "remove-deactivate-servicee825e501-5f10-4a19-966e-4f6d669950ea" do
  block do
    node.run_list.remove("recipe[deactivate-servicee825e501-5f10-4a19-966e-4f6d669950ea]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee825e501-5f10-4a19-966e-4f6d669950ea]") }
  notifies :stop, "service[service_e825e501-5f10-4a19-966e-4f6d669950ea_jar]", :immediately
end
