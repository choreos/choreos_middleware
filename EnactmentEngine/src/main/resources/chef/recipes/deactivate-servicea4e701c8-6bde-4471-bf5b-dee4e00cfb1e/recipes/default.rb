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

#ruby_block "disable-servicea4e701c8-6bde-4471-bf5b-dee4e00cfb1e" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicea4e701c8-6bde-4471-bf5b-dee4e00cfb1e::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a4e701c8-6bde-4471-bf5b-dee4e00cfb1e']['InstallationDir']}/service-a4e701c8-6bde-4471-bf5b-dee4e00cfb1e.jar]", :immediately
#end

ruby_block "remove-servicea4e701c8-6bde-4471-bf5b-dee4e00cfb1e" do
  block do
  	node.run_list.remove("recipe[servicea4e701c8-6bde-4471-bf5b-dee4e00cfb1e::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea4e701c8-6bde-4471-bf5b-dee4e00cfb1e::jar]") }
end

ruby_block "remove-deactivate-servicea4e701c8-6bde-4471-bf5b-dee4e00cfb1e" do
  block do
    node.run_list.remove("recipe[deactivate-servicea4e701c8-6bde-4471-bf5b-dee4e00cfb1e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea4e701c8-6bde-4471-bf5b-dee4e00cfb1e]") }
end
