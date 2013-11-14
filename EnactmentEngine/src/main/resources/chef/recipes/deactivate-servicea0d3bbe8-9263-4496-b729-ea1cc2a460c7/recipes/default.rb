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

ruby_block "disable-servicea0d3bbe8-9263-4496-b729-ea1cc2a460c7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea0d3bbe8-9263-4496-b729-ea1cc2a460c7::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a0d3bbe8-9263-4496-b729-ea1cc2a460c7']['InstallationDir']}/service-a0d3bbe8-9263-4496-b729-ea1cc2a460c7.jar]", :immediately
end

ruby_block "remove-servicea0d3bbe8-9263-4496-b729-ea1cc2a460c7" do
  block do
  	node.run_list.remove("recipe[servicea0d3bbe8-9263-4496-b729-ea1cc2a460c7::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea0d3bbe8-9263-4496-b729-ea1cc2a460c7::jar]") }
end

ruby_block "remove-deactivate-servicea0d3bbe8-9263-4496-b729-ea1cc2a460c7" do
  block do
    node.run_list.remove("recipe[deactivate-servicea0d3bbe8-9263-4496-b729-ea1cc2a460c7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea0d3bbe8-9263-4496-b729-ea1cc2a460c7]") }
end
