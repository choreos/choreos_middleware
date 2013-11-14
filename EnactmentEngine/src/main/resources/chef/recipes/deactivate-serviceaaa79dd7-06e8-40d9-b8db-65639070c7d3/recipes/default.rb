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

ruby_block "remove-serviceaaa79dd7-06e8-40d9-b8db-65639070c7d3" do
  block do
  	node.run_list.remove("recipe[serviceaaa79dd7-06e8-40d9-b8db-65639070c7d3::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceaaa79dd7-06e8-40d9-b8db-65639070c7d3::jar]") }
end

ruby_block "remove-deactivate-serviceaaa79dd7-06e8-40d9-b8db-65639070c7d3" do
  block do
    node.run_list.remove("recipe[deactivate-serviceaaa79dd7-06e8-40d9-b8db-65639070c7d3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceaaa79dd7-06e8-40d9-b8db-65639070c7d3]") }
  notifies :stop, "service[service_aaa79dd7-06e8-40d9-b8db-65639070c7d3_jar]", :immediately
end
