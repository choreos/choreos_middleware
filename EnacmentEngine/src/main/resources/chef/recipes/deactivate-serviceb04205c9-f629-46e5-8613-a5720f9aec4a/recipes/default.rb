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

ruby_block "disable-serviceb04205c9-f629-46e5-8613-a5720f9aec4a" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb04205c9-f629-46e5-8613-a5720f9aec4a::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['InstallationDir']}/service-b04205c9-f629-46e5-8613-a5720f9aec4a.jar]", :immediately
end

ruby_block "remove-serviceb04205c9-f629-46e5-8613-a5720f9aec4a" do
  block do
  	node.run_list.remove("recipe[serviceb04205c9-f629-46e5-8613-a5720f9aec4a::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb04205c9-f629-46e5-8613-a5720f9aec4a::jar]") }
end

ruby_block "remove-deactivate-serviceb04205c9-f629-46e5-8613-a5720f9aec4a" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb04205c9-f629-46e5-8613-a5720f9aec4a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb04205c9-f629-46e5-8613-a5720f9aec4a]") }
end
