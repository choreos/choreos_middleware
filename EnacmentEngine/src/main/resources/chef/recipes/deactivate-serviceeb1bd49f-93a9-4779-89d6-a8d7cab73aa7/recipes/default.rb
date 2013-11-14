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

ruby_block "disable-serviceeb1bd49f-93a9-4779-89d6-a8d7cab73aa7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceeb1bd49f-93a9-4779-89d6-a8d7cab73aa7::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['eb1bd49f-93a9-4779-89d6-a8d7cab73aa7']['InstallationDir']}/service-eb1bd49f-93a9-4779-89d6-a8d7cab73aa7.jar]", :immediately
end

ruby_block "remove-serviceeb1bd49f-93a9-4779-89d6-a8d7cab73aa7" do
  block do
  	node.run_list.remove("recipe[serviceeb1bd49f-93a9-4779-89d6-a8d7cab73aa7::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceeb1bd49f-93a9-4779-89d6-a8d7cab73aa7::jar]") }
end

ruby_block "remove-deactivate-serviceeb1bd49f-93a9-4779-89d6-a8d7cab73aa7" do
  block do
    node.run_list.remove("recipe[deactivate-serviceeb1bd49f-93a9-4779-89d6-a8d7cab73aa7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceeb1bd49f-93a9-4779-89d6-a8d7cab73aa7]") }
end
