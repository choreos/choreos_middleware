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

ruby_block "remove-activate-servicec94752f5-1e10-4d50-9bcd-050d6ec2ce49" do
  block do
    node.run_list.remove("recipe[activate-servicec94752f5-1e10-4d50-9bcd-050d6ec2ce49]")
  end
  only_if { node.run_list.include?("recipe[activate-servicec94752f5-1e10-4d50-9bcd-050d6ec2ce49]") }
end


ruby_block "remove-deactivate-servicec94752f5-1e10-4d50-9bcd-050d6ec2ce49" do
  block do
    node.run_list.remove("recipe[deactivate-servicec94752f5-1e10-4d50-9bcd-050d6ec2ce49]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec94752f5-1e10-4d50-9bcd-050d6ec2ce49]") }
  notifies :stop, "service[service_c94752f5-1e10-4d50-9bcd-050d6ec2ce49_jar]", :immediately
end
