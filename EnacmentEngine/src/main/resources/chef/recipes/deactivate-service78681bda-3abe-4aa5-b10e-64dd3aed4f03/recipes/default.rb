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

#ruby_block "disable-service78681bda-3abe-4aa5-b10e-64dd3aed4f03" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service78681bda-3abe-4aa5-b10e-64dd3aed4f03::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['78681bda-3abe-4aa5-b10e-64dd3aed4f03']['InstallationDir']}/service-78681bda-3abe-4aa5-b10e-64dd3aed4f03.jar]", :immediately
#end

ruby_block "remove-service78681bda-3abe-4aa5-b10e-64dd3aed4f03" do
  block do
  	node.run_list.remove("recipe[service78681bda-3abe-4aa5-b10e-64dd3aed4f03::jar]")
  end
  only_if { node.run_list.include?("recipe[service78681bda-3abe-4aa5-b10e-64dd3aed4f03::jar]") }
end

ruby_block "remove-deactivate-service78681bda-3abe-4aa5-b10e-64dd3aed4f03" do
  block do
    node.run_list.remove("recipe[deactivate-service78681bda-3abe-4aa5-b10e-64dd3aed4f03]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service78681bda-3abe-4aa5-b10e-64dd3aed4f03]") }
end
