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

#ruby_block "disable-service$NAME" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service$NAME::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar]", :immediately
#end

ruby_block "remove-service$NAME" do
  block do
  	node.run_list.remove("recipe[service$NAME::jar]")
  end
  only_if { node.run_list.include?("recipe[service$NAME::jar]") }
end

ruby_block "remove-deactivate-service$NAME" do
  block do
    node.run_list.remove("recipe[deactivate-service$NAME]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service$NAME]") }
end
