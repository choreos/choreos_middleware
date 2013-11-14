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

#ruby_block "disable-serviceac9a6319-a9c2-4a1a-8836-33fe854481b4" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceac9a6319-a9c2-4a1a-8836-33fe854481b4::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ac9a6319-a9c2-4a1a-8836-33fe854481b4']['InstallationDir']}/service-ac9a6319-a9c2-4a1a-8836-33fe854481b4.jar]", :immediately
#end

ruby_block "remove-serviceac9a6319-a9c2-4a1a-8836-33fe854481b4" do
  block do
  	node.run_list.remove("recipe[serviceac9a6319-a9c2-4a1a-8836-33fe854481b4::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceac9a6319-a9c2-4a1a-8836-33fe854481b4::jar]") }
end

ruby_block "remove-deactivate-serviceac9a6319-a9c2-4a1a-8836-33fe854481b4" do
  block do
    node.run_list.remove("recipe[deactivate-serviceac9a6319-a9c2-4a1a-8836-33fe854481b4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceac9a6319-a9c2-4a1a-8836-33fe854481b4]") }
end
