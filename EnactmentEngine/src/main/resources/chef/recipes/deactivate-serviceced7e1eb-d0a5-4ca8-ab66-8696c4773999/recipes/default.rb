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

#ruby_block "disable-serviceced7e1eb-d0a5-4ca8-ab66-8696c4773999" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceced7e1eb-d0a5-4ca8-ab66-8696c4773999::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ced7e1eb-d0a5-4ca8-ab66-8696c4773999']['InstallationDir']}/service-ced7e1eb-d0a5-4ca8-ab66-8696c4773999.jar]", :immediately
#end

ruby_block "remove-serviceced7e1eb-d0a5-4ca8-ab66-8696c4773999" do
  block do
  	node.run_list.remove("recipe[serviceced7e1eb-d0a5-4ca8-ab66-8696c4773999::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceced7e1eb-d0a5-4ca8-ab66-8696c4773999::jar]") }
end

ruby_block "remove-deactivate-serviceced7e1eb-d0a5-4ca8-ab66-8696c4773999" do
  block do
    node.run_list.remove("recipe[deactivate-serviceced7e1eb-d0a5-4ca8-ab66-8696c4773999]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceced7e1eb-d0a5-4ca8-ab66-8696c4773999]") }
end
