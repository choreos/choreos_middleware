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

ruby_block "disable-service13a1dea8-af65-445f-8576-93ef680ff360" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service13a1dea8-af65-445f-8576-93ef680ff360::jar]") }
  notifies :stop, "service[service_13a1dea8-af65-445f-8576-93ef680ff360_jar]", :immediately
end

ruby_block "remove-service13a1dea8-af65-445f-8576-93ef680ff360" do
  block do
  	node.run_list.remove("recipe[service13a1dea8-af65-445f-8576-93ef680ff360::jar]")
  end
  only_if { node.run_list.include?("recipe[service13a1dea8-af65-445f-8576-93ef680ff360::jar]") }
end

ruby_block "remove-deactivate-service13a1dea8-af65-445f-8576-93ef680ff360" do
  block do
    node.run_list.remove("recipe[deactivate-service13a1dea8-af65-445f-8576-93ef680ff360]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service13a1dea8-af65-445f-8576-93ef680ff360]") }
end
