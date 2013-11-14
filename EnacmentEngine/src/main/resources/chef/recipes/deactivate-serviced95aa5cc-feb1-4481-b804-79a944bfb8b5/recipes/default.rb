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

ruby_block "disable-serviced95aa5cc-feb1-4481-b804-79a944bfb8b5" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced95aa5cc-feb1-4481-b804-79a944bfb8b5::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['d95aa5cc-feb1-4481-b804-79a944bfb8b5']['InstallationDir']}/service-d95aa5cc-feb1-4481-b804-79a944bfb8b5.jar]", :immediately
end

ruby_block "remove-serviced95aa5cc-feb1-4481-b804-79a944bfb8b5" do
  block do
  	node.run_list.remove("recipe[serviced95aa5cc-feb1-4481-b804-79a944bfb8b5::jar]")
  end
  only_if { node.run_list.include?("recipe[serviced95aa5cc-feb1-4481-b804-79a944bfb8b5::jar]") }
end

ruby_block "remove-deactivate-serviced95aa5cc-feb1-4481-b804-79a944bfb8b5" do
  block do
    node.run_list.remove("recipe[deactivate-serviced95aa5cc-feb1-4481-b804-79a944bfb8b5]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced95aa5cc-feb1-4481-b804-79a944bfb8b5]") }
end
