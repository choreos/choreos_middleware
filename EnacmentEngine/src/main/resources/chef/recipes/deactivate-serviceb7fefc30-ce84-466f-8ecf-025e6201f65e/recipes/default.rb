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

#ruby_block "disable-serviceb7fefc30-ce84-466f-8ecf-025e6201f65e" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceb7fefc30-ce84-466f-8ecf-025e6201f65e::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['b7fefc30-ce84-466f-8ecf-025e6201f65e']['InstallationDir']}/service-b7fefc30-ce84-466f-8ecf-025e6201f65e.jar]", :immediately
#end

ruby_block "remove-serviceb7fefc30-ce84-466f-8ecf-025e6201f65e" do
  block do
  	node.run_list.remove("recipe[serviceb7fefc30-ce84-466f-8ecf-025e6201f65e::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb7fefc30-ce84-466f-8ecf-025e6201f65e::jar]") }
end

ruby_block "remove-deactivate-serviceb7fefc30-ce84-466f-8ecf-025e6201f65e" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb7fefc30-ce84-466f-8ecf-025e6201f65e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb7fefc30-ce84-466f-8ecf-025e6201f65e]") }
end
