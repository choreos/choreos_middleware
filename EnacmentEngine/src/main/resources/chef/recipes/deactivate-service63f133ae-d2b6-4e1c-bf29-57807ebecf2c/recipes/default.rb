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

#ruby_block "disable-service63f133ae-d2b6-4e1c-bf29-57807ebecf2c" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service63f133ae-d2b6-4e1c-bf29-57807ebecf2c::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['63f133ae-d2b6-4e1c-bf29-57807ebecf2c']['InstallationDir']}/service-63f133ae-d2b6-4e1c-bf29-57807ebecf2c.jar]", :immediately
#end

ruby_block "remove-service63f133ae-d2b6-4e1c-bf29-57807ebecf2c" do
  block do
  	node.run_list.remove("recipe[service63f133ae-d2b6-4e1c-bf29-57807ebecf2c::jar]")
  end
  only_if { node.run_list.include?("recipe[service63f133ae-d2b6-4e1c-bf29-57807ebecf2c::jar]") }
end

ruby_block "remove-deactivate-service63f133ae-d2b6-4e1c-bf29-57807ebecf2c" do
  block do
    node.run_list.remove("recipe[deactivate-service63f133ae-d2b6-4e1c-bf29-57807ebecf2c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service63f133ae-d2b6-4e1c-bf29-57807ebecf2c]") }
end
