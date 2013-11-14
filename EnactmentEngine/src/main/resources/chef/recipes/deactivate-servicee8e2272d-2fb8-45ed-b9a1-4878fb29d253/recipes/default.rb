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

#ruby_block "disable-servicee8e2272d-2fb8-45ed-b9a1-4878fb29d253" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicee8e2272d-2fb8-45ed-b9a1-4878fb29d253::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['e8e2272d-2fb8-45ed-b9a1-4878fb29d253']['InstallationDir']}/service-e8e2272d-2fb8-45ed-b9a1-4878fb29d253.jar]", :immediately
#end

ruby_block "remove-servicee8e2272d-2fb8-45ed-b9a1-4878fb29d253" do
  block do
  	node.run_list.remove("recipe[servicee8e2272d-2fb8-45ed-b9a1-4878fb29d253::jar]")
  end
  only_if { node.run_list.include?("recipe[servicee8e2272d-2fb8-45ed-b9a1-4878fb29d253::jar]") }
end

ruby_block "remove-deactivate-servicee8e2272d-2fb8-45ed-b9a1-4878fb29d253" do
  block do
    node.run_list.remove("recipe[deactivate-servicee8e2272d-2fb8-45ed-b9a1-4878fb29d253]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee8e2272d-2fb8-45ed-b9a1-4878fb29d253]") }
end
