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

#ruby_block "disable-service39225113-2688-439b-986d-167c28f60d66" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service39225113-2688-439b-986d-167c28f60d66::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['39225113-2688-439b-986d-167c28f60d66']['InstallationDir']}/service-39225113-2688-439b-986d-167c28f60d66.jar]", :immediately
#end

ruby_block "remove-service39225113-2688-439b-986d-167c28f60d66" do
  block do
  	node.run_list.remove("recipe[service39225113-2688-439b-986d-167c28f60d66::jar]")
  end
  only_if { node.run_list.include?("recipe[service39225113-2688-439b-986d-167c28f60d66::jar]") }
end

ruby_block "remove-deactivate-service39225113-2688-439b-986d-167c28f60d66" do
  block do
    node.run_list.remove("recipe[deactivate-service39225113-2688-439b-986d-167c28f60d66]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service39225113-2688-439b-986d-167c28f60d66]") }
end
