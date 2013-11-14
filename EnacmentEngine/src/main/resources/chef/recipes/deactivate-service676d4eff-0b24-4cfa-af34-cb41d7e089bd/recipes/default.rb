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

#ruby_block "disable-service676d4eff-0b24-4cfa-af34-cb41d7e089bd" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service676d4eff-0b24-4cfa-af34-cb41d7e089bd::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['676d4eff-0b24-4cfa-af34-cb41d7e089bd']['InstallationDir']}/service-676d4eff-0b24-4cfa-af34-cb41d7e089bd.jar]", :immediately
#end

ruby_block "remove-service676d4eff-0b24-4cfa-af34-cb41d7e089bd" do
  block do
  	node.run_list.remove("recipe[service676d4eff-0b24-4cfa-af34-cb41d7e089bd::jar]")
  end
  only_if { node.run_list.include?("recipe[service676d4eff-0b24-4cfa-af34-cb41d7e089bd::jar]") }
end

ruby_block "remove-deactivate-service676d4eff-0b24-4cfa-af34-cb41d7e089bd" do
  block do
    node.run_list.remove("recipe[deactivate-service676d4eff-0b24-4cfa-af34-cb41d7e089bd]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service676d4eff-0b24-4cfa-af34-cb41d7e089bd]") }
end
