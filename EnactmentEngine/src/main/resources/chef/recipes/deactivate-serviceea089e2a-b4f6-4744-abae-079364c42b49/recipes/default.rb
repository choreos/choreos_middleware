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

ruby_block "remove-activate-serviceea089e2a-b4f6-4744-abae-079364c42b49" do
  block do
    node.run_list.remove("recipe[activate-serviceea089e2a-b4f6-4744-abae-079364c42b49]")
  end
  only_if { node.run_list.include?("recipe[activate-serviceea089e2a-b4f6-4744-abae-079364c42b49]") }
end


ruby_block "remove-deactivate-serviceea089e2a-b4f6-4744-abae-079364c42b49" do
  block do
    node.run_list.remove("recipe[deactivate-serviceea089e2a-b4f6-4744-abae-079364c42b49]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceea089e2a-b4f6-4744-abae-079364c42b49]") }
  notifies :stop, "service[service_ea089e2a-b4f6-4744-abae-079364c42b49_jar]", :immediately
end
