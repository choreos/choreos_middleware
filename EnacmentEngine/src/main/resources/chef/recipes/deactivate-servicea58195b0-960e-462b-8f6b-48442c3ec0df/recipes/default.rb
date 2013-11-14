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

ruby_block "disable-servicea58195b0-960e-462b-8f6b-48442c3ec0df" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea58195b0-960e-462b-8f6b-48442c3ec0df::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['InstallationDir']}/service-a58195b0-960e-462b-8f6b-48442c3ec0df.jar]", :immediately
end

ruby_block "remove-servicea58195b0-960e-462b-8f6b-48442c3ec0df" do
  block do
  	node.run_list.remove("recipe[servicea58195b0-960e-462b-8f6b-48442c3ec0df::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea58195b0-960e-462b-8f6b-48442c3ec0df::jar]") }
end

ruby_block "remove-deactivate-servicea58195b0-960e-462b-8f6b-48442c3ec0df" do
  block do
    node.run_list.remove("recipe[deactivate-servicea58195b0-960e-462b-8f6b-48442c3ec0df]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea58195b0-960e-462b-8f6b-48442c3ec0df]") }
end
