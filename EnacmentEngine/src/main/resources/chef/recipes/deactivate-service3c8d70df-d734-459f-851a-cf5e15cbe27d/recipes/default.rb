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

#ruby_block "disable-service3c8d70df-d734-459f-851a-cf5e15cbe27d" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service3c8d70df-d734-459f-851a-cf5e15cbe27d::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['3c8d70df-d734-459f-851a-cf5e15cbe27d']['InstallationDir']}/service-3c8d70df-d734-459f-851a-cf5e15cbe27d.jar]", :immediately
#end

ruby_block "remove-service3c8d70df-d734-459f-851a-cf5e15cbe27d" do
  block do
  	node.run_list.remove("recipe[service3c8d70df-d734-459f-851a-cf5e15cbe27d::jar]")
  end
  only_if { node.run_list.include?("recipe[service3c8d70df-d734-459f-851a-cf5e15cbe27d::jar]") }
end

ruby_block "remove-deactivate-service3c8d70df-d734-459f-851a-cf5e15cbe27d" do
  block do
    node.run_list.remove("recipe[deactivate-service3c8d70df-d734-459f-851a-cf5e15cbe27d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service3c8d70df-d734-459f-851a-cf5e15cbe27d]") }
end
