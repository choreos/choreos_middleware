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

#ruby_block "disable-service220053fc-89d3-4988-8887-db079fa3a7ba" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service220053fc-89d3-4988-8887-db079fa3a7ba::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['220053fc-89d3-4988-8887-db079fa3a7ba']['InstallationDir']}/service-220053fc-89d3-4988-8887-db079fa3a7ba.jar]", :immediately
#end

ruby_block "remove-service220053fc-89d3-4988-8887-db079fa3a7ba" do
  block do
  	node.run_list.remove("recipe[service220053fc-89d3-4988-8887-db079fa3a7ba::jar]")
  end
  only_if { node.run_list.include?("recipe[service220053fc-89d3-4988-8887-db079fa3a7ba::jar]") }
end

ruby_block "remove-deactivate-service220053fc-89d3-4988-8887-db079fa3a7ba" do
  block do
    node.run_list.remove("recipe[deactivate-service220053fc-89d3-4988-8887-db079fa3a7ba]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service220053fc-89d3-4988-8887-db079fa3a7ba]") }
end
