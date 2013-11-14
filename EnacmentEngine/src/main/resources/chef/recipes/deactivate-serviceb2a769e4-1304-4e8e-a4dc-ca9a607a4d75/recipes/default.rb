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

ruby_block "remove-activate-serviceb2a769e4-1304-4e8e-a4dc-ca9a607a4d75" do
  block do
    node.run_list.remove("recipe[activate-serviceb2a769e4-1304-4e8e-a4dc-ca9a607a4d75]")
  end
  only_if { node.run_list.include?("recipe[activate-serviceb2a769e4-1304-4e8e-a4dc-ca9a607a4d75]") }
end


ruby_block "remove-deactivate-serviceb2a769e4-1304-4e8e-a4dc-ca9a607a4d75" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb2a769e4-1304-4e8e-a4dc-ca9a607a4d75]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb2a769e4-1304-4e8e-a4dc-ca9a607a4d75]") }
  notifies :stop, "service[service_b2a769e4-1304-4e8e-a4dc-ca9a607a4d75_jar]", :immediately
end
