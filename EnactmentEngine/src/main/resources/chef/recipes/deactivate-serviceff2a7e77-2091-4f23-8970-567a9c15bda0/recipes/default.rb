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

#ruby_block "disable-serviceff2a7e77-2091-4f23-8970-567a9c15bda0" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceff2a7e77-2091-4f23-8970-567a9c15bda0::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ff2a7e77-2091-4f23-8970-567a9c15bda0']['InstallationDir']}/service-ff2a7e77-2091-4f23-8970-567a9c15bda0.jar]", :immediately
#end

ruby_block "remove-serviceff2a7e77-2091-4f23-8970-567a9c15bda0" do
  block do
  	node.run_list.remove("recipe[serviceff2a7e77-2091-4f23-8970-567a9c15bda0::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceff2a7e77-2091-4f23-8970-567a9c15bda0::jar]") }
end

ruby_block "remove-deactivate-serviceff2a7e77-2091-4f23-8970-567a9c15bda0" do
  block do
    node.run_list.remove("recipe[deactivate-serviceff2a7e77-2091-4f23-8970-567a9c15bda0]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceff2a7e77-2091-4f23-8970-567a9c15bda0]") }
end
