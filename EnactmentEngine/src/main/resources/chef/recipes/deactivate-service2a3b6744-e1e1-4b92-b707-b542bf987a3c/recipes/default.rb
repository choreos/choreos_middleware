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

ruby_block "remove-service2a3b6744-e1e1-4b92-b707-b542bf987a3c" do
  block do
  	node.run_list.remove("recipe[service2a3b6744-e1e1-4b92-b707-b542bf987a3c::jar]")
  end
  only_if { node.run_list.include?("recipe[service2a3b6744-e1e1-4b92-b707-b542bf987a3c::jar]") }
end

ruby_block "remove-deactivate-service2a3b6744-e1e1-4b92-b707-b542bf987a3c" do
  block do
    node.run_list.remove("recipe[deactivate-service2a3b6744-e1e1-4b92-b707-b542bf987a3c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2a3b6744-e1e1-4b92-b707-b542bf987a3c]") }
  notifies :stop, "service[service_2a3b6744-e1e1-4b92-b707-b542bf987a3c_jar]", :immediately
end
